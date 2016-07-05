/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingCollection;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainsEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.metadata.ApplicationEditionConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author aduan
 */
public class TenantSubscriptionUtil
{
	public static class RestClient
	{
		public RestClient()
		{
		}

		public String get(String url)
		{
			if (StringUtil.isEmpty(url)) {
				return null;
			}

			ClientConfig cc = new DefaultClientConfig();
			Client client = Client.create(cc);
			char[] authToken = RegistrationManager.getInstance().getAuthorizationToken();
			String auth = String.copyValueOf(authToken);
			logger.debug("Retrieved authorization token from registration manager: " + auth);
			Builder builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
					.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			return builder.get(String.class);
		}
	}

	private static Logger logger = LogManager.getLogger(TenantSubscriptionUtil.class);
	private static final String SUBSCRIBED_SERVICE_NAME_APM = "APM";
	private static final String SUBSCRIBED_SERVICE_NAME_ITA = "ITAnalytics";
	private static final String SUBSCRIBED_SERVICE_NAME_LA = "LogAnalytics";
	private static final String SUBSCRIBED_SERVICE_NAME_SA = "SecurityAnalytics";
	private static String SERVICE_PROVIDER_NAME_APM = "ApmUI";
	private static String SERVICE_PROVIDER_NAME_ITA = "emcitas-ui-apps";
	private static String SERVICE_PROVIDER_NAME_TA = "TargetAnalytics";
	private static String SERVICE_PROVIDER_NAME_LA = "LoganService";
	private static String SERVICE_PROVIDER_NAME_SA = "SecurityAnalyticsUI";
	private static final String PARAM_NAME_DASHBOARD_INELIGIBLE = "DASHBOARD_INELIGIBLE";

	private static Map<String, String> providerToServiceMap = new Hashtable<String, String>();

	static {
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_LA, SUBSCRIBED_SERVICE_NAME_LA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_TA, SUBSCRIBED_SERVICE_NAME_ITA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_ITA, SUBSCRIBED_SERVICE_NAME_ITA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_APM, SUBSCRIBED_SERVICE_NAME_APM);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_SA, SUBSCRIBED_SERVICE_NAME_SA);
	}

	public static List<String> getProviderNameFromServiceName(String providerName)
	{
		if (providerName == null) {
			return null;
		}
		switch (providerName) {
			case SUBSCRIBED_SERVICE_NAME_LA:
				return Arrays.asList(SERVICE_PROVIDER_NAME_LA);
			case SUBSCRIBED_SERVICE_NAME_ITA:
				return Arrays.asList(SERVICE_PROVIDER_NAME_TA, SERVICE_PROVIDER_NAME_ITA);
			case SUBSCRIBED_SERVICE_NAME_APM:
				return Arrays.asList(SERVICE_PROVIDER_NAME_APM);
			case SUBSCRIBED_SERVICE_NAME_SA:
				return Arrays.asList(SERVICE_PROVIDER_NAME_SA);
		}
		return null;
	}

	public static List<Category> getTenantSubscribedCategories(String tenant, boolean includeDashboardIneligible)
			throws EMAnalyticsFwkException
			{
		List<Category> resultList = new ArrayList<Category>();
		if (tenant == null) {
			return resultList;
		}
		CategoryManager catMan = CategoryManager.getInstance();
		List<Category> catList = catMan.getAllCategories();
		List<String> subscribedServices = TenantSubscriptionUtil.getTenantSubscribedServices(tenant);
		if (catList != null && catList.size() > 0) {
			if (subscribedServices != null && subscribedServices.size() > 0) {
				for (Category cat : catList) {
					//EMCPDF-997 If a widget group has special parameter DASHBOARD_INELIGIBLE=true,
					//we do NOT show all widgets of this group inside widget selector
					if (subscribedServices.contains(providerToServiceMap.get(cat.getProviderName()))
							&& !TenantSubscriptionUtil.isCategoryHiddenInWidgetSelector(cat, includeDashboardIneligible)) {
						resultList.add(cat);
					}
				}
			}
		}

		return resultList;
			}

	public static List<String> getTenantSubscribedServiceProviders(String tenant) throws IOException
	{
		List<String> subscribedApps = TenantSubscriptionUtil.getTenantSubscribedServices(tenant);
		if (subscribedApps == null) {
			logger.debug("Get empty(null) subscribed APPs");
			return null;
		}
		List<String> providers = new ArrayList<String>();
		for (String app : subscribedApps) {
			List<String> providerList = TenantSubscriptionUtil.getProviderNameFromServiceName(app);
			if (providerList != null) {
				providers.addAll(providerList);
			}
		}
		logger.debug("Get subscribed provider names: {} for tenant {}",
				StringUtil.arrayToCommaDelimitedString(providers.toArray()), tenant);
		return providers;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getTenantSubscribedServices(String tenant)
	{
		// try to load from subscribeCache
		CacheManager cm = CacheManager.getInstance();
		Tenant cacheTenant = new Tenant(tenant);
		List<String> cachedApps;
		try {
			cachedApps = (List<String>) cm.getCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIBE_CACHE,
					CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS);
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
		if (cachedApps != null) {
			logger.debug(
					"retrieved subscribed apps for tenant {} from subscribe cache: "
							+ StringUtil.arrayToCommaDelimitedString(cachedApps.toArray()), tenant);
			return cachedApps;
		}

		Link domainLink = RegistryLookupUtil.getServiceInternalLink("EntityNaming", "1.0+", "collection/domains", tenant);
		if (domainLink == null || StringUtil.isEmpty(domainLink.getHref())) {
			logger.warn("Checking tenant (" + tenant
					+ ") subscriptions: null/empty entity naming service collection/domains is retrieved.");
			return null;
		}
		logger.info("Checking tenant (" + tenant + ") subscriptions. The entity naming href is " + domainLink.getHref());
		String domainHref = domainLink.getHref();
		RestClient rc = new RestClient();
		String domainsResponse = rc.get(domainHref);
		logger.info("Checking tenant (" + tenant + ") subscriptions. Domains list response is " + domainsResponse);
		ObjectMapper mapper = JSONUtil.buildNormalMapper();
		try {
			DomainsEntity de = JSONUtil.fromJson(mapper, domainsResponse, DomainsEntity.class);//ju.fromJson(domainsResponse, DomainsEntity.class);
			if (de == null || de.getItems() == null || de.getItems().size() <= 0) {
				logger.warn("Checking tenant (" + tenant
						+ ") subscriptions: null/empty domains entity or domains item retrieved.");
				return null;
			}
			String tenantAppUrl = null;
			for (DomainEntity domain : de.getItems()) {
				if ("TenantApplicationMapping".equals(domain.getDomainName())) {
					tenantAppUrl = domain.getCanonicalUrl();
					break;
				}
			}
			if (tenantAppUrl == null || "".equals(tenantAppUrl)) {
				logger.warn("Checking tenant (" + tenant + ") subscriptions. 'TenantApplicationMapping' not found");
				return null;
			}
			String appMappingUrl = tenantAppUrl + "/lookups?opcTenantId=" + tenant;
			logger.info("Checking tenant (" + tenant + ") subscriptions. tenant application mapping lookup URL is "
					+ appMappingUrl);
			String appMappingJson = rc.get(appMappingUrl);
			logger.info("Checking tenant (" + tenant + ") subscriptions. application lookup response json is " + appMappingJson);
			if (appMappingJson == null || "".equals(appMappingJson)) {
				return null;
			}
			AppMappingCollection amec = JSONUtil.fromJson(mapper, appMappingJson, AppMappingCollection.class);//.fromJsonToList(appMappingJson, AppMappingCollection.class);
			if (amec == null || amec.getItems() == null || amec.getItems().isEmpty()) {
				logger.error("Checking tenant (" + tenant + ") subscriptions. Empty application mapping items are retrieved");
				return null;
			}
			AppMappingEntity ame = null;
			for (AppMappingEntity entity : amec.getItems()) {
				if (entity.getValues() == null) {
					continue;
				}
				for (AppMappingEntity.AppMappingValue amv : entity.getValues()) {
					if (tenant.equals(amv.getOpcTenantId())) {
						ame = entity;
						break;
					}

				}
			}
			if (ame == null || ame.getValues() == null || ame.getValues().isEmpty()) {
				logger.error("Checking tenant (" + tenant
						+ ") subscriptions. Failed to get an application mapping for the specified tenant");
				return null;
			}
			String apps = null;
			for (AppMappingEntity.AppMappingValue amv : ame.getValues()) {
				if (tenant.equals(amv.getOpcTenantId())) {
					apps = amv.getApplicationNames();
					break;
				}
			}
			logger.info("Checking tenant (" + tenant + ") subscriptions. applications for the tenant are " + apps);
			if (apps == null || "".equals(apps)) {
				return null;
			}
			List<String> origAppsList = Arrays.asList(apps
					.split(ApplicationEditionConverter.APPLICATION_EDITION_ELEMENT_DELIMINATOR));
			//put into cache
			cm.putCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIBE_CACHE, CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS,
					origAppsList);
			logger.debug(
					"Store subscribed apps for tenant {} to subscribe cache: "
							+ StringUtil.arrayToCommaDelimitedString(origAppsList.toArray()), tenant);
			return origAppsList;

		}
		catch (IOException e) {
			logger.error(e);
			return null;
		}
	}

	private static boolean isCategoryHiddenInWidgetSelector(Category category, boolean includeDashboardIneligible)
	{
		boolean hiddenInWidgetSelector = false;
		if (!includeDashboardIneligible) {
			List<Parameter> params = category.getParameters();
			if (params != null && params.size() > 0) {
				for (Parameter param : params) {
					if (PARAM_NAME_DASHBOARD_INELIGIBLE.equals(param.getName()) && "1".equals(param.getValue())) {
						hiddenInWidgetSelector = true;
						break;
					}
				}
			}
		}

		return hiddenInWidgetSelector;
	}
}

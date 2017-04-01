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

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.subscription2.*;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * @author aduan
 */
public class TenantSubscriptionUtil
{
	private static Logger LOGGER = LogManager.getLogger(TenantSubscriptionUtil.class);
	private static final String SUBSCRIBED_SERVICE_NAME_APM = "APM";
	private static final String SUBSCRIBED_SERVICE_NAME_ITA = "ITAnalytics";
	private static final String SUBSCRIBED_SERVICE_NAME_LA = "LogAnalytics";
	private static final String SUBSCRIBED_SERVICE_NAME_SA = "SecurityAnalytics";
	private static final String SUBSCRIBED_SERVICE_NAME_OCS = "Orchestration";
	private static String SERVICE_PROVIDER_NAME_APM = "ApmUI";
	private static String SERVICE_PROVIDER_NAME_ITA = "emcitas-ui-apps";
	private static String SERVICE_PROVIDER_NAME_TA = "TargetAnalytics";
	private static String SERVICE_PROVIDER_NAME_LA = "LogAnalyticsUI";
	private static String SERVICE_PROVIDER_NAME_SA = "SecurityAnalyticsUI";
	private static String SERVICE_PROVIDER_NAME_OCS = "CosUIService";
	private static final String PARAM_NAME_DASHBOARD_INELIGIBLE = "DASHBOARD_INELIGIBLE";

	private static Map<String, String> providerToServiceMap = new HashMap<>();

	static {
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_LA, SUBSCRIBED_SERVICE_NAME_LA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_TA, SUBSCRIBED_SERVICE_NAME_ITA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_ITA, SUBSCRIBED_SERVICE_NAME_ITA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_APM, SUBSCRIBED_SERVICE_NAME_APM);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_SA, SUBSCRIBED_SERVICE_NAME_SA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_OCS, SUBSCRIBED_SERVICE_NAME_OCS);
	}

	public static List<String> getProviderNameFromServiceName(String providerName)
	{
		if (providerName == null) {
			return Collections.emptyList();
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
			case SUBSCRIBED_SERVICE_NAME_OCS:
				return Arrays.asList(SERVICE_PROVIDER_NAME_OCS);
			default:
				break;
		}
		return Collections.emptyList();
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
		List<String> subscribedServices = TenantSubscriptionUtil.getTenantSubscribedServices(tenant,new TenantSubscriptionInfo());
		if (catList != null && !catList.isEmpty() && subscribedServices != null && !subscribedServices.isEmpty()) {
			for (Category cat : catList) {
				//EMCPDF-997 If a widget group has special parameter DASHBOARD_INELIGIBLE=true,
				//we do NOT show all widgets of this group inside widget selector
				if (subscribedServices.contains(providerToServiceMap.get(cat.getProviderName()))
						&& !TenantSubscriptionUtil.isCategoryHiddenInWidgetSelector(cat, includeDashboardIneligible)) {
					resultList.add(cat);
				}
			}
		}

		return resultList;
			}

	public static List<String> getTenantSubscribedServiceProviders(String tenant) throws IOException
	{
		TenantSubscriptionInfo tenantSubscriptionInfo = new TenantSubscriptionInfo();
		List<String> subscribedApps = TenantSubscriptionUtil.getTenantSubscribedServices(tenant,tenantSubscriptionInfo);
		LOGGER.info("Retrieved subscribedApps is {}",subscribedApps);
		if (subscribedApps == null) {
			LOGGER.debug("Get empty(null) subscribed APPs");
			return Collections.emptyList();
		}
		List<String> providers = new ArrayList<String>();
		for (String app : subscribedApps) {
			List<String> providerList = TenantSubscriptionUtil.getProviderNameFromServiceName(app);
			if (providerList != null) {
				providers.addAll(providerList);
			}
		}
		//handle v2/v3
		if(!checkLicVersion(tenantSubscriptionInfo) && !providers.isEmpty() && !providers.contains(SERVICE_PROVIDER_NAME_TA)){
			providers.add(SERVICE_PROVIDER_NAME_TA);
		}
		LOGGER.debug("Get subscribed provider names: {} for tenant {}",
				StringUtil.arrayToCommaDelimitedString(providers.toArray()), tenant);
		return providers;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getTenantSubscribedServices(String tenant,TenantSubscriptionInfo tenantSubscriptionInfo)
	{
		// try to load from subscribeCache
		/*CacheManager cm = CacheManager.getInstance();
		Tenant cacheTenant = new Tenant(tenant);
		List<String> cachedApps;
		try {
			cachedApps = (List<String>) cm.getCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIBED_SERVICE_CACHE,
					CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS);
		}
		catch (Exception e) {
			LOGGER.error(e);
			return Collections.emptyList();
		}
		if (cachedApps != null) {
			LOGGER.debug(
					"retrieved subscribed apps for tenant {} from subscribe cache: "
							+ StringUtil.arrayToCommaDelimitedString(cachedApps.toArray()), tenant);
			return cachedApps;
		}*/

		Link domainLink = RegistryLookupUtil.getServiceInternalLink("TenantService", "1.0+", "collection/tenants", tenant);
		if (domainLink == null || StringUtil.isEmpty(domainLink.getHref())) {
			LOGGER.warn("Checking serviceRequest for (" + tenant
					+ ") subscriptions: null/empty entity naming service collection/domains is retrieved.");
			return Collections.emptyList();
		}
		LOGGER.info("Checking serviceRequest for (" + tenant + ") subscriptions. The entity naming href is " + domainLink.getHref());
		String domainHref = domainLink.getHref() + "/" + tenant + "/serviceRequest";
		RestClient rc = new RestClient();
		String domainsResponse = rc.get(domainHref);
		LOGGER.info("Checking serviceRequest api for (" + tenant + ") subscriptions. subscribapps response is " + domainsResponse);
		ObjectMapper mapper = JSONUtil.buildNormalMapper();
		try {
			List<ServiceRequestCollection> src = JSONUtil.fromJsonToList(domainsResponse, ServiceRequestCollection.class);
			if(src == null || src.isEmpty()){
				LOGGER.error("Checking serviceRequest for tenant (" + tenant + ") subscriptions. Empty application mapping items are retrieved");
				return Collections.emptyList();
			}
			List<SubscriptionApps> subAppsList = new ArrayList<SubscriptionApps>();
			for(ServiceRequestCollection s : src){
				SubscriptionApps subscriptionApps = new SubscriptionApps();
				if(s.getOrderComponents()!= null){
					subscriptionApps.setTrial(s.isTrial());
					subscriptionApps.setServiceType(s.getServiceType());
					OrderComponents orderComponents = s.getOrderComponents();
					if(orderComponents.getServiceComponent()!=null && orderComponents.getServiceComponent().getComponent()!=null){
						for(Component com : orderComponents.getServiceComponent().getComponent()){
							EditionComponent editionComponent = new EditionComponent();
							editionComponent.setComponentId(orderComponents.getServiceComponent().getComponent_id());
							if(com !=null && com.getComponent_parameter()!=null && !com.getComponent_parameter().isEmpty()){
								for(ComponentParameter componentParameter : com.getComponent_parameter()){
									if("APPLICATION_EDITION".equals(componentParameter.getKey())){
										editionComponent.setEdition(componentParameter.getValue());
										subscriptionApps.addEditionComponent(editionComponent);
										LOGGER.info("EditionComponent with id {} and edition {} is added",editionComponent.getComponentId(),editionComponent.getEdition());
									}
								}
							}
						}
					}
					subAppsList.add(subscriptionApps);
				}
			}
			tenantSubscriptionInfo.setSubscriptionAppsList(subAppsList);
			List<String> subscribeAppsList= SubsriptionApps2Util.getSubscribedAppsList(tenantSubscriptionInfo);
			LOGGER.info("After mapping Subscribed App list is {}",subscribeAppsList);
			if(subscribeAppsList == null ){
				LOGGER.error("After Mapping action,Empty subscription list found!");
				return Collections.emptyList();
			}
			/*cm.putCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIBED_SERVICE_CACHE, CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS,
					origAppsList);*/
//                    cm.getCache(CacheConstants.CACHES_SUBSCRIBED_SERVICE_CACHE).put(cacheKey,subscribeAppsList);
			return subscribeAppsList;

		}
		catch (IOException e) {
			LOGGER.error(e);
			return Collections.emptyList();
		}
	}

	private static boolean isCategoryHiddenInWidgetSelector(Category category, boolean includeDashboardIneligible)
	{
		boolean hiddenInWidgetSelector = false;
		if (!includeDashboardIneligible) {
			List<Parameter> params = category.getParameters();
			if (params != null && !params.isEmpty()) {
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

	/**
	 * this method is for check subscrib apps license version
	 * if version is V1 return true;
	 * if version is V2/V3 return false;
	 * @param tenantSubscriptionInfo
	 * @return
	 */
	private static boolean checkLicVersion(TenantSubscriptionInfo tenantSubscriptionInfo){
		if(tenantSubscriptionInfo!=null && tenantSubscriptionInfo.getAppsInfoList()!=null){
			for(AppsInfo appsInfo : tenantSubscriptionInfo.getAppsInfoList()){
				if(SubsriptionApps2Util.V2_TENANT.equals(appsInfo.getLicVersion()) ||
						SubsriptionApps2Util.V3_TENANT.equals(appsInfo.getLicVersion())){
					//V2/V3
					return false;
				}
			}
		}
		//V1
		return true;

	}

	private TenantSubscriptionUtil()
	{
	}
}

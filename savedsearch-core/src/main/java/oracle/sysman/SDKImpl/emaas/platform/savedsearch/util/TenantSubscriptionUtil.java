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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.subscription2.*;
import oracle.sysman.emaas.platform.emcpdf.cache.api.ICacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.exception.ExecutionException;
import oracle.sysman.emaas.platform.emcpdf.cache.support.CacheManagers;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.DefaultKeyGenerator;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Keys;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Tenant;
import oracle.sysman.emaas.platform.emcpdf.cache.util.CacheConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		//v1:true, v2/v3:false
		boolean isV1Tenant = false;
		List<Category> catList = catMan.getAllCategories();
		LOGGER.info("Cat list is {}", catList);
		TenantSubscriptionInfo tenantSubscriptionInfo = new TenantSubscriptionInfo();
		List<String> subscribedServices = TenantSubscriptionUtil.getTenantSubscribedServices(tenant,tenantSubscriptionInfo);
		isV1Tenant = checkTenantVersion(tenantSubscriptionInfo);
		LOGGER.info("Tenant version check: Is tenant v1 tenant? {}", isV1Tenant);
		if (catList != null && !catList.isEmpty() && subscribedServices != null && !subscribedServices.isEmpty()) {
			for (Category cat : catList) {
				//EMCPDF-997 If a widget group has special parameter DASHBOARD_INELIGIBLE=true,
				//we do NOT show all widgets of this group inside widget selector
				if (subscribedServices.contains(providerToServiceMap.get(cat.getProviderName()))
						&& !TenantSubscriptionUtil.isCategoryHiddenInWidgetSelector(cat, includeDashboardIneligible)) {
					resultList.add(cat);
				}
				//only current cat is UDE cat, and for v2/v3/v4 tenant, and result list did not contains it yet, then we add.
				if("Data Explorer".equals(cat.getName()) && !isV1Tenant && !resultList.contains(cat)){
					resultList.add(cat);
					LOGGER.info("Adding UDE widget group info into result for v2/v3/v4 tenant");
				}
			}
		}

		return resultList;
			}

	/**
	 * if v1 tenant, return true, v2/v3 return false;
	 * @param tenantSubscriptionInfo
	 * @return
	 */
	private static boolean checkTenantVersion(TenantSubscriptionInfo tenantSubscriptionInfo){
		if(tenantSubscriptionInfo.getAppsInfoList()!=null && !tenantSubscriptionInfo.getAppsInfoList().isEmpty()){
			for(AppsInfo appsInfo : tenantSubscriptionInfo.getAppsInfoList()){
				if(SubsriptionApps2Util.V2_TENANT.equals(appsInfo.getLicVersion()) ||
                        SubsriptionApps2Util.V3_TENANT.equals(appsInfo.getLicVersion()) ||
						SubsriptionApps2Util.V4_TENANT.equals(appsInfo.getLicVersion())){
					LOGGER.info("Check tenant version is V2/V3/v4 tenant.");
					return false;
				}
			}
		}
		LOGGER.info("Check tenant version is V1 tenant.");
		//v1
		return true;
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
		//handle v2/v3/v4
		if(!checkLicVersion(tenantSubscriptionInfo) && !subscribedApps.isEmpty() && !providers.contains(SERVICE_PROVIDER_NAME_TA)){
			LOGGER.info("Adding TargetAnalytics into providers...");
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
		final ICacheManager cm= CacheManagers.getInstance().build();
		Tenant cacheTenant = new Tenant(tenant);
		final Object cacheKey = DefaultKeyGenerator.getInstance().generate(cacheTenant,new Keys(CacheConstants.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS));
		CachedTenantSubcriptionInfo cachedTenantSubcriptionInfo = null;
		List<String> cachedApps;
		try {
			LOGGER.info("Trying to retrieve tenant subscription info from cache....");
			cachedTenantSubcriptionInfo = (CachedTenantSubcriptionInfo) cm.getCache(CacheConstants.CACHES_SUBSCRIBED_SERVICE_CACHE).get(cacheKey);

			if (cachedTenantSubcriptionInfo != null) {
				cachedApps = cachedTenantSubcriptionInfo.getSubscribedAppsList();
				TenantSubscriptionInfo tenantSubscriptionInfo1 = cachedTenantSubcriptionInfo.getTenantSubscriptionInfo();
				LOGGER.info("retrieved tenantSubscriptionInfo for tenant {} from cache,data is {}", tenant, tenantSubscriptionInfo1);
				LOGGER.info("retrieved subscribed apps for tenant {} from cache,data is {}", tenant, cachedApps);
				if (cachedApps != null && tenantSubscriptionInfo1 != null) {
					copyTenantSubscriptionInfo(tenantSubscriptionInfo1, tenantSubscriptionInfo);
					return cachedApps;
				}
			}
		}catch (ExecutionException e) {
			//if exception occurred, log an error info then retrieve data from API
			LOGGER.error("ExecutionException occurred when using cache...", e);
		}catch (Exception e) {
			//if exception occurred, log an error info then retrieve data from API
			LOGGER.error("Exception occurred when using cache...", e);
		}

		VersionedLink domainLink = RegistryLookupUtil.getServiceInternalLinkHttp("TenantService", "1.0+", "collection/tenants", tenant);
		if (domainLink == null || StringUtil.isEmpty(domainLink.getHref())) {
			LOGGER.warn("Checking serviceRequest for (" + tenant
					+ ") subscriptions: null/empty entity naming service collection/domains is retrieved.");
			return Collections.emptyList();
		}
		LOGGER.info("Checking serviceRequest for (" + tenant + ") subscriptions. The entity naming href is " + domainLink.getHref());
		String domainHref = domainLink.getHref() + "/" + tenant + "/serviceRequest";
		RestClient rc = new RestClient();
		String domainsResponse = rc.get(domainHref,domainLink.getAuthToken());
		LOGGER.debug("Checking serviceRequest api for (" + tenant + ") subscriptions. subscribapps response is " + domainsResponse);
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
			LOGGER.info("Put subscribe apps into cache,{},{}", subscribeAppsList, tenantSubscriptionInfo);
			cm.getCache(CacheConstants.CACHES_SUBSCRIBED_SERVICE_CACHE).put(cacheKey,new CachedTenantSubcriptionInfo(subscribeAppsList, tenantSubscriptionInfo));
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
	 * if version is V2/V3/v4 return false;
	 * @param tenantSubscriptionInfo
	 * @return
	 */
	private static boolean checkLicVersion(TenantSubscriptionInfo tenantSubscriptionInfo){
		if(tenantSubscriptionInfo!=null && tenantSubscriptionInfo.getAppsInfoList()!=null){
			for(AppsInfo appsInfo : tenantSubscriptionInfo.getAppsInfoList()){
				if(SubsriptionApps2Util.V2_TENANT.equals(appsInfo.getLicVersion()) ||
						SubsriptionApps2Util.V3_TENANT.equals(appsInfo.getLicVersion()) ||
						SubsriptionApps2Util.V4_TENANT.equals(appsInfo.getLicVersion())){
					//V2/V2/v3
					return false;
				}
			}
		}
		//V1
		return true;

	}

    private static void copyTenantSubscriptionInfo(TenantSubscriptionInfo from, TenantSubscriptionInfo to){
        if(from == null || to == null){
            LOGGER.error("Cannot copy value into or from null object!");
            return;
        }
        List<AppsInfo> toAppsInfoList  = new ArrayList<AppsInfo>();
        toAppsInfoList.addAll(from.getAppsInfoList());
        to.setAppsInfoList(toAppsInfoList);
    }

	private TenantSubscriptionUtil()
	{
	}
}

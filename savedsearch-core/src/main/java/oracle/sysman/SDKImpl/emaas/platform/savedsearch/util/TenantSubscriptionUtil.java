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
import oracle.sysman.emaas.platform.emcpdf.tenant.subscription2.*;
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
	private static final String SUBSCRIBED_SERVICE_NAME_MONITORING = "Monitoring";
	private static String SERVICE_PROVIDER_NAME_APM = "ApmUI";
	private static String SERVICE_PROVIDER_NAME_ITA = "emcitas-ui-apps";
	private static String SERVICE_PROVIDER_NAME_TA = "TargetAnalytics";
	private static String SERVICE_PROVIDER_NAME_LA = "LogAnalyticsUI";
	private static String SERVICE_PROVIDER_NAME_SA = "SecurityAnalyticsUI";
	private static String SERVICE_PROVIDER_NAME_OCS = "CosUIService";
	private static String SERVICE_PROVIDER_NAME_MONITORING = "MonitoringServiceUI";
	private static final String PARAM_NAME_DASHBOARD_INELIGIBLE = "DASHBOARD_INELIGIBLE";

	private static Map<String, String> providerToServiceMap = new HashMap<>();

	static {
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_LA, SUBSCRIBED_SERVICE_NAME_LA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_TA, SUBSCRIBED_SERVICE_NAME_ITA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_ITA, SUBSCRIBED_SERVICE_NAME_ITA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_APM, SUBSCRIBED_SERVICE_NAME_APM);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_SA, SUBSCRIBED_SERVICE_NAME_SA);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_OCS, SUBSCRIBED_SERVICE_NAME_OCS);
		providerToServiceMap.put(SERVICE_PROVIDER_NAME_MONITORING,SUBSCRIBED_SERVICE_NAME_MONITORING);
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
			case SUBSCRIBED_SERVICE_NAME_MONITORING:
				return Arrays.asList(SERVICE_PROVIDER_NAME_MONITORING);
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
		List<String> subscribedServices = oracle.sysman.emaas.platform.emcpdf.tenant.TenantSubscriptionUtil.getTenantSubscribedServices(tenant,tenantSubscriptionInfo);
		isV1Tenant = checkLicVersion(tenantSubscriptionInfo);
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

	public static List<String> getTenantSubscribedServiceProviders(String tenant) throws IOException
	{
		TenantSubscriptionInfo tenantSubscriptionInfo = new TenantSubscriptionInfo();
		List<String> subscribedApps = oracle.sysman.emaas.platform.emcpdf.tenant.TenantSubscriptionUtil.getTenantSubscribedServices(tenant,tenantSubscriptionInfo);
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
		// some service are not in the subscribed app list
		providers.add("EventUI");
		providers.add("Dashboard-UI");
		
		LOGGER.debug("Get subscribed provider names: {} for tenant {}",
				StringUtil.arrayToCommaDelimitedString(providers.toArray()), tenant);
		return providers;
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
	 * if v1 tenant, return true, v2/v3/v4 return false;
	 * @param tenantSubscriptionInfo
	 * @return
	 */
	private static boolean checkLicVersion(TenantSubscriptionInfo tenantSubscriptionInfo){
		if(tenantSubscriptionInfo!=null && tenantSubscriptionInfo.getAppsInfoList()!=null){
			for(AppsInfo appsInfo : tenantSubscriptionInfo.getAppsInfoList()){
				if("V2_MODEL".equals(appsInfo.getLicVersion()) ||
						"V3_MODEL".equals(appsInfo.getLicVersion()) ||
						"V4_MODEL".equals(appsInfo.getLicVersion())){
					LOGGER.info("Check tenant version is V2/V3/v4 tenant.");
					return false;
				}
			}
		}
		LOGGER.info("Check tenant version is V1 tenant.");
		//v1
		return true;
	}

	private TenantSubscriptionUtil()
	{
	}
}

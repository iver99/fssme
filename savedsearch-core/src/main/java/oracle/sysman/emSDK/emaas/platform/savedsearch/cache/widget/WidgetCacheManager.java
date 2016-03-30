/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.widget;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager;

/**
 * @author guochen
 */
public class WidgetCacheManager
{
	private static final Logger logger = LogManager.getLogger(WidgetCacheManager.class);
	private static WidgetCacheManager instance = new WidgetCacheManager();

	public static WidgetCacheManager getInstance()
	{
		return instance;
	}

	private Set<Object> cachedKeys;

	private WidgetCacheManager()
	{
		cachedKeys = new HashSet<Object>();
		logger.info("Completed initializing widget cache manager");
		CacheManager.getInstance();
	}

	public void clearCachedKeys()
	{
		cachedKeys = new HashSet<Object>();
		logger.debug("Cached keys for widget cache manager have been cleared");
	}

	public String getWigetListFromCache(Tenant cacheTenant, String widgetGroupId, boolean includeDashboardIneligible)
			throws Exception
	{
		CacheManager cm = CacheManager.getInstance();
		String wgtList = (String) cm.getCacheable(cacheTenant, CacheManager.CACHES_WIDGET_CACHE,
				new Keys(TenantContext.getContext().getUsername(), widgetGroupId, includeDashboardIneligible));
		if (wgtList != null) {
			Object generatedKeys = new WidgetKeyGenerator().generate(cacheTenant, widgetGroupId, includeDashboardIneligible,
					TenantContext.getContext().getUsername());
			cachedKeys.add(generatedKeys);
			logger.debug("After getting the wiget list, update the cached keys");
		}
		else {
			logger.debug("Got empty widget list from cache with name={}", CacheManager.CACHES_WIDGET_CACHE);
		}

		return wgtList;
	}

	public void reloadRefreshableCaches()
	{
		if (cachedKeys == null || cachedKeys.isEmpty()) {
			logger.debug("There is no cached keys for reloading.");
			return;
		}
		TenantInfo oldti = TenantContext.getContext();
		try {
			for (Object key : cachedKeys) {
				if (key instanceof RefreshableWidgetKeys) {
					RefreshableWidgetKeys dk = (RefreshableWidgetKeys) key;
					try {
						logger.debug("Reload to refresh cache for tenant {} and keys {}", dk.getTenant(),
								StringUtil.arrayToCommaDelimitedString(dk.getParams()));
						String userName = dk.getUserName();
						TenantInfo ti = new TenantInfo(userName, dk.getTenant().getTenantId());
						ti.settenantName(dk.getTenant().getTenantName());
						TenantContext.setContext(ti);
						List<String> providers = TenantSubscriptionUtil
								.getTenantSubscribedServiceProviders(TenantContext.getContext().gettenantName());
						logger.debug("Retrieved subscribed providers {} for tenant {}",
								StringUtil.arrayToCommaDelimitedString(providers.toArray()),
								TenantContext.getContext().gettenantName());
						Boolean includeDashboardIneligible = dk.getIncludeDashboardIneligible();
						String widgetGroupId = dk.getWidgetGroupId();
						List<Widget> widgetList = SearchManager.getInstance()
								.getWidgetListByProviderNames(includeDashboardIneligible, providers, widgetGroupId);
						String message = WidgetManager.getInstance().getWidgetJsonStringFromWidgetList(widgetList);
						storeWidgetListToCache(dk.getTenant(), message, widgetGroupId, includeDashboardIneligible);
						logger.debug("Completed to reload refreshable cache for tenant {} and keys {}", dk.getTenant(),
								StringUtil.arrayToCommaDelimitedString(dk.getParams()));
					}
					catch (Exception e) {
						logger.error(e);
					}
				}
				else {
					logger.debug("Unfortunately, the cached key isn't of type DefaultKey, it's type is {}",
							key.getClass().getName());
				}
			}
		}
		finally {
			TenantContext.setContext(oldti);
		}
	}

	public String storeWidgetListToCache(Tenant cacheTenant, String widgetList, String widgetGroupId,
			Boolean includeDashboardIneligible)
	{
		String userName = TenantContext.getContext().getUsername();
		Object generatedKeys = new WidgetKeyGenerator().generate(cacheTenant, widgetGroupId, includeDashboardIneligible,
				TenantContext.getContext().getUsername());
		CacheManager cm = CacheManager.getInstance();
		if (widgetList == null || widgetList.isEmpty()) {
			logger.debug("Ignore to saving empty/null widget list to cache, and remove (if exists) from cache");
			cm.removeCacheable(cacheTenant, CacheManager.CACHES_WIDGET_CACHE,
					new Keys(userName, widgetGroupId, includeDashboardIneligible));
			if (cachedKeys.contains(generatedKeys)) {
				cachedKeys.remove(generatedKeys);
			}
			return widgetList;
		}
		String wgtList = (String) cm.putCacheable(cacheTenant, CacheManager.CACHES_WIDGET_CACHE,
				new Keys(userName, widgetGroupId, includeDashboardIneligible), widgetList);
		cachedKeys.add(generatedKeys);
		return wgtList;
	}
}

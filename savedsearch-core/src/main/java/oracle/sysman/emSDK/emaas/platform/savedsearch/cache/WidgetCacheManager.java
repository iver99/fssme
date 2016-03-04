/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;

/**
 * @author guochen
 */
public class WidgetCacheManager
{
	private static final Logger logger = LogManager.getLogger(WidgetCacheManager.class);
	private static WidgetCacheManager instance;

	static {
		instance = new WidgetCacheManager();
	}

	public static WidgetCacheManager getInstance()
	{
		return instance;
	}

	private final Set<Object> cachedKeys;

	private WidgetCacheManager()
	{
		cachedKeys = new HashSet<Object>();
		logger.info("Completed initializing widget cache manager");
		CacheManager.getInstance();
	}

	@SuppressWarnings("unchecked")
	public List<Widget> getWigetListFromCache(Tenant cacheTenant) throws Exception
	{
		CacheManager cm = CacheManager.getInstance();
		List<Widget> wgtList = (List<Widget>) cm.getCacheable(cacheTenant, CacheManager.CACHES_WIDGET_CACHE,
				TenantContext.getContext().getUsername());
		if (wgtList != null) {
			Object generatedKeys = new DefaultKeyGenerator().generate(cacheTenant,
					new Keys(TenantContext.getContext().getUsername()));
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
				if (key instanceof DefaultKey) {
					DefaultKey dk = (DefaultKey) key;
					try {
						logger.debug("Reload to refresh cache for tenant {} and keys {}", dk.getTenant(),
								StringUtil.arrayToCommaDelimitedString(dk.getParams()));
						String userName = (String) dk.getParams()[0];
						TenantInfo ti = new TenantInfo(userName, dk.getTenant().getTenantId());
						ti.settenantName(dk.getTenant().getTenantName());
						TenantContext.setContext(ti);
						List<String> providers = TenantSubscriptionUtil
								.getTenantSubscribedServiceProviders(TenantContext.getContext().gettenantName());
						logger.debug("Retrieved subscribed providers {} for tenant {}",
								StringUtil.arrayToCommaDelimitedString(providers.toArray()),
								TenantContext.getContext().gettenantName());
						List<Widget> widgetList = SearchManager.getInstance().getWidgetListByProviderNames(false, providers,
								null);

						storeWidgetListToCache(dk.getTenant(), widgetList);
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

	@SuppressWarnings("unchecked")
	public List<Widget> storeWidgetListToCache(Tenant cacheTenant, List<Widget> widgetList)
	{
		String userName = TenantContext.getContext().getUsername();
		Object generatedKeys = new DefaultKeyGenerator().generate(cacheTenant, new Keys(userName));
		CacheManager cm = CacheManager.getInstance();
		if (widgetList == null || widgetList.isEmpty()) {
			logger.debug("Ignore to saving empty/null widget list to cache, and remove (if exists) from cache");
			cm.removeCacheable(cacheTenant, CacheManager.CACHES_WIDGET_CACHE, new Keys(userName));
			if (cachedKeys.contains(generatedKeys)) {
				cachedKeys.remove(generatedKeys);
			}
			return widgetList;
		}
		List<Widget> wgtList = (List<Widget>) cm.putCacheable(cacheTenant, CacheManager.CACHES_WIDGET_CACHE, new Keys(userName),
				widgetList);
		cachedKeys.add(generatedKeys);
		return wgtList;
	}
}

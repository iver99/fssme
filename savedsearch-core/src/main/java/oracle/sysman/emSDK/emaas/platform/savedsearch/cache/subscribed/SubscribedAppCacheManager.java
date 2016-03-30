/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.subscribed;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

/**
 * @author guochen
 */
public class SubscribedAppCacheManager
{
	private static final Logger logger = LogManager.getLogger(SubscribedAppCacheManager.class);
	private static SubscribedAppCacheManager instance = new SubscribedAppCacheManager();

	public static SubscribedAppCacheManager getInstance()
	{
		return instance;
	}

	private Set<Object> cachedKeys;

	private SubscribedAppCacheManager()
	{
		cachedKeys = new HashSet<Object>();
		logger.info("Completed initializing subscribed app cache manager");
		CacheManager.getInstance();
	}

	public void clearCachedKeys()
	{
		cachedKeys = new HashSet<Object>();
		logger.debug("Cached keys for subscribed app cache manager have been cleared");
	}

	public List<String> getSubscribedAppFromCache(Tenant cacheTenant) throws Exception
	{
		CacheManager cm = CacheManager.getInstance();
		@SuppressWarnings("unchecked")
		List<String> cachedApps = (List<String>) cm.getCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIPTION_CACHE,
				CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS);
		if (cachedApps != null) {
			cachedKeys.add(cacheTenant);
			logger.debug("After getting the subscribed app, update the cached keys");
		}
		else {
			logger.debug("Got empty subscribed app from cache with name={}", CacheManager.CACHES_SUBSCRIPTION_CACHE);
		}

		return cachedApps;
	}

	public void reloadRefreshableCaches()
	{
		if (cachedKeys == null || cachedKeys.isEmpty()) {
			logger.debug("There is no refreshable subscribed apps cached keys for reloading.");
			return;
		}
		for (Object key : cachedKeys) {
			if (key instanceof Tenant) {
				Tenant tenant = (Tenant) key;
				try {
					logger.debug("Reload to refresh subscribed apps cache for tenant {}", tenant);
					List<String> apps = TenantSubscriptionUtil.internalGetTenantSubscribedServices(tenant.getTenantName());
					storeSubscribedAppsToCache(tenant, apps);
					logger.debug("Completed to reload refreshable subscribed apps cache for tenant {}", tenant);
				}
				catch (Exception e) {
					logger.error(e);
				}
			}
			else {
				logger.debug("Unfortunately, the subscribed apps cached key isn't of type DefaultKey, it's type is {}",
						key.getClass().getName());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> storeSubscribedAppsToCache(Tenant cacheTenant, List<String> apps)
	{
		CacheManager cm = CacheManager.getInstance();
		if (apps == null || apps.isEmpty()) {
			logger.debug("Ignore to saving empty/null subscribed app list to cache, and remove (if exists) from cache");
			cm.removeCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIPTION_CACHE,
					CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS);
			if (cachedKeys.contains(cacheTenant)) {
				cachedKeys.remove(cacheTenant);
			}
			return apps;
		}
		apps = (List<String>) cm.putCacheable(cacheTenant, CacheManager.CACHES_SUBSCRIPTION_CACHE,
				CacheManager.LOOKUP_CACHE_KEY_SUBSCRIBED_APPS, apps);
		cachedKeys.add(cacheTenant);
		logger.debug("subscribed app list has been stored inside cache to cache");
		return apps;
	}
}

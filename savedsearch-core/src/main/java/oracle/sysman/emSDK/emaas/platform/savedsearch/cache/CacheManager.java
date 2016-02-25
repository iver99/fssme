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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;

/**
 * @author guochen
 */
public class CacheManager
{
	private static final Logger logger = LogManager.getLogger(CacheManager.class);

	public static final String CACHES_LOOKUP_CACHE = "lookupCache";
	public static final String CACHES_WIDGET_CACHE = "widgetCache";
	public static final String CACHES_ETERNAL_CACHE = "eternalCache";

	public static final String LOOKUP_CACHE_KEY_SUBSCRIBED_APPS = "subscribedApps";
	public static final String LOOKUP_CACHE_KEY_INTERNAL_LINK = "internalLink";
	public static final String LOOKUP_CACHE_KEY_ALL_WIDGET_APPS = "allWidgetsApps";

	private static CacheManager instance;

	static {
		instance = new CacheManager();
	}

	public static CacheManager getInstance()
	{
		return instance;
	}

	private KeyGenerator keyGen;

	private CacheManager()
	{
		keyGen = new DefaultKeyGenerator();
		CacheFactory.ensureCluster();
		getCache(CacheManager.CACHES_LOOKUP_CACHE);
		getCache(CacheManager.CACHES_WIDGET_CACHE);
		getCache(CacheManager.CACHES_ETERNAL_CACHE);
		logger.info("Completed coherence cache manager initialization.");
	}

	public NamedCache getCache(String cacheName)
	{
		if (StringUtil.isEmpty(cacheName)) {
			return null;
		}
		return getInternalCache(cacheName);
	}

	public Object getCacheable(String cacheName, Keys keys) throws Exception
	{
		return getCacheable(null, cacheName, keys);
	}

	public Object getCacheable(String cacheName, Keys keys, ICacheFetchFactory ff) throws Exception
	{
		return getCacheable(null, cacheName, keys, ff);
	}

	public Object getCacheable(String cacheName, String key) throws Exception
	{
		return getCacheable(cacheName, new Keys(key));
	}

	public Object getCacheable(Tenant tenant, String cacheName, Keys keys) throws Exception
	{
		return getCacheable(tenant, cacheName, keys, null);
	}

	public Object getCacheable(Tenant tenant, String cacheName, Keys keys, ICacheFetchFactory ff) throws Exception
	{
		NamedCache cache = getInternalCache(cacheName);
		if (cache == null) {
			return null;
		}
		Object key = getInternalKey(tenant, keys);
		if (key == null) {
			return null;
		}
		Object value = cache.get(key);
		if (value == null && ff != null) {
			logger.debug("Cache not retrieved, trying to load with fetch factory");
			value = ff.fetchCachable(key);
			if (value != null) {
				cache.put(key, value);
				logger.debug("Successfully fetched data, putting to cache");
			}
		}
		if (value == null) {
			logger.debug("Not retrieved cache with cache name {} and key {} for tenant {}", cacheName, key, tenant);
			return null;
		}
		logger.debug("Retrieved cacheable with key={} and value={} for tenant={}", key, value, tenant);
		return value;
	}

	public Object getCacheable(Tenant tenant, String cacheName, String key) throws Exception
	{
		return getCacheable(tenant, cacheName, new Keys(key));
	}

	public Object getCacheable(Tenant tenant, String cacheName, String key, ICacheFetchFactory ff) throws Exception
	{
		return getCacheable(tenant, cacheName, new Keys(key), ff);
	}

	public Object getInternalKey(Tenant tenant, Keys keys)
	{
		return keyGen.generate(tenant, keys);
	}

	public Object putCache(String key, Object value)
	{
		NamedCache nc = CacheFactory.getCache("lookupCache");
		nc.put(key, value);
		return value;
	}

	public Object putCacheable(String cacheName, Keys keys, Object value)
	{
		return putCacheable(null, cacheName, keys, value);
	}

	public Object putCacheable(String cacheName, String key, Object value)
	{
		return putCacheable(cacheName, new Keys(key), value);
	}

	public Object putCacheable(Tenant tenant, String cacheName, Keys keys, Object value)
	{
		NamedCache cache = getInternalCache(cacheName);
		if (cache == null) {
			return null;
		}
		Object key = getInternalKey(tenant, keys);
		if (key == null) {
			return null;
		}
		cache.put(key, value);
		logger.debug("Cacheable with tenant={}, key={} and value={} is put to cache {}", tenant, key, value, cacheName);

		return value;
	}

	public Object putCacheable(Tenant tenant, String cacheName, String key, Object value)
	{
		return putCacheable(tenant, cacheName, new Keys(key), value);
	}

	public Object removeCacheable(String cacheName, Keys keys)
	{
		return removeCacheable(null, cacheName, keys);
	}

	public Object removeCacheable(Tenant tenant, String cacheName, Keys keys)
	{
		NamedCache cache = getInternalCache(cacheName);
		if (cache == null) {
			return null;
		}
		Object key = getInternalKey(tenant, keys);
		if (key == null) {
			return null;
		}
		Object obj = cache.get(key);
		cache.remove(key);
		logger.debug("Cacheable with key={} and value={} is removed from cache {}", key, obj, cacheName);
		return obj;
	}

	public Object removeCacheable(Tenant tenant, String cacheName, String key)
	{
		return removeCacheable(tenant, cacheName, new Keys(key));
	}

	public void setKeyGenerator(KeyGenerator keyGenerator)
	{
		keyGen = keyGenerator;
	}

	/**
	 * @param cacheName
	 * @param key
	 * @return
	 */
	private NamedCache getInternalCache(String cacheName)
	{
		if (cacheName == null) {
			logger.warn("Not retrieved from cache for null cache name");
			return null;
		}
		NamedCache cache = null;
		try {
			cache = CacheFactory.getCache(cacheName);
		}
		catch (IllegalArgumentException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		if (cache == null) {
			logger.debug("Not retrieved cache with cache name {}", cacheName);
			return null;
		}
		return cache;
	}
}

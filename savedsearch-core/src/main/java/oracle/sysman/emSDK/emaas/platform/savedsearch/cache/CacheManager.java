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


import java.util.ResourceBundle;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.CacheFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.CacheUnit;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.Element;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guochen
 * ## for more information about cache,you can find it in the wiki below ##
 * https://confluence.oraclecorp.com/confluence/pages/viewpage.action?pageId=177111759
 */
public class CacheManager
{
	private static final Logger LOGGER = LogManager.getLogger(CacheManager.class);

	public static final String CACHES_LOOKUP_CACHE = "lookupCache";
	public static final String CACHES_SCREENSHOT_CACHE = "screenshotCache";
	public static final String CACHES_ETERNAL_CACHE = "eternalCache";
	public static final String CACHES_SUBSCRIBE_CACHE = "subscribeCache";

	public static final String LOOKUP_CACHE_KEY_SUBSCRIBED_APPS = "subscribedApps";
	public static final String LOOKUP_CACHE_KEY_EXTERNAL_LINK = "externalLink";
	public static final String LOOKUP_CACHE_KEY_INTERNAL_LINK = "internalLink";
	public static final String LOOKUP_CACHE_KEY_VANITY_BASE_URL = "vanityBaseUrl";
	public static final String LOOKUP_CACHE_KEY_CLOUD_SERVICE_LINKS = "cloudServiceLinks";
	public static final String LOOKUP_CACHE_KEY_ADMIN_LINKS = "adminLinks";
	public static final String LOOKUP_CACHE_KEY_HOME_LINKS = "homeLinks";
	public static final String LOOKUP_CACHE_KEY_VISUAL_ANALYZER = "visualAnalyzer";

	ResourceBundle conf = ResourceBundle.getBundle("cache_config");
	private static CacheManager instance = new CacheManager();

	public static CacheManager getInstance()
	{
		return instance;
	}

	private KeyGenerator keyGen;

	private CacheManager()
	{
		keyGen = new DefaultKeyGenerator();
		LOGGER.info("Initialization LRU CacheManager!!");
		CacheFactory.getCache(CACHES_LOOKUP_CACHE,Integer.valueOf(conf.getString("CACHES_LOOKUP_CACHE_EXPIRE_TIME")));
		CacheFactory.getCache(CACHES_SCREENSHOT_CACHE);
		CacheFactory.getCache(CACHES_ETERNAL_CACHE);
		CacheFactory.getCache(CACHES_SUBSCRIBE_CACHE,Integer.valueOf(conf.getString("CACHES_SUBSCRIBE_CACHE_EXPIRE_TIME")));
	}

	public CacheUnit getCache(String cacheName)
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
		CacheUnit cache = getInternalCache(cacheName);
		if (cache == null) {
			return null;
		}
		Object key = getInternalKey(tenant, keys);
		if (key == null) {
			return null;
		}
		Object value = cache.get(key.toString());
		if (value == null && ff != null) {
			LOGGER.debug("Cache not retrieved, trying to load with fetch factory");
			value = ff.fetchCachable(key);
			if (value != null) {
				cache.put(key.toString(), new Element(key,value));
				LOGGER.debug("Successfully fetched data, putting to cache");
			}
		}
		if (value == null) {
			LOGGER.debug("Not retrieved cache with cache name {} and key {} for tenant {}", cacheName, key, tenant);
			return null;
		}
		LOGGER.debug("Retrieved cacheable with key={} and value={} for tenant={}", key, value, tenant);
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
		CacheUnit nc = CacheFactory.getCache("lookupCache");
		nc.put(key, new Element(key,value));
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
		CacheUnit cache = getInternalCache(cacheName);
		if (cache == null) {
			return null;
		}
		Object key = getInternalKey(tenant, keys);
		if (key == null) {
			return null;
		}
		cache.put(key.toString(), new Element(key,value));
		LOGGER.debug("Cacheable with tenant={}, key={} and value={} is put to cache {}", tenant, key, value, cacheName);

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
		CacheUnit cache = getInternalCache(cacheName);
		if (cache == null) {
			return null;
		}
		Object key = getInternalKey(tenant, keys);
		if (key == null) {
			return null;
		}
		Object obj = cache.get(key.toString());
		cache.remove(key.toString());
		LOGGER.debug("Cacheable with key={} and value={} is removed from cache {}", key, obj, cacheName);
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
	private CacheUnit getInternalCache(String cacheName)
	{
		if (cacheName == null) {
			LOGGER.warn("Not retrieved from cache for null cache name");
			return null;
		}
		CacheUnit cache = null;
		try {
			cache = CacheFactory.getCache(cacheName);
		}
		catch (IllegalArgumentException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		if (cache == null) {
			LOGGER.debug("Not retrieved cache with cache name {}", cacheName);
			return null;
		}
		return cache;
	}
}

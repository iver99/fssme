package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CacheFactory {
	
	private static final Logger LOGGER = LogManager.getLogger(CacheFactory.class);
	private static final int DEFAULT_EXPIRE_TIME=0;
	private static final int DEFAULT_CACHE_UNIT_CAPACITY = CacheConfig.DEFAULT_CAPACITY;
	
	private static Map<String,CacheUnit> cacheUnitMap=new ConcurrentHashMap<String,CacheUnit>();
	
	
	public static CacheUnit getCache(String cacheName){
		return getCache(cacheName,DEFAULT_CACHE_UNIT_CAPACITY,DEFAULT_EXPIRE_TIME);
	}

	private CacheFactory() {
	}

	public static CacheUnit getCache(String cacheName, int capacity, int timeToLive)
	{
		if(cacheName ==null) {
			LOGGER.error("CacheFactory:Cache name cannot be null!");
			throw new IllegalArgumentException("cache name cannot be null!");
		}
		if("".equals(cacheName)) {
			LOGGER.error("CacheFactory:Cache name cannot be empty!");
			throw new IllegalArgumentException("cache name cannot be empty!");
		}
		CacheUnit cu=cacheUnitMap.get(cacheName);
		if(cu == null){
			return createCacheUnit(cacheName, capacity, timeToLive);
		}else{
			return cu;
		}
		
	}
	
	private static CacheUnit createCacheUnit(String cacheName, int capacity, int timeToLive)
	{
		CacheUnit cu = new CacheUnit(cacheName, capacity, timeToLive);
		cacheUnitMap.put(cacheName, cu);
		LOGGER.debug("CacheFactory:Cache named: {},time to live: {} has been created.", cacheName, timeToLive);
		return cu;
	}
	public static ConcurrentHashMap<String, CacheUnit> getCacheUnitMap() {
		return (ConcurrentHashMap<String, CacheUnit>) cacheUnitMap;
	}
}

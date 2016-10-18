package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CacheFactory {
	
	private static final Logger LOGGER = LogManager.getLogger(CacheFactory.class);
	private static final int DEFAULT_EXPIRE_TIME=0;
	
	private static Map<String,CacheUnit> cacheUnitMap=new ConcurrentHashMap<String,CacheUnit>();
	
	
	public static CacheUnit getCache(String cacheName){
		return getCache(cacheName,DEFAULT_EXPIRE_TIME);
	}

	private CacheFactory() {
	}

	public static CacheUnit getCache(String cacheName, int timeToLive){
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
			return createCacheUnit(cacheName, timeToLive);
		}else{
			return cu;
		}
		
	}
	
	private static CacheUnit createCacheUnit(String cacheName,int timeToLive){
		CacheUnit cu=new CacheUnit(cacheName,timeToLive);
		cacheUnitMap.put(cacheName, cu);
		LOGGER.debug("CacheFactory:Cache named: {},time to live: {} has been created.", cacheName, timeToLive);
		return cu;
	}
}

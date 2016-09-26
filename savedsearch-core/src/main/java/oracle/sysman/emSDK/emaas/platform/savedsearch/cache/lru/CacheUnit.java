package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;


import java.util.ResourceBundle;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.inter.ICacheUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CacheUnit implements ICacheUnit{
	
private static final Logger LOGGER = LogManager.getLogger(CacheUnit.class);
	
	private CacheLinkedHashMap<String,Element> cacheLinkedHashMap;
	private final int timeToLive;
	private int cacheCapacity;
	private String name;
	
	//constant
	private final static int DEFAULT_TIME_TO_LIVE=0;// means live forever
	private final static String DEFAULT_CACHE_UNIT_NAME="default_cache_unit";
	public static final int DEFAULT_CACHE_CAPACITY=Integer.valueOf(ResourceBundle.getBundle("cache_config").getString("DEFAULT_CACHE_UNIT_CAPACITY"));//default capacity is 500
	//constructor
	public CacheUnit(){
		this(DEFAULT_CACHE_UNIT_NAME,DEFAULT_CACHE_CAPACITY,DEFAULT_TIME_TO_LIVE);
	}
	
	public CacheUnit(int timeToLive){
		this(DEFAULT_CACHE_UNIT_NAME,DEFAULT_CACHE_CAPACITY,timeToLive);
	}
	
	public CacheUnit(int capacity,int timeToLive){
		this(DEFAULT_CACHE_UNIT_NAME,capacity,timeToLive);
	}
	
	public CacheUnit(String name){
		this(name,DEFAULT_CACHE_CAPACITY,DEFAULT_TIME_TO_LIVE);
	}
	
	public CacheUnit(String name,int timeToLive){
		this(name,DEFAULT_CACHE_CAPACITY,timeToLive);
	}
	public CacheUnit(String name,int capacity,int timeToLive){
		this.name=name;
		this.timeToLive=timeToLive;
		this.cacheCapacity=capacity;
		this.cacheLinkedHashMap=new CacheLinkedHashMap<String, Element>(capacity);

	}
	
	
	@Override
	public boolean put(String key,Element value){
		if(key ==null) {
			LOGGER.debug("CacheUnit:Cannot put into CacheUnit:key cannot be null!");
			throw new IllegalArgumentException("cannot put into CacheUnit:key cannot be null!");
		}
		if(value ==null) {
			LOGGER.debug("CacheUnit:Cannot put into CacheUnit:value cannot be null!");
			throw new IllegalArgumentException("cannot put into CacheUnit:value cannot be null!");
		}
		value.setLastAccessTime(getCurrentTime());
		cacheLinkedHashMap.put(key, value);
		return true;
		
	}
	
	@Override
	public boolean remove(String key){
			return cacheLinkedHashMap.remove(key) == null?false:true;
		
	}
	@Override
	public Object get(String key){
		return getElementValue(key);
		
	}
	/**
	 * get the element for a specific key,if the element is expired,remove it.
	 * @param key
	 * @return
	 */
	private Object getElementValue(String key) {
		if (key == null) {
			LOGGER.debug("CacheUnit:key is null,returning null...");
			return null;
		}
		Element e = (Element) cacheLinkedHashMap.get(key);
		if (e == null) {
			LOGGER.debug("CacheUnit:Element is null,returning null...");
			return null;
		}
		if(e.isExpired(timeToLive)){
			//remove action
			LOGGER.debug("CacheUnit:The Element is expired,removing it from cache unit..");
			cacheLinkedHashMap.remove(key);
			LOGGER.debug("CacheUnit:Element is null,returning null...");
			return null;
		}
		e.setLastAccessTime(getCurrentTime());
		//update action(last access time is updated)
		cacheLinkedHashMap.putWithoutLock(key, e);
		LOGGER.debug("CacheUnit:Get element from cache successful,and element has been updated!");
		return e.getValue();
	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCacheCapacity() {
		return cacheCapacity;
	}

	public void setCacheCapacity(int cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	public CacheLinkedHashMap<String, Element> getCacheLinkedHashMap() {
		return cacheLinkedHashMap;
	}

	public void setCacheLinkedHashMap(
			CacheLinkedHashMap<String, Element> cacheLinkedHashMap) {
		this.cacheLinkedHashMap = cacheLinkedHashMap;
	}
	
	public boolean isEmpty(){
		return this.cacheLinkedHashMap.getCacheMap().isEmpty()?true:false;
	}

	@Override
	public void clearCache() {
		cacheLinkedHashMap.clear();
	}
	
	
	
	
}

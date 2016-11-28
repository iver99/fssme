package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;



import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheConfig;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.inter.ICacheUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class CacheUnit implements ICacheUnit{
	
private static final Logger LOGGER = LogManager.getLogger(CacheUnit.class);
	@JsonIgnore
	private CacheLinkedHashMap<String,Element> cacheLinkedHashMap;
	private String name;
	private final int timeToLive;
	@JsonIgnore
	private int cacheCapacity;
	private CacheUnitStatus cacheUnitStatus;
	
	//constant
	private final static int DEFAULT_TIME_TO_LIVE=0;// means live forever
	private final static String DEFAULT_CACHE_UNIT_NAME="default_cache_unit";
	public static final int DEFAULT_CACHE_CAPACITY= CacheConfig.DEFAULT_CAPACITY;
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
		this.cacheUnitStatus=new CacheUnitStatus(capacity);
		LOGGER.debug("Creating a CacheUnit named {} and expiration time is {} and capacity is {}"+name,timeToLive,capacity);
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
		cacheLinkedHashMap.put(key, value);
		this.cacheUnitStatus.setUsage(this.cacheUnitStatus.getUsage()+1);
		return true;
		
	}
	
	@Override
	public boolean remove(String key){
		this.cacheUnitStatus.setUsage(this.cacheUnitStatus.getUsage()-1);
		this.cacheUnitStatus.setEvictionCount(this.cacheUnitStatus.getEvictionCount()+1);
		return cacheLinkedHashMap.remove(key) != null;
		
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
		this.cacheUnitStatus.setRequestCount(this.cacheUnitStatus.getRequestCount()+1);
		if (key == null) {
			return null;
		}
		Element e = (Element) cacheLinkedHashMap.get(key);
		if (e == null) {
			return null;
		}
		if(e.isExpired(timeToLive)){
			//remove action
			LOGGER.debug("CacheUnit:The Element is expired,removing it from cache unit..");
			cacheLinkedHashMap.remove(key);
			LOGGER.debug("CacheUnit:Element is null,returning null...");
			this.cacheUnitStatus.setUsage(this.cacheUnitStatus.getUsage()-1);
			this.cacheUnitStatus.setEvictionCount(this.cacheUnitStatus.getEvictionCount()+1);
			return null;
		}
		this.cacheUnitStatus.setHitCount(this.cacheUnitStatus.getHitCount()+1);
		return e.getValue();
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
	@JsonProperty("isEmpty")
	public boolean isEmpty(){
		return this.cacheLinkedHashMap.getCacheMap().isEmpty();
	}

	@Override
	public void clearCache() {
		cacheLinkedHashMap.clear();
		this.cacheUnitStatus.setEvictionCount(0L);
		this.cacheUnitStatus.setHitCount(0L);
		this.cacheUnitStatus.setRequestCount(0L);
		this.cacheUnitStatus.setUsage(0);

	}
	public CacheUnitStatus getCacheUnitStatus() {
		return cacheUnitStatus;
	}
	/**
	 * @return the timeToLive
	 */
	public int getTimeToLive()
	{
		return timeToLive;
	}
	
	
	
}

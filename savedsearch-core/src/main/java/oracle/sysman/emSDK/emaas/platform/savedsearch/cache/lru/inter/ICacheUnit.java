package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.inter;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.Element;


/**
 * Cache Unit interface
 * @author chendonghao
 *
 */
public interface ICacheUnit {

	public boolean put(String key,Element e);
	
	public Object get(String key);
	
	public boolean remove(String key);
	
	public void clearCache();

	
	
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class CacheLinkedHashMapTest {
	CacheLinkedHashMap<String, String> cacheMap = new CacheLinkedHashMap<String, String>(100);
	
	public void testPut() {
		cacheMap.put("testPut", "testPut");
		Assert.assertTrue(cacheMap.containsKey("testPut"));
		Assert.assertTrue(cacheMap.containsValue("testPut"));
		Assert.assertEquals(cacheMap.getUnderLock("testPut"), "testPut");
		Assert.assertNotNull(cacheMap.remove("testPut"));
	}
	
	public void testPutWithoutLock() {
		cacheMap.putWithoutLock("testPutWithoutLock", "testPutWithoutLock");
		Assert.assertEquals(cacheMap.get("testPutWithoutLock"), "testPutWithoutLock");
		cacheMap.clear();
		Assert.assertEquals(cacheMap.size(), 0);
	}
	
	public void testGetSet() {
		cacheMap.getCacheCapacity();
		cacheMap.setCacheCapacity(100);
		cacheMap.getCacheMap();
		cacheMap.setCacheMap(new LinkedHashMap<String, String>());
	}
}

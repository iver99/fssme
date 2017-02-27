package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;


import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.DefaultKeyGenerator;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.ICacheFetchFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class CacheManagerTest
{
	@Test
	public void testPutGetRemoveCacheable() throws Exception
	{
		final Integer cachedValue = 1;
		CacheManager cm = CacheManager.getInstance();
		cm.putCacheable("eternalCache", new Keys("test"), cachedValue);
		Integer value = (Integer) cm.getCacheable("eternalCache", new Keys("test"));
		Assert.assertEquals(value, Integer.valueOf(1));
		value = (Integer) cm.getCacheable("eternalCache", new Keys("test not exists"));
		Assert.assertNull(value);
		cm.removeCacheable("eternalCache", new Keys("test"));
		value = (Integer) cm.getCacheable("eternalCache", new Keys("test"));
		Assert.assertNull(value);
		value = (Integer) cm.getCacheable("eternalCache", new Keys("test"), new ICacheFetchFactory() {
			@Override
			public Object fetchCachable(Object key)
			{
				return cachedValue;
			}

		});
		Assert.assertEquals(value, cachedValue);
		value = (Integer) cm.getCacheable("eternalCache", new Keys("test"));
		Assert.assertEquals(value, Integer.valueOf(1));
		cm.putCacheable("eternalCache", "test2", null);
		Assert.assertNull(cm.getCacheable("eternalCache", new Keys("test2")));
		Assert.assertNull(cm.getCacheable("notExistingCache", new Keys("notExistingKey")));
		Assert.assertNull(cm.getCacheable("eternalCache", "notExistingKey"));
		Assert.assertNull(cm.getCacheable(null, new Keys("notExistingKey")));
		Assert.assertNull(cm.getCache(""));
		Assert.assertNotNull(cm.getCache("notExistingCache"));
		Assert.assertEquals(cm.putCache("key", "value").toString(), "value");
		
		cm.logCacheStatus();
	}

	@Test
	public void testPutGetRemoveCacheableDifferentThread() throws Exception
	{
		CacheManager cm = CacheManager.getInstance();
		cm.putCacheable("eternalCache", new Keys("test thread"), 1);
		Integer value = (Integer) cm.getCacheable("eternalCache", new Keys("test thread"));
		Assert.assertEquals(value, Integer.valueOf(1));

		new Thread(new Runnable() {
			@Override
			public void run()
			{
				CacheManager cm = CacheManager.getInstance();
				Integer value;
				try {
					value = (Integer) cm.getCacheable("eternalCache", new Keys("test thread"));
					Assert.assertEquals(value, Integer.valueOf(1));
					value = (Integer) cm.getCacheable("eternalCache", new Keys("test not exists"));
					Assert.assertNull(value);
					cm.removeCacheable("eternalCache", new Keys("test thread"));
					value = (Integer) cm.getCacheable("eternalCache", new Keys("test thread"));
					Assert.assertNull(value);
				}
				catch (Exception e) {
					Assert.fail(e.getLocalizedMessage(), e);
				}
			}
		}) {
		}.start();
	}

	@Test
	public void testPutGetRemoveCacheableWithTenant() throws Exception
	{
		final Integer cachedValue = 1;
		Tenant tenant1 = new Tenant("tenant1");
		Tenant tenant2 = new Tenant("tenant2");
		CacheManager cm = CacheManager.getInstance();
		cm.putCacheable(tenant1, "eternalCache", new Keys("test"), cachedValue);
		Integer value = (Integer) cm.getCacheable(tenant2, "eternalCache", new Keys("test not exists"));
		Assert.assertNull(value);
		cm = CacheManager.getInstance();
		value = (Integer) cm.getCacheable(tenant1, "eternalCache", new Keys("test"));
		Assert.assertEquals(value, cachedValue);
		cm.removeCacheable(tenant1, "eternalCache", new Keys("test"));
		value = (Integer) cm.getCacheable(tenant1, "eternalCache", "test");
		Assert.assertNull(value);
		value = (Integer) cm.getCacheable(tenant1, "eternalCache", new Keys("test"), new ICacheFetchFactory() {
			@Override
			public Object fetchCachable(Object key)
			{
				return cachedValue;
			}

		});
		Assert.assertEquals(value, cachedValue);
		value = (Integer) cm.getCacheable(tenant1, "eternalCache", "test", null);
		Assert.assertEquals(value, cachedValue);
		Assert.assertEquals(cm.removeCacheable(tenant1, "eternalCache", "test"), cachedValue);
	}
	
	@Test
	public void testDisableEnableCache() {
		final Integer cachedValue = 1;
		CacheManager cm = CacheManager.getInstance();
		cm.disableCacheManager();
		cm.logCacheStatus();
		cm.removeCacheable("cacheName", new Keys("keys"));
		Assert.assertNull(cm.putCacheable(null, "eternalCache", "test", cachedValue));
		
		cm.enableCacheManager();
		cm.setKeyGenerator(new DefaultKeyGenerator());
	}
}

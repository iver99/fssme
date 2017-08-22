package oracle.sysman.emaas.platform.savedsearch.services;

import org.testng.annotations.Test;

@Test(groups = { "s2" })
public class CacheServiceTest {

    @Test
    public void testCacheService() throws Exception {
        CacheServiceManager manager = new CacheServiceManager();
        manager.getName();
        manager.postStart(null);
        manager.postStop(null);
        manager.preStart(null);
        manager.preStop(null);
    }
}

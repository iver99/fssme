package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import org.testng.annotations.Test;

/**
 * Created by chehao on 2017/5/18 10:45.
 */
@Test(groups = {"s2"})
public class CacheUnitStatusTest {

    @Test
    public void testGetHitRate(){
        CacheUnitStatus cacheUnitStatus = new CacheUnitStatus();
//        cacheUnitStatus.setCapacity(1000);
        cacheUnitStatus.setHitCount(200L);
        cacheUnitStatus.setRequestCount(1000L);
        cacheUnitStatus.getHitRate();

        cacheUnitStatus.setHitCount(-2L);
        cacheUnitStatus.setRequestCount(1000L);
        cacheUnitStatus.getHitRate();

        cacheUnitStatus.setHitCount(-10L);
        cacheUnitStatus.setRequestCount(0L);
        cacheUnitStatus.getHitRate();
    }

    public void testGetUsageRate(){
        CacheUnitStatus cacheUnitStatus = new CacheUnitStatus();
        cacheUnitStatus.setCapacity(1000);
        cacheUnitStatus.setUsage(10);
        cacheUnitStatus.getUsageRate();

        cacheUnitStatus.setCapacity(-1000);
        cacheUnitStatus.setUsage(10);
        cacheUnitStatus.getUsageRate();

        cacheUnitStatus.setCapacity(1000);
        cacheUnitStatus.setUsage(-10);
        cacheUnitStatus.getUsageRate();

        cacheUnitStatus.setCapacity(1000);
        cacheUnitStatus.setUsage(0);
        cacheUnitStatus.getUsageRate();
    }
}

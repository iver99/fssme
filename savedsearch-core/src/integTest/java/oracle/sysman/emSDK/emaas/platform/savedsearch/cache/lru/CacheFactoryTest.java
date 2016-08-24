package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/21.
 */
@Test(groups={"s1"})
public class CacheFactoryTest {
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testGetCacheIllegalArgumentException(){
        CacheFactory.getCache(null);
        CacheFactory.getCache("");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testGetCacheIllegalArgumentException2nd(){
        CacheFactory.getCache("");
    }

}
package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import java.util.Date;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xiadai on 2016/8/21.
 */
@Test(groups={"s2"})
public class ScreenshotCacheManagerTest {
    private ScreenshotCacheManager screenshotCacheManager = ScreenshotCacheManager.getInstance();
    @Mocked
    Tenant tenant;
    @Mocked
    CacheManager cacheManager;
    @Mocked
    ScreenshotElement screenshotElement;
    @Test
    public void testGetScreenshotFromCacheParamNull()throws Exception{
        Assert.assertNull(screenshotCacheManager.getScreenshotFromCache(tenant,null,"filename"));
        Assert.assertNull(screenshotCacheManager.getScreenshotFromCache(tenant,1L,null));
    }

    @Test
    public void testGetScreenshotFromCacheResultNull()throws Exception{
        new Expectations(){
            {
                cacheManager.getCacheable((Tenant)any, anyString,(Keys)any);
                result = null;
            }
        };
        screenshotCacheManager.getScreenshotFromCache(tenant, 1L, "filename");
    }
    @Test
    public void testGetScreenshotFromCache()throws Exception{
        new Expectations(){
            {
                cacheManager.getCacheable((Tenant)any, anyString,(Keys)any);
                result = screenshotElement;
            }
        };
        screenshotCacheManager.getScreenshotFromCache(tenant, 1L, "filename");
    }

    @Test
    public void testStoreBase64ScreenshotToCacheParamNull() throws Exception {
        screenshotCacheManager.storeBase64ScreenshotToCache(tenant, 1L, null);
    }
    @Mocked
    ScreenshotData screenshotData;
    @Test
    public void testStoreBase64ScreenshotToCache() throws Exception {
        final Date modificationDate = new Date();
        final Date creationDate = new Date();
        new Expectations(){
            {
                screenshotData.getCreationDate();
                result = creationDate;
                screenshotData.getModificationDate();
                result = modificationDate;
                screenshotData.getScreenshot();
                result = "data:image/png;base64,";
                cacheManager.putCacheable((Tenant)any, anyString, (Keys)any, (ScreenshotElement)any);
            }
        };
        screenshotCacheManager.storeBase64ScreenshotToCache(tenant, 1L, screenshotData);
    }

    @Test
    public void testStoreBase64ScreenshotToCache1() throws Exception {
        final Date modificationDate = new Date();
        final Date creationDate = new Date();
        new Expectations(){
            {
                screenshotData.getCreationDate();
                result = creationDate;
                screenshotData.getModificationDate();
                result = modificationDate;
                screenshotData.getScreenshot();
                result = "data:image/jpeg;base64,";
                cacheManager.putCacheable((Tenant)any, anyString, (Keys)any, (ScreenshotElement)any);
            }
        };
        screenshotCacheManager.storeBase64ScreenshotToCache(tenant, 1L, screenshotData);

    }

    @Test
    public void testStoreBase64ScreenshotToCache2() throws Exception {
        final Date modificationDate = new Date();
        final Date creationDate = new Date();
        new Expectations(){
            {
                screenshotData.getCreationDate();
                result = creationDate;
                screenshotData.getModificationDate();
                result = modificationDate;
                screenshotData.getScreenshot();
                result = "data:image/,";
            }
        };
        screenshotCacheManager.storeBase64ScreenshotToCache(tenant, 1L, screenshotData);

    }
    @Test
    public void testStoreBase64ScreenshotToCache4() throws Exception {
        final Date modificationDate = new Date();
        final Date creationDate = new Date();
        new Expectations(){
            {
                screenshotData.getCreationDate();
                result = creationDate;
                screenshotData.getModificationDate();
                result = modificationDate;
                screenshotData.getScreenshot();
                result = null;
            }
        };
        screenshotCacheManager.storeBase64ScreenshotToCache(tenant, 1L, screenshotData);

    }

}
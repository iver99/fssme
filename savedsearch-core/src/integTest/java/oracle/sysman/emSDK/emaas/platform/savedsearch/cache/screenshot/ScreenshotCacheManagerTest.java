package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */

@Test(groups = {"s1"})
public class ScreenshotCacheManagerTest {

    ScreenshotCacheManager screenshotCacheManager = ScreenshotCacheManager.getInstance();

    @Test
    public void testStoreBase64ScreenshotToCacheAndGetScreenshotFromCache() throws Exception {
        Tenant tenant = new Tenant("tenantnamexx");
        Long widgetId = 1234L;
        String fileName = "screenshotxx";
        screenshotCacheManager.storeBase64ScreenshotToCache(tenant,widgetId,new Date(),new Date(),null);

        screenshotCacheManager.storeBase64ScreenshotToCache(tenant,widgetId,new Date(),new Date(),"data:image/png;base64,"+fileName);

        Assert.assertNull(screenshotCacheManager.getScreenshotFromCache(tenant,-2L,fileName));
        Assert.assertNull(screenshotCacheManager.getScreenshotFromCache(null,widgetId,fileName));
        Assert.assertNull(screenshotCacheManager.getScreenshotFromCache(tenant,widgetId,""));
        Assert.assertTrue(screenshotCacheManager.getScreenshotFromCache(tenant,widgetId,fileName) instanceof ScreenshotElement);
    }

    @Test
    public void testStoreBase64ScreenshotToCache2() throws Exception {
        ScreenshotData screenshotData = new ScreenshotData("data:image/png;base64,screenshotyy",new Date(),new Date());
        screenshotCacheManager.storeBase64ScreenshotToCache(new Tenant("tenantnameyy"),5678L,null);

        screenshotCacheManager.storeBase64ScreenshotToCache(new Tenant("tenantnameyy"),5678L,screenshotData);
    }
}
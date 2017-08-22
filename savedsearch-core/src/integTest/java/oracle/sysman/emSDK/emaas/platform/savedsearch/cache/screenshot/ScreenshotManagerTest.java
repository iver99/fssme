package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import oracle.sysman.emaas.platform.emcpdf.cache.tool.ScreenshotData;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Tenant;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by chehao on 8/22/2017 15:47.
 */
@Test(groups="s2")
public class ScreenshotManagerTest {

    @Test
    public void testGetScreenshotFromCache() throws Exception {
        Tenant tenant = new Tenant("test");
        BigInteger widgetId = BigInteger.ONE;
        ScreenshotManager.getInstance().getScreenshotFromCache(tenant, widgetId, "fileName");
        //null file name
        ScreenshotManager.getInstance().getScreenshotFromCache(tenant, widgetId, null);
        //null id
        ScreenshotManager.getInstance().getScreenshotFromCache(tenant, null, null);
    }

    @Test
    public void testStoreBase64ScreenshotToCache(){
        Tenant tenant = new Tenant("test");
        BigInteger widgetId = BigInteger.ONE;
        ScreenshotData screenshotData = new ScreenshotData("data:image/png;base64,123",new Date(),new Date());
        ScreenshotData screenshotData1 = new ScreenshotData("data:image/jpeg;base64,123",new Date(),new Date());
        ScreenshotManager.getInstance().storeBase64ScreenshotToCache(tenant,widgetId,screenshotData);

        ScreenshotManager.getInstance().storeBase64ScreenshotToCache(tenant,widgetId,null);

        ScreenshotManager.getInstance().storeBase64ScreenshotToCache(tenant,null,screenshotData);

        ScreenshotManager.getInstance().storeBase64ScreenshotToCache(tenant,widgetId,screenshotData1);
    }
}

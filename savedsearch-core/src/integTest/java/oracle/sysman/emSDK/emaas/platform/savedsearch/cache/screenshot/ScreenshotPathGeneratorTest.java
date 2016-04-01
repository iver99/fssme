package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class ScreenshotPathGeneratorTest {
    ScreenshotPathGenerator screenshotPathGenerator = ScreenshotPathGenerator.getInstance();

    @Test
    public void testGenerateFileName() throws Exception {
        Assert.assertTrue(screenshotPathGenerator.generateFileName(1234L,new Date(),new Date()) instanceof String);
        Assert.assertNull(screenshotPathGenerator.generateFileName(null,new Date(),new Date()));
        screenshotPathGenerator.generateFileName(1234L,null,new Date());
        screenshotPathGenerator.generateFileName(1234L,new Date(),null);
        screenshotPathGenerator.generateFileName(1234L,null,null);
    }

    @Test
    public void testGenerateScreenshotUrl() throws Exception {
        String baseUrl = "baseUrlxx";
        Long widgetId = 1234L;
        Date creation = new Date();
        Date modification = new Date();
        Assert.assertTrue(screenshotPathGenerator.generateScreenshotUrl(baseUrl,widgetId,creation,modification) instanceof String);
        Assert.assertNull(screenshotPathGenerator.generateScreenshotUrl("",widgetId,creation,modification));
        Assert.assertNull(screenshotPathGenerator.generateScreenshotUrl(baseUrl,null,creation,modification));
    }
}
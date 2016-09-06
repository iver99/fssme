package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class ScreenshotPathGeneratorTest {
    ScreenshotPathGenerator screenshotPathGenerator = ScreenshotPathGenerator.getInstance();
    Date now = new Date();

    @Test
    public void testGenerateFileName(){
        Assert.assertTrue(screenshotPathGenerator.generateFileName(1234L, now, now) instanceof String);
        Assert.assertNull(screenshotPathGenerator.generateFileName(null, now, now));
        screenshotPathGenerator.generateFileName(1234L,null, now);
        screenshotPathGenerator.generateFileName(1234L, now,null);
        screenshotPathGenerator.generateFileName(1234L,null,null);
    }

    @Test
    public void testGenerateScreenshotUrl(){
        String baseUrl = "baseUrlxx";
        Long widgetId = 1234L;
        Date creation = now;
        Date modification = now;
        Assert.assertTrue(screenshotPathGenerator.generateScreenshotUrl(baseUrl,widgetId,creation,modification) instanceof String);
        Assert.assertNull(screenshotPathGenerator.generateScreenshotUrl("",widgetId,creation,modification));
        Assert.assertNull(screenshotPathGenerator.generateScreenshotUrl(baseUrl,null,creation,modification));
    }

    @Test
    public void testValidFileName(){
        Assert.assertTrue(screenshotPathGenerator.validFileName(10000L, "20160821_10000.png","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(10000L, "","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(10000L, "invalid","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(10001L, "20160821_10000.png","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(10001L, "20160821_10000.png","20160921_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(10000L, "20160821_10000.png","invalid"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(10000L, "20160821_10000.png","invalid_10000.png"));
    }
}
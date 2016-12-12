package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class ScreenshotPathGeneratorTest {
	private BigInteger TEST_ID_10000 = new BigInteger("10000");
	private BigInteger TEST_ID_10001 = new BigInteger("10001");
    ScreenshotPathGenerator screenshotPathGenerator = ScreenshotPathGenerator.getInstance();
    Date now = new Date();

    @Test
    public void testGenerateFileName(){
        Assert.assertTrue(screenshotPathGenerator.generateFileName(BigInteger.ONE, now, now) instanceof String);
        Assert.assertNull(screenshotPathGenerator.generateFileName(null, now, now));
        screenshotPathGenerator.generateFileName(BigInteger.ONE, null, now);
        screenshotPathGenerator.generateFileName(BigInteger.ONE, now, null);
        screenshotPathGenerator.generateFileName(BigInteger.ONE, null, null);
    }

    @Test
    public void testGenerateScreenshotUrl(){
        String baseUrl = "baseUrlxx";
        BigInteger widgetId = BigInteger.ONE;
        Date creation = now;
        Date modification = now;
        Assert.assertTrue(screenshotPathGenerator.generateScreenshotUrl(baseUrl,widgetId,creation,modification) instanceof String);
        Assert.assertNull(screenshotPathGenerator.generateScreenshotUrl("",widgetId,creation,modification));
        Assert.assertNull(screenshotPathGenerator.generateScreenshotUrl(baseUrl,null,creation,modification));
    }

    @Test
    public void testValidFileName(){
        Assert.assertTrue(screenshotPathGenerator.validFileName(TEST_ID_10000, "20160821_10000.png","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(TEST_ID_10000, "","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(TEST_ID_10000, "invalid","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(TEST_ID_10001, "20160821_10000.png","20160721_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(TEST_ID_10001, "20160821_10000.png","20160921_10000.png"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(TEST_ID_10000, "20160821_10000.png","invalid"));
        Assert.assertFalse(screenshotPathGenerator.validFileName(TEST_ID_10000, "20160821_10000.png","invalid_10000.png"));
    }
}
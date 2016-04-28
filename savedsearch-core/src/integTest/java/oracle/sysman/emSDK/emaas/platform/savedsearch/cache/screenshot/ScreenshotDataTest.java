package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */

@Test(groups = {"s1"})
public class ScreenshotDataTest {
    ScreenshotData screenshotData;
    Date now = new Date();

    @BeforeMethod
    public void setUp() throws Exception {
        String screenshot = "screenshotxx";
        Date creationDate = now;
        Date modificationDate = now;
        screenshotData = new ScreenshotData(screenshot,creationDate,modificationDate);
    }

    @Test
    public void testGetCreationDate() throws Exception {
        screenshotData.setCreationDate(now);
        Assert.assertEquals(screenshotData.getCreationDate(), now);
    }

    @Test
    public void testGetModificationDate() throws Exception {
        screenshotData.setModificationDate(now);
        Assert.assertEquals(screenshotData.getModificationDate(), now);
    }

    @Test
    public void testGetScreenshot() throws Exception {
        String screenshot = "screenshotyy";
        screenshotData.setScreenshot(screenshot);
        Assert.assertEquals(screenshotData.getScreenshot(),screenshot);
    }
}
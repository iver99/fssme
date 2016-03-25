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

    @BeforeMethod
    public void setUp() throws Exception {
        String screenshot = "screenshotxx";
        Date creationDate = new Date();
        Date modificationDate = new Date();
        screenshotData = new ScreenshotData(screenshot,creationDate,modificationDate);
    }

    @Test
    public void testGetCreationDate() throws Exception {
        Date creationDate = new Date();
        screenshotData.setCreationDate(creationDate);
        Assert.assertEquals(screenshotData.getCreationDate(),creationDate);
    }

    @Test
    public void testGetModificationDate() throws Exception {
        Date modificationDate = new Date();
        screenshotData.setModificationDate(modificationDate);
        Assert.assertEquals(screenshotData.getModificationDate(),modificationDate);
    }

    @Test
    public void testGetScreenshot() throws Exception {
        String screenshot = "screenshotyy";
        screenshotData.setScreenshot(screenshot);
        Assert.assertEquals(screenshotData.getScreenshot(),screenshot);
    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import com.tangosol.util.Binary;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class ScreenshotElementTest {
    ScreenshotElement screenshotElement;

    @BeforeMethod
    public void setUp() throws Exception {
        String fileName = "fileNamexx";
        Binary buffer = null;
        screenshotElement = new ScreenshotElement(fileName,buffer);
    }

    @Test
    public void testGetBuffer() throws Exception {
        Binary buffer = new Binary();
        screenshotElement.setBuffer(buffer);
        Assert.assertEquals(screenshotElement.getBuffer(),buffer);
    }

    @Test
    public void testGetFileName() throws Exception {
        String fileName = "fileNameyy";
        screenshotElement.setFileName(fileName);
        Assert.assertEquals(screenshotElement.getFileName(),fileName);
    }
}
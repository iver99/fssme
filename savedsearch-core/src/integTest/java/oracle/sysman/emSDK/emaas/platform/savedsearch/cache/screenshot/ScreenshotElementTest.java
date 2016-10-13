package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/21.
 */
@Test(groups = {"s1"})
public class ScreenshotElementTest {
    private byte[] bytes = new byte[2];
    private Binary binary = new Binary(bytes);
    private String filename = "filename";
    private ScreenshotElement screenshotElement = new ScreenshotElement(filename,binary);
    @Test
    public void testGetBuffer() throws Exception {
        Assert.assertEquals(binary, screenshotElement.getBuffer());
    }

    @Test
    public void testGetFileName() throws Exception {
        Assert.assertEquals(filename, screenshotElement.getFileName());
    }

    @Test
    public void testSetBuffer() throws Exception {
        byte[] byteArrays = new byte[2];
        Binary binaryLocal = new Binary(byteArrays);
        screenshotElement.setBuffer(binaryLocal);
    }

    @Test
    public void testSetFileName() throws Exception {
        screenshotElement.setFileName("newfilename");
    }

}
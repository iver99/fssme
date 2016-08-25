package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/21.
 */
@Test(groups = {"s1"})
public class BinaryTest {
    private byte[] bytes = new byte[2];
    private Binary binary = new Binary(bytes);
    @Test
    public void testGetData() throws Exception {
        Assert.assertEquals(bytes, binary.getData());
    }

    @Test
    public void testSetData() throws Exception {
        binary.setData(bytes);
    }

}
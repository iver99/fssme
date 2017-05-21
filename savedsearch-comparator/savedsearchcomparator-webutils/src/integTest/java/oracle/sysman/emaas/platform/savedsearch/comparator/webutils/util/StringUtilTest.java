package oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util;

import org.testng.Assert;
import org.testng.annotations.Test;


@Test(groups = {"s2"})
public class StringUtilTest {

    @Test
    public void testStringUtil(){
        Assert.assertTrue(StringUtil.isEmpty(""));

        Assert.assertFalse(StringUtil.isEmpty("string"));
    }
}

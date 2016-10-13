package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/14.
 */

@Test(groups = {"s1"})
public class ParameterTypeTest {

    @Test
    public void testFromIntValue() {
        Assert.assertEquals(ParameterType.fromIntValue(1),ParameterType.STRING);
        Assert.assertEquals(ParameterType.fromIntValue(2),ParameterType.CLOB);
        Assert.assertNull(ParameterType.fromIntValue(3));
    }

    @Test
    public void testGetIntValue() {
        Assert.assertEquals(ParameterType.STRING.getIntValue(),1);
        Assert.assertEquals(ParameterType.CLOB.getIntValue(),2);
    }
}
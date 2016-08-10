package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/14.
 */

@Test(groups = {"s1"})
public class ParameterTest {
    Parameter parameter;

    @BeforeMethod
    public void setUp()  {
        parameter = new Parameter();
        parameter.setName("namexx");
        parameter.setType(ParameterType.STRING);
        parameter.setValue("valuexx");
    }

    @Test
    public void testEquals()  {
        Parameter param2 = new Parameter();
        Assert.assertFalse(param2.equals(parameter));

        Assert.assertTrue(parameter.equals(parameter));

        Assert.assertTrue(parameter.equals("namexx"));
    }

    @Test
    public void testGetName()  {
        Assert.assertEquals(parameter.getName(),"namexx");
    }

    @Test
    public void testGetType()  {
        Assert.assertTrue(parameter.getType() instanceof ParameterType);
    }

    @Test
    public void testGetValue()  {
        Assert.assertEquals(parameter.getValue(),"valuexx");
    }
}
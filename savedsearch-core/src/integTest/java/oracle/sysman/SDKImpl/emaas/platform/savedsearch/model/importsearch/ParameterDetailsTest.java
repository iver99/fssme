package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = {"s1"})
public class ParameterDetailsTest {

    ParameterDetails parameterDetails;

    @Test
    public void testGetName() throws Exception {
        parameterDetails = new ParameterDetails();
        parameterDetails.setName("namexx");
        Assert.assertEquals(parameterDetails.getName(),"namexx");
    }

    @Test
    public void testGetValue() throws Exception {
        parameterDetails = new ParameterDetails();
        parameterDetails.setValue("valuexx");
        Assert.assertEquals(parameterDetails.getValue(),"valuexx");
    }
}
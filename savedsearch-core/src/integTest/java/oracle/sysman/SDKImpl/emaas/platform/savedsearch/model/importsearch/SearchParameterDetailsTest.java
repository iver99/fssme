package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = {"s1"})
public class SearchParameterDetailsTest {

    SearchParameterDetails searchParameterDetails;
    @Test
    public void testEquals() throws Exception {
        searchParameterDetails = new SearchParameterDetails();
        searchParameterDetails.setName("namexx");

        Parameter parameter = new Parameter();
        parameter.setName("namexx");
        Assert.assertTrue(searchParameterDetails.equals(parameter));

        String str = new String("namexx");
        Assert.assertTrue(searchParameterDetails.equals(str));

        Assert.assertFalse(searchParameterDetails.equals(searchParameterDetails));

    }

    @Test
    public void testGetName() throws Exception {
        searchParameterDetails = new SearchParameterDetails();
        searchParameterDetails.setName("namexx");
        Assert.assertEquals(searchParameterDetails.getName(),"namexx");
    }

    @Test
    public void testGetType() throws Exception {
        searchParameterDetails = new SearchParameterDetails();
        searchParameterDetails.setType(ParameterType.STRING);
        Assert.assertEquals(searchParameterDetails.getType(),ParameterType.STRING);
    }

    @Test
    public void testGetValue() throws Exception {
        searchParameterDetails = new SearchParameterDetails();
        searchParameterDetails.setValue("valuexx");
        Assert.assertEquals(searchParameterDetails.getValue(),"valuexx");
    }

    @Test
    public void testGetAttributes() throws Exception {
        searchParameterDetails = new SearchParameterDetails();
        searchParameterDetails.setAttributes("attributesxx");
        Assert.assertEquals(searchParameterDetails.getAttributes(),"attributesxx");
    }
}
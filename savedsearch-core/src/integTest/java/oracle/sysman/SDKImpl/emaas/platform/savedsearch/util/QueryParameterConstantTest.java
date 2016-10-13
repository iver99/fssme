package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author  jishshi
 * @since  2016/2/29.
 */
@Test(groups = {"s1"})
public class QueryParameterConstantTest {

    @Test
    public void testUserName() throws Exception {
        Assert.assertEquals(QueryParameterConstant.USER_NAME,"userName");
    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = {"s1"})
public class ValidationUtilTest {

    @Test
    public void testValidateLength() throws EMAnalyticsWSException {
        ValidationUtil.validateLength(null, null, 0);

        ValidationUtil.validateLength("name", "a", 1);

    }
}
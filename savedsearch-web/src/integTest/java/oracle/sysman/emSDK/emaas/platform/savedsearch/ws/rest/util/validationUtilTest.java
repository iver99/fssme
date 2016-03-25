package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups={"s1"})
public class validationUtilTest {
    private validationUtil validationUtil = new validationUtil();

    @Test
    public void testValidateLength() throws Exception {
        oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.validationUtil.validateLength(null,null,0);
        try {
            oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.validationUtil.validateLength("name", "value", 1);
        }catch(EMAnalyticsWSException e){

        }
    }
}
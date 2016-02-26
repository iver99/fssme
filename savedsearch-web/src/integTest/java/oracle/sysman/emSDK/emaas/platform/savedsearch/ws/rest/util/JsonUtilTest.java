package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups={"s1"})
public class JsonUtilTest {
    JsonUtil jsonUtil = JsonUtil.buildNormalMapper();

    @Test
    public void testFromJson() throws Exception {
        Assert.assertNull(jsonUtil.fromJson(null,String.class));
    }

    @Test
    public void testToJson() throws Exception {
        Assert.assertNull(jsonUtil.toJson(new Object()));

    }
}
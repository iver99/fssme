package oracle.sysman.emSDK.emaas.platform.savedsearch.common;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/23.
 */
@Test(groups = {"s2"})
public class ExecutionContextTest {
    @Mocked
    TenantContext tenantContext;
    @Mocked
    TenantInfo tenantInfo;
    @Test
    public void testGetCurrentUser() throws Exception {
        ExecutionContext executionContext = ExecutionContext.getExecutionContext();
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "Oracle";
            }
        };
        Assert.assertEquals("Oracle", executionContext.getCurrentUser());

    }

}
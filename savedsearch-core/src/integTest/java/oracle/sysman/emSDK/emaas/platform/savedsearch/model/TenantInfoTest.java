package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author jishshi
 * @since 2016/3/1.
 */
@Test(groups = {"s1"})
public class TenantInfoTest {
    TenantInfo tenantInfo;

    @BeforeMethod
    public void setUp() throws Exception {
        tenantInfo = new TenantInfo(null,null);
    }

    @Test
    public void testGetTenantInternalId() throws Exception {
        Long id = 1l;
        tenantInfo.setTenantInternalId(id);
        Assert.assertEquals(tenantInfo.getTenantInternalId(),id);
    }

    @Test
    public void testGettenantName() throws Exception {
        String name = "tenantName";
        tenantInfo.settenantName(name);
        Assert.assertEquals(tenantInfo.gettenantName(),name);
    }

    @Test
    public void testGetUsername() throws Exception {
        String name = "userName";
        tenantInfo.setUsername(name);
        Assert.assertEquals(tenantInfo.getUsername(),name);
    }

    @Test
    public void testToString() throws Exception {
        tenantInfo = new TenantInfo("user",1l,"tenantName");
        Assert.assertEquals(tenantInfo.toString().trim(),"[TenantName:][InternalTenantId:1][UserName:user]");
    }
}
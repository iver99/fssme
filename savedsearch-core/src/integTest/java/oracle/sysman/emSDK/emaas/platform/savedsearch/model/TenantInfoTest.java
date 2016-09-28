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
    public void setUp() {
        tenantInfo = new TenantInfo(null,null);
    }

    @Test
    public void testGetTenantInternalId() {
        Long id = 1L;
        tenantInfo.setTenantInternalId(id);
        Assert.assertEquals(tenantInfo.getTenantInternalId(),id);
    }

    @Test
    public void testGettenantName() {
        String name = "tenantName";
        tenantInfo.settenantName(name);
        Assert.assertEquals(tenantInfo.gettenantName(),name);
    }

    @Test
    public void testGetUsername() {
        String name = "userName";
        tenantInfo.setUsername(name);
        Assert.assertEquals(tenantInfo.getUsername(),name);
    }

    @Test
    public void testToString() {
        tenantInfo = new TenantInfo("user",1L,"tenantName");
        Assert.assertEquals(tenantInfo.toString().trim(),"[TenantName:tenantName][InternalTenantId:1][UserName:user]");
    }
}
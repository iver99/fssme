package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class TenantTest {

    Tenant tenant1,tenant2;
    @Test
    public void testEquals() throws Exception {
        tenant1 = new Tenant(1234L);
        Assert.assertTrue(tenant1.equals(tenant1));
        Assert.assertFalse(tenant1.equals(new String("ee")));
        tenant2 = new Tenant(1234L,"tenantnamexx");
        Assert.assertFalse(tenant1.equals(tenant2));
        tenant2 = new Tenant(5678L);
        Assert.assertFalse(tenant1.equals(tenant2));
        tenant1.setTenantId(null);
        Assert.assertFalse(tenant1.equals(tenant2));
        tenant1.setTenantId(5678L);
        Assert.assertTrue(tenant1.equals(tenant2));
        tenant1 = new Tenant(1234L,"tenantnamexx");
        tenant2 = new Tenant(1234L,"tenantnameyy");
        Assert.assertFalse(tenant1.equals(tenant2));
    }

    @Test
    public void testGetTenantId() throws Exception {
        tenant1 = new Tenant(1234L);
        Assert.assertEquals(tenant1.getTenantId(),(Long)1234L);
        tenant1.setTenantId(5678L);
        Assert.assertEquals(tenant1.getTenantId(),(Long)5678L);
    }

    @Test
    public void testGetTenantName() throws Exception {
        tenant1 = new Tenant("tenantnamexx");
        Assert.assertEquals(tenant1.getTenantName(),"tenantnamexx");
        tenant1.setTenantName("tenantnameyy");
        Assert.assertEquals(tenant1.getTenantName(),"tenantnameyy");
    }

    @Test
    public void testHashCode() throws Exception {
        tenant1 = new Tenant(1234L,"tenantnamexx");
        Assert.assertEquals(tenant1.hashCode(),-1859484814);
    }

    @Test
    public void testToString() throws Exception {
        tenant1 = new Tenant(1234L,"tenantnamexx");
        tenant1.toString();
        tenant1 = new Tenant(null,null);
        tenant1.toString();
    }
}
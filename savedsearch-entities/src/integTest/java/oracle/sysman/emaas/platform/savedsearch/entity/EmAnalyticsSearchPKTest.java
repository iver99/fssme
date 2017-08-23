package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups={"s1"})
public class EmAnalyticsSearchPKTest {
    
    @Test
    public void testEmAnalyticsSearchPK() {
        EmAnalyticsSearchPK pk = new EmAnalyticsSearchPK(1L, BigInteger.ONE);
        pk.setSearchId(BigInteger.ONE);
        pk.setTenantId(1L);
        Assert.assertEquals(pk.getTenantId(), new Long(1L));
        Assert.assertEquals(pk.getSearchId(), BigInteger.ONE);
    }
    
    @Test
    public void testEquals() {
        EmAnalyticsSearchPK pk = new EmAnalyticsSearchPK(1L, BigInteger.ONE);
        EmAnalyticsSearchPK same = new EmAnalyticsSearchPK(1L, BigInteger.ONE);
        EmAnalyticsSearchPK dif = new EmAnalyticsSearchPK(2L, BigInteger.ONE);
        
        Assert.assertTrue(pk.equals(same));
        Assert.assertFalse(pk.equals(dif));
    }
}

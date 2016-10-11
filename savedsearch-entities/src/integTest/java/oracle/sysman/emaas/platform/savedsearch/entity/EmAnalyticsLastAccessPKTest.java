package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test (groups = {"s1"})
public class EmAnalyticsLastAccessPKTest {
    private EmAnalyticsLastAccessPK emAnalyticsLastAccessPK;

    @BeforeClass
    public void setUp() {
        emAnalyticsLastAccessPK = new EmAnalyticsLastAccessPK();
        emAnalyticsLastAccessPK.setAccessedBy("accessedBy1");
        emAnalyticsLastAccessPK.setObjectId(BigInteger.ONE);
        emAnalyticsLastAccessPK.setObjectType(1L);
    }

    @Test (groups = {"s1"})
    public void testEquals() {
        Assert.assertTrue(emAnalyticsLastAccessPK.equals(emAnalyticsLastAccessPK));

        Assert.assertFalse(emAnalyticsLastAccessPK.equals(new String("astring")));

        EmAnalyticsLastAccessPK emAnalyticsLastAccessPK2 = new EmAnalyticsLastAccessPK();
        emAnalyticsLastAccessPK2.setAccessedBy("accessedBy1");
        emAnalyticsLastAccessPK2.setObjectId(BigInteger.ONE);
        emAnalyticsLastAccessPK2.setObjectType(1L);
        Assert.assertTrue(emAnalyticsLastAccessPK.equals(emAnalyticsLastAccessPK2));
    }

    @Test (groups = {"s1"})
    public void testGetAccessedBy() {
        Assert.assertEquals("accessedBy1",emAnalyticsLastAccessPK.getAccessedBy());
    }

    @Test (groups = {"s1"})
    public void testGetObjectId() {
        Assert.assertEquals(BigInteger.ONE, emAnalyticsLastAccessPK.getObjectId());
    }

    @Test (groups = {"s1"})
    public void testGetObjectType() {
        Assert.assertEquals(1L,emAnalyticsLastAccessPK.getObjectType());
    }

    @Test (groups = {"s1"})
    public void testHashCode() {
        Assert.assertEquals(emAnalyticsLastAccessPK.hashCode(), -1782757767);
    }
}
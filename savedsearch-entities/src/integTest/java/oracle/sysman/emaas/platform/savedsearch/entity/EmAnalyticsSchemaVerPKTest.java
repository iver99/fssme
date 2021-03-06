package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test (groups = {"s1"})
public class EmAnalyticsSchemaVerPKTest {
    private EmAnalyticsSchemaVerPK emAnalyticsSchemaVerPK;

    @BeforeClass
    public void setUp(){
        emAnalyticsSchemaVerPK = new EmAnalyticsSchemaVerPK();
        emAnalyticsSchemaVerPK.setMajor(111L);
        emAnalyticsSchemaVerPK.setMinor(222L);
    }

    @Test (groups = {"s1"})
    public void testEquals(){
        Assert.assertTrue(emAnalyticsSchemaVerPK.equals(emAnalyticsSchemaVerPK));

        Assert.assertFalse(emAnalyticsSchemaVerPK.equals(new String("astring")));

        EmAnalyticsSchemaVerPK emAnalyticsSchemaVerPK2 = new EmAnalyticsSchemaVerPK();
        emAnalyticsSchemaVerPK2.setMajor(333L);
        emAnalyticsSchemaVerPK2.setMinor(222L);
        Assert.assertFalse(emAnalyticsSchemaVerPK2.equals(emAnalyticsSchemaVerPK));
    }

    @Test (groups = {"s1"})
    public void testGetMajor(){
        Assert.assertEquals(111L,emAnalyticsSchemaVerPK.getMajor());
    }

    @Test (groups = {"s1"})
    public void testGetMinor(){
        Assert.assertEquals(222L,emAnalyticsSchemaVerPK.getMinor());
    }

    @Test (groups = {"s1"})
    public void testHashCode(){
        Assert.assertEquals(emAnalyticsSchemaVerPK.hashCode(),20000);
    }
}
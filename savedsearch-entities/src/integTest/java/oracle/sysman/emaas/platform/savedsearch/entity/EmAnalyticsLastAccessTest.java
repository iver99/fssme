package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test (groups = {"s1"})
public class EmAnalyticsLastAccessTest {
    private EmAnalyticsLastAccess emAnalyticsLastAccess;
    private EmAnalyticsLastAccess emAnalyticsLastAccess1;

    @BeforeClass
    public void setUp(){
        emAnalyticsLastAccess = new EmAnalyticsLastAccess();
        emAnalyticsLastAccess1 = new EmAnalyticsLastAccess(BigInteger.ONE,"accessedBy2",2L);
    }

    @Test (groups = {"s1"})
    public void testGetAccessDate(){
        Date now = new Date();
        emAnalyticsLastAccess.setAccessDate(now);
        Assert.assertEquals(now,emAnalyticsLastAccess.getAccessDate());
    }

    @Test (groups = {"s1"})
    public void testGetAccessedBy(){
        Assert.assertEquals("accessedBy2",emAnalyticsLastAccess1.getAccessedBy());
    }

    @Test (groups = {"s1"})
    public void testGetObjectId() {
        Assert.assertEquals(BigInteger.ONE, emAnalyticsLastAccess1.getObjectId());
    }

    @Test (groups = {"s1"})
    public void testGetObjectType(){
        Assert.assertEquals(2L,emAnalyticsLastAccess1.getObjectType());
    }
}
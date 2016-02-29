package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test (groups = {"s1"})
public class EmAnalyticsLastAccessTest {
    private EmAnalyticsLastAccess emAnalyticsLastAccess;
    private EmAnalyticsLastAccess emAnalyticsLastAccess1;

    @BeforeClass
    public void setUp() throws Exception {
        emAnalyticsLastAccess = new EmAnalyticsLastAccess();
        emAnalyticsLastAccess1 = new EmAnalyticsLastAccess(222L,"accessedBy2",2L);
    }

    @Test (groups = {"s1"})
    public void testGetAccessDate() throws Exception {
        Date now = new Date();
        emAnalyticsLastAccess.setAccessDate(now);
        Assert.assertEquals(now,emAnalyticsLastAccess.getAccessDate());
    }

    @Test (groups = {"s1"})
    public void testGetAccessedBy() throws Exception {
        Assert.assertEquals("accessedBy2",emAnalyticsLastAccess1.getAccessedBy());
    }

    @Test (groups = {"s1"})
    public void testGetObjectId() throws Exception {
        Assert.assertEquals(222L,emAnalyticsLastAccess1.getObjectId());
    }

    @Test (groups = {"s1"})
    public void testGetObjectType() throws Exception {
        Assert.assertEquals(2L,emAnalyticsLastAccess1.getObjectType());
    }
}
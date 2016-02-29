package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test (groups = {"s1"})
public class EmAnalyticsSchemaVerTest {
    private EmAnalyticsSchemaVer emAnalyticsSchemaVer;

    @BeforeClass
    public void setUp() throws Exception {
        emAnalyticsSchemaVer = new EmAnalyticsSchemaVer();
    }

    @Test (groups = {"s1"})
    public void testGetId() throws Exception {
        EmAnalyticsSchemaVerPK id = new EmAnalyticsSchemaVerPK();
        emAnalyticsSchemaVer.setId(id);
        Assert.assertEquals(id,emAnalyticsSchemaVer.getId());
    }
}
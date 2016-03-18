package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s1"})
public class EmAnalyticsSearchLastAccessTest {
    private EmAnalyticsSearchLastAccess emAnalyticsSearchLastAccess;
    private EmAnalyticsSearchLastAccess emAnalyticsSearchLastAccess2;

    @BeforeClass
    public void setUp() throws Exception {
        emAnalyticsSearchLastAccess = new EmAnalyticsSearchLastAccess();
        emAnalyticsSearchLastAccess2 = new EmAnalyticsSearchLastAccess(123412341234L,"accessedBy2");
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsSearch() throws Exception {
        EmAnalyticsSearch emAnalyticsSearch = new EmAnalyticsSearch();
        emAnalyticsSearchLastAccess.setEmAnalyticsSearch(emAnalyticsSearch);
        Assert.assertEquals(emAnalyticsSearch,emAnalyticsSearchLastAccess.getEmAnalyticsSearch());
    }
}
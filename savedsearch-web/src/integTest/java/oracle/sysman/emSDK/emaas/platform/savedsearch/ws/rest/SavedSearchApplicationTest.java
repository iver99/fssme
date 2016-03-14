package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups={"s1"})
public class SavedSearchApplicationTest {
    private SavedSearchApplication savedSearchApplication = new SavedSearchApplication();
    @Test
    public void testGetClasses() throws Exception {
        Assert.assertNotNull(savedSearchApplication.getClasses());
    }
}
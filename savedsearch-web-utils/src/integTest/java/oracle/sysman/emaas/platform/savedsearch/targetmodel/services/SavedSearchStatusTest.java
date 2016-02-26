package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.utils.GlobalStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-24.
 */
public class SavedSearchStatusTest {
    SavedSearchStatus savedSearchStatus;

    @Test
    public void testGetStatus_StatusUp() throws Exception {
        savedSearchStatus = new SavedSearchStatus();
        Assert.assertTrue(savedSearchStatus.getStatus() instanceof String);
    }

    @Test
    public void testGetStatus_StatusDown(@Mocked final GlobalStatus globalStatus) throws Exception {
        new Expectations(){
            {
                globalStatus.isSavedSearchUp();
                result = false;
            }
        };
        savedSearchStatus = new SavedSearchStatus();
        Assert.assertTrue(savedSearchStatus.getStatus() instanceof String);
    }

    @Test
    public void testGetStatus_StatusOutOfService(@Mocked final GlobalStatus globalStatus) throws Exception {
        new Expectations(){
            {
                globalStatus.isSavedSearchUp();
                returns(true,false);
            }
        };
        savedSearchStatus = new SavedSearchStatus();
        Assert.assertTrue(savedSearchStatus.getStatus() instanceof String);
    }

    @Test
    public void testGetStatusMsg_UpAndRunning() throws Exception {
        savedSearchStatus = new SavedSearchStatus();
        Assert.assertEquals(savedSearchStatus.getStatusMsg(),"SavedSearch is up and running.");
    }

    @Test
    public void testGetStatusMsg_Stopped(@Mocked final GlobalStatus globalStatus) throws Exception {
        new Expectations(){
            {
                globalStatus.isSavedSearchUp();
                result = false;
            }
        };
        savedSearchStatus = new SavedSearchStatus();
        Assert.assertEquals(savedSearchStatus.getStatusMsg(),"SavedSearch is being stopped.");
    }

    @Test
    public void testGetStatusMsg_OutOfService(@Mocked final GlobalStatus globalStatus) throws Exception {
        new Expectations(){
            {
                globalStatus.isSavedSearchUp();
                returns(true,false);
            }
        };
        savedSearchStatus = new SavedSearchStatus();
        Assert.assertEquals(savedSearchStatus.getStatusMsg(),"SavedSearch is out of service.");
    }
}
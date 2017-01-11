package oracle.sysman.emaas.platform.savedsearch.comparator.test.targetmodel.services;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services.GlobalStatus;
import oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services.SavedsearchComparatorStatus;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test(groups = {"s2"})
public class SavedSearchStatusTest {
    SavedsearchComparatorStatus savedSearchStatus;

    @Test
    public void testGetStatus_StatusUp(){
        savedSearchStatus = new SavedsearchComparatorStatus();
        Assert.assertTrue(savedSearchStatus.getStatus() instanceof String);
    }

    @Test
    public void testGetStatus_StatusDown(@Mocked final GlobalStatus globalStatus){
        new Expectations(){
            {
                globalStatus.issavedsearchComparatorUp();
                result = false;
            }
        };
        savedSearchStatus = new SavedsearchComparatorStatus();
        Assert.assertTrue(savedSearchStatus.getStatus() instanceof String);
    }

    @Test
    public void testGetStatus_StatusOutOfService(@Mocked final GlobalStatus globalStatus){
        new Expectations(){
            {
                globalStatus.issavedsearchComparatorUp();
                returns(true,false);
            }
        };
        savedSearchStatus = new SavedsearchComparatorStatus();
        Assert.assertTrue(savedSearchStatus.getStatus() instanceof String);
    }

    @Test
    public void testGetStatusMsg_UpAndRunning(){
        savedSearchStatus = new SavedsearchComparatorStatus();
        Assert.assertEquals(savedSearchStatus.getStatusMsg(),"Savedsearch-Comparator is up and running.");
    }

    @Test
    public void testGetStatusMsg_Stopped(@Mocked final GlobalStatus globalStatus){
        new Expectations(){
            {
                globalStatus.issavedsearchComparatorUp();
                result = false;
            }
        };
        savedSearchStatus = new SavedsearchComparatorStatus();
        Assert.assertEquals(savedSearchStatus.getStatusMsg(),"Savedsearch-Comparator is being stopped.");
    }

    @Test
    public void testGetStatusMsg_OutOfService(@Mocked final GlobalStatus globalStatus){
        new Expectations(){
            {
                globalStatus.issavedsearchComparatorUp();
                returns(true,false);
            }
        };
        savedSearchStatus = new SavedsearchComparatorStatus();
        Assert.assertEquals(savedSearchStatus.getStatusMsg(),"Savedsearch-Comparator is out of service.");
    }
}
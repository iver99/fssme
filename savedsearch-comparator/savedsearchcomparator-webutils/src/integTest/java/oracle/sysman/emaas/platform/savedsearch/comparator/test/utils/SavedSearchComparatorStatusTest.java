package oracle.sysman.emaas.platform.savedsearch.comparator.test.utils;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services.GlobalStatus;
import oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services.SavedsearchComparatorStatus;

import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class SavedSearchComparatorStatusTest {
	 @Mocked
	    GlobalStatus globalStatus;
	    private SavedsearchComparatorStatus dashboardComparatorStatus = new SavedsearchComparatorStatus();
	    @Test
	    public void testGetStatus(){
	        new Expectations(){
	            {
	                globalStatus.issavedsearchComparatorUp();
	                result = false;
	            }
	        };
	        dashboardComparatorStatus.getStatus();
	        dashboardComparatorStatus.getStatusMsg();
	        dashboardComparatorStatus.equals(dashboardComparatorStatus);
	    }
	    @Test
	    public void testGetStatus2nd(){
	        new Expectations(){
	            {
	                globalStatus.issavedsearchComparatorUp();
	                result = true;
	            }
	        };
	        dashboardComparatorStatus.getStatus();
	        dashboardComparatorStatus.getStatusMsg();
	        dashboardComparatorStatus.equals(dashboardComparatorStatus);
	    }
	   

}

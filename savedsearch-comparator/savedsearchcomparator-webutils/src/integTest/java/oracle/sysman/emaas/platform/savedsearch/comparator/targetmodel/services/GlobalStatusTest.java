package oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services;

import org.testng.annotations.Test;


@Test(groups = {"s2"})
public class GlobalStatusTest {

    @Test
    public void testIsSavedsearchComparatorUp(){
        GlobalStatus.setSavedsearchComparatorUpStatus();
    }

    @Test
    public void testSetSavedsearchComparatorDownStatus(){
        GlobalStatus.setSavedsearchComparatorDownStatus();
    }
    @Test
    public void testSetSavedsearchComparatorUpStatus(){
        GlobalStatus.isSavedsearchComparatorUp();
    }
}

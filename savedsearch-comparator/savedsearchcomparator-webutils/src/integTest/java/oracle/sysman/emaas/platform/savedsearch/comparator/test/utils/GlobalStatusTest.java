package oracle.sysman.emaas.platform.savedsearch.comparator.test.utils;

import oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services.GlobalStatus;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test(groups = {"s1"})
public class GlobalStatusTest {

    GlobalStatus globalStatus;

    @Test
    public void testIsSavedSearchUp() throws Exception {
        globalStatus.setsavedsearchComparatorDownStatus();
        Assert.assertEquals(globalStatus.issavedsearchComparatorUp(),false);

        globalStatus.setsavedsearchComparatorUpStatus();
        Assert.assertEquals(globalStatus.issavedsearchComparatorUp(),true);

    }

}
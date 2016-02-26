package oracle.sysman.emaas.platform.savedsearch.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-24.
 */
public class GlobalStatusTest {

    GlobalStatus globalStatus;

    @Test
    public void testIsSavedSearchUp() throws Exception {
        globalStatus = new GlobalStatus();
        globalStatus.setSavedSearchDownStatus();
        Assert.assertEquals(globalStatus.isSavedSearchUp(),false);

        globalStatus.setSavedSearchUpStatus();
        Assert.assertEquals(globalStatus.isSavedSearchUp(),true);

    }

}
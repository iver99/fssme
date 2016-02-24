package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/19/2016.
 */
public class UpdatedLoggerLevelTest {
    private UpdatedLoggerLevel updatedLoggerLevel = new UpdatedLoggerLevel();
    @Test
    public void testGetLevel() throws Exception {
        String level = "level";
        updatedLoggerLevel.setLevel(level);
        Assert.assertEquals(level,updatedLoggerLevel.getLevel());

    }

//    @Test
//    public void testSetLevel() throws Exception {
//
//    }
}
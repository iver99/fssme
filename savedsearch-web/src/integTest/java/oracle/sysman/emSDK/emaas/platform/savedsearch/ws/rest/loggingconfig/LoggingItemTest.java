package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import org.apache.logging.log4j.core.config.LoggerConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups={"s1"})
public class LoggingItemTest {
    private LoggerConfig loggerConfig = new LoggerConfig();
    private LoggingItem loggingItem = new LoggingItem(new LoggerConfig(),123456L);

    @Test
    public void testGetCurrentLoggingLevel() throws Exception {
        loggingItem.setCurrentLoggingLevel("level");
        Assert.assertEquals("level",loggingItem.getCurrentLoggingLevel());
    }

    @Test
    public void testGetLastUpdatedEpoch() throws Exception {
        loggingItem.setLastUpdatedEpoch(1234L);
        Assert.assertEquals((Long)1234L,loggingItem.getLastUpdatedEpoch());
    }

    @Test
    public void testGetLoggerName() throws Exception {
        loggingItem.setLoggerName("name");
        Assert.assertEquals("name",loggingItem.getLoggerName());

    }

//    @Test
//    public void testSetCurrentLoggingLevel() throws Exception {
//
//
//    }
//
//    @Test
//    public void testSetLastUpdatedEpoch() throws Exception {
//
//    }
//
//    @Test
//    public void testSetLoggerName() throws Exception {
//
//    }
}
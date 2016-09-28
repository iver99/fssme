package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import org.apache.logging.log4j.core.config.LoggerConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups={"s1"})
public class LoggingItemTest {
    private LoggingItem loggingItem = new LoggingItem(new LoggerConfig(),123456L);

    @Test
    public void testGetCurrentLoggingLevel()  {
        loggingItem.setCurrentLoggingLevel("level");
        Assert.assertEquals("level",loggingItem.getCurrentLoggingLevel());
    }

    @Test
    public void testGetLastUpdatedEpoch()  {
        loggingItem.setLastUpdatedEpoch(1234L);
        Assert.assertEquals((Long)1234L,loggingItem.getLastUpdatedEpoch());
    }

    @Test
    public void testGetLoggerName()  {
        loggingItem.setLoggerName("name");
        Assert.assertEquals("name",loggingItem.getLoggerName());

    }

//    @Test
//    public void testSetCurrentLoggingLevel()  {
//
//
//    }
//
//    @Test
//    public void testSetLastUpdatedEpoch()  {
//
//    }
//
//    @Test
//    public void testSetLoggerName()  {
//
//    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import org.apache.logging.log4j.core.config.LoggerConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups = {"s1"})
public class LoggingItemsTest {
    private LoggingItems loggingItems = new LoggingItems();

    @Test
    public void testAddLoggerConfig() throws Exception {
        LoggerConfig loggerConfig = new LoggerConfig();
        Long timestamp = 1234L;
        loggingItems.addLoggerConfig(loggerConfig, timestamp);
    }

    public void testAddLoggerConfig2nd() throws Exception {
        LoggerConfig loggerConfig = null;
        Long timestamp = 1234L;
        loggingItems.addLoggerConfig(loggerConfig, timestamp);
    }

    @Test
    public void testGetItems() throws Exception {
        LoggerConfig loggerConfig = new LoggerConfig();
        Long timestamp = 1234L;
        List<LoggingItem> list = new ArrayList<LoggingItem>();
        for (int i = 0; i < 3; i++) list.add(new LoggingItem(loggerConfig, timestamp));
        loggingItems.setItems(list);
        Assert.assertEquals(3, loggingItems.getItems().size());
    }

    @Test
    public void testGetTotal() throws Exception {
        loggingItems.setTotal(10);
        Assert.assertEquals(10, loggingItems.getTotal());
    }
}

//    @Test
//    public void testSetItems() throws Exception {
//
//    }
//
//    @Test
//    public void testSetTotal() throws Exception {
//
//    }
//}
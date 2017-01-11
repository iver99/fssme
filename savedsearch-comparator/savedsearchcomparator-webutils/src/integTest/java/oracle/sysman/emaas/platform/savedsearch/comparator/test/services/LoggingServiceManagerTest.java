package oracle.sysman.emaas.platform.savedsearch.comparator.test.services;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.services.LoggingServiceManager;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.LogUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import weblogic.application.ApplicationLifecycleEvent;

/**
 * @author qianqi
 * @since 16-2-22.
 */
@Test (groups = {"s2"})
public class LoggingServiceManagerTest {
    LoggingServiceManager loggingServiceManager;

    @Mocked
    Logger logger;

    @Mocked
    LogManager logManager;

    @Mocked
    LogUtil logUtil;

    @Test
    public void testGetName() throws Exception {
        loggingServiceManager = new LoggingServiceManager();
        Assert.assertEquals(loggingServiceManager.getName(), "Savedsearch Service Comparator Logging Service");
    }

    @Test
    public void testPostStart() throws Exception {
        loggingServiceManager = new LoggingServiceManager();
        loggingServiceManager.postStart(new ApplicationLifecycleEvent(null,null,false));
        // cover  InstanceAlreadyExistsException
        loggingServiceManager.postStart(new ApplicationLifecycleEvent(null,null,false));
    }

    @Test
    public void testPostStop() throws Exception {
        loggingServiceManager = new LoggingServiceManager();
        loggingServiceManager.postStop(new ApplicationLifecycleEvent(null,null,false));
    }



    @Test
    public void testPreStop() throws Exception {
        loggingServiceManager = new LoggingServiceManager();
        loggingServiceManager.preStop(new ApplicationLifecycleEvent(null,null,false));

        Deencapsulation.setField(loggingServiceManager, "tempMBeanExists", true);
        loggingServiceManager.preStop(new ApplicationLifecycleEvent(null,null,false));

    }

}
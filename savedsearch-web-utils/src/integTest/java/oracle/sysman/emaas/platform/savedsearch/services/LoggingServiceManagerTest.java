package oracle.sysman.emaas.platform.savedsearch.services;

import com.sun.jmx.mbeanserver.JmxMBeanServer;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.DeploymentOperationType;

import java.net.URL;

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
        Assert.assertEquals(loggingServiceManager.getName(), "Saved Search Logging Service");
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
    public void testPreStart() throws Exception {
        loggingServiceManager = new LoggingServiceManager();
        loggingServiceManager.preStart(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStop(@Mocked final JmxMBeanServer jmxMBeanServer) throws Exception {
        new Expectations() {
            {
            //   MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); returns instance is of JmxMBeanServer
                logger.info(anyString);
                times = 4;
            }
        };

        loggingServiceManager = new LoggingServiceManager();
        loggingServiceManager.preStop(new ApplicationLifecycleEvent(null,null,false));

        Deencapsulation.setField(loggingServiceManager, "tempMBeanExists", true);
        loggingServiceManager.preStop(new ApplicationLifecycleEvent(null,null,false));

    }

}
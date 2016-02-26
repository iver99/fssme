package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import oracle.sysman.emaas.platform.savedsearch.utils.JMXUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;

import javax.management.*;
import javax.naming.InitialContext;
import java.lang.management.ManagementFactory;

/**
 * @author qianqi
 * @since 16-2-22.
 */
@Test (groups = {"s2"})
public class EMTargetInitializerTest {
    EMTargetInitializer emTargetInitializer;

    @Mocked
    Logger logger;

    @Mocked
    LogManager logManager;

    @BeforeMethod
    public void setUp() throws Exception {
        new NonStrictExpectations(){
            {
                logManager.getLogger(EMTargetInitializer.class);
                result = logger;
            }
        };

    }

    @Test
    public void testGetName() throws Exception {
        emTargetInitializer = new EMTargetInitializer();
        Assert.assertEquals("Saved Search Target Initializer",emTargetInitializer.getName());
    }

    @Test
    public void testPostStart_Exception(@Mocked final MBeanServer mbs,@Mocked final Exception e) throws Exception {
        emTargetInitializer = new EMTargetInitializer();
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPostStart_noneExceptionAndInstanceAlreadyExistsException(@Mocked final MBeanServer mbs, @Mocked final InitialContext initialContext, @Mocked final InstanceAlreadyExistsException e) throws Exception {
        new Expectations(){
            {
                InitialContext.doLookup(anyString);
                result = "xxxx";
                times = 2;
//  ?              logger.error(anyString,e);
//                times = 1;
            }
        };
        emTargetInitializer = new EMTargetInitializer();

        //none exception
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null));

        //InstanceAlreadyExistsException
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null));

    }

    @Test
    public void testPostStart_MalformedObjectNameException(@Mocked final MBeanServer mbs,@Mocked final InitialContext initialContext) throws Exception {
        new Expectations(){
            {
                InitialContext.doLookup(anyString);
                result = new MalformedObjectNameException();
                times = 1;
            }
        };
        emTargetInitializer = new EMTargetInitializer();
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null));
    }



    @Test
    public void testPostStop() throws Exception {
        emTargetInitializer.postStop(null);
    }

    @Test
    public void testPreStart() throws Exception {
        emTargetInitializer.preStart(null);
    }


    @Test
    public void testPreStop_isRegistered(@Mocked final ManagementFactory managementFactory, @Mocked final MBeanServer mbs, @Mocked final JMXUtil jmxUtil) throws Exception {
        new Expectations(){
            {
                ManagementFactory.getPlatformMBeanServer();
                result = mbs;
                mbs.isRegistered(withAny(new ObjectName("")));
                result = true;
                JMXUtil.getInstance();
                result = jmxUtil;
                jmxUtil.unregisterMBeans();
                result = null;
            }
        };
        emTargetInitializer = new EMTargetInitializer();
        emTargetInitializer.preStop(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStop_notRegistered(@Mocked final ManagementFactory managementFactory, @Mocked final MBeanServer mbs, @Mocked final JMXUtil jmxUtil) throws Exception {
        new Expectations(){
            {
                ManagementFactory.getPlatformMBeanServer();
                result = mbs;
                mbs.isRegistered(withAny(new ObjectName("")));
                result = false;
                JMXUtil.getInstance();
                result = jmxUtil;
                jmxUtil.unregisterMBeans();
                result = new Exception();
            }
        };
        emTargetInitializer = new EMTargetInitializer();
        emTargetInitializer.preStop(new ApplicationLifecycleEvent(null));
    }
}
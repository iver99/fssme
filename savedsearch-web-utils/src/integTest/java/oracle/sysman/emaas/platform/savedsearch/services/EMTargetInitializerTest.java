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
import javax.naming.NamingException;
import java.lang.management.ManagementFactory;

/**
 * @author qianqi
 * @since 16-2-22.
 */
@Test (groups = {"s2"})
public class EMTargetInitializerTest {
    EMTargetInitializer emTargetInitializer;

    @Mocked
    Logger LOGGER;

    @Mocked
    LogManager logManager;

    @BeforeMethod
    public void setUp(){
        new NonStrictExpectations(){
            {
                logManager.getLogger(EMTargetInitializer.class);
                result = LOGGER;
            }
        };

    }

    @Test
    public void testGetName(){
        emTargetInitializer = new EMTargetInitializer();
        Assert.assertEquals("Saved Search Target Initializer",emTargetInitializer.getName());
    }

    @Test
    public void testPostStart_Exception(@Mocked final MBeanServer mbs,@Mocked final Exception e){
        emTargetInitializer = new EMTargetInitializer();
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null,null,false));
    }

    @Test
    public void testPostStart_noneExceptionAndInstanceAlreadyExistsException(@Mocked final MBeanServer mbs, @Mocked final InitialContext initialContext, @Mocked final InstanceAlreadyExistsException e) throws NamingException {
        new Expectations(){
            {
                InitialContext.doLookup(anyString);
                result = "xxxx";
                times = 2;
//  ?              LOGGER.error(anyString,e);
//                times = 1;
            }
        };
        emTargetInitializer = new EMTargetInitializer();

        //none exception
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null,null,false));

        //InstanceAlreadyExistsException
        emTargetInitializer.postStart(new ApplicationLifecycleEvent(null,null,false));

    }

    @Test
    public void testPostStart_MalformedObjectNameException(@Mocked final MBeanServer mbs,@Mocked final InitialContext initialContext) throws NamingException {
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
    public void testPostStop(){
        emTargetInitializer.postStop(null);
    }

    @Test
    public void testPreStart(){
        emTargetInitializer.preStart(null);
    }


    @Test
    public void testPreStop_isRegistered(@Mocked final ManagementFactory managementFactory, @Mocked final MBeanServer mbs, @Mocked final JMXUtil jmxUtil) throws MalformedObjectNameException, InstanceNotFoundException, MBeanRegistrationException {
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
    public void testPreStop_notRegistered(@Mocked final ManagementFactory managementFactory, @Mocked final MBeanServer mbs, @Mocked final JMXUtil jmxUtil) throws MalformedObjectNameException, InstanceNotFoundException, MBeanRegistrationException {
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
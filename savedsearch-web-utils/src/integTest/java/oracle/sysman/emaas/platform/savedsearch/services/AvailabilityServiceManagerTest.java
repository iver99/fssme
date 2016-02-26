package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;

import javax.management.Notification;

/**
 * @author qianqi
 * @since 16-2-19.
 */

@Test (groups = {"s2"})
public class AvailabilityServiceManagerTest {
    AvailabilityServiceManager availabilityServiceManager;


    @Mocked
    Logger logger;

    @Mocked
     LogManager logManager;

    @Mocked
    ApplicationLifecycleEvent applicationLifecycleEvent;


    @BeforeMethod
    public void setUp() throws Exception {

        new NonStrictExpectations(){
            {
                logManager.getLogger(AvailabilityServiceManager.class);
                result = logger;
            }
        };
    }

    @Test(groups = {"s1"})
    public void testGetName() throws Exception {
        AvailabilityServiceManager availabilityServiceManager = new AvailabilityServiceManager(new RegistryServiceManager());
        Assert.assertEquals("Saved Search Timer Service",availabilityServiceManager.getName());
    }

    @Test
    public void testHandleNotification_DBCMReturnFalse(@Mocked Notification notification, @Injectable final RegistryServiceManager rsm) throws Exception {

        new Expectations(){
            {
                new MockUp<DBConnectionManager>(){
                    @Mock
                    public boolean isDatabaseConnectionAvailable(){
                        return false;
                    }
                };
                rsm.isRegistrationComplete();
                returns(null,false,false,true);
                rsm.registerService();
                returns(false);
                logger.debug(anyString,anyLong,anyInt);
                times = 3;
                logger.info(anyString);
                times = 1;
            }
        };

        availabilityServiceManager = new AvailabilityServiceManager(rsm);
 //       rsm.setRegistrationComplete(null);
        availabilityServiceManager.handleNotification(notification,null);

//        rsm.setRegistrationComplete(false);
        availabilityServiceManager.handleNotification(notification,null);

//        rsm.setRegistrationComplete(true);
        availabilityServiceManager.handleNotification(notification,null);

    }

    @Test
    public void testHandleNotification_DBCMReturnTrue(@Mocked Notification notification, @Mocked final RegistryServiceManager rsm) throws Exception {

        new Expectations(){
            {
                new MockUp<DBConnectionManager>(){
                    @Mock
                    public boolean isDatabaseConnectionAvailable(){
                        return true;
                    }
                };
                rsm.isRegistrationComplete();
                returns(null,false,false,true);
                rsm.registerService();
                returns(false);
                logger.debug(anyString,anyLong,anyInt);
                times = 3;
                logger.info(anyString);
                times = 1;
                rsm.makeServiceUp();
                times = 1;
            }
        };

        availabilityServiceManager = new AvailabilityServiceManager(rsm);
        //       rsm.setRegistrationComplete(null);
        availabilityServiceManager.handleNotification(notification,null);

//        rsm.setRegistrationComplete(false);
        availabilityServiceManager.handleNotification(notification,null);

//        rsm.setRegistrationComplete(true);
        availabilityServiceManager.handleNotification(notification,null);

    }

    @Test
    public void testPostStart(@Mocked Notification notification, @Injectable final RegistryServiceManager rsm) throws Exception {
//        new Expectations(){
//            {
//                logger.info(anyString,anyLong);
//                times = 1;
//            }
//        };

//        availabilityServiceManager = new AvailabilityServiceManager(rsm);
//        availabilityServiceManager.postStart(applicationLifecycleEvent);
    }

    @Test
    public void testPostStop(@Mocked final RegistryServiceManager rsm) throws Exception {
        availabilityServiceManager = new AvailabilityServiceManager(rsm);
        availabilityServiceManager.postStop(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStart(@Mocked final RegistryServiceManager rsm) throws Exception {
        availabilityServiceManager = new AvailabilityServiceManager(rsm);
        availabilityServiceManager.preStart(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStop(@Mocked final RegistryServiceManager rsm) throws Exception {
//        new Expectations(){
//            {
//                timer.addNotificationListener(new AvailabilityServiceManager(rsm),null,null);
//                result = null;
//                timer.start();
//                result = null;
//            }
//        }
//        availabilityServiceManager = new AvailabilityServiceManager(rsm);
//        availabilityServiceManager.preStop(new ApplicationLifecycleEvent(null));

    }
}
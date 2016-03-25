package oracle.sysman.emaas.platform.savedsearch.wls.lifecycle;

import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.services.*;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.i18n.logging.NonCatalogLogger;

import javax.persistence.Persistence;

/**
 * @author qianqi
 * @since 16-2-24.
 */
public class AbstractApplicationLifecycleServiceTest {
//    AbstractApplicationLifecycleService abstractApplicationLifecycleService;
//
//    @Mocked
//    NonCatalogLogger logger;
//
//    Persistence persistence;
//
//    @Test
//    public void testAddApplicationServiceManager(@Mocked ApplicationServiceManager applicationServiceManager) throws Exception {
//        abstractApplicationLifecycleService = new AbstractApplicationLifecycleService(applicationServiceManager);
//        abstractApplicationLifecycleService.addApplicationServiceManager(applicationServiceManager);
//    }
//
//    @Test
//    public void testPostStart() throws Exception {
//        ApplicationServiceManager applicationServiceManager1 = new LoggingServiceManager();
//        abstractApplicationLifecycleService= new AbstractApplicationLifecycleService(applicationServiceManager1);
//        abstractApplicationLifecycleService.postStart(new ApplicationLifecycleEvent(null,null,false));
//
//    }
//
//    @Test
//    public void testPostStop() throws Exception {
//        ApplicationServiceManager applicationServiceManager1 = new LoggingServiceManager();
//        abstractApplicationLifecycleService= new AbstractApplicationLifecycleService(applicationServiceManager1);
//        abstractApplicationLifecycleService.postStop(new ApplicationLifecycleEvent(null,null,false));
//    }
//
//    @Test
//    public void testPreStart() throws Exception {
//        ApplicationServiceManager applicationServiceManager1 = new LoggingServiceManager();
//        abstractApplicationLifecycleService= new AbstractApplicationLifecycleService(applicationServiceManager1);
//        abstractApplicationLifecycleService.preStart(new ApplicationLifecycleEvent(null,null,false));
//    }
//
//    @Test
//    public void testPreStop() throws Exception {
//        ApplicationServiceManager applicationServiceManager1 = new LoggingServiceManager();
//        abstractApplicationLifecycleService= new AbstractApplicationLifecycleService(applicationServiceManager1);
//        abstractApplicationLifecycleService.preStop(new ApplicationLifecycleEvent(null,null,false));
//    }
//
//    @Test
//    public void testCreateAnInstance(@Mocked final NonCatalogLogger nonCatalogLogger) throws Exception {
//        ApplicationServiceManager applicationServiceManager1 = new LoggingServiceManager();
//        RegistryServiceManager applicationServiceManager2 = new RegistryServiceManager();
//        ApplicationServiceManager applicationServiceManager3 = new AvailabilityServiceManager(applicationServiceManager2);
//        ApplicationServiceManager applicationServiceManager4 = new EMTargetInitializer();
//
//        abstractApplicationLifecycleService = new AbstractApplicationLifecycleService(applicationServiceManager1,applicationServiceManager2,applicationServiceManager3,applicationServiceManager4);
//
//    }
}
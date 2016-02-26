//package oracle.sysman.emaas.platform.savedsearch.wls.lifecycle;
//
//import mockit.Mocked;
//import oracle.sysman.emaas.platform.savedsearch.services.AvailabilityServiceManager;
//import oracle.sysman.emaas.platform.savedsearch.services.EMTargetInitializer;
//import oracle.sysman.emaas.platform.savedsearch.services.LoggingServiceManager;
//import oracle.sysman.emaas.platform.savedsearch.services.RegistryServiceManager;
//import org.testng.annotations.Test;
//import weblogic.i18n.logging.NonCatalogLogger;
//
///**
// * @author qianqi
// * @since 16-2-24.
// */
//public class AbstractApplicationLifecycleServiceTest {
//    AbstractApplicationLifecycleService abstractApplicationLifecycleService;
//
////    @Test
////    public void testAddApplicationServiceManager(@Mocked ApplicationServiceManager applicationServiceManager) throws Exception {
////        abstractApplicationLifecycleService = new AbstractApplicationLifecycleService(applicationServiceManager);
////        abstractApplicationLifecycleService.addApplicationServiceManager(applicationServiceManager);
////    }
//
//    @Test
//    public void testPostStart() throws Exception {
//
//    }
//
//    @Test
//    public void testPostStop() throws Exception {
//
//    }
//
//    @Test
//    public void testPreStart() throws Exception {
//
//    }
//
//    @Test
//    public void testPreStop() throws Exception {
//
//    }
//
//    @Test
//    public void testCreateAnInstance(@Mocked final NonCatalogLogger nonCatalogLogger) throws Exception {
//        ApplicationServiceManager applicationServiceManager1 = new LoggingServiceManager();
//        RegistryServiceManager applicationServiceManager2 = new RegistryServiceManager();
//        ApplicationServiceManager applicationServiceManager3 = new AvailabilityServiceManager(applicationServiceManager2);
//        ApplicationServiceManager applicationServiceManager4 = new EMTargetInitializer();
//
//        abstractApplicationLifecycleService = new AbstractApplicationLifecycleService(applicationServiceManager1,applicationServiceManager2);
//    }
//}
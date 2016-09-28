package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SchemaVersion;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.VersionManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test (groups = {"s2"})
public class VersionValidationServiceManagerTest {

    VersionValidationServiceManager versionValidationServiceManager;
    @Test
    public void testGetName(){
        versionValidationServiceManager = new VersionValidationServiceManager();
        Assert.assertEquals(versionValidationServiceManager.getName(),"Version Validation Service");
    }

    @Test
    public void testPostStart_FailToStartup(@Mocked final VersionManager versionManager){
        new Expectations(){
            {
                VersionManager.getInstance();
                result = versionManager;
            }
        };

        versionValidationServiceManager = new VersionValidationServiceManager();
        try {
            versionValidationServiceManager.postStart(new ApplicationLifecycleEvent(null,null,false));
        }
        catch (Exception e){
            //catch the exception that postStart throws when failed to startup
        }
    }

    @Test
    public void testPostStart(@Mocked final VersionManager versionManager, @Mocked final SchemaVersion schemaVersion){
        new Expectations(){
            {
                VersionManager.getInstance();
                result = versionManager;

                schemaVersion.getMajorVersion();
                result = 1;

                schemaVersion.getMinorVersion();
                result = 0;
            }
        };

        versionValidationServiceManager = new VersionValidationServiceManager();
        try {
            versionValidationServiceManager.postStart(new ApplicationLifecycleEvent(null,null,false));
        }
        catch (Exception e){
            //catch the exception that postStart throws when failed to startup
        }
    }

//    @Test
//    public void testPostStop(){
//        versionValidationServiceManager = new VersionValidationServiceManager();
//        versionValidationServiceManager.postStop(new ApplicationLifecycleEvent(null,null,false));
//    }
//
//    @Test
//    public void testPreStart(){
//        versionValidationServiceManager = new VersionValidationServiceManager();
//        versionValidationServiceManager.preStart(new ApplicationLifecycleEvent(null,null,false));
//    }
//
//    @Test
//    public void testPreStop(){
//        versionValidationServiceManager = new VersionValidationServiceManager();
//        versionValidationServiceManager.preStop(new ApplicationLifecycleEvent(null,null,false));
//    }
}
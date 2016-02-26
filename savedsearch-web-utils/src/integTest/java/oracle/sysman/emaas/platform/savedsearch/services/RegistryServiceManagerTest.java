package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InfoManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import oracle.sysman.emaas.platform.savedsearch.property.PropertyReader;
import oracle.sysman.emaas.platform.savedsearch.utils.RegistryLookupUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import weblogic.application.ApplicationLifecycleEvent;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;

/**
 * @author qianqi
 * @since 16-2-22.
 */
@Test (groups = {"s2"})
public class RegistryServiceManagerTest {
    RegistryServiceManager registryServiceManager;

    @Test
    public void testGetName() throws Exception {
        registryServiceManager = new RegistryServiceManager();
        Assert.assertEquals(registryServiceManager.getName(),"Service Registry Service");
    }

    @Test
    public void testIsRegistrationComplete() throws Exception {
        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.setRegistrationComplete(true);
        Assert.assertEquals(registryServiceManager.isRegistrationComplete(),new Boolean(true));
    }

    @Test
    public void testMakeServiceOutOfService(@Mocked final RegistrationManager registrationManager,@Mocked final RegistrationClient registrationClient) throws Exception {
        new Expectations(){
            {
                RegistrationManager.getInstance();
                result = registrationManager;
                registrationManager.getRegistrationClient();
                result = registrationClient;
                registrationClient.updateStatus(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
            }
        };

        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.makeServiceOutOfService();
    }

    @Test
    public void testMakeServiceUp(@Mocked final RegistrationManager registrationManager,@Mocked final RegistrationClient registrationClient) throws Exception {
        new Expectations(){
            {
                RegistrationManager.getInstance();
                result = registrationManager;
                registrationManager.getRegistrationClient();
                result = registrationClient;
                registrationClient.updateStatus(InstanceInfo.InstanceStatus.UP);
            }
        };

        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.makeServiceUp();
    }

    @Test
    public void testPostStart() throws Exception {
        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.postStart(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPostStop() throws Exception {
        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.postStop(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStart() throws Exception {
        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.preStart(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testPreStop(@Mocked final RegistrationManager registrationManager, @Mocked final RegistrationClient registrationClient) throws Exception {
        new Expectations(){
            {
                RegistrationManager.getInstance();
                result = registrationManager;
                registrationManager.getRegistrationClient();
                result = registrationClient;
                registrationClient.shutdown();
            }
        };
        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.preStop(new ApplicationLifecycleEvent(null));
    }

    @Test
    public void testRegisterService(@Mocked final InitialContext initialContext, @Mocked final MBeanServer mBeanServer, @Mocked InstanceNotFoundException e) throws Exception {
        new Expectations(){
            {
                initialContext.lookup(anyString);
                result = mBeanServer;
                mBeanServer.getAttribute((ObjectName)any,anyString);
                result = null;
            }
        };

        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.registerService();
    }

    @Test
    public void testRegisterService_ServiceInternalLinkNotNull(@Mocked final InfoManager infoManager, @Mocked final InstanceInfo instanceInfo, @Mocked final RegistrationManager registrationManager, @Mocked final InitialContext initialContext, @Mocked final MBeanServer mBeanServer, @Mocked RegistryLookupUtil registryLookupUtil, @Mocked PropertyReader propertyReader, @Mocked final Properties properties) throws Exception {
        new Expectations(RegistryServiceManager.class) {
            {
                RegistrationManager.getInstance();
                result = registrationManager;
                registrationManager.initComponent((Properties)any);
                result = null;

                Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTP);
                result = "http";
                Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTPS);
                result = "https";
                RegistryLookupUtil.getServiceInternalLink(anyString,anyString,anyString,null);
                result = new Link();

                InfoManager.getInstance();
                result = infoManager;
                infoManager.getInfo();
                result = instanceInfo;
                instanceInfo.setLinks((List<Link>)any);
            }
        };

        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.registerService();
    }

    @Test
    public void testRegisterService_S(@Mocked RegistryLookupUtil registryLookupUtil, @Mocked PropertyReader propertyReader) throws Exception {
        new Expectations(RegistryServiceManager.class) {
            {
//                , @Mocked final Properties properties, @Mocked final LogManager logManager, @Mocked final Logger logger, @Mocked final Link link
                Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTP);
                result = "http";
                Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTPS);
                result = "https";
                RegistryLookupUtil.getServiceInternalLink(anyString,anyString,anyString,null);
                result = new Link();
//
//                LogManager.getLogger(anyString);
//                result = logger;
//                properties.putAll((Map)any);
//                result = null;

//                link.withRel(anyString);
//                result = new Link();
//                link.withHref(anyString);
//                result = new Link();
            }
        };

        registryServiceManager = new RegistryServiceManager();
        registryServiceManager.registerService();
    }

}
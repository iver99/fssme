package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.List;

import javax.management.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.testng.Assert;
import org.testng.annotations.Test;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InfoManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.NonServiceResource;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import oracle.sysman.emaas.platform.savedsearch.property.PropertyReader;
import weblogic.application.ApplicationLifecycleEvent;

/**
 * @author qianqi
 * @since 16-2-22.
 */
@Test(groups = { "s2" })
public class RegistryServiceManagerTest
{
	RegistryServiceManager registryServiceManager;
	@Mocked
	ApplicationLifecycleEvent applicationLifecycleEvent;
	@Test
	public void testGetName()
	{
		registryServiceManager = new RegistryServiceManager();
		Assert.assertEquals(registryServiceManager.getName(), "Service Registry Service");
	}

	@Test
	public void testIsRegistrationComplete()
	{
		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.setRegistrationComplete(true);
		Assert.assertEquals(registryServiceManager.isRegistrationComplete(), new Boolean(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMakeServiceOutOfService(@Mocked final RegistrationManager registrationManager,
			@Mocked final RegistrationClient registrationClient)
	{
		new Expectations() {
			{
				RegistrationManager.getInstance();
				result = registrationManager;
				registrationManager.getRegistrationClient();
				result = registrationClient;
				registrationClient.outOfServiceCausedBy((List<InstanceInfo>) any, (List<NonServiceResource>) any,
						(List<String>) any);
			}
		};

		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.makeServiceOutOfService(null, null, null);
	}

	@Test
	public void testMakeServiceUp(@Mocked final RegistrationManager registrationManager,
			@Mocked final RegistrationClient registrationClient)
	{
		new Expectations() {
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
	public void testPostStart()
	{
		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.postStart(applicationLifecycleEvent);
	}

	@Test
	public void testPostStop()
	{
		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.postStop(applicationLifecycleEvent);
	}

	@Test
	public void testPreStart()
	{
		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.preStart(applicationLifecycleEvent);
	}

	@Test
	public void testPreStop(@Mocked final RegistrationManager registrationManager,
			@Mocked final RegistrationClient registrationClient)
	{
		new Expectations() {
			{
				RegistrationManager.getInstance();
				result = registrationManager;
				registrationManager.getRegistrationClient();
				result = registrationClient;
				registrationClient.shutdown();
			}
		};
		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.preStop(applicationLifecycleEvent);
	}

	@Test
	public void testRegisterService(@Mocked final InitialContext initialContext, @Mocked final MBeanServer mBeanServer,
			@Mocked InstanceNotFoundException e, @Mocked PropertyReader propertyReader) throws NamingException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
		new Expectations() {
			{
				initialContext.lookup(anyString);
				result = mBeanServer;
				mBeanServer.getAttribute((ObjectName) any, anyString);
				result = null;
			}
		};

		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.registerService();
	}

	@Test
	public void testRegisterService_S(/*@Mocked RegistryLookupUtil registryLookupUtil,*/ @Mocked PropertyReader propertyReader)
			
	{
		new Expectations(RegistryServiceManager.class) {
			{
				Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTP);
				result = "http";
				Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTPS);
				result = "https";
				//RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, null);
				//result = new Link();
			}
		};

		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.registerService();
	}

	@Test
	public void testRegisterService_ServiceInternalLinkNotNull(@Mocked final InfoManager infoManager,
			@Mocked final InstanceInfo instanceInfo, @Mocked final RegistrationManager registrationManager,
			@Mocked final InitialContext initialContext, @Mocked final MBeanServer mBeanServer,
			/*@Mocked RegistryLookupUtil registryLookupUtil,*/ @Mocked PropertyReader propertyReader)
	{
		new Expectations(RegistryServiceManager.class) {
			{
				RegistrationManager.getInstance();
				result = registrationManager;

				Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTP);
				result = "http";
				Deencapsulation.invoke(RegistryServiceManager.class, "getApplicationUrl", RegistryServiceManager.UrlType.HTTPS);
				result = "https";
				//RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, null);
				//result = new Link();
			}
		};

		registryServiceManager = new RegistryServiceManager();
		registryServiceManager.registerService();
	}

}

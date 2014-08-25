package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InfoManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo.InstanceStatus;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import oracle.sysman.emaas.platform.savedsearch.property.PropertyReader;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractServicesManager;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.logging.NonCatalogLogger;

public class SavedSearchServicesRegistryService implements ApplicationService
{
	interface Builder
	{
		Properties build();
	}

	class ServiceConfigBuilder implements Builder
	{
		private final Map<String, String> serviceConfigMap = new HashMap<String, String>();

		@Override
		public Properties build()
		{
			Properties propSaver = new Properties();
			propSaver.putAll(serviceConfigMap);
			return propSaver;
		}

		/**
		 * @param canonicalEndpoints
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder canonicalEndpoints(String canonicalEndpoints)
		{
			serviceConfigMap.put("canonicalEndpoints", canonicalEndpoints);
			return this;
		}

		/**
		 * @param controlledDataTypes
		 *            example "LogFile, Target Delta"
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder controlledDatatypes(String controlledDataTypes)
		{
			serviceConfigMap.put("controlledDataTypes", controlledDataTypes);
			return this;
		}

		/**
		 * @param dataTypes
		 *            example "LogFile"
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder datatypes(String dataTypes)
		{
			serviceConfigMap.put("dataTypes", dataTypes);
			return this;
		}

		/**
		 * @param leaseRenewalInterval
		 *            example 30000
		 * @param timeUnit
		 *            TimeUnit.SECONDS
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder leaseRenewalInterval(long leaseRenewalInterval, TimeUnit timeUnit)
		{
			long leaseRenewalIntervalInSecs = TimeUnit.SECONDS.convert(leaseRenewalInterval, timeUnit);
			serviceConfigMap.put("leaseInfo.renewalIntervalInSecs", String.valueOf(leaseRenewalIntervalInSecs));
			return this;
		}

		/**
		 * @param loadScore
		 *            example "0.9"
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder loadScore(double loadScore)
		{
			serviceConfigMap.put("loadScore", String.valueOf(loadScore));
			return this;
		}

		/**
		 * @param registryUrls
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder registryUrls(String registryUrls)
		{
			serviceConfigMap.put("registryUrls", registryUrls);
			return this;
		}

		/**
		 * @param serviceName
		 *            example "DataReceiver.storage"
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder serviceName(String serviceName)
		{
			serviceConfigMap.put("serviceName", serviceName);
			return this;
		}

		/**
		 * @param serviceUrls
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder serviceUrls(String serviceUrls)
		{
			serviceConfigMap.put("serviceUrls", serviceUrls);
			return this;
		}

		/**
		 * @param supportedTargetTypes
		 *            example "LogFile, Target Delta"
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder supportedTargetTypes(String supportedTargetTypes)
		{
			serviceConfigMap.put("supportedTargetTypes", supportedTargetTypes);
			return this;
		}

		/**
		 * @param version
		 *            example "1.0"
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder version(String version)
		{
			serviceConfigMap.put("version", version);
			return this;
		}

		/**
		 * @param virtualEndpoints
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder virtualEndpoints(String virtualEndpoints)
		{
			serviceConfigMap.put("virtualEndpoints", virtualEndpoints);
			return this;
		}

                /**
                 * @param authToken
                 *            The authorization token to be used for interapp communication
                 * @return ServiceConfigBuilder
                 */
                public ServiceConfigBuilder authToken(String authToken)
                {
                        serviceConfigMap.put("authToken", authToken);
                        return this;
                }
	}

	enum UrlType
	{
		HTTP, HTTPS
	}

	private static final String NAV_BASE = "/savedsearch/v1";
	private static final String NAV_SEARCH = "/savedsearch/v1/search";
	private static final String NAV_FOLDER = "/savedsearch/v1/folder";
	private static final String NAV_CATEGORY = "/savedsearch/v1/category";
	private final NonCatalogLogger logger = new NonCatalogLogger(AbstractServicesManager.APPLICATION_LOGGER_SUBSYSTEM
			+ ".serviceregistry");

	public static final ObjectName WLS_RUNTIME_SERVICE_NAME;

	static {
		try {
			WLS_RUNTIME_SERVICE_NAME = ObjectName
					.getInstance("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
		}
		catch (Exception e) {
			throw new Error("Well-known JMX names are corrupt - code bug", e);
		}
	}

	private static String getApplicationUrl(UrlType urlType) throws Exception
	{
		InitialContext ctx = new InitialContext();
		try {
			MBeanServer runtimeServer = (MBeanServer) ctx.lookup("java:comp/jmx/runtime");
			ObjectName serverRuntimeName = (ObjectName) runtimeServer.getAttribute(WLS_RUNTIME_SERVICE_NAME, "ServerRuntime");
			switch (urlType) {
				case HTTP:
					return (String) runtimeServer.invoke(serverRuntimeName, "getURL", new Object[] { "http" },
							new String[] { String.class.getName() });
				case HTTPS:
					return (String) runtimeServer.invoke(serverRuntimeName, "getURL", new Object[] { "https" },
							new String[] { String.class.getName() });
				default:
					throw new IllegalStateException("Unknown UrlType, ServerRuntime URL lookup failed");
			}
		}
		finally {
			ctx.close();
		}
	}

	@Override
	public String getName()
	{
		return "Service Registry Service";
	}

	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception
	{
		logger.notice("Post-starting 'Service Registry' application service");
		String applicationUrl = SavedSearchServicesRegistryService.getApplicationUrl(UrlType.HTTP);
		logger.debug("Application URL to register with 'Service Registry': " + applicationUrl);

		logger.info("Building 'Service Registry' configuration");
		Properties serviceProps = PropertyReader.loadProperty(PropertyReader.SERVICE_PROPS);

		ServiceConfigBuilder builder = new ServiceConfigBuilder();
		builder.serviceName(serviceProps.getProperty("serviceName")).version(serviceProps.getProperty("version"))
				.virtualEndpoints(applicationUrl + NAV_BASE).canonicalEndpoints(applicationUrl + NAV_BASE)
				.registryUrls(serviceProps.getProperty("registryUrls")).loadScore(0.9)
				.leaseRenewalInterval(3000, TimeUnit.SECONDS).serviceUrls(serviceProps.getProperty("serviceUrls"));

                if (serviceProps.getProperty("authToken")!=null) {
                    builder.authToken(serviceProps.getProperty("authToken"));
                }
		logger.info("Initializing RegistrationManager");
		RegistrationManager.getInstance().initComponent(builder.build());

		InfoManager
				.getInstance()
				.getInfo()
				.setLinks(
						Arrays.asList(new Link().withRel("navigation").withHref(applicationUrl + NAV_BASE),
								new Link().withRel("search").withHref(applicationUrl + NAV_SEARCH), new Link().withRel("folder")
										.withHref(applicationUrl + NAV_FOLDER),
								new Link().withRel("category").withHref(applicationUrl + NAV_CATEGORY)));

		logger.info("Registering service with 'Service Registry'");
		RegistrationManager.getInstance().getRegistrationClient().register();
		RegistrationManager.getInstance().getRegistrationClient().updateStatus(InstanceStatus.UP);
                if (smProps.getProperty("authToken")!=null) {
                    LookupManager.getInstance().withAuthorization(serviceProps.getProperty("authToken"));
                }
		LookupManager.getInstance().initComponent(Arrays.asList(serviceProps.getProperty("serviceUrls")));
	}

	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception
	{

	}

	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception
	{
	}

	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		logger.notice("Post-stopping 'Service Registry' application service");
		RegistrationManager.getInstance().getRegistrationClient().shutdown();
		logger.debug("Post-stopped 'Service Regsitry'");
	}

}

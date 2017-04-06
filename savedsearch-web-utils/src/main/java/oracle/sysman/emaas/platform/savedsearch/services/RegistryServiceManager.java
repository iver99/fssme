package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InfoManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo.InstanceStatus;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.NonServiceResource;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import oracle.sysman.emaas.platform.savedsearch.property.PropertyReader;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractApplicationLifecycleService;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import weblogic.application.ApplicationLifecycleEvent;

public class RegistryServiceManager implements ApplicationServiceManager
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
		 * @param characteristics
		 * @return ServiceConfigBuilder
		 */
		public ServiceConfigBuilder characteristics(String characteristics)
		{
			serviceConfigMap.put("characteristics", characteristics);
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

	}

	enum UrlType
	{
		HTTP, HTTPS
	}

	//OBSOLETE_XX will be removed once static links take effect
	private static final String OBSOLETE_NAV = "navigation";
	private static final String OBSOLETE_SEARCH = "search";
	private static final String OBSOLETE_FOLDER = "folder";
	private static final String OBSOLETE_CATEGORY = "category";

	private static final String NAV_BASE = "/savedsearch/v1";
	private static final String NAV_SEARCH = "/savedsearch/v1/search";
	private static final String NAV_FOLDER = "/savedsearch/v1/folder";
	private static final String NAV_CATEGORY = "/savedsearch/v1/category";
	private static final String NAV_SEARCHES = "/savedsearch/v1/searches";
	private static final String NAV_ENTITIES = "/savedsearch/v1/entities";
	private static final String NAV_CATEGORIES = "/savedsearch/v1/categories";
	private static final String NAV_WIDGETS = "/savedsearch/v1/widgets";
	private static final String NAV_WIDGETGROUPS = "/savedsearch/v1/widgetgroups";
	private static final String NAV_LOGCONFIGS = "/savedsearch/v1/_logging/configs";

	private static final String STATIC_NAV = "static/savedsearch.navigation";
	private static final String STATIC_SEARCH = "static/savedsearch.search";
	private static final String STATIC_CATEGORY = "static/savedsearch.category";
	private static final String STATIC_FOLDER = "static/savedsearch.folder";
	private static final String STATIC_SEARCHES = "static/savedsearch.searches";
	private static final String STATIC_ENTITIES = "static/savedsearch.entities";
	private static final String STATIC_CATEGORIES = "static/savedsearch.categories";
	private static final String STATIC_WIDGETS = "static/savedsearch.widgets";
	private static final String STATIC_WIDGETGROUPS = "static/savedsearch.widgetgroups";
	private static final String REL_LOG_CONFIG = "log/configuration";

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

	private static final Logger LOGGER = LogManager
			.getLogger(AbstractApplicationLifecycleService.APPLICATION_LOGGER_SUBSYSTEM + ".serviceregistry");

	private Boolean registrationComplete = null;

	@Override
	public String getName()
	{
		return "Service Registry Service";
	}

	public Boolean isRegistrationComplete()
	{
		return registrationComplete;
	}

	/**
	 * Update saved search service status to out of service on service manager
	 */
	public void makeServiceOutOfService(List<InstanceInfo> services, List<NonServiceResource> resources,
			List<String> otherReasons)
	{
		RegistrationManager.getInstance().getRegistrationClient().outOfServiceCausedBy(services, resources, otherReasons);
		//		RegistrationManager.getInstance().getRegistrationClient().updateStatus(InstanceStatus.OUT_OF_SERVICE);
	}

	/**
	 * Update saved search service status to up on service manager
	 */
	public void makeServiceUp()
	{
		RegistrationManager.getInstance().getRegistrationClient().updateStatus(InstanceStatus.UP);
	}

	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception
	{
		LOGGER.info("Post-starting 'Service Registry' application service");
		registerService();

	}

	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception
	{
		// Do nothing
	}

	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception
	{
		// Do nothing
	}

	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		LOGGER.info("Pre-stopping 'Service Registry' application service");
		RegistrationManager.getInstance().getRegistrationClient().shutdown();
		LOGGER.debug("Pre-stopped 'Service Regsitry'");
	}

	public boolean registerService()
	{
		try {
			LOGGER.info("Registering service...");
			String applicationUrl = RegistryServiceManager.getApplicationUrl(UrlType.HTTP);
			String applicationUrlSSL = RegistryServiceManager.getApplicationUrl(UrlType.HTTPS);
			LOGGER.debug("Application URL to register with 'Service Registry': " + applicationUrl);
			LOGGER.debug("Application SSL URL to register with 'Service Registry': " + applicationUrlSSL);

			LOGGER.info("Building 'Service Registry' configuration");
			Properties serviceProps = PropertyReader.loadProperty(PropertyReader.SERVICE_PROPS);

			LOGGER.info("Initialize lookup manager");
			LookupManager.getInstance().initComponent(Arrays.asList(serviceProps.getProperty("serviceUrls")));

			ServiceConfigBuilder builder = new ServiceConfigBuilder();
			builder.serviceName(serviceProps.getProperty("serviceName")).version(serviceProps.getProperty("version"));

			StringBuilder virtualEndPoints = new StringBuilder();
			StringBuilder canonicalEndPoints = new StringBuilder();
			if (applicationUrl != null) {
				virtualEndPoints.append(applicationUrl + NAV_BASE);
				canonicalEndPoints.append(applicationUrl + NAV_BASE);
			}
			if (applicationUrlSSL != null) {
				if (virtualEndPoints.length() > 0) {
					virtualEndPoints.append(",");
					canonicalEndPoints.append(",");
				}
				virtualEndPoints.append(applicationUrlSSL + NAV_BASE);
				canonicalEndPoints.append(applicationUrlSSL + NAV_BASE);
			}
			builder.virtualEndpoints(virtualEndPoints.toString()).canonicalEndpoints(canonicalEndPoints.toString());
			builder.registryUrls(serviceProps.getProperty("registryUrls")).loadScore(0.9)
					.characteristics(serviceProps.getProperty("characteristics")).leaseRenewalInterval(3000, TimeUnit.SECONDS)
					.serviceUrls(serviceProps.getProperty("serviceUrls"));

			LOGGER.info("Initializing RegistrationManager");
			RegistrationManager.getInstance().initComponent(builder.build());

			List<Link> links = new ArrayList<Link>();
			HashMap<String, String> overriedTypes = new HashMap<String,String>();
			overriedTypes.put("POST", "INGEST_WRITE_NO_PIPELINE");
			if (applicationUrl != null) {
				links.add(new Link().withRel(OBSOLETE_NAV).withHref(applicationUrl + NAV_BASE));
				links.add(new Link().withRel(OBSOLETE_FOLDER).withHref(applicationUrl + NAV_FOLDER).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(OBSOLETE_CATEGORY).withHref(applicationUrl + NAV_CATEGORY));
				links.add(new Link().withRel(OBSOLETE_SEARCH).withHref(applicationUrl + NAV_SEARCH).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(STATIC_NAV).withHref(applicationUrl + NAV_BASE));
				links.add(new Link().withRel(STATIC_FOLDER).withHref(applicationUrl + NAV_FOLDER).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(STATIC_CATEGORY).withHref(applicationUrl + NAV_CATEGORY));
				links.add(new Link().withRel(STATIC_SEARCH).withHref(applicationUrl + NAV_SEARCH).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(STATIC_SEARCHES).withHref(applicationUrl + NAV_SEARCHES));
				links.add(new Link().withRel(STATIC_ENTITIES).withHref(applicationUrl + NAV_ENTITIES));
				links.add(new Link().withRel(STATIC_CATEGORIES).withHref(applicationUrl + NAV_CATEGORIES));
				links.add(new Link().withRel(STATIC_WIDGETS).withHref(applicationUrl + NAV_WIDGETS));
				links.add(new Link().withRel(STATIC_WIDGETGROUPS).withHref(applicationUrl + NAV_WIDGETGROUPS));
				links.add(new Link().withRel(REL_LOG_CONFIG).withHref(applicationUrl + NAV_LOGCONFIGS));
			}
			if (applicationUrlSSL != null) {
				links.add(new Link().withRel(OBSOLETE_NAV).withHref(applicationUrlSSL + NAV_BASE));
				links.add(new Link().withRel(OBSOLETE_FOLDER).withHref(applicationUrlSSL + NAV_FOLDER).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(OBSOLETE_CATEGORY).withHref(applicationUrlSSL + NAV_CATEGORY));
				links.add(new Link().withRel(OBSOLETE_SEARCH).withHref(applicationUrlSSL + NAV_SEARCH).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(STATIC_NAV).withHref(applicationUrlSSL + NAV_BASE));
				links.add(new Link().withRel(STATIC_FOLDER).withHref(applicationUrlSSL + NAV_FOLDER).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(STATIC_CATEGORY).withHref(applicationUrlSSL + NAV_CATEGORY));
				links.add(new Link().withRel(STATIC_SEARCH).withHref(applicationUrlSSL + NAV_SEARCH).withOverrideTypes(overriedTypes));
				links.add(new Link().withRel(STATIC_SEARCHES).withHref(applicationUrlSSL + NAV_SEARCHES));
				links.add(new Link().withRel(STATIC_ENTITIES).withHref(applicationUrlSSL + NAV_ENTITIES));
				links.add(new Link().withRel(STATIC_CATEGORIES).withHref(applicationUrlSSL + NAV_CATEGORIES));
				links.add(new Link().withRel(STATIC_WIDGETS).withHref(applicationUrlSSL + NAV_WIDGETS));
				links.add(new Link().withRel(STATIC_WIDGETGROUPS).withHref(applicationUrlSSL + NAV_WIDGETGROUPS));
				links.add(new Link().withRel(REL_LOG_CONFIG).withHref(applicationUrlSSL + NAV_LOGCONFIGS));
			}
			InfoManager.getInstance().getInfo().setLinks(links);

			LOGGER.info("Registering service with 'Service Registry'");
			RegistrationManager.getInstance().getRegistrationClient().register();
			RegistrationManager.getInstance().getRegistrationClient().updateStatus(InstanceStatus.UP);
			//register OAuth ready status with Service Registry
			RegistrationManager.getInstance().getRegistrationClient().setOauthReady(true);

			setRegistrationComplete(Boolean.TRUE);
			LOGGER.info("Service manager is up. Completed saved search service registration");
		}
		catch (Exception e) {
			setRegistrationComplete(Boolean.FALSE);
			LOGGER.error(
					"Errors occurrs in registration. Service manager might be down. Daved search service registration is not complete.");
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return registrationComplete;
	}

	public void setRegistrationComplete(Boolean isComplete)
	{
		registrationComplete = isComplete;
	}
}

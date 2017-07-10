package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractApplicationLifecycleService;

public class ApplicationLifecycleService extends AbstractApplicationLifecycleService
{

	public ApplicationLifecycleService()
	{
		addApplicationServiceManager(new LoggingServiceManager());
		RegistryServiceManager rsm = new RegistryServiceManager();
		addApplicationServiceManager(rsm);
		addApplicationServiceManager(new AvailabilityServiceManager(rsm));
		addApplicationServiceManager(new EMTargetInitializer());
		addApplicationServiceManager(new LifecycleNotificationServiceManager());
		addApplicationServiceManager(new CacheServiceManager());
		addApplicationServiceManager(new ThreadPoolManager());
		addApplicationServiceManager(new MetaDataManager());
	}

}

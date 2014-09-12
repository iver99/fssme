package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractApplicationLifecycleService;

public class ApplicationLifecycleService extends AbstractApplicationLifecycleService
{

	public ApplicationLifecycleService()
	{
		super(new LoggingServiceManager(), new VersionValidationServiceManager(), new RegistryServiceManager());
	}

}

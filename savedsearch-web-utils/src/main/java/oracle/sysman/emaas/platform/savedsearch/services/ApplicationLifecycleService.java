package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractApplicationLifecycleService;

public class ApplicationLifecycleService extends AbstractApplicationLifecycleService
{

	public ApplicationLifecycleService()
	{
		//SND 9/11: removed  VersionValidationServiceManager() - was causing class loading issue when redeploying app
		super(new LoggingServiceManager(), new RegistryServiceManager());

	}

}

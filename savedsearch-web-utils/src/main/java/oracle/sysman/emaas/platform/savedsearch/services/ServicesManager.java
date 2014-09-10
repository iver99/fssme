package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractServicesManager;

public class ServicesManager extends AbstractServicesManager
{

	public ServicesManager()
	{

		super(new SavedSearchServicesRegistryService());

	}

}

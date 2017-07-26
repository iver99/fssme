package oracle.sysman.emaas.platform.savedsearch.comparator.ws.timer;

import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.wls.lifecycle.AbstractApplicationLifecycleService;

public class SavedsearchComparatorTriggerService extends AbstractApplicationLifecycleService{
	
	public SavedsearchComparatorTriggerService()
	{		
		addApplicationServiceManager(new SavedsearchComparatorServiceManager());
	}


}

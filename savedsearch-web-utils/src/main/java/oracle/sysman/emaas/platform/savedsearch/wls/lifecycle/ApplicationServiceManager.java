package oracle.sysman.emaas.platform.savedsearch.wls.lifecycle;

import weblogic.application.ApplicationLifecycleEvent;

public interface ApplicationServiceManager
{
	public String getName();

	public void postStart(ApplicationLifecycleEvent evt) throws Exception;

	public void postStop(ApplicationLifecycleEvent evt) throws Exception;

	public void preStart(ApplicationLifecycleEvent evt) throws Exception;

	public void preStop(ApplicationLifecycleEvent evt) throws Exception;

}

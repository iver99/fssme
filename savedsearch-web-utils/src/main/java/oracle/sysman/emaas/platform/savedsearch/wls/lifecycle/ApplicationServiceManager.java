package oracle.sysman.emaas.platform.savedsearch.wls.lifecycle;

import weblogic.application.ApplicationLifecycleEvent;

public interface ApplicationServiceManager
{
	public String getName();

	public void postStart(ApplicationLifecycleEvent evt) ;

	public void postStop(ApplicationLifecycleEvent evt) ;

	public void preStart(ApplicationLifecycleEvent evt) ;

	public void preStop(ApplicationLifecycleEvent evt) ;

}

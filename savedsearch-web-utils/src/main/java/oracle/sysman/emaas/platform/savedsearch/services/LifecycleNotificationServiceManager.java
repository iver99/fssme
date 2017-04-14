package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle.SSFLifeCycleNotification;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle.SSFLifeCycleNotifyEntity.SSFNotificationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weblogic.application.ApplicationLifecycleEvent;

/**
 * Created by guochen on 3/16/17.
 */
public class LifecycleNotificationServiceManager implements ApplicationServiceManager {
	private static final Logger LOGGER = LogManager.getLogger(LifecycleNotificationServiceManager.class);

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#getName()
	 */
	@Override
	public String getName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception {
		try {
			new SSFLifeCycleNotification().notify(SSFNotificationType.UP);
			LOGGER.info("SSF lifecycle UP notification has been sent to concerned services");
		} catch (Exception e) { // for any issue occurres of lifecycle notification, we just record an exception and prevents the exception to break the startup procedure
			LOGGER.error(e);
		}
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception {
		// currently there is the requirements to notify SSF up only, so don't need to lookup service registry and then call notification for 'DOWN'
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception {

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception {

	}
}

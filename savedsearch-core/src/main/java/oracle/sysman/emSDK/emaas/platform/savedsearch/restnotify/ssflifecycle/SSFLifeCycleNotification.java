package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle;

import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle.SSFLifeCycleNotifyEntity.SSFNotificationType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by guochen on 3/16/17.
 */
public class SSFLifeCycleNotification {
	private static final Logger LOGGER = LogManager.getLogger(SSFLifeCycleNotification.class);
	public static final String SSF_LIFECYCLE_REL = "ssf.lifecycle.notify";

	public void notify(SSFNotificationType type) {
		if (type == null) {
			LOGGER.info("Didn't notify of ssf lifecycle for null ssf notify type");
			return;
		}
		LOGGER.info("Starts to notify an SSF lifecycle event. The entity type is {}", type);
		// TODO do something
	}
}

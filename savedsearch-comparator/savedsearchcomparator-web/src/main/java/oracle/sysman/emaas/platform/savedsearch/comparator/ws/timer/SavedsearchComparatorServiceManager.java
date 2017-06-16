package oracle.sysman.emaas.platform.savedsearch.comparator.ws.timer;

import java.util.Date;

import javax.management.InstanceNotFoundException;
import javax.management.NotificationListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.wls.lifecycle.ApplicationServiceManager;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.management.timer.Timer;

public class SavedsearchComparatorServiceManager implements ApplicationServiceManager{
	
	private final static Logger LOGGER = LogManager.getLogger(SavedsearchComparatorServiceManager.class);

	private Timer timer;
	private Integer notificationId;
	// comparison and sync will be triggered every 6 hours 
	private static final long PERIOD = Timer.ONE_HOUR * 6;

	@Override
	public String getName() {
		
		return "Savedsearch comparator timer";
	}

	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception {
		LOGGER.info("Start to run timer");
		timer = new Timer();
		NotificationListener notification = new SavedsearchComparatorHandlerNotification();
		timer.addNotificationListener(notification, null, null);
		Date timerTriggerAt = new Date(new Date().getTime() + 10000L);
		notificationId = timer.addNotification("SavedsearchComparisonServiceTimer", null, notification, timerTriggerAt, PERIOD, 0);
		timer.start();
		LOGGER.info("Timer for savedsearch comparison started. notificationId={}", notificationId);
		
	}

	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception {
		LOGGER.info("Pre-stopping comparison service");
		try {
			timer.stop();
			timer.removeNotification(notificationId);
			LOGGER.info("Timer for savedsearch comparison stopped. notificationId={}", notificationId);
		}
		catch (InstanceNotFoundException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

}

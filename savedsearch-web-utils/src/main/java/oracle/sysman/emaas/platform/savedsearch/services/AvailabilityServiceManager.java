package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.Date;

import javax.management.InstanceNotFoundException;
import javax.management.Notification;
import javax.management.NotificationListener;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.DBConnectionManager;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.application.ApplicationLifecycleEvent;
import weblogic.management.timer.Timer;

/**
 * @author guobaochen
 */
public class AvailabilityServiceManager implements ApplicationServiceManager, NotificationListener
{
	private final Logger logger = LogManager.getLogger(AvailabilityServiceManager.class);

	private static final long PERIOD = Timer.ONE_MINUTE;
	private Timer timer;
	private Integer notificationId;
	private final RegistryServiceManager rsm;

	public AvailabilityServiceManager(RegistryServiceManager rsm)
	{
		this.rsm = rsm;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#getName()
	 */
	@Override
	public String getName()
	{
		return "Saved Search Timer Service";
	}

	/* (non-Javadoc)
	 * @see javax.management.NotificationListener#handleNotification(javax.management.Notification, java.lang.Object)
	 */
	@Override
	public void handleNotification(Notification notification, Object handback)
	{
		logger.debug("Time triggered handler method. sequenceNumber={}, notificationId={}", notification.getSequenceNumber(),
				notificationId);
		if (rsm.isRegistrationComplete() == null) {
			logger.info("RegistryServiceManager hasn't registered. Check registry service next time");
			return;
		}
		// check if service manager is up and registration is complete
		if (!rsm.isRegistrationComplete() && !rsm.registerService()) {
			logger.warn("Saved search service registration is not completed. Ignore dependant services availability checking");
			return;

		}
		// check database available
		boolean isDBAvailable = isDatabaseAvailable();
		// update saved search service status
		if (!isDBAvailable) {
			rsm.makeServiceOutOfService();
			logger.info("Saved search service is out of service because database is unavailable");
		}
		else {
			try {
				rsm.makeServiceUp();
				logger.debug("Saved search service is up");
			}
			catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#postStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception
	{
		timer = new Timer();
		timer.addNotificationListener(this, null, null);
		Date timerTriggerAt = new Date(new Date().getTime() + 10000L);
		notificationId = timer.addNotification("SavedSearchServiceTimer", null, this, timerTriggerAt, PERIOD, 0);
		timer.start();
		logger.info("Timer for saved search service dependencies checking started. notificationId={}", notificationId);
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#postStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception
	{
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#preStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception
	{
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		logger.info("Pre-stopping availability service");
		try {
			timer.stop();
			timer.removeNotification(notificationId);
			logger.info("Timer for dashboards dependencies checking stopped. notificationId={}", notificationId);
		}
		catch (InstanceNotFoundException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	private boolean isDatabaseAvailable()
	{
		DBConnectionManager dbcm = DBConnectionManager.getInstance();
		return dbcm.isDatabaseConnectionAvailable();
	}
}

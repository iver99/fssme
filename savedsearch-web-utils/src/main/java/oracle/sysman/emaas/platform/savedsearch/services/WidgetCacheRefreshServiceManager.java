/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.Date;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.WidgetCacheManager;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.management.timer.Timer;

/**
 * @author guochen
 */
public class WidgetCacheRefreshServiceManager implements ApplicationServiceManager, NotificationListener
{
	private static final long PERIOD = Timer.ONE_SECOND * 2;

	private final Logger logger = LogManager.getLogger(WidgetCacheRefreshServiceManager.class);
	private Timer timer;
	private Integer notificationId;

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#getName()
	 */
	@Override
	public String getName()
	{
		return "Saved Search Widget Cache Refresh Service";
	}

	/* (non-Javadoc)
	 * @see javax.management.NotificationListener#handleNotification(javax.management.Notification, java.lang.Object)
	 */
	@Override
	public void handleNotification(Notification notification, Object handback)
	{
		logger.info("Timer for saved search service widget cache refreshing is triggered. Try to refresh refreshable cache");
		WidgetCacheManager.getInstance().reloadRefreshableCaches();
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
		notificationId = timer.addNotification("SavedSearchServiceWidgetCacheRefreshTimer", null, this, timerTriggerAt, PERIOD,
				0);
		timer.start();
		logger.info("Timer for saved search service widget cache refreshing started. notificationId={}", notificationId);
		WidgetCacheManager.getInstance();
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
		if (timer != null) {
			logger.info("Timer for saved search service widget cache refreshing stops. notificationId={}", notificationId);
			timer.stop();
		}
	}

}

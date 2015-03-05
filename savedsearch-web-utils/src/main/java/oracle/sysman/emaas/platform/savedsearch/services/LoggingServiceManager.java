/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.services;

import java.lang.management.ManagementFactory;
import java.net.URL;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import oracle.sysman.emaas.platform.savedsearch.wls.management.AppLoggingManageMXBean;

import org.apache.logging.log4j.core.config.Configurator;

import weblogic.application.ApplicationLifecycleEvent;

/**
 * @author sdhamdhe Initializes the loggers - by default we log entries to
 *         /var/opt/ORCLemaas/logs/savedsearchService/savedSearchService.log
 */
public class LoggingServiceManager implements ApplicationServiceManager
{
	public static final String MBEAN_NAME = "oracle.sysman.emaas.platform.savedsearch.logging.beans:type=AppLoggingManageMXBean";

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#getName()
	 */
	@Override
	public String getName()
	{
		return "Saved Search Logging Service";
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception
	{
		URL url = LoggingServiceManager.class.getResource("/log4j2_ssf.xml");
		Configurator.initialize("root", LoggingServiceManager.class.getClassLoader(), url.toURI());
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception
	{
		// no impl
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception
	{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		mbs.registerMBean(new AppLoggingManageMXBean(), new ObjectName(MBEAN_NAME));
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		mbs.unregisterMBean(new ObjectName(MBEAN_NAME));
	}
}

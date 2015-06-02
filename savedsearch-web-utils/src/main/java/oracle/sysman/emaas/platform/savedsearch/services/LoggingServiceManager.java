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

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import oracle.sysman.emaas.platform.savedsearch.wls.management.AppLoggingManageMXBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import weblogic.application.ApplicationLifecycleEvent;

/**
 * @author sdhamdhe Initializes the loggers - by default we log entries to
 *         /var/opt/ORCLemaas/logs/savedsearchService/savedSearchService.log
 */
public class LoggingServiceManager implements ApplicationServiceManager
{
	private final Logger logger = LogManager.getLogger(LoggingServiceManager.class);
	public static final String MBEAN_NAME = "oracle.sysman.emaas.platform.savedsearch.logging.beans:type=AppLoggingManageMXBean";
	public static final String MBEAN_NAME_TMP = "oracle.sysman.emaas.platform.savedsearch.logging.beans:type=AppLoggingManageMXBean"
			+ System.currentTimeMillis();
	private boolean tempMBeanExists = false;

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
		logger.info("Log4j2 has been configured");

		try {
			registerMBean(MBEAN_NAME);
		}
		catch (InstanceAlreadyExistsException e) {
			logger.error("MBean '" + MBEAN_NAME + "' exists already when trying to register. Unregister it first.", e);
			try {
				unregisterMBean(MBEAN_NAME);
			}
			catch (Exception ex) {
				logger.error(ex);
				// failed to unregister with name 'MBEAN_NAME', register with a temporary name
				registerMBean(MBEAN_NAME_TMP);
				tempMBeanExists = true;
				return;
			}
			registerMBean(MBEAN_NAME);
		}
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
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		logger.info("Pre-stopping logging service");
		try {
			if (tempMBeanExists) {
				tempMBeanExists = false;
				unregisterMBean(MBEAN_NAME_TMP);
			}
			unregisterMBean(MBEAN_NAME);
		}
		catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	private void registerMBean(String name) throws InstanceAlreadyExistsException, MBeanRegistrationException,
	NotCompliantMBeanException, MalformedObjectNameException
	{
		ObjectName on = new ObjectName(name);
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		mbs.registerMBean(new AppLoggingManageMXBean(), on);
		logger.info("MBean '" + name + "' has been registered");
	}

	private void unregisterMBean(String name) throws MBeanRegistrationException, InstanceNotFoundException,
	MalformedObjectNameException
	{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		mbs.unregisterMBean(new ObjectName(name));
		logger.info("MBean '" + name + "' has been un-registered");
	}
}

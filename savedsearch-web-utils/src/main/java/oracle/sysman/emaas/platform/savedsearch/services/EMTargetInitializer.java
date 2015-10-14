/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.services;

import java.lang.management.ManagementFactory;

import javax.annotation.Resource;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import oracle.sysman.emaas.platform.savedsearch.utils.JMXUtil;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.application.ApplicationLifecycleEvent;

public class EMTargetInitializer implements ApplicationServiceManager
{

	private static final Logger logger = LogManager.getLogger(EMTargetMXBeanImpl.class);
	private static final String m_target_type = EMTargetConstants.m_target_type;
	private static final String SVR_MBEAN_NAME_PREFIX = "EMDomain:Type=EMIntegration,EMTargetType=" + m_target_type + ",Name=";

	@Resource(lookup = "java:module/ModuleName")
	private String moduleName;

	@Resource(lookup = "java:app/AppName")
	private String appName;

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#getName()
	 */
	@Override
	public String getName()
	{
		return "Saved Search Target Initializer";
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#postStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception
	{
		String emTargetMBeanName = "";
		try {
			appName = InitialContext.doLookup("java:app/AppName");
			emTargetMBeanName = SVR_MBEAN_NAME_PREFIX + appName;
			ObjectName emTargetRuntimeName = new ObjectName(emTargetMBeanName);
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			EMTargetMXBeanImpl mxbean = new EMTargetMXBeanImpl(appName);
			mbs.registerMBean(mxbean, emTargetRuntimeName);
			JMXUtil.getInstance().registerMBeans();
		}
		catch (InstanceAlreadyExistsException e) {
			logger.error("EMTargetMXBeanImpl already exists ", e);
		}
		catch (MalformedObjectNameException e) {
			logger.error("Incorrect Object name for MBean", e);
		}
		catch (Exception e) {
			logger.error("MBean Registration failed for EMTargetMxBean", e);
		}

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#postStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws Exception
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#preStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws Exception
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		String emTargetMBeanName = SVR_MBEAN_NAME_PREFIX + appName;
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName emTargetRuntimeName = new ObjectName(emTargetMBeanName);
			if (mbs.isRegistered(emTargetRuntimeName)) {
				mbs.unregisterMBean(emTargetRuntimeName);
			}
			JMXUtil.getInstance().unregisterMBeans();

		}
		catch (Exception e) {
			logger.error("Unregister MBean for " + m_target_type + " failed.", e);
		}

	}
}

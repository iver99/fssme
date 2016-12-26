/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JMXUtil
{

	private static final Logger _logger = LogManager.getLogger(JMXUtil.class);
	private static volatile JMXUtil instance = null;

	public static final String SAVEDSEARCHCOMPARATOR_STATUS = "";//"oracle.sysman.emaas.platform.savedsearch:Name=DashboardUIStatus,Type=oracle.sysman.emaas.platform.dashboards.ui.targetmodel.services.DashboardUIStatus";

	public static JMXUtil getInstance()
	{
		if (instance == null) {
			synchronized (JMXUtil.class) {
				if (instance == null) {
					instance = new JMXUtil();
				}
			}
		}

		return instance;
	}

	private MBeanServer server = null;

	private JMXUtil()
	{
	}

	public void registerMBeans() throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException,
			NotCompliantMBeanException
	{
		server = ManagementFactory.getPlatformMBeanServer();
		ObjectName statusObjectName = new ObjectName(SAVEDSEARCHCOMPARATOR_STATUS);
		if (!server.isRegistered(statusObjectName)) {
			SavedsearchComparatorStatus savedsearchComparatorStatus = new SavedsearchComparatorStatus();
			server.registerMBean(savedsearchComparatorStatus, statusObjectName);
		}

		_logger.info("start Savedsearch Comparator MBeans!");
	}

	public void unregisterMBeans() throws MalformedObjectNameException, MBeanRegistrationException, InstanceNotFoundException
	{
		ObjectName statusObjectName = new ObjectName(SAVEDSEARCHCOMPARATOR_STATUS);
		if (server.isRegistered(statusObjectName)) {
			server.unregisterMBean(statusObjectName);
		}
		_logger.info("stop Savedsearch Comparator MBeans!");
	}

}

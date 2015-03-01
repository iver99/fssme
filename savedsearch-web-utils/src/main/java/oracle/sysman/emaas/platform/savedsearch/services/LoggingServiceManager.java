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

import java.io.InputStream;

import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

import weblogic.application.ApplicationLifecycleEvent;

/**
 * @author sdhamdhe Initializes the loggers - by default we log entries to
 *         /var/opt/ORCLemaas/logs/savedsearchService/savedSearchService.log
 */
public class LoggingServiceManager implements ApplicationServiceManager
{

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#getName()
	 */
	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return "Saved Search Logging Service";
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws Exception
	{
		InputStream stream = null;
		try {
			stream = LoggingServiceManager.class.getResourceAsStream("/log4j_ssf.xml");
			new DOMConfigurator().doConfigure(stream, LogManager.getLoggerRepository());
		}
		finally {
			if (stream != null) {
				try {
					stream.close();
				}
				catch (Exception e) {
					//ignore exception
				}
			}
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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws Exception
	{
		// TODO Auto-generated method stub

	}
}

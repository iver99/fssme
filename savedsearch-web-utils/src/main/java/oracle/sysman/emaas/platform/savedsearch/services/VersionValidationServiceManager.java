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

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SchemaVersion;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.VersionManager;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractApplicationLifecycleService;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.application.ApplicationLifecycleEvent;

/**
 * Code version should match schema version, this is used to validate this during SSF service startup If validation fails, SSF
 * service will not startup
 *
 * @author miao
 */
public class VersionValidationServiceManager implements ApplicationServiceManager
{
	private final Logger LOGGER = LogManager.getLogger(AbstractApplicationLifecycleService.APPLICATION_LOGGER_SUBSYSTEM
			+ ".serviceversionvalidation");
	public static final String SERVICE_NAME_VERSION_VALIDATION = "Version Validation Service";
	public static final int SSF_CODE_VERSION_MAJOR = 1;
	public static final int SSF_CODE_VERSION_MINOR = 0;

	/**
	 * Code Version History <br>
	 * 0.5 The first released version of SSF
	 */

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#getName()
	 */
	@Override
	public String getName()
	{
		return SERVICE_NAME_VERSION_VALIDATION;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStart(ApplicationLifecycleEvent evt) 
	{
		// TODO Auto-generated method stub
		SchemaVersion schemaVer = VersionManager.getInstance().getSchemaVersion();
		try {
			if (SSF_CODE_VERSION_MAJOR == schemaVer.getMajorVersion() && SSF_CODE_VERSION_MINOR == schemaVer.getMinorVersion()) {
				LOGGER.info("code version matched schema version:(" + SSF_CODE_VERSION_MAJOR + "." + SSF_CODE_VERSION_MINOR + ")");
			}
			else {
				String errMsg = "Failed to startup. Code version(" + SSF_CODE_VERSION_MAJOR + "." + SSF_CODE_VERSION_MINOR
						+ ") doesn't match schema version:(" + schemaVer.getMajorVersion() + "." + schemaVer.getMinorVersion()
						+ ")";
				LOGGER.error(errMsg);
			}
		}
		catch (Exception e) {
			LOGGER.error("Failed to startup:" + e.getMessage(), e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#postStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void postStop(ApplicationLifecycleEvent evt) 
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStart(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStart(ApplicationLifecycleEvent evt) 
	{

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.savedsearch.services.ApplicationService#preStop(weblogic.application.ApplicationLifecycleEvent)
	 */
	@Override
	public void preStop(ApplicationLifecycleEvent evt) 
	{
		// TODO Auto-generated method stub

	}

}

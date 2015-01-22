/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.UpgradeManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * @author vinjoshi
 */
public abstract class UpgradeManager
{
	/**
	 * Returns an instance of the manager.
	 * 
	 * @return an instance of the manager
	 */
	public static UpgradeManager getInstance()
	{
		return UpgradeManagerImpl.getInstance();
	}

	public abstract boolean upgradeData() throws EMAnalyticsFwkException;
}

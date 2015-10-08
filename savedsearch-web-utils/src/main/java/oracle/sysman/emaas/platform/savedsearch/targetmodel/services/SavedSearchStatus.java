/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import oracle.sysman.emaas.platform.savedsearch.utils.GlobalStatus;

/**
 * @author vinjoshi
 */
public class SavedSearchStatus implements SavedSearchStatusMBean
{

	@Override
	public String getStatus()
	{
		if (!GlobalStatus.isSavedSearchUp()) {
			return GlobalStatus.STATUS_DOWN;
		}

		else if (GlobalStatus.isSavedSearchUp()) {

			return GlobalStatus.STATUS_UP;
		}
		else {
			return GlobalStatus.STATUS_OUT_OF_SERVICE;
		}

	}

	@Override
	public String getStatusMsg()
	{

		if (!GlobalStatus.isSavedSearchUp()) {
			return "SavedSearch is being stopped.";
		}

		else if (GlobalStatus.isSavedSearchUp()) {
			return "SavedSearch is up and running.";
		}
		else {
			return "SavedSearch is out of service.";
		}

	}

}

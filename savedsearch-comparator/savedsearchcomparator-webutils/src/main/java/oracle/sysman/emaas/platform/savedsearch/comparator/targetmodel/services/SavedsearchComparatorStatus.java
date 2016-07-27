/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services;

public class SavedsearchComparatorStatus implements SavedsearchComparatorStatusMBean
{
	@Override
	public String getStatus()
	{
		if (!GlobalStatus.issavedsearchComparatorUp()) {
			return GlobalStatus.STATUS_DOWN;
		}

		else if (GlobalStatus.issavedsearchComparatorUp()) {

			return GlobalStatus.STATUS_UP;
		}
		else {
			return GlobalStatus.STATUS_OUT_SSF_SERVICE;
		}

	}

	@Override
	public String getStatusMsg()
	{

		if (!GlobalStatus.issavedsearchComparatorUp()) {
			return "Savedsearch-Comparator is being stopped.";
		}

		else if (GlobalStatus.issavedsearchComparatorUp()) {
			return "Savedsearch-Comparator is up and running.";
		}
		else {
			return "Savedsearch-Comparator is out of service.";
		}

	}

}

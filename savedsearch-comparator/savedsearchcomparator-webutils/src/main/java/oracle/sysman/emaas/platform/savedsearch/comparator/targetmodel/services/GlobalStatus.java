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

import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalStatus
{
	public static final String STATUS_DOWN = "DOWN";
	public static final String STATUS_UP = "UP";
	public static final String STATUS_OUT_SSF_SERVICE = "OUT_SSF_SERVICE";
	private final static boolean SAVEDSEARCHCOMPARATOR_UP = true;
	private final static boolean SAVEDSEARCHCOMPARATOR_DOWN = false;
	private static AtomicBoolean savedsearchComparatorStatus = new AtomicBoolean(SAVEDSEARCHCOMPARATOR_UP);

	public static boolean issavedsearchComparatorUp()
	{
		return savedsearchComparatorStatus.get();
	}

	public static void setsavedsearchComparatorDownStatus()
	{
		savedsearchComparatorStatus.set(SAVEDSEARCHCOMPARATOR_DOWN);
	}

	public static void setsavedsearchComparatorUpStatus()
	{
		savedsearchComparatorStatus.set(SAVEDSEARCHCOMPARATOR_UP);
	}

}

/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author vinjoshi
 */
public class GlobalStatus
{
	public static final String STATUS_DOWN = "DOWN";
	public static final String STATUS_UP = "UP";
	public static final String STATUS_OUT_OF_SERVICE = "OUT_OF_SERVICE";
	private final static boolean SAVEDSEARCH_UP = true;
	private final static boolean SAVEDSEARCH_DOWN = false;
	private static AtomicBoolean savedSearchStatus = new AtomicBoolean(SAVEDSEARCH_UP);

	public static boolean isSavedSearchUp()
	{
		return savedSearchStatus.get();
	}

	public static void setSavedSearchDownStatus()
	{
		savedSearchStatus.set(SAVEDSEARCH_DOWN);
	}

	public static void setSavedSearchUpStatus()
	{
		savedSearchStatus.set(SAVEDSEARCH_UP);
	}

}

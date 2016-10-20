/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */
 
package oracle.sysman.emSDK.emaas.platform.savedsearch.exception;

/**
 * @author guochen
 *
 */
public class EMAnalyticsDatabaseUnavilException extends EMAnalyticsFwkException
{
	private static final long serialVersionUID = 9145095920461462561L;
	private static final String DATABASE_UNAVAILABLE_ERROR_MSG = "(SSF Dependency Error) SSF database is unavailable.";
	
	/**
	 * @param throwable
	 */
	public EMAnalyticsDatabaseUnavilException()
	{
		super(EMAnalyticsDatabaseUnavilException.DATABASE_UNAVAILABLE_ERROR_MSG, 
				EMAnalyticsFwkException.ERR_DATABASE_UNAVAILABLE, null);
	}
}

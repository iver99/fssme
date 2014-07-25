/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.exception;

/**
 * This is a temporary Exception, may merge/remove later on
 *
 * @author miayu
 */
public class EMAnalyticsFwkJsonException extends Exception
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7933688847951894178L;

	/**
	 * Copied from oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException, need to merge these two in
	 * a better approach
	 */
	public static final int JSON_OBJECT_TO_JSON_EXCEPTION = 70040;

	public static final int JSON_JSON_TO_OBJECT = 70041;

	private final int errorCode;

	public EMAnalyticsFwkJsonException(String message, int errorCode, Throwable throwable)
	{
		super(message, throwable);
		this.errorCode = errorCode;

	}

	public int getStatusCode()
	{
		return 500;
	}
}

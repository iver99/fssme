/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;

import java.io.UnsupportedEncodingException;

/**
 * @author vinjoshi
 */
public class validationUtil
{

	public static void validateLength(String name, String value, int fieldLength) throws EMAnalyticsWSException
	{
		try {

			if (value != null && value.getBytes("UTF-8").length > fieldLength) {
				throw new EMAnalyticsWSException("The maximum length of a " + name + " is " + fieldLength
						+ " bytes.Please enter valid " + name + ".", EMAnalyticsWSException.JSON_INVALID_LENGTH);
			}

		}
		catch (UnsupportedEncodingException e) {

		}

	}
}

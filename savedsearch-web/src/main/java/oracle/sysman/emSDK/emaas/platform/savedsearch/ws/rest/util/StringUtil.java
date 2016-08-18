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

/**
 * @author aduan
 */
public class StringUtil
{
	private StringUtil() {
	}

	public static boolean isEmpty(String s)
	{
		if (s == null) {
			return true;
		}
		if ("".equals(s.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSpecialCharFound(String value)
	{

		boolean specialCharFound = false;
		if (value == null || "".equalsIgnoreCase(value.trim())) {
			return specialCharFound;
		}
		int sLen = value.length();
		for (int i = 0; i < sLen && !specialCharFound; i++) {
			char c = value.charAt(i);
			switch (c) {
				case '<':
				case '>':
					specialCharFound = true;
					default:
						break;
			}
		}

		return specialCharFound;
	}

}

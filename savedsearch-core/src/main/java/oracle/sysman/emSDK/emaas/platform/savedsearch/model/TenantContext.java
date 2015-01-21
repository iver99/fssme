/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

/**
 * @author vinjoshi
 *
 */
/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

/**
 * @author vinjoshi
 */
public class TenantContext
{

	private static final ThreadLocal<String> _threadLocal = new ThreadLocal<String>();

	/**
	 * clear context to avoid memory leak for TaasContext
	 */
	public static void clearContext()
	{
		String current = _threadLocal.get();
		if (current != null) {

			_threadLocal.remove();

		}

	}

	public static String getContext()
	{
		return _threadLocal.get();
	}

	public static void setContext(String value)
	{
		_threadLocal.set(value);

	}

	private TenantContext()
	{

	}

}

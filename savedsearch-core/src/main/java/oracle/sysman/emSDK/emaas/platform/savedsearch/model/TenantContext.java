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

	private static final ThreadLocal<Long> _threadLocal = new ThreadLocal<Long>();

	/**
	 * clear context to avoid memory leak for TaasContext
	 */
	public static void clearContext()
	{
		Long current = _threadLocal.get();
		if (current != null) {

			_threadLocal.remove();

		}

	}

	public static Long getContext()
	{
		return _threadLocal.get();
	}

	public static void setContext(Long value)
	{
		_threadLocal.set(value);

	}

	private TenantContext()
	{

	}

}

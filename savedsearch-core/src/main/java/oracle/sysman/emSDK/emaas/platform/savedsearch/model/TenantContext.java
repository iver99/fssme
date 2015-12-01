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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;

import org.apache.logging.log4j.ThreadContext;

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

	private static final ThreadLocal<TenantInfo> _threadLocal = new ThreadLocal<TenantInfo>();

	/**
	 * clear context to avoid memory leak for TaasContext
	 */
	public static void clearContext()
	{
		TenantInfo current = _threadLocal.get();
		if (current != null) {

			_threadLocal.remove();

		}

		ThreadContext.remove(LogUtil.LOG_PROP_TENANTID);
	}

	public static TenantInfo getContext()
	{
		return _threadLocal.get();
	}

	public static void setContext(TenantInfo info)
	{
		_threadLocal.set(info);

	}

	private TenantContext()
	{

	}

}

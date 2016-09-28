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

	private static final ThreadLocal<TenantInfo> threadLocal = new ThreadLocal<TenantInfo>();

	/**
	 * clear context to avoid memory leak for TaasContext
	 */
	public static void clearContext()
	{
		TenantInfo current = threadLocal.get();
		if (current != null) {

			threadLocal.remove();

		}

		ThreadContext.remove(LogUtil.LOG_PROP_TENANTID);
	}

	public static TenantInfo getContext()
	{
		return threadLocal.get();
	}

	public static void setContext(TenantInfo info)
	{
		threadLocal.set(info);

	}

	private TenantContext()
	{

	}

}

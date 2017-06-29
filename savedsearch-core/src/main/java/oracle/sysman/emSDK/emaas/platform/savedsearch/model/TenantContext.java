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

import java.util.Locale;

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
	private static final ThreadLocal<Locale> localeThreadLocal = new ThreadLocal<>();
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
		clearLocale();
	}

	public static TenantInfo getContext()
	{
		return threadLocal.get();
	}

	public static void setContext(TenantInfo info)
	{
		threadLocal.set(info);

	}
	public static void clearLocale()
	{
		Locale locale = localeThreadLocal.get();
		if (locale != null) {
			localeThreadLocal.remove();
		}
	}
	// default local is Locale.US
	public static Locale getLocale()
	{
		Locale locale = localeThreadLocal.get();
		return locale == null ? Locale.US : locale;
	}
	public static void setLocale(Locale locale)
	{
		localeThreadLocal.set(locale);
	}
	private TenantContext()
	{

	}

}

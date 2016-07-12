/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

/**
 * @author aduan
 */
public class RequestContext
{
	public enum RequestType
	{
		// External request with presence of OAM_REMOTE_USER header
		EXTERNAL,
		// Internal request with both X-USER-IDENTITY-DOMAIN-NAME and X-REMOTE-USER header
		INTERNAL_TENANT_USER,
		// Internal request with only X-USER-IDENTITY-DOMAIN-NAME
		INTERNAL_TENANT,
		// Other requests that should not be allowed
		ERRONEOUS
	}

	private static final ThreadLocal<RequestType> _threadLocal = new ThreadLocal<RequestType>();

	/**
	 * clear context to avoid memory leak
	 */
	public static void clearContext()
	{
		RequestType current = _threadLocal.get();
		if (current != null) {
			_threadLocal.remove();
		}
	}

	public static RequestType getContext()
	{
		return _threadLocal.get();
	}

	public static void setContext(RequestType reqType)
	{
		_threadLocal.set(reqType);

	}

	private RequestContext()
	{
	}
}
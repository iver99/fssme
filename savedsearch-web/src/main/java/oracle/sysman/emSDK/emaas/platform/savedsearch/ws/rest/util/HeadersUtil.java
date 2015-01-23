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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

/**
 * @author vinjoshi
 */
public class HeadersUtil
{
	private static final String HEADER_TENANT_ID = "X-USER-IDENTITY-DOMAIN-NAME";

	public static String getTenantId(HttpHeaders request)
	{
		List<String> header = request.getRequestHeader(HEADER_TENANT_ID);
		return header.get(0);
	}

	public static String getTenantId(HttpServletRequest request)
	{
		String header = request.getHeader(HEADER_TENANT_ID);
		return header;
	}
}

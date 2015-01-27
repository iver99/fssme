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

import javax.servlet.http.HttpServletRequest;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;

/**
 * @author vinjoshi
 *
 */

/**
 * @author vinjoshi
 */
public class HeadersUtil
{
	private static final String HEADER_TENANT_ID = "X-USER-IDENTITY-DOMAIN-NAME";

	public static Long getInternalTenantId(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String header = request.getHeader(HEADER_TENANT_ID);
		Long internalId = null;
		if (header == null) {
			throw new EMAnalyticsFwkException("Tenant Id cannot be null.", EMAnalyticsFwkException.ERR_EMPTY_TENANT_ID, null);
		}
		try {
			internalId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(header);
			System.out.println("found");
		}
		catch (BasicServiceMalfunctionException e) {
			throw new EMAnalyticsFwkException("Tenant Id " + header + " does not exist.",
					EMAnalyticsFwkException.ERR_VALID_TENANT_ID, null);
		}

		return internalId;
	}

	public static Long getTenantId(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		return HeadersUtil.getInternalTenantId(request);
	}
}

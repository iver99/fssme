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
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private static final String HEADER_REMORE_USER = "X-REMOTE-USER";

	public static final String SSF_HEADER = "ssfheadertest";

	private static final Logger _logger = LogManager.getLogger(HeadersUtil.class);

	public static Long getTenantId(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		return HeadersUtil.getInternalTenantId(request);
	}

	public static TenantInfo getTenantInfo(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		Long id = HeadersUtil.getInternalTenantId(request);
		String user = HeadersUtil.getUserName(request);
		return new TenantInfo(user, id);

	}

	private static Long getInternalTenantId(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String header = request.getHeader(HEADER_TENANT_ID);
		Long internalId = null;
		String testHeader = request.getHeader(SSF_HEADER);
		Boolean isTestEnv = false;
		if (testHeader != null) {
			isTestEnv = testHeader.equalsIgnoreCase(SSF_HEADER);
		}

		if (isTestEnv) {
			try {
				internalId = Long.parseLong(header);
			}
			catch (NumberFormatException e) {
				internalId = null;
			}
		}
		else {

			if (header == null) {
				throw new EMAnalyticsFwkException("Tenant Id cannot be null.", EMAnalyticsFwkException.ERR_EMPTY_TENANT_ID, null);
			}
			try {
				internalId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(header);
				_logger.info("id" + internalId);
			}
			catch (BasicServiceMalfunctionException e) {
				throw new EMAnalyticsFwkException("Tenant Id " + header + " does not exist.",
						EMAnalyticsFwkException.ERR_VALID_TENANT_ID, null);
			}

			if (internalId == null) {
				throw new EMAnalyticsFwkException("Internal Tenant Id is null.", EMAnalyticsFwkException.ERR_VALID_TENANT_ID,
						null);
			}

		}

		return internalId;
	}

	private static String getUserName(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String userTenant = null;
		String userName = null;

		userTenant = request.getHeader(HEADER_REMORE_USER);
		if (userTenant == null) {
			throw new EMAnalyticsFwkException("User name  cannot be null.", EMAnalyticsFwkException.ERR_VALID_USER_NAME, null);
		}
		int idx = userTenant.indexOf(".");
		userName = userTenant.substring(idx + 1, userTenant.length());
		if (userName == null || "".equalsIgnoreCase(userName.trim())) {
			throw new EMAnalyticsFwkException("User name  cannot be null.", EMAnalyticsFwkException.ERR_VALID_USER_NAME, null);
		}

		return userName;
	}
}

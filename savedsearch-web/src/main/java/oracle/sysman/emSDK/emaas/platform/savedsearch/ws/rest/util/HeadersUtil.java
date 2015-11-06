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

	public static final String SSF_HEADER = "ssfheadertest";

	public static final String OAM_HEADER = "OAM_REMOTE_USER";

	private static final Logger _logger = LogManager.getLogger(HeadersUtil.class);

	public static TenantInfo getTenantInfo(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		HeadersUtil.validateOAMHeader(request);
		Long id = HeadersUtil.getInternalTenantId(request);
		String userTenant = request.getHeader(OAM_HEADER);
		String user = userTenant.substring(userTenant.indexOf(".") + 1, userTenant.length());
		TenantInfo info = new TenantInfo(user, id);
		info.settenantName(userTenant.substring(0, userTenant.indexOf(".")));
		return info;

	}

	private static Long getInternalTenantId(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String header = request.getHeader(OAM_HEADER);
		Long internalId = null;
		String testHeader = request.getHeader(SSF_HEADER);
		Boolean isTestEnv = false;
		if (testHeader != null) {
			isTestEnv = testHeader.equalsIgnoreCase(SSF_HEADER);
		}
		if (isTestEnv) {
			try {
				internalId = Long.parseLong(header.substring(0, header.indexOf(".")));
			}
			catch (NumberFormatException e) {
				internalId = null;
			}
		}
		else {
			String tName = header.substring(0, header.indexOf("."));
			try {
				internalId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(tName);
				_logger.info("Internal tenant id =" + internalId);
			}
			catch (BasicServiceMalfunctionException e) {
				_logger.error("Tenant Id " + header + " does not exist.");
				throw new EMAnalyticsFwkException("Tenant Id " + tName + " does not exist.",
						EMAnalyticsFwkException.ERR_VALID_TENANT_ID, null);
			}

			if (internalId == null) {
				_logger.error("Internal Tenant Id is null.");
				throw new EMAnalyticsFwkException("Internal Tenant Id is null.", EMAnalyticsFwkException.ERR_VALID_TENANT_ID,
						null);
			}

		}

		return internalId;
	}

	/*private static String getUserName(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String userTenant = null;
		String userName = null;

		userTenant = request.getHeader(HEADER_REMORE_USER);
		if (userTenant == null) {
			_logger.error(HEADER_REMORE_USER + " header value missing.");
			throw new EMAnalyticsFwkException(HEADER_REMORE_USER + " header value missing.",
					EMAnalyticsFwkException.ERR_VALID_USER_NAME, null);
		}
		int idx = userTenant.indexOf(".");
		if (idx <= 0) {

			_logger.error("Tenant name was  not provided , Please provide " + HEADER_REMORE_USER
					+ " header value in following format <tenant_name>.<user_name>");
			throw new EMAnalyticsFwkException("Tenant name was not provided ,Please provide " + HEADER_REMORE_USER
					+ " header value in following format <tenant_name>.<user_name>", EMAnalyticsFwkException.ERR_VALID_USER_NAME,
					null);
		}

		userName = userTenant.substring(idx + 1, userTenant.length());
		if (userName == null || "".equalsIgnoreCase(userName.trim())) {
			_logger.error("User name was not provided , Please provide " + HEADER_REMORE_USER
					+ " header value in following format <tenant_name>.<user_name>");
			throw new EMAnalyticsFwkException("User name was not provided , Please provide " + HEADER_REMORE_USER
					+ " header value in following format <tenant_name>.<user_name>", EMAnalyticsFwkException.ERR_VALID_USER_NAME,
					null);
		}

		return userName;
	}*/

	private static void validateOAMHeader(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String userTenant = null;
		String userName = null;

		userTenant = request.getHeader(OAM_HEADER);
		if (userTenant == null) {
			_logger.error(OAM_HEADER + " header value is missing.");
			throw new EMAnalyticsFwkException(OAM_HEADER + " header value is missing.",
					EMAnalyticsFwkException.ERR_VALID_OAM_HEADER, null);
		}
		int idx = userTenant.indexOf(".");
		if (idx <= 0) {

			_logger.error(" Please provide " + OAM_HEADER + " header value in following format <tenant_name>.<user_name>");
			throw new EMAnalyticsFwkException("Please provide " + OAM_HEADER
					+ " header value in following format <tenant_name>.<user_name>", EMAnalyticsFwkException.ERR_VALID_USER_NAME,
					null);
		}

		userName = userTenant.substring(0, idx);
		if (userName == null || "".equalsIgnoreCase(userName.trim())) {
			_logger.error("Tenant name was not provided , Please provide " + OAM_HEADER
					+ " header value in following format <tenant_name>.<user_name>");
			throw new EMAnalyticsFwkException("Tenant name was not provided , Please provide " + OAM_HEADER
					+ " header value in following format <tenant_name>.<user_name>", EMAnalyticsFwkException.ERR_VALID_USER_NAME,
					null);
		}

		userName = userTenant.substring(idx + 1, userTenant.length());

		if (userName == null || "".equalsIgnoreCase(userName.trim())) {
			_logger.error("User name was not provided , Please provide " + OAM_HEADER
					+ " header value in following format <tenant_name>.<user_name>");
			throw new EMAnalyticsFwkException("User name was not provided , Please provide " + OAM_HEADER
					+ " header value in following format <tenant_name>.<user_name>", EMAnalyticsFwkException.ERR_VALID_USER_NAME,
					null);
		}

	}
}

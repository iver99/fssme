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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext.RequestType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

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

	// EMCPSSF-253 saved search REST calls should not require OAM_REMOTE_USER.
	// Look for X-REMOTE-USER if OAM_REMOTE_USER is missing.
	// For backwards compatibility, need to continue supporting the use of OAM_REMOTE_USER by internal clients.
	public static final String OAM_REMOTE_USER_HEADER = "OAM_REMOTE_USER";
	public static final String X_REMOTE_USER_HEADER = "X-REMOTE-USER";
	public static final String X_USER_IDENTITY_DOMAIN_NAME_HEADER = "X-USER-IDENTITY-DOMAIN-NAME";

	private static final Logger LOGGER = LogManager.getLogger(HeadersUtil.class);

	private HeadersUtil() {
	}

	public static TenantInfo getTenantInfo(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		HeadersUtil.validateOAMHeader(request);
		Long id = HeadersUtil.getInternalTenantId(request);
		String userTenant = HeadersUtil.getRemoteUserHeader(request);
		String user = null;
		String tenantName = null;
		// For internal request with tenant only
		if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
			tenantName = request.getHeader(X_USER_IDENTITY_DOMAIN_NAME_HEADER);
		}
		// For external request and internal request with tenant and user
		else {
			user = userTenant.substring(userTenant.indexOf(".") + 1, userTenant.length());
			tenantName = userTenant.substring(0, userTenant.indexOf("."));
		}
		TenantInfo info = new TenantInfo(user, id);
		info.settenantName(tenantName);
		return info;

	}

	private static Long getInternalTenantId(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String header = HeadersUtil.getRemoteUserHeader(request);
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
				LOGGER.error(e.getLocalizedMessage());
				internalId = null;
			}
		}
		else {
			String tName = null;
			if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
				tName = request.getHeader(X_USER_IDENTITY_DOMAIN_NAME_HEADER);
			}
			else {
				tName = header.substring(0, header.indexOf("."));
			}
			try {
				internalId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(tName);
				LOGGER.info("Internal tenant id =" + internalId);
			}
			catch (BasicServiceMalfunctionException e) {
				LOGGER.error("Tenant Id " + header + " does not exist.");
				throw new EMAnalyticsFwkException("Tenant Id " + tName + " does not exist.",
						EMAnalyticsFwkException.ERR_VALID_TENANT_ID, null);
			}

			if (internalId == null) {
				LOGGER.error("Internal Tenant Id is null.");
				throw new EMAnalyticsFwkException("Internal Tenant Id is null.", EMAnalyticsFwkException.ERR_VALID_TENANT_ID,
						null);
			}

			// EMCSSF-168: opc tenant id is expected in logging messages
			ThreadContext.put(LogUtil.LOG_PROP_TENANTID, header);
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

	private static String getRemoteUserHeader(HttpServletRequest request)
	{
		String remoteUser = request.getHeader(OAM_REMOTE_USER_HEADER);
		// Look for X-REMOTE-USER if OAM_REMOTE_USER is missing
		if (remoteUser == null) {
			remoteUser = request.getHeader(X_REMOTE_USER_HEADER);
		}
		return remoteUser;
	}

	private static void validateOAMHeader(HttpServletRequest request) throws EMAnalyticsFwkException
	{
		String userTenant = null;
		String userName = null;

		userTenant = HeadersUtil.getRemoteUserHeader(request);
		// For internal request with tenant only
		if (RequestType.INTERNAL_TENANT.equals(RequestContext.getContext())) {
			String tenantIdHeader = request.getHeader(X_USER_IDENTITY_DOMAIN_NAME_HEADER);
			if (tenantIdHeader == null) {
				LOGGER.error(X_USER_IDENTITY_DOMAIN_NAME_HEADER + " header value is missing.");
				throw new EMAnalyticsFwkException(X_USER_IDENTITY_DOMAIN_NAME_HEADER + " header value is missing.",
						EMAnalyticsFwkException.ERR_VALID_OAM_HEADER, null);
			}
		}
		// For external and internal request with both tenant and user
		else {
			if (userTenant == null) {
				LOGGER.error(X_REMOTE_USER_HEADER + " header value is missing.");
				throw new EMAnalyticsFwkException(X_REMOTE_USER_HEADER + " header value is missing.",
						EMAnalyticsFwkException.ERR_VALID_OAM_HEADER, null);
			}
			int idx = userTenant.indexOf(".");
			if (idx <= 0) {

				LOGGER.error(" Please provide " + X_REMOTE_USER_HEADER
						+ " header value in following format <tenant_name>.<user_name>");
				throw new EMAnalyticsFwkException("Please provide " + X_REMOTE_USER_HEADER
						+ " header value in following format <tenant_name>.<user_name>",
						EMAnalyticsFwkException.ERR_VALID_USER_NAME, null);
			}

			userName = userTenant.substring(0, idx);
			if (userName == null || "".equalsIgnoreCase(userName.trim())) {
				LOGGER.error("Tenant name was not provided , Please provide " + X_REMOTE_USER_HEADER
						+ " header value in following format <tenant_name>.<user_name>");
				throw new EMAnalyticsFwkException("Tenant name was not provided , Please provide " + X_REMOTE_USER_HEADER
						+ " header value in following format <tenant_name>.<user_name>",
						EMAnalyticsFwkException.ERR_VALID_USER_NAME, null);
			}

			userName = userTenant.substring(idx + 1, userTenant.length());

			if (userName == null || "".equalsIgnoreCase(userName.trim())) {
				LOGGER.error("User name was not provided , Please provide " + X_REMOTE_USER_HEADER
						+ " header value in following format <tenant_name>.<user_name>");
				throw new EMAnalyticsFwkException("User name was not provided , Please provide " + X_REMOTE_USER_HEADER
						+ " header value in following format <tenant_name>.<user_name>",
						EMAnalyticsFwkException.ERR_VALID_USER_NAME, null);
			}
		}

	}
}

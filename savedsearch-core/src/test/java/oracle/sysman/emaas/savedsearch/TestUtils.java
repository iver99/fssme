/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;

/**
 * @author vinjoshi
 */
public class TestUtils
{
	public static final String TENANT_ID_OPC1 = "TenantOpc1";
	public static final String TENANT_ID_OPC2 = "TenantOpc2";
	public static final String TENANT_ID_OPC3 = "TenantOpc3";

	public static Long getInternalTenantId(String id)
	{
		Long internalId = null;

		/* for testing 
		try {
			internalId = Long.parseLong(id);
		}
		catch (NumberFormatException e) {

		}

		if (internalId != null) {
			return internalId;
		} */

		if (id == null) {
			new EMAnalyticsFwkException("Tenant Id cannot be null.", EMAnalyticsFwkException.ERR_EMPTY_TENANT_ID, null);
		}
		try {
			internalId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(id);
		}
		catch (BasicServiceMalfunctionException e) {
			new EMAnalyticsFwkException("Tenant Id " + id + " does not exist.", EMAnalyticsFwkException.ERR_VALID_TENANT_ID, null);
		}

		return internalId;
	}
}

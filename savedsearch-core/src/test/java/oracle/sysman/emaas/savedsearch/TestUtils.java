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

/**
 * @author vinjoshi
 */
public class TestUtils
{
	public static final String TENANT_ID_OPC1 = "1";
	public static final String TENANT_ID_OPC2 = "2";
	public static final String TENANT_ID_OPC3 = "3";

	public static Long getInternalTenantId(String id)
	{
		Long internalId = null;

		try {
			internalId = Long.parseLong(id);
		}
		catch (NumberFormatException e) {
			id = null;
		}
		return internalId;
	}
}

/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

/**
 * @author vinjoshi
 */
public class TenantUtil
{
	private final String m_value;

	public TenantUtil(String value)
	{
		m_value = value;
	}

	public String getTenantId()
	{
		String userTenant = null;
		String userName = null;
		try {
			userTenant = m_value;
			int idx = userTenant.indexOf(".");
			userName = userTenant.substring(0, idx);
		}
		catch (Exception e) {
			userName = null;
		}
		return userName;
	}

	public String getUserName()
	{
		String userTenant = null;
		String userName = null;
		try {
			userTenant = m_value;
			int idx = userTenant.indexOf(".");
			userName = userTenant.substring(idx + 1, userTenant.length());
		}
		catch (Exception e) {
			userName = null;
		}
		return userName;
	}

}

/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

/**
 * @author vinjoshi
 */
public class TenantInfo
{
	private String m_username;
	private Long m_tenantInternalId;

	public TenantInfo(String user, Long id)
	{
		m_username = user;
		m_tenantInternalId = id;
	}

	public Long getTenantInternalId()
	{
		return m_tenantInternalId;
	}

	public String getUsername()
	{
		return m_username;
	}

	public void setTenantInternalId(Long tenantInternalId)
	{
		m_tenantInternalId = tenantInternalId;
	}

	public void setUsername(String username)
	{
		m_username = username;
	}

}

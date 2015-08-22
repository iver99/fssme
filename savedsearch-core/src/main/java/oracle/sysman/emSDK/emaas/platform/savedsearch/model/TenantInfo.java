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
	private String m_tenantName;

	private Long m_tenantInternalId;

	public TenantInfo(String user, Long id)
	{
		m_username = user;
		m_tenantInternalId = id;

	}

	public TenantInfo(String user, Long id, String tname)
	{
		m_username = user;
		m_tenantInternalId = id;
		m_tenantName = tname;
	}

	public Long getTenantInternalId()
	{
		return m_tenantInternalId;
	}

	/**
	 * @return the m_tenantName
	 */
	public String gettenantName()
	{
		return m_tenantName;
	}

	public String getUsername()
	{
		return m_username;
	}

	public void setTenantInternalId(Long tenantInternalId)
	{
		m_tenantInternalId = tenantInternalId;
	}

	/**
	 * @param m_tenantName
	 *            the m_tenantName to set
	 */
	public void settenantName(String m_tenantName)
	{
		this.m_tenantName = m_tenantName;
	}

	public void setUsername(String username)
	{
		m_username = username;
	}

	@Override
	public String toString()
	{
		String tName = m_tenantName != null && !m_tenantName.isEmpty() ? m_tenantName : "";
		long id = m_tenantInternalId != null ? m_tenantInternalId : -1;
		String uName = m_username != null && !m_username.isEmpty() ? m_username : "";
		String result = String.format(tName + " |  " + id + " | " + uName) + System.getProperty("line.separator");
		return result;

	}
}

/*public static void main(String rags[])
{
	TenantInfo t = new TenantInfo("", null);
	t.settenantName("");
	System.out.print(t);

	t = new TenantInfo(null, null);
	t.settenantName(null);
	System.out.print(t);

	t = new TenantInfo("a", 4555L);
	t.settenantName("b");
	System.out.print(t);
}*/
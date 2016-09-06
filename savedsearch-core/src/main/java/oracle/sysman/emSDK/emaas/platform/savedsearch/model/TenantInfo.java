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

	private String mUsername;
	private String mTenantName;

	private Long mTenantInternalId;

	public TenantInfo(String user, Long id)
	{
		mUsername = user;
		mTenantInternalId = id;

	}

	public TenantInfo(String user, Long id, String tname)
	{
		mUsername = user;
		mTenantInternalId = id;
		mTenantName = tname;
	}

	public Long getTenantInternalId()
	{
		return mTenantInternalId;
	}

	/**
	 * @return the mTenantName
	 */
	public String gettenantName()
	{
		return mTenantName;
	}

	public String getUsername()
	{
		return mUsername;
	}

	public void setTenantInternalId(Long tenantInternalId)
	{
		mTenantInternalId = tenantInternalId;
	}

	/**
	 * @param mTenantName
	 *            the mTenantName to set
	 */
	public void settenantName(String mTenantName)
	{
		this.mTenantName = mTenantName;
	}

	public void setUsername(String username)
	{
		mUsername = username;
	}

	@Override
	public String toString()
	{
		String tName = mTenantName != null && !mTenantName.isEmpty() ? mTenantName : "";
		long id = mTenantInternalId != null ? mTenantInternalId : -1;
		String uName = mUsername != null && !mUsername.isEmpty() ? mUsername : "";

		String result = String.format("[TenantName:" + tName + "][InternalTenantId:" + id + "][UserName:" + uName + "]")
				+ System.getProperty("line.separator");

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
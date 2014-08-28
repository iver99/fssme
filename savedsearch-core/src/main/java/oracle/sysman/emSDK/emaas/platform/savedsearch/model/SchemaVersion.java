/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

/**
 * @author miao
 */
public class SchemaVersion implements Version
{
	public static final String VERSION_NAME = "Schema Version";
	private final long majorVersion;
	private final long minorVersion;

	public SchemaVersion(long major, long minor)
	{
		majorVersion = major;
		minorVersion = minor;
	}

	/**
	 * @return the majorVersion
	 */
	public long getMajorVersion()
	{
		return majorVersion;
	}

	/**
	 * @return the minorVersion
	 */
	public long getMinorVersion()
	{
		return minorVersion;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.Version#getName()
	 */
	@Override
	public String getName()
	{
		return VERSION_NAME;
	}

}

/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.services;


/**
 * @author vinjoshi
 *
 */

/**
 * EMTargetMXBeanImpl is the implementation for EMTargetMXBean.
 */
public class EMTargetMXBeanImpl implements EMTargetMXBean
{

	private static final String ORACLE_EMAAS_SAVED_SEARCH = EMTargetConstants.ORACLE_EMAAS_SAVED_SEARCH;
	private String m_name = null;

	public EMTargetMXBeanImpl(String name)
	{
		m_name = name;
	}

	@Override
	public String getEMTargetType() throws Exception
	{
		return ORACLE_EMAAS_SAVED_SEARCH;
	}

	@Override
	public String getName() throws Exception
	{
		return m_name;
	}
}

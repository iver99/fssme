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
 * MXBean interface for EM Target Discovery.
 */
public interface EMTargetMXBean
{
	/**
	 * Gets the EM target type.
	 * 
	 * @exception Exception
	 *                if the operation fails
	 */
	public String getEMTargetType() throws Exception;

	/**
	 * Gets the EM target name.
	 * 
	 * @exception Exception
	 *                if the operation fails
	 */
	public String getName() throws Exception;

}

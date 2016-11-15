/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */
 
package oracle.sysman.emaas.platform.savedsearch.services;

/**
 * @author guochen
 *
 */
public class DependencyStatus
{
	private Boolean databaseUp;
	
	private static final DependencyStatus INSTANCE = new DependencyStatus();
	
	private DependencyStatus() {
		databaseUp = Boolean.FALSE;
	}

	public static DependencyStatus getInstance() {
		return INSTANCE;
	}

	/**
	 * @return the databaseUp
	 */
	public Boolean isDatabaseUp()
	{
		return databaseUp;
	}

	/**
	 * @param databaseUp the databaseUp to set
	 */
	public void setDatabaseUp(Boolean databaseUp)
	{
		this.databaseUp = databaseUp;
	}
}


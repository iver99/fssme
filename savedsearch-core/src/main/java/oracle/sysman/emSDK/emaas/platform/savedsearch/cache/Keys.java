/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

/**
 * @author guochen
 */
public class Keys
{
	private final Object[] keys;

	public Keys(Object... keys)
	{
		this.keys = keys;
	}

	/**
	 * @return the keys
	 */
	public Object[] getKeys()
	{
		return keys;
	}
}

/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows;

import java.util.Map.Entry;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;

/**
 * @author guochen
 */
public class InstanceData<T>
{
	private String key;
	private LookupClient client;
	private T data;
	/**
	 * @param instance
	 * @param data
	 */
	public InstanceData(String key, LookupClient client, T data)
	{
		super();
		this.key = key;
		this.client = client;
		this.data = data;
	}
	
	
	/**
	 * @return the data
	 */
	public T getData()
	{
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */

	public String getKey() {
		return key;
	}


	public LookupClient getClient() {
		return client;
	}


	
}

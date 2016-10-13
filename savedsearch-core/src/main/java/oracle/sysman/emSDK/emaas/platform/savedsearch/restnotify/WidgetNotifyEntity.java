/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify;

import java.math.BigInteger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;

/**
 * @author guochen
 */
public class WidgetNotifyEntity
{
	private BigInteger uniqueId;
	private String name;
	private Integer affactedObjects;

	public WidgetNotifyEntity()
	{

	}

	public WidgetNotifyEntity(Search search)
	{
		if (search == null) {
			return;
		}
		uniqueId = search.getId();
		name = search.getName();
	}

	/**
	 * @return the affactedObjects
	 */
	public Integer getAffactedObjects()
	{
		return affactedObjects;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the uniqueId
	 */
	public BigInteger getUniqueId()
	{
		return uniqueId;
	}

	/**
	 * @param affactedObjects
	 *            the affactedObjects to set
	 */
	public void setAffactedObjects(Integer affactedObjects)
	{
		this.affactedObjects = affactedObjects;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param uniqueId
	 *            the uniqueId to set
	 */
	public void setUniqueId(BigInteger uniqueId)
	{
		this.uniqueId = uniqueId;
	}
}

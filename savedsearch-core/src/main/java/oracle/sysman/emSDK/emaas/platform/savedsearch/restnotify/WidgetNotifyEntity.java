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

import java.util.Date;
import java.math.BigInteger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;

/**
 * @author guochen
 */
public class WidgetNotifyEntity
{
	private BigInteger uniqueId;
	private String name;
	private WidgetNotificationType type;
	private Date notifyTime;
	/**
	 * Used as a 'returned value' to indicate who many objects are impacted by this widget change notification
	 */
	private Integer affactedObjects;

	public WidgetNotifyEntity()
	{

	}

	public WidgetNotifyEntity(Search search, Date notifyTime, WidgetNotificationType type)
	{
		if (search == null) {
			return;
		}
		uniqueId = search.getId();
		name = search.getName();
		this.notifyTime = notifyTime;
		this.type = type;
	}

	public WidgetNotifyEntity(EmAnalyticsSearch eas, Date notifyTime, WidgetNotificationType type)
	{
		if (eas == null) {
			return;
		}
		uniqueId = eas.getId();
		name = eas.getName();
		this.notifyTime = notifyTime;
		this.type = type;
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
	
	public Date getNotifyTime()
	{
		return notifyTime;
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
	
	public void setNotifyTime(Date notifyTime)
	{
		this.notifyTime = notifyTime;
	}

	/**
	 * @param uniqueId
	 *            the uniqueId to set
	 */
	public void setUniqueId(BigInteger uniqueId)
	{
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the type
	 */
	public WidgetNotificationType getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(WidgetNotificationType type)
	{
		this.type = type;
	}
}

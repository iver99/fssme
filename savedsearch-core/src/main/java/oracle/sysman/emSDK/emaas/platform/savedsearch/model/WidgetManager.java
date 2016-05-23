/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * @author guochen
 */
public abstract class WidgetManager
{
	/**
	 * Returns an instance of the manager.
	 *
	 * @return instance of the manager
	 */
	public static WidgetManager getInstance()
	{
		return WidgetManagerImpl.getInstance();
	}

	public abstract String getWidgetJsonStringFromWidgetList(List<Widget> widgetList) throws EMAnalyticsFwkException;
}

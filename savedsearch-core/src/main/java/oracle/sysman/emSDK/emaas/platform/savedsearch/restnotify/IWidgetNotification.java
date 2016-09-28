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

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;

/**
 * @author guochen
 */
public interface IWidgetNotification
{
	void notify(Search search, Date notifyTime);

	void notify(WidgetNotifyEntity wne);
}

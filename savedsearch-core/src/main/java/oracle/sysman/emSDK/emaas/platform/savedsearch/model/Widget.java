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

/**
 * @author guochen
 */
public interface Widget extends Search
{
	/**
	 * Returns the category for the widget
	 *
	 * @return category for the widget
	 */
	Category getCategory();
}

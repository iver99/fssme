/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;

/**
 * @author guochen
 */
public class WidgetImpl extends SearchImpl implements Widget
{
	private Category category;

	/* (non-Javadoc)
	 * @see oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl#getCategory()
	 */
	@Override
	public Category getCategory()
	{
		return category;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl#setCategory(oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category)
	 */
	public void setCategory(Category category)
	{
		this.category = category;
	}
}

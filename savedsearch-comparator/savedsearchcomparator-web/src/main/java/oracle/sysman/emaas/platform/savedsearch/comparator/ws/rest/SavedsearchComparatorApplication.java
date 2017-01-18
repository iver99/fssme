/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author pingwu
 */
@ApplicationPath("api")
public class SavedsearchComparatorApplication extends Application
{
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(ZDTAPI.class);
		return hs;
	}
}

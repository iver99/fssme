/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author vinjoshi
 */
public class RemoveHeader extends HttpServletRequestWrapper
{

	private static final String CONTENT_TYPE = "Content-Type";

	public RemoveHeader(HttpServletRequest request)
	{
		super(request);
	}

	@Override
	public String getHeader(String name)
	{
		String header = super.getHeader(name);
		return header != null ? header : super.getParameter(name); // Note: you can't use getParameterValues() here.
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getHeaderNames()
	{
		List<String> names = Collections.list(super.getHeaderNames());
		names.remove(CONTENT_TYPE);
		names.addAll(Collections.list(super.getParameterNames()));
		return Collections.enumeration(names);
	}
}
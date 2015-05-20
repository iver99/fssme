/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.updatesearch.test.updatesearchexception;

/**
 * @author vinjoshi
 *
 */

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.exception.UpdateSearchException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateSearchExceptionTest
{
	static final Throwable th = new Exception("this is a throwable object");
	static final UpdateSearchException updateex = new UpdateSearchException();
	static final UpdateSearchException updateex1 = new UpdateSearchException("test message");
	static final UpdateSearchException updateex2 = new UpdateSearchException("test message", th);
	static final UpdateSearchException updateex3 = new UpdateSearchException(th);

	@Test
	public void UpdateSearchException()
	{
		Assert.assertNull(updateex.getMessage());
	}

	@Test
	public void UpdateSearchExceptionString()
	{
		Assert.assertEquals(updateex1.getMessage(), "test message");
	}

	@Test
	public void UpdateSearchExceptionStringThrowable()
	{
		Assert.assertEquals(updateex2.getCause(), th);
	}

	@Test
	public void UpdateSearchExceptionThrowable()
	{
		Assert.assertEquals(updateex3.getCause(), th);
	}
}

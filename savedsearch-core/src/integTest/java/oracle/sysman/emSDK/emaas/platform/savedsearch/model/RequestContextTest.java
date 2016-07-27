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

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.RequestContext.RequestType;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author aduan
 */
@Test(groups = { "s1" })
public class RequestContextTest
{
	@Test
	public void testRequestContext()
	{
		RequestContext.setContext(RequestType.EXTERNAL);
		Assert.assertEquals(RequestContext.getContext(), RequestType.EXTERNAL);

		RequestContext.setContext(RequestType.INTERNAL_TENANT_USER);
		Assert.assertEquals(RequestContext.getContext(), RequestType.INTERNAL_TENANT_USER);

		RequestContext.setContext(RequestType.INTERNAL_TENANT);
		Assert.assertEquals(RequestContext.getContext(), RequestType.INTERNAL_TENANT);

		RequestContext.setContext(RequestType.ERRONEOUS);
		Assert.assertEquals(RequestContext.getContext(), RequestType.ERRONEOUS);

		RequestContext.clearContext();
		Assert.assertNull(RequestContext.getContext());
	}
}

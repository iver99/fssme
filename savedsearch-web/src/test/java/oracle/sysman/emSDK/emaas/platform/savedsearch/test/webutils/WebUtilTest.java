/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.test.webutils;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.AppMappingCollection;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.AppMappingEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.DomainEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.DomainEntity.DomainKeyEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.DomainsEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class WebUtilTest
{

	@Test
	public void testJsonUtil()
	{
		AppMappingCollection obj = new AppMappingCollection();
		obj.setCount(1);
		obj.setItems(null);
		obj.setTotal(1);
		Assert.assertNotNull(obj.getCount());
		Assert.assertNull(obj.getItems());
		Assert.assertNotNull(obj.getTotal());

		AppMappingEntity obj1 = new AppMappingEntity();
		obj1.setCanonicalUrl("test");
		obj1.setDomainName("test");
		obj1.setDomainUuid("test");
		obj1.setHash(1L);
		obj1.setKeys(null);
		obj1.setUuid("test");
		obj1.setValues(null);
		Assert.assertNull(obj1.getKeys());
		Assert.assertNull(obj1.getValues());
		Assert.assertNotNull(obj1.getCanonicalUrl());
		Assert.assertNotNull(obj1.getDomainName());
		Assert.assertNotNull(obj1.getDomainUuid());
		Assert.assertNotNull(obj1.getHash());
		Assert.assertNotNull(obj1.getUuid());
		DomainEntity obj2 = new DomainEntity();
		obj2.setCanonicalUrl("");
		obj2.setDomainName("");
		obj2.setUuid("");
		DomainKeyEntity key = new DomainKeyEntity();
		key.setName("");
		Assert.assertNotNull(key.getName());
		obj2.setKeys(null);
		Assert.assertNotNull(obj2.getCanonicalUrl());
		Assert.assertNotNull(obj2.getDomainName());
		Assert.assertNotNull(obj2.getUuid());
		Assert.assertNull(obj2.getKeys());
		DomainsEntity obj3 = new DomainsEntity();
		obj3.setCount(1);
		obj3.setItems(null);
		obj3.setTotal(1);
		Assert.assertNotNull(obj3.getCount());
		Assert.assertNotNull(obj3.getTotal());
		Assert.assertNull(obj3.getItems());

	}

	/*@Test
	public void testProerptyReader() throws IOException
	{

		Assert.assertNotNull(PropertyReader.loadProperty(PropertyReader.SERVICE_PROPS));
		Assert.assertNull(PropertyReader.loadProperty(".SERVICE_PROPS"));
	}

	@Test
	public void testVersion() throws Exception
	{
		VersionValidationServiceManager version = new VersionValidationServiceManager();
		Assert.assertNotNull(version.getName());
		version.postStart(null);
		version.postStop(null);
		version.preStart(null);
		version.preStop(null);
		RegistryLookupUtil.getServiceInternalLink("RegistryService", "0.1", "collection/instances", null);
		new AppLoggingManageMXBean().getLogLevels();

	}*/

}

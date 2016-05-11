/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.test.util;

import java.util.Set;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.SearchParameterDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.QueryParameterConstant;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingCollection;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainsEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainEntity.DomainKeyEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.SavedSearchApplication;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class SavedSearchCommonTest
{

	@Test (groups = {"s1"})
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

	@Test (groups = {"s1"})
	public void testSavedSearchApplication()
	{
		SavedSearchApplication test = new SavedSearchApplication();
		Set<Class<?>> s = test.getClasses();
		Assert.assertNotNull(s);
	}

	@Test (groups = {"s1"})
	public void testSearchParameterDetails() throws EMAnalyticsFwkException
	{
		SearchParameterDetails t1 = new SearchParameterDetails();
		t1.setName("test");
		t1.setAttributes("test");
		t1.setType(ParameterType.CLOB);
		t1.setValue("test");
		Assert.assertNotNull(t1.getName());
		Assert.assertNotNull(t1.getType());
		Assert.assertNotNull(t1.getValue());
		Assert.assertNotNull(t1.getAttributes());

		SearchParameterDetails t2 = new SearchParameterDetails();
		t2.setName("test1");
		t2.setAttributes("test");
		t2.setType(ParameterType.CLOB);
		t2.setValue("test");
		Assert.assertFalse(t1.equals(t2));
		Assert.assertFalse(t1.equals("test1"));
		Parameter p1 = new Parameter();
		p1.setName("test1");
		Assert.assertFalse(t1.equals(p1));
		p1.setName("test");
		Assert.assertTrue(t1.equals(p1));
		Assert.assertTrue(t1.equals("test"));
	}

	@Test (groups = {"s1"})
	public void testStringEmptyTest()
	{
		boolean result = StringUtil.isEmpty(null);
		Assert.assertEquals(result, true);
		result = StringUtil.isEmpty("");
		Assert.assertEquals(result, true);
		Assert.assertEquals(QueryParameterConstant.USER_NAME, "userName");
	}

	@Test (groups = {"s1"})
	public void testTenantInfo()
	{
		long i = 1;
		TenantInfo a = new TenantInfo("admin", i, "admin1");
		Assert.assertTrue(a.getUsername().equals("admin"));
		Assert.assertTrue(a.getTenantInternalId() == i);
		a.setTenantInternalId(i);
		Assert.assertTrue(a.getTenantInternalId() == i);
		Assert.assertTrue(a.gettenantName().equals("admin1"));

	}

}

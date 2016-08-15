/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.zdt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import mockit.Mocked;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author chehao
 */
@Test(groups = { "s2" })
public class DataManagerTest
{
	private final long count = 666L;
	@Mocked
	private List list;

	/*@Mocked
	private DataManager dataManager;*/

	@Test
	public void testCheckFormat()
	{
		try {
			Method method = DataManager.class.getDeclaredMethod("checkFormat", new Class[] { String.class });
			method.setAccessible(true);
			String result = method.invoke(new DataManager(), "2016-07-12 08:55:59.647387").toString();
			Assert.assertEquals(result, "2016-07-12 08:55:59.647");
			result = method.invoke(new DataManager(), "2016-07-12 08:55:59.64738").toString();
			Assert.assertEquals(result, "2016-07-12 08:55:59.647");
			result = method.invoke(new DataManager(), "2016-07-12 08:55:59.6473").toString();
			Assert.assertEquals(result, "2016-07-12 08:55:59.647");
			result = method.invoke(new DataManager(), "2016-07-12 08:55:59.647").toString();
			Assert.assertEquals(result, "2016-07-12 08:55:59.647");
			result = method.invoke(new DataManager(), "2016-07-12 08:55:59.64").toString();
			Assert.assertEquals(result, "2016-07-12 08:55:59.640");
			result = method.invoke(new DataManager(), "2016-07-12 08:55:59.6").toString();
			Assert.assertEquals(result, "2016-07-12 08:55:59.600");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void testCompareLastModificationDate()
	{
		Method method;
		try {
			method = DataManager.class.getDeclaredMethod("compareLastModificationDate",
					new Class[] { String.class, String.class });
			method.setAccessible(true);
			int result = Integer.valueOf(method.invoke(new DataManager(), "2016-07-12 08:55:59.647", "2016-07-12 08:55:59.647")
					.toString());
			Assert.assertTrue(result == 0);
			result = Integer.valueOf(method.invoke(new DataManager(), "2016-07-12 08:55:59.648", "2016-07-12 08:55:59.647")
					.toString());
			Assert.assertTrue(result > 0);
			result = Integer.valueOf(method.invoke(new DataManager(), "2016-07-12 08:55:59.646", "2016-07-12 08:55:59.647")
					.toString());
			Assert.assertTrue(result < 0);

		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	//	@Test
	/*public void testGetAllCategoryCount(@Mocked final TenantInfo tenant, @Mocked final DataManager dataManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final Number number)
	{
		new Expectations() {
			{
				Deencapsulation.invoke(dataManager, "getEntityManager");
				result = entityManager;
				entityManager.createNativeQuery(anyString);
				result = query;
				query.getSingleResult();
				result = 666;
				number.toString();
				result = 666L;
			}
		};

		long dbCount = dataManager.getAllCategoryCount();
		Assert.assertEquals(dbCount, count);
	}*/

	//	@Test
	/*public void testGetDatabaseTableData(@Mocked final EntityManager entityManager, @Mocked final DataManager dataManager,
			@Mocked final Query query)
	{
		//		List list = new ArrayList();

		new Expectations() {
			{
				Deencapsulation.invoke(dataManager, "getEntityManager");
				result = entityManager;
				entityManager.createNativeQuery(anyString);
				query.getResultList();
				result = list;
			}
		};

		//		DataManager.getInstance().getDatabaseTableData("sql");

	}*/
}

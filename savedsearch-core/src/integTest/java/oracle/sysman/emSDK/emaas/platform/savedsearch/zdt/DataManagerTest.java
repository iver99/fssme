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
import java.util.Map;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Verifications;

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
	private List<Map<String, Object>> list;

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

	@Test
	public void testGetAllCategoryCount()
	{
		new MockUp<DataManager>() {
			@Mock
			public long getAllCategoryCount()
			{
				return count;
			}
		};
		new Verifications() {
			{
				DataManager.getInstance().getAllCategoryCount();
			}
		};
		long dbCount = DataManager.getInstance().getAllCategoryCount();
		Assert.assertEquals(dbCount, count);
	}

	@Test
	public void testGetAllFolderCount()
	{
		new MockUp<DataManager>() {
			@Mock
			public long getAllFolderCount()
			{
				return count;
			}
		};
		new Verifications() {
			{
				DataManager.getInstance().getAllFolderCount();
			}
		};
		long dbCount = DataManager.getInstance().getAllFolderCount();
		Assert.assertEquals(dbCount, count);
	}

	@Test
	public void testGetAllSearchCount()
	{
		new MockUp<DataManager>() {
			@Mock
			public long getAllSearchCount()
			{
				return count;
			}
		};
		new Verifications() {
			{
				DataManager.getInstance().getAllSearchCount();
			}
		};
		long dbCount = DataManager.getInstance().getAllSearchCount();
		Assert.assertEquals(dbCount, count);
	}

	@Test
	public void testGetDatabaseTableData()
	{
		new MockUp<DataManager>() {
			@Mock
			private List<Map<String, Object>> getDatabaseTableData(String nativeSql)
			{
				return list;
			}
		};
		Object o = null;
		try {
			Method method = DataManager.class.getDeclaredMethod("getDatabaseTableData", new Class[] { String.class });
			method.setAccessible(true);
			o = method.invoke(new DataManager(), "testSql");
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(o, list);
	}
}

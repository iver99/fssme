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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Verifications;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author chehao
 */
@Test(groups = { "s2" })
public class DataManagerTest
{
	private DataManager dataManager = DataManager.getInstance();
	
	@Test
	public void testSaveToComparatorTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
	/*	final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};*/
		dataManager.saveToComparatorTable(entityManager, "2017-05-12 11:21:23", "2017-05-12 11:21:23", "full", "", 0.11);
	}
	
	@Test
	public void testSaveToSyncTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		/*final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};*/
		dataManager.saveToSyncTable("2017-05-12 11:21:23", "2017-05-12 11:21:23", "full", "", 0.11,"2017-05-12 11:21:23");
	}
	
	
	@Test
	public void testGetComparedDataToSync(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		final List<Map<String, Object>> list = new ArrayList<>();
		String date = "2017-05-12 11:21:23";
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getComparedDataToSync(entityManager, date);
		dataManager.getComparedDataToSync(entityManager, null);
		
	}
	
	@Test
	public void testGetSyncStatus(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getComparatorStatus(entityManager);
		
	}
	
	@Test
	public void testGetCompareStatus(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getSyncStatus(entityManager);
		
	}
	
	@Test
	public void testGetLastComparisonDateForSync(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		final List<Object> list = new ArrayList<>();
		list.add("2017-05-12 11:21:23");
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getLastComparisonDateForSync(entityManager);
		
	}
	
	@Test
	public void testGetLatestComparisonDateForCompare(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		final List<Object> list = new ArrayList<>();
		list.add("2017-05-12 11:21:23");
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getLatestComparisonDateForCompare(entityManager);
		
	}

	
	
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
	public void testGetAllCategoryCount(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Object> data = new ArrayList<Object>();
		data.add("123");
		new Expectations(){
	            {
	            	query.getResultList();
	                result = data;
	            }
	        };
	        dataManager.getAllCategoryCount(entityManager, "2017-05-23 16:19:02");
	}

	@Test
	public void testGetAllFolderCount(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Object> data = new ArrayList<Object>();
		data.add("123");
		new Expectations(){
            {
            	query.getResultList();
                result = data;
            }
        };
        dataManager.getAllFolderCount(entityManager, "2017-05-23 16:19:02");
		
	}

	@Test
	public void testGetAllSearchCount(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Object> data = new ArrayList<Object>();
		data.add("123");
		new Expectations(){
            {
            	query.getResultList();
                result = data;
            }
        };
        dataManager.getAllSearchCount(entityManager, "2017-05-23 16:19:02");
		
	}
	
	@Test
	public void testGetAllSearchParamCount(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Object> data = new ArrayList<Object>();
		data.add("123");
		new Expectations(){
            {
            	query.getResultList();
                result = data;
            }
        };
        dataManager.getAllSearchParamsCount(entityManager, "2017-05-23 16:19:02");
		
	}
	
	@Test
	public void testGetAllCategoryParamCount(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Object> data = new ArrayList<Object>();
		data.add("123");
		new Expectations(){
            {
            	query.getResultList();
                result = data;
            }
        };
        dataManager.getAllCategoryPramsCount(entityManager, "2017-05-23 16:19:02");
		
	}
	
	@Test
	public void testGetFolderTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getFolderTableData(entityManager,"full", "2017-05-09 14:35:21", "2017-05-09 14:35:21");
		dataManager.getFolderTableData(entityManager,"incremental", "2017-05-09 14:35:21", "2017-05-09 14:35:21");
	}

	@Test
	public void testGetSearchParamTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getSearchParamTableData(entityManager,"full", "2017-05-09 14:35:21", "2017-05-09 14:35:21");		
		dataManager.getSearchParamTableData(entityManager,"incremental", "2017-05-09 14:35:21", "2017-05-09 14:35:21");		
	}
	
	@Test
	public void testGetSearchTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getSearchTableData(entityManager,"full", "2017-05-09 14:35:21", "2017-05-09 14:35:21");
		dataManager.getSearchTableData(entityManager,"incremental", "2017-05-09 14:35:21", "2017-05-09 14:35:21");
	}
	
	@Test
	public void testGetCategoryTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query)
	{
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				query.getResultList();
				result = list;
			}
		};
		dataManager.getCategoryTableData(entityManager,"full", "2017-05-09 14:35:21", "2017-05-09 14:35:21");
	}
	
	@Test
	public void testGetCategoryParamTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query q1)
	{
		final List<Map<String, Object>> list = new ArrayList<>();
		new Expectations(){
			{
				q1.getResultList();
				result = list;
			}
		};
		dataManager.getCategoryParamTableData(entityManager,"full", "2017-05-09 14:35:21","2017-05-09 14:35:21");
		dataManager.getCategoryParamTableData(entityManager,"incremental", "2017-05-09 14:35:21" ,"2017-05-09 14:35:21");
	}
	
	@Test
	public void testSyncCategoryParamTableInsert(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager,@Mocked final Query query) {
		BigInteger categoryId =  new BigInteger("1");
		String name = "Name";
		String paramValue = "paramValue"; 
		Long tenantId = 1L;
		String creationDate = "creationDate";
		String lastModificationDate = "lastModificationDate";
		Integer deleted = new Integer("0");
		new Expectations(){
			{
				entityManager.createNativeQuery(anyString);
                result = query;               
                query.getResultList();
                result =  new ArrayList<Map<String, Object>>();
				
			}
		};
		dataManager.syncCategoryParamTable(entityManager,categoryId, name, paramValue, tenantId, creationDate, lastModificationDate,deleted);
	}
	
	
	@Test
	public void testSyncCategoryParamTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		BigInteger categoryId =  new BigInteger("1");
		String name = "Name";
		String paramValue = "paramValue"; 
		Long tenantId = 1L;
		String creationDate = "creationDate";
		String lastModificationDate = "lastModificationDate";
		Integer deleted = new Integer("0");
		final Map<String, Object> objs = new HashMap<String,Object>();		
		Date date = new Date();
		objs.put("LAST_MODIFICATION_DATE", null);
		objs.put("CREATION_DATE", date);
		new Expectations(){
			{
				query.getResultList();
				result = objs;
			}
		};
		dataManager.syncCategoryParamTable(entityManager,categoryId, name, paramValue, tenantId, creationDate, lastModificationDate,deleted);
		dataManager.syncCategoryParamTable(entityManager,null, name, paramValue, tenantId, creationDate, lastModificationDate,deleted);
		dataManager.syncCategoryParamTable(entityManager,categoryId, null, paramValue, tenantId, creationDate, lastModificationDate,deleted);
		dataManager.syncCategoryParamTable(entityManager,categoryId, name, null, tenantId, creationDate, lastModificationDate,deleted);
		dataManager.syncCategoryParamTable(entityManager,categoryId, name, paramValue, null, creationDate, lastModificationDate,deleted);
		dataManager.syncCategoryParamTable(entityManager,categoryId, name, paramValue, tenantId, null, lastModificationDate,deleted);
		dataManager.syncCategoryParamTable(entityManager,categoryId, name, paramValue, tenantId, creationDate, null,deleted);
	}
	
	@Test
	public void testSyncFolderTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		BigInteger folderId = new BigInteger("1");
		String name= "name";
		BigInteger parentId= new BigInteger("2");
		String description= "description";
		String creationDate="creationDate";
		String owner="owner";
		String lastModificationDate="lastModificationDate";
		String lastModifiedBy="lastModifiedBy";
		String nameNlsid="nameNlsid";
		String nameSubsystem="nameSubsystem";
		String descriptionNlsid="descriptionNlsid";
		String descriptionSubsystem="descriptionSubsystem";
		Integer systemFolder=new Integer("1"); 
		String emPluginId="emPluginId";
		Integer uiHidden= new Integer("2"); 
		BigInteger deleted = new BigInteger("1"); 
		Long tenantId = 3L;
		final Map<String, Object> objs = new HashMap<String,Object>();		
		Date date = new Date();
		objs.put("LAST_MODIFICATION_DATE", null);
		objs.put("CREATION_DATE", date);
		new Expectations(){
			{
				query.getResultList();
				result = objs;
			}
		};
		
		dataManager.syncFolderTable(entityManager,folderId, name, parentId, description, creationDate,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
		
		dataManager.syncFolderTable(entityManager,null, name, parentId, description, creationDate,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
		
		dataManager.syncFolderTable(entityManager,folderId, null, parentId, description, creationDate,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
		
		dataManager.syncFolderTable(entityManager,folderId, name, null, description, creationDate,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
		
		dataManager.syncFolderTable(entityManager,folderId, name, parentId, description, null,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
		
		dataManager.syncFolderTable(entityManager,folderId, name, parentId, description, creationDate,
				owner, null, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
		
		dataManager.syncFolderTable(entityManager,folderId, name, parentId, description, creationDate,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, null);
		
	}
	
	@Test
	public void testSyncFolderTableInsert(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		BigInteger folderId = new BigInteger("1");
		String name= "name";
		BigInteger parentId= new BigInteger("2");
		String description= "description";
		String creationDate="creationDate";
		String owner="owner";
		String lastModificationDate="lastModificationDate";
		String lastModifiedBy="lastModifiedBy";
		String nameNlsid="nameNlsid";
		String nameSubsystem="nameSubsystem";
		String descriptionNlsid="descriptionNlsid";
		String descriptionSubsystem="descriptionSubsystem";
		Integer systemFolder=new Integer("1"); 
		String emPluginId="emPluginId";
		Integer uiHidden= new Integer("2"); 
		BigInteger deleted = new BigInteger("1"); 
		Long tenantId = 3L;
		
		new Expectations(){
			{
				query.getResultList();

				result =  new ArrayList<Map<String, Object>>();

				
			}
		};
		
		dataManager.syncFolderTable(entityManager,folderId, name, parentId, description, creationDate,
				owner, lastModificationDate, lastModifiedBy, nameNlsid, nameSubsystem,
				descriptionNlsid, descriptionSubsystem, systemFolder, emPluginId, uiHidden, deleted, tenantId);
	}
	
	@Test
	public void testSyncSearchParamsTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query){
		BigInteger searchId=new BigInteger("1"); 
		String name="name";
		String paramAttributes="paramAttributes";
		Long paramType=1L;
		String paramValueStr="paramValueStr";
		String paramValueClob="paramValueClob";
		Long tenantId=1L; 
		String creationDate="creationDate"; 
		String lastModificationDate="lastModificationDate";
		Integer deleted = new Integer("0");
		final Map<String, Object> objs = new HashMap<String,Object>();		
		Date date = new Date();
		objs.put("LAST_MODIFICATION_DATE", null);
		objs.put("CREATION_DATE", date);
		new Expectations(){
			{
				query.getResultList();
				result = objs;
			}
		};
		dataManager.syncSearchParamsTable(entityManager,searchId, name, paramAttributes, paramType, paramValueStr, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
		
		dataManager.syncSearchParamsTable(entityManager,null, name, paramAttributes, paramType, paramValueStr, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
		
		dataManager.syncSearchParamsTable(entityManager,searchId, null, paramAttributes, paramType, paramValueStr, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
		
		dataManager.syncSearchParamsTable(entityManager,searchId, name, null, paramType, paramValueStr, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
		
		dataManager.syncSearchParamsTable(entityManager,searchId, name, paramAttributes, null, paramValueStr, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
		
		dataManager.syncSearchParamsTable(entityManager,searchId, name, paramAttributes, paramType, null, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
		
		dataManager.syncSearchParamsTable(entityManager,searchId, name, paramAttributes, paramType, paramValueStr, 
				paramValueClob, tenantId, creationDate, null,deleted);
		
	}
	
	@Test
	public void testSyncSearchParamsTableInsert(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query){
		BigInteger searchId=new BigInteger("1"); 
		String name="name";
		String paramAttributes="paramAttributes";
		Long paramType=1L;
		String paramValueStr="paramValueStr";
		String paramValueClob="paramValueClob";
		Long tenantId=1L; 
		String creationDate="creationDate"; 
		String lastModificationDate="lastModificationDate";
		Integer deleted = new Integer("0");
		new Expectations(){
			{
				query.getResultList();

				result =  new ArrayList<Map<String, Object>>();

			}
		};
		dataManager.syncSearchParamsTable(entityManager,searchId, name, paramAttributes, paramType, paramValueStr, 
				paramValueClob, tenantId, creationDate, lastModificationDate,deleted);
	
	}
	
	@Test
	public void testSyncSearchTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query){
		BigInteger searchId=new BigInteger("1");
		String name="name"; 
		String owner ="owner"; 
		String creationDate="creationDate"; 
		String lastModificationDate="lastModificationDate"; 
		String lastModifiedBy="lastModifiedBy";  
		String description="description";  
		BigInteger folderId=new BigInteger("1");
		BigInteger categoryId=new BigInteger("1");
		String nameNlsid="nameNlsid"; 
		String nameSubsystem="nameSubsystem"; 
		String descriptionNlsid="descriptionNlsid"; 
		String descriptionSubsystem="descriptionSubsystem"; 
		Integer systemSearch=new Integer("1");
		String emPluginId="emPluginId"; 
		Integer isLocked=new Integer("1"); 
		String metaDataClob="metaDataClob"; 
		String searchDisplayStr="name";  
		Integer uiHidden=new Integer("1"); 
		BigInteger deleted=new BigInteger("1");
		Integer isWidget=new Integer("1"); 
		Long tenantId=1L; 
		String nameWidgetSource="nameWidgetSource"; 
		String widgetGroupName="widgetGroupName"; 
		String widgetScreenshotHref="widgetScreenshotHref"; 
		String widgetIcon="widgetIcon"; 
		String widgetKocName="widgetKocName";  
		String viewModel="viewModel"; 
		String widgetTemplate="widgetTemplate"; 
		String widgetSupportTimeControl="widgetSupportTimeControl"; 
		Long widgetLinkedDashboard=1L; 
		Long widgetDefaultWidth=1L; 
		Long widgetDefaultHeight=1L;
		String dashboardIneligible="dashboardIneligible"; 
		String providerName="providerName"; 
		String providerVersion="providerVersion"; 
		String providerAssetRoot="providerAssetRoot"; 
		final Map<String, Object> objs = new HashMap<String,Object>();		
		Date date = new Date();
		objs.put("LAST_MODIFICATION_DATE", null);
		objs.put("CREATION_DATE", date);
		new Expectations(){
			{
				query.getResultList();
				result = objs;

			}
		};
		
		dataManager.syncSearchTable(entityManager,searchId, name, owner, creationDate, lastModificationDate, 
				lastModifiedBy, description, folderId, categoryId,systemSearch, isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
		dataManager.syncSearchTable(entityManager,null, name, owner, creationDate, lastModificationDate, 
				lastModifiedBy, description, folderId, categoryId, systemSearch,isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
		dataManager.syncSearchTable(entityManager,searchId, null, owner, creationDate, lastModificationDate, 
				lastModifiedBy, description, folderId, categoryId, systemSearch,isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
		dataManager.syncSearchTable(entityManager,searchId, name, owner, null, lastModificationDate, 
				lastModifiedBy, description, folderId, categoryId, systemSearch, isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
		dataManager.syncSearchTable(entityManager,searchId, name, owner, creationDate, null, 
				lastModifiedBy, description, folderId, categoryId, systemSearch, isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
		dataManager.syncSearchTable(entityManager,searchId, name, owner, creationDate, lastModificationDate, 
				lastModifiedBy, description, folderId, null,  systemSearch,isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
		dataManager.syncSearchTable(entityManager,searchId, name, owner, creationDate, lastModificationDate, 
				lastModifiedBy, description, folderId, categoryId, systemSearch, isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
	}
	
	@Test
	public void testSyncSearchTableInsert(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query q1){
		BigInteger searchId=new BigInteger("1");
		String name="name"; 
		String owner ="owner"; 
		String creationDate="creationDate"; 
		String lastModificationDate="lastModificationDate"; 
		String lastModifiedBy="lastModifiedBy";  
		String description="description";  
		BigInteger folderId=new BigInteger("1");
		BigInteger categoryId=new BigInteger("1");
		String nameNlsid="nameNlsid"; 
		String nameSubsystem="nameSubsystem"; 
		String descriptionNlsid="descriptionNlsid"; 
		String descriptionSubsystem="descriptionSubsystem"; 
		Integer systemSearch=new Integer("1");
		String emPluginId="emPluginId"; 
		Integer isLocked=new Integer("1"); 
		String metaDataClob="metaDataClob"; 
		String searchDisplayStr="name";  
		Integer uiHidden=new Integer("1"); 
		BigInteger deleted=new BigInteger("1");
		Integer isWidget=new Integer("1"); 
		Long tenantId=1L; 
		String nameWidgetSource="nameWidgetSource"; 
		String widgetGroupName="widgetGroupName"; 
		String widgetScreenshotHref="widgetScreenshotHref"; 
		String widgetIcon="widgetIcon"; 
		String widgetKocName="widgetKocName";  
		String viewModel="viewModel"; 
		String widgetTemplate="widgetTemplate"; 
		String widgetSupportTimeControl="widgetSupportTimeControl"; 
		Long widgetLinkedDashboard=1L; 
		Long widgetDefaultWidth=1L; 
		Long widgetDefaultHeight=1L;
		String dashboardIneligible="dashboardIneligible"; 
		String providerName="providerName"; 
		String providerVersion="providerVersion"; 
		String providerAssetRoot="providerAssetRoot"; 
		
		new Expectations(){
			{
				q1.getResultList();

				result = new ArrayList<Map<String, Object>>();	

			}
		};
		
		dataManager.syncSearchTable(entityManager,searchId, name, owner, creationDate, lastModificationDate, 
				lastModifiedBy, description, folderId, categoryId,systemSearch, isLocked, 
				metaDataClob, searchDisplayStr, uiHidden, deleted, isWidget, tenantId, nameWidgetSource, 
				widgetGroupName, widgetScreenshotHref, widgetIcon, widgetKocName, viewModel, widgetTemplate, 
				widgetSupportTimeControl, widgetLinkedDashboard, widgetDefaultWidth, widgetDefaultHeight, 
				dashboardIneligible, providerName, providerVersion, providerAssetRoot);
		
	}

 	@Test
	public void testSyncCategoryTable(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query q1) {
		BigInteger categoryId = new BigInteger("1");
		String name = "name"; 
		String description = "description";
		String owner = "owner";
		String creationDate = "creationDate";
		String nameNlsid = "nameNlsid";
		String nameSubSystem = "nameSubSystem";
		String descriptionNlsid = "descriptionNlsid";
		String descriptionSubSystem = "descriptionSubSystem";
		String emPluginId = "emPluginId";
		BigInteger defaultFolderId =new BigInteger("1");
		BigInteger deleted = new BigInteger("0");
		String providerName = "providerName";
		String providerVersion = "providerVersion";
		String providerDiscovery = "providerDiscovery";
		String providerAssetroot = "providerAssetroot";
		Long tenantId = 1L; 
		String dashboardIneligible = "dashboardIneligible";
		String lastModificationDate = "lastModificationDate";

		final Map<String, Object> objs = new HashMap<String,Object>();		
		Date date = new Date();
		objs.put("LAST_MODIFICATION_DATE", null);
		objs.put("CREATION_DATE", date);
		new Expectations(){
			{
				q1.getResultList();
				result = objs;

			}
		};
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,null, name, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, null, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, null, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, null, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, owner, 
				null, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, null, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, null, dashboardIneligible, lastModificationDate);
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, null);
		
	} 
 	
 	@Test
	public void testSyncCategoryTableInsert(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager entityManager, @Mocked final Query query) {
		BigInteger categoryId = new BigInteger("1");
		String name = "name"; 
		String description = "description";
		String owner = "owner";
		String creationDate = "creationDate";
		String nameNlsid = "nameNlsid";
		String nameSubSystem = "nameSubSystem";
		String descriptionNlsid = "descriptionNlsid";
		String descriptionSubSystem = "descriptionSubSystem";
		String emPluginId = "emPluginId";
		BigInteger defaultFolderId =new BigInteger("1");
		BigInteger deleted = new BigInteger("0");
		String providerName = "providerName";
		String providerVersion = "providerVersion";
		String providerDiscovery = "providerDiscovery";
		String providerAssetroot = "providerAssetroot";
		Long tenantId = 1L; 
		String dashboardIneligible = "dashboardIneligible";
		String lastModificationDate = "lastModificationDate";
		
		new Expectations(){
			{
				query.getResultList();

				result =  new ArrayList<Map<String, Object>>();

			}
		};
		
		dataManager.syncCategoryTable(entityManager,categoryId, name, description, owner, 
				creationDate, nameNlsid, nameSubSystem, descriptionNlsid, descriptionSubSystem, 
				emPluginId, defaultFolderId, deleted, providerName, providerVersion, 
				providerDiscovery, providerAssetroot, tenantId, dashboardIneligible, lastModificationDate);
	
	} 
}

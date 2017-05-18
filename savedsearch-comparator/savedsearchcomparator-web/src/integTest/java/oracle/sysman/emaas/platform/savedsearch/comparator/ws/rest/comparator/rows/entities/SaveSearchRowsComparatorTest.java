package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTException;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.SavedsearchRowsComparator;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.LeaseInfo.Builder;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;

import org.testng.Assert;
import org.testng.annotations.Test;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

@Test(groups = { "s1" })
public class SaveSearchRowsComparatorTest
{
	@Mocked
	AbstractComparator abstractComparator;
	

	private static final String JSON_RESPONSE_DATA_TABLE="{"
			+ "\"EMS_ANALYTICS_CATEGORY\": [{"
			+ 		"\"CATEGORY_ID\":5,"
			+ 		"\"NAME\":\"Target Card\","
			+ 		"\"DESCRIPTION\":\"Search Category for Target Card\","
			+ 		"\"OWNER\":\"ORACLE\","
			+ 		"\"CREATION_DATE\":\"2016-07-22 05:41:07.403198\","
			+ 		"\"NAME_NLSID\":\"TARGETCARD_CATEGORY_NAME\","
			+ 		"\"NAME_SUBSYSTEM\":\"EMANALYTICS\","
			+ 		"\"DESCRIPTION_NLSID\":\"TARGETCARD_CATEGORY_DESC\","
			+ 		"\"DESCRIPTION_SUBSYSTEM\":\"EMANALYTICS\","
			+ 		"\"EM_PLUGIN_ID\":\"oracle.sysman.core\","
			+ 		"\"DEFAULT_FOLDER_ID\":6,"
			+ 		"\"DELETED\":0,"
			+ 		"\"PROVIDER_NAME\":\"TargetCard\","
			+ 		"\"PROVIDER_VERSION\":\"1.0\","
			+ 		"\"PROVIDER_DISCOVERY\":null,"
			+ 		"\"PROVIDER_ASSET_ROOT\":\"home\","
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"DASHBOARD_INELIGIBLE\":\"0\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"2016-07-22 13:41:07.406812\""
			+ 	"}],"
			+ "\"EMS_ANALYTICS_CATEGORY_PARAMS\": [{"
			+ 		"\"CATEGORY_ID\":5,"
			+ 		"\"NAME\":\"DASHBOARD_INELIGIBLE\","
			+ 		"\"PARAM_VALUE\":1,"
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"CREATION_DATE\":\"2016-07-22 13:41:07.412344\","
			+ 			"\"LAST_MODIFICATION_DATE\":\"2016-07-22 13:41:07.412344\""
			+ 		"}"
			+ "],"
			+ "\"EMS_ANALYTICS_FOLDERS\": [{"
			+ 		"\"FOLDER_ID\":1,"
			+ 		"\"NAME\":\"All Searches\","
			+ 		"\"PARENT_ID\":null,"
			+ 		"\"DESCRIPTION\":\"Search Console Root Folder\","
			+ 		"\"CREATION_DATE\":\"2016-07-04 06:35:41.35228\","
			+ 		"\"OWNER\":\"ORACLE\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"2016-07-04 06:35:41.35228\","
			+ 		"\"LAST_MODIFIED_BY\":\"ORACLE\","
			+ 		"\"NAME_NLSID\":\"ALL_SEARCHES_NAME\","
			+ 		"\"NAME_SUBSYSTEM\":\"EMANALYTICS\","
			+ 		"\"DESCRIPTION_NLSID\":\"ALL_SEARCHES_DESC\","
			+ 		"\"DESCRIPTION_SUBSYSTEM\":\"EMANALYTICS\","
			+ 		"\"SYSTEM_FOLDER\":1,"
			+ 		"\"EM_PLUGIN_ID\":\"oracle.sysman.core\","
			+ 		"\"UI_HIDDEN\":0,"
			+ 		"\"DELETED\":0,"
			+ 		"\"TENANT_ID\": 1"
			+ "}],"
			+ "\"EMS_ANALYTICS_SEARCH\": [{"
			+ 		"\"SEARCH_ID\":2024,"
			+ 		"\"SEARCH_GUID\":\"36CA8B8ECE2A3495E0536403C40AE7A2\","
			+ 		"\"NAME\":\"All Linux Package Lifecycle Activities\","
			+ 		"\"OWNER\":\"ORACLE\","
			+ 		"\"CREATION_DATE\":\"2016-07-04 06:35:42.270776\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"2016-07-04 06:35:42.270776\","
			+ 		"\"LAST_MODIFIED_BY\":\"ORACLE\","
			+ 		"\"DESCRIPTION\":\"Time-based histogram showing count of package \","
			+ 		"\"FOLDER_ID\":2,"
			+ 		"\"CATEGORY_ID\":1,"
			+ 		"\"NAME_NLSID\":null,"
			+ 		"\"NAME_SUBSYSTEM\":null,"
			+ 		"\"DESCRIPTION_NLSID\":null,"
			+ 		"\"DESCRIPTION_SUBSYSTEM\":null,"
			+ 		"\"SYSTEM_SEARCH\":1,"
			+ 		"\"EM_PLUGIN_ID\":null,"
			+ 		"\"IS_LOCKED\":0,"
			+ 		"\"METADATA_CLOB\":null,"
			+ 		"\"SEARCH_DISPLAY_STR\":\"Log Entity' LIKE '/var/log/yum.log\","
			+ 		"\"UI_HIDDEN\":0,"
			+ 		"\"DELETED\":0,"
			+ 		"\"IS_WIDGET\":1,"
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"NAME_WIDGET_SOURCE\":0,"
			+ 		"\"WIDGET_GROUP_NAME\":0,"
			+ 		"\"WIDGET_SCREENSHOT_HREF\":0,"
			+ 		"\"WIDGET_ICON\":0,"
			+ 		"\"WIDGET_KOC_NAME\":0,"
			+ 		"\"WIDGET_VIEWMODEL\":0,"
			+ 		"\"WIDGET_TEMPLATE\":0,"
			+ 		"\"WIDGET_SUPPORT_TIME_CONTROL\":0,"
			+ 		"\"WIDGET_LINKED_DASHBOARD\":0,"
			+ 		"\"WIDGET_DEFAULT_WIDTH\":0,"
			+ 		"\"WIDGET_DEFAULT_HEIGHT\":0,"
			+ 		"\"DASHBOARD_INELIGIBLE\":0,"
			+ 		"\"PROVIDER_NAME\":0,"
			+ 		"\"PROVIDER_VERSION\":0,"
			+ 		"\"PROVIDER_ASSET_ROOT\": 1"
			+ "}],"
			+ "\"EMS_ANALYTICS_SEARCH_PARAMS\": [{"
			+ 		"\"SEARCH_ID\":2024,"
			+ 		"\"NAME\":\"meId\","
			+ 		"\"PARAM_ATTRIBUTES\":null,"
			+ 		"\"PARAM_TYPE\":1,"
			+ 		"\"PARAM_VALUE_STR\":\"A18304C26936401BDCF3AF196F5C80C5\","
			+ 		"\"PARAM_VALUE_CLOB\":null,"
			+ 		"\"TENANT_ID\":null,"
			+ 		"\"CREATION_DATE\":\"2016-07-21 05:46:12.098\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"2016-07-21 05:46:12.098\""
			+ "}],"
		
			+ "\"EMS_ANALYTICS_LAST_ACCESS\": [{"
			+ 		"\"OBJECT_ID\":2000,"
			+ 		"\"ACCESSED_BY\":\"ORACLE\","
			+ 		"\"OBJECT_TYPE\":2,"
			+ 		"\"ACCESS_DATE\":\"2016-07-21 05:46:12.098\","
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"CREATION_DATE\":\"2016-07-21 05:46:12.098\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"2016-07-21 05:46:12.098\","
			+ 		"\"PARAM_VALUE_STR\":\"test value\"}]"
			+ "}";

	@Test
	public void testCompareInstancesData() throws IOException
	{
		SavedsearchRowsComparator src1 = new SavedsearchRowsComparator();
		ZDTTableRowEntity tre1 = Deencapsulation.invoke(src1, "retrieveRowsEntityFromJsonForSingleInstance",
				JSON_RESPONSE_DATA_TABLE);
		ZDTTableRowEntity tre2 = Deencapsulation.invoke(src1, "retrieveRowsEntityFromJsonForSingleInstance",
				JSON_RESPONSE_DATA_TABLE);
		InstancesComparedData<ZDTTableRowEntity> cd = Deencapsulation.invoke(src1, "compareInstancesData",
				new InstanceData<ZDTTableRowEntity>(null,null, tre1,0), new InstanceData<ZDTTableRowEntity>(null,null, tre2,0));
		// the 2 instances have the same data, so there is no difference from the compared result
		ZDTTableRowEntity result1 = cd.getInstance1().getData();
		ZDTTableRowEntity result2 = cd.getInstance1().getData();
		Assert.assertNull(result1.getSavedSearchCategory());
		Assert.assertNull(result1.getSavedSearchCategoryParams());
		Assert.assertNull(result1.getSavedSearchSearch());
		Assert.assertNull(result1.getSavedSearchSearchParams());
		
		Assert.assertNull(result2.getSavedSearchCategory());
		Assert.assertNull(result2.getSavedSearchCategoryParams());
		Assert.assertNull(result2.getSavedSearchSearch());
		Assert.assertNull(result2.getSavedSearchSearchParams());

		// test "not equals"
		SavedsearchRowsComparator src2 = new SavedsearchRowsComparator();
		tre1 = Deencapsulation.invoke(src2, "retrieveRowsEntityFromJsonForSingleInstance", JSON_RESPONSE_DATA_TABLE);
		tre2 = Deencapsulation.invoke(src2, "retrieveRowsEntityFromJsonForSingleInstance", JSON_RESPONSE_DATA_TABLE);
		SavedSearchCategoryRowEntity sscr1 = new SavedSearchCategoryRowEntity();
		sscr1.setCategoryId("12");
		sscr1.setDeleted("2");
		tre1.getSavedSearchCategory().add(sscr1);
		SavedSearchCategoryRowEntity sscr2 = new SavedSearchCategoryRowEntity();
		sscr2.setCategoryId("10");
		sscr2.setDeleted("0");
		tre2.getSavedSearchCategory().add(sscr2);

		cd = Deencapsulation.invoke(src2, "compareInstancesData", new InstanceData<ZDTTableRowEntity>(null, null,tre1,0),
				new InstanceData<ZDTTableRowEntity>(null,null, tre2,0));
		result1 = cd.getInstance1().getData();
		result2 = cd.getInstance2().getData();
		Assert.assertEquals(result1.getSavedSearchCategory().get(0), sscr1);
		
		Assert.assertEquals(result2.getSavedSearchCategory().get(0), sscr2);
	}

	@Test
	public void testRetrieveRowsEntityFromJsonForSingleInstance() throws IOException
	{
		SavedsearchRowsComparator src = new SavedsearchRowsComparator();
		ZDTTableRowEntity tre = Deencapsulation.invoke(src, "retrieveRowsEntityFromJsonForSingleInstance",
				JSON_RESPONSE_DATA_TABLE);
		Assert.assertNotNull(tre);
		Assert.assertEquals(tre.getSavedSearchCategory().get(0).getName(), "Target Card");
		Assert.assertEquals(tre.getSavedSearchCategoryParams().get(0).getName(), "DASHBOARD_INELIGIBLE");
		Assert.assertEquals(tre.getSavedSearchFoldersy().get(0).getName(), "All Searches");
		Assert.assertEquals(tre.getSavedSearchSearch().get(0).getSearchId(), "2024");
		Assert.assertEquals(tre.getSavedSearchSearchParams().get(0).getName(), "meId");
	}
	
	@Test
	public void testCompare(@Mocked final AbstractComparator abstractComparator, 
			@Mocked final LookupClient client1, @Mocked final LookupClient client2,
			@Mocked final InstanceInfo info, @Mocked final InstanceInfo.Builder builder) throws Exception {
		
	      final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
	       
	      /* 	
	    	new Expectations(){
	            {
	                abstractComparator.getOMCInstances();
	                result = lookupEntry;
	                lookupEntry.put("omc1",client1);
	    	    	lookupEntry.put("omc2",client2);
	    	   	
	    	    	InstanceInfo.Builder.newBuilder();//.withServiceName(SAVEDSEARCH_SERVICE_NAME).withVersion(SAVEDSEARCH_VERSION).build();
	    			result = builder;
	    			builder.withServiceName(anyString);
	    			result = builder;
	    			builder.withVersion(anyString);
	    			result = builder;
	    			builder.build();
	    			result = info;
	    			client.lookup(new InstanceQuery(info));
	    			result = infos;
    	    	InstanceInfo.Builder.newBuilder().withServiceName(anyString).withVersion(anyString).build();
	    	    	result = builder;
	    	    	client.lookup(new InstanceQuery(info));
	    	    	result = infos;
	    	    	infos.add(info);
	    	    	
	            }
	        };*/
	        SavedsearchRowsComparator src = new SavedsearchRowsComparator();
			
	        src.compare("tenantId","userTenant");
	}

	
	@Test
	public void testSync() throws Exception {
		SavedsearchRowsComparator src = new SavedsearchRowsComparator();
		
		ZDTTableRowEntity tableRow1 = new ZDTTableRowEntity();
    	tableRow1.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	
    	ZDTTableRowEntity tableRow2 = new ZDTTableRowEntity();
    	tableRow2.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	
    	InstanceData<ZDTTableRowEntity> instance1 = new InstanceData<ZDTTableRowEntity>("", null,tableRow1,  100);
    	InstanceData<ZDTTableRowEntity> instance2 = new InstanceData<ZDTTableRowEntity>("", null,tableRow2,  100);
    	
    	
    	InstancesComparedData<ZDTTableRowEntity> comparedData = new InstancesComparedData<ZDTTableRowEntity>(instance1, instance2);  
		
		src.sync(comparedData,"tenantId", "userTenant");
	}
	
	@Test
	public void testcountForComparedRows() {
		ZDTTableRowEntity tableRow1 = new ZDTTableRowEntity();
    	tableRow1.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	SavedsearchRowsComparator src = new SavedsearchRowsComparator();
    	src.countForComparedRows(tableRow1);
	}
}

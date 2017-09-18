package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.sysman.emInternalSDK.rproxy.lookup.CloudLookupException;
import oracle.sysman.emInternalSDK.rproxy.lookup.CloudLookups;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.config.ClientConfig;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTException;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.TenantSubscriptionUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.SavedsearchRowsComparator;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.LeaseInfo.Builder;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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

	@Mocked
	LookupClient client1;
	@Mocked
	LookupClient client2;

	private static final String JSON_RESPONSE_DATA_TABLE="{"
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
	public void testCompareInstancesData() throws IOException, ZDTException
	{
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
		SavedsearchRowsComparator src1 = new SavedsearchRowsComparator();
		JsonUtil ju = JsonUtil.buildNormalMapper();
		ZDTTableRowEntity tre1 = ju.fromJson(JSON_RESPONSE_DATA_TABLE, ZDTTableRowEntity.class);
		ZDTTableRowEntity tre2 = ju.fromJson(JSON_RESPONSE_DATA_TABLE, ZDTTableRowEntity.class);
		InstancesComparedData<ZDTTableRowEntity> cd = Deencapsulation.invoke(src1, "compareInstancesData",
				new InstanceData<ZDTTableRowEntity>(null,null, tre1), new InstanceData<ZDTTableRowEntity>(null,null, tre2));
		// the 2 instances have the same data, so there is no difference from the compared result
		ZDTTableRowEntity result1 = cd.getInstance1().getData();
		ZDTTableRowEntity result2 = cd.getInstance1().getData();
		Assert.assertNull(result1.getSavedSearchSearch());
		Assert.assertNull(result1.getSavedSearchSearchParams());
		
//		Assert.assertNull(result2.getSavedSearchCategory());
//		Assert.assertNull(result2.getSavedSearchCategoryParams());
		Assert.assertNull(result2.getSavedSearchSearch());
		Assert.assertNull(result2.getSavedSearchSearchParams());

		// test "not equals"
		SavedsearchRowsComparator src2 = new SavedsearchRowsComparator();
		tre1 = ju.fromJson(JSON_RESPONSE_DATA_TABLE, ZDTTableRowEntity.class);
		tre2 = ju.fromJson(JSON_RESPONSE_DATA_TABLE, ZDTTableRowEntity.class);

		cd = Deencapsulation.invoke(src2, "compareInstancesData", new InstanceData<ZDTTableRowEntity>(null, null,tre1),
				new InstanceData<ZDTTableRowEntity>(null,null, tre2));
		result1 = cd.getInstance1().getData();
		result2 = cd.getInstance2().getData();
	}

	@Test
	public void testsaveComparatorStatus() throws Exception {
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
        SavedsearchRowsComparator drc = new SavedsearchRowsComparator();
		drc.saveComparatorStatus("tenantId", "userTenant", null, null);
		drc.setClient1(null);
		drc.setClient2(null);
		drc.setKey1(null);
		drc.setKey2(null);
		drc.getClient1();
		drc.getClient2();
		drc.getKey1();
		drc.getKey2();
	}
	
	
	@Test
	public void testretrieveSyncStatusForOmcInstance() throws Exception {
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
        SavedsearchRowsComparator drc = new SavedsearchRowsComparator();
		drc.retrieveSyncStatusForOmcInstance(null, null);
	}
	
	@Test 
	public void testretrieveComparatorStatusForOmcInstance() throws Exception {
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
        SavedsearchRowsComparator drc = new SavedsearchRowsComparator();
		drc.retrieveComparatorStatusForOmcInstance(null, null);
	}
	
	@Test 
	public void testretrieveTenants(@Mocked LookupClient client) throws Exception {
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
        SavedsearchRowsComparator drc = new SavedsearchRowsComparator();
		drc.retrieveTenants(null, null,client);
	}
	
	@Test
	 public void testCompare() throws ZDTException, JSONException {
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
		final JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		array.put(123456);
		obj.put("client1", array);
		obj.put("client2", array);
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
        SavedsearchRowsComparator drc = new SavedsearchRowsComparator();
	
		drc.compare("id", "name", "type", "2017-05-25 16:03:02",false,"tenant");
		drc.compare("id", "name", "type", "2017-05-25 16:03:02",false,null);
	}
	
	@Test
	public void testcountForComparedRows() throws ZDTException {
		final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
            }
        };
        SavedsearchRowsComparator drc = new SavedsearchRowsComparator();
		ZDTTableRowEntity entity = new ZDTTableRowEntity();
		entity.setSavedSearchFoldersy(null);
		entity.setSavedSearchSearch(null);
		entity.setSavedSearchSearchParams(null);
		drc.countForComparedRows(entity);
	
	}
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.HalfSyncException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.NoComparedResultException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.exception.SyncException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * Created by pingwu 
 */

@Test(groups = { "s2" })
public class ZDTAPITest {
    private ZDTAPI zdtapi = new ZDTAPI();
    @Mocked
    DataManager dataManager;
    @Mocked
    Throwable throwable;
    @Test
    public void testGetAllTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        final List<Map<String, Object>> list = new ArrayList<>();
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getSearchTableData(em, anyString, anyString, anyString, anyString);
                result = list;
            }
        };
        zdtapi.getAllTableData("incremental", null, null);
        zdtapi.getAllTableData("full", "2017-05-25 16:03:02", "tenant");
    }

    @Test
    public void testGetAllTableDataException(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        final List<Map<String, Object>> list = new ArrayList<>();
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getSearchTableData(em, anyString, anyString, anyString, anyString);
                result = new JSONException(throwable);
            }
        };
        zdtapi.getAllTableData("incremental", "2017-05-25 16:03:02", "tenant");
    }
    
    @Test
    public void testGetAllTenants(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) {
    	final List<Object> tenants = new ArrayList<Object>();
    	tenants.add("tenant");
    	new Expectations() {
    		{
    			DataManager.getInstance();
    			result = dataManager;
    			dataManager.getLatestComparisonDateForCompare(em);
    			result = "date";
    			dataManager.getAllTenants(em);
    			result = tenants;
    		}
    	};
    	zdtapi.getAllTenants();
    }

    @Test
    public void testGetEntitiesCoung(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getAllFolderCount(em, anyString);
                result = 1;
                dataManager.getAllSearchCount(em, anyString);
                result = 1;
                dataManager.getAllSearchParamsCount(em, anyString);
                result = 1;
            }
        };
        zdtapi.getEntitiesCount("2017-05-25 16:03:02");
    }
    

    @Test
    public void testGetSyncStatus(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) {
    	 final List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
         Map<String, Object> data = new HashMap<String, Object>();
         data.put("SYNC_DATE", "2017-05-12 15:20:21");
         data.put("NEXT_SCHEDULE_SYNC_DATE", "2017-05-12 15:20:21");
         data.put("SYNC_TYPE", "full");
         data.put("DIVERGENCE_PERCENTAGE", 0.12);
         resultData.add(data);
    	
    	new Expectations() {
    		{
    			DataManager.getInstance();
                result = dataManager;
                dataManager.getSyncStatus(em);
                result = resultData;
    		}
    	};
    	zdtapi.getSyncStatus();
    }
    
    @Test
    public void testGetComparisonStatus(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) {
    	 final List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
         Map<String, Object> data = new HashMap<String, Object>();
         data.put("COMPARISON_DATE", "2017-05-12 15:20:21");
         data.put("NEXT_SCHEDULE_COMPARISON_DATE", "2017-05-12 15:20:21");
         data.put("COMPARISON_TYPE", "full");
         data.put("DIVERGENCE_PERCENTAGE", 0.12);
         resultData.add(data);
    	
    	new Expectations() {
    		{
    			DataManager.getInstance();
                result = dataManager;
                dataManager.getComparatorStatus(em);
                result = resultData;
    		}
    	};
    	zdtapi.getComparisonStatus();
    }
    
    @Test
    public void testSaveComparisonResult(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws JSONException {
    	String json  = "{\"lastComparisonDateTime\":\"2017-05-12 15:20:21\", \"comparisonType\":\"full\",\"comparisonResult\":\"{}\",\"divergencePercentage\":0.11,\"nextScheduledComparisonDateTime\":\"2017-05-12 15:20:21\"}";
    	JSONObject object = new JSONObject(json);
    	zdtapi.saveComparatorData(object);
    }
    
    @Test
    public void testSaveComparisonResult2(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws JSONException {
    	String json  = "{ \"comparisonType\":\"full\",\"comparisonResult\":\"{}\",\"divergencePercentage\":0.11,\"nextScheduledComparisonDateTime\":\"2017-05-12 15:20:21\"}";
    	JSONObject object = new JSONObject(json);
    	zdtapi.saveComparatorData(object);
    }

    @Test
    public void testHalfSync1(@Mocked final PersistenceManager persistenceManager,
                             @Mocked final EntityManager em, @Mocked final JsonUtil jsonUtil) throws Exception {
        final List<Map<String, Object>> comparedDataToSync = new ArrayList<Map<String, Object>>();
        final Map<String, Object> comparedData = new HashMap<String, Object>();
//        comparedData.put("COMPARISON_RESULT", "{ \"EMS_DASHBOARD_SET\": [{\"DASHBOARD_SET_ID\": \"888888\",\"TENANT_ID\": 1565220054,\"SUB_DASHBOARD_ID\": \"9999999\",\"POSITION\": 0,\"CREATION_DATE\": \"2017-03-17 07:46:51.07\",\"LAST_MODIFICATION_DATE\": \"2017-03-17 07:46:51.07\",\"DELETED\": \"0\"}]}");
        comparedData.put("SYNC_RESULT", 1);
        comparedData.put("COMPARISON_DATE", "2017-05-12 15:20:21");
        comparedDataToSync.add(comparedData);
        final Map<String, Object> halfSyncRecord = new HashMap<>();
        halfSyncRecord.put("COMPARISON_RESULT","data...");
        final ZDTTableRowEntity data = new ZDTTableRowEntity();
        new Expectations() {
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.checkHalfSyncRecord(em);
                result = comparedData;
                dataManager.getHalfSyncedComparedData(em, anyString);
                result =halfSyncRecord;
                JsonUtil.buildNormalMapper();
                result = jsonUtil;

                jsonUtil.fromJson(anyString,ZDTTableRowEntity.class);
                result = data;
            }
        };
        zdtapi.sync();
    }

    @Test
    public void testHalfSyncException(@Mocked final PersistenceManager persistenceManager,
                              @Mocked final EntityManager em, @Mocked final JsonUtil jsonUtil) throws Exception {
        final List<Map<String, Object>> comparedDataToSync = new ArrayList<Map<String, Object>>();
        final Map<String, Object> comparedData = new HashMap<String, Object>();
//        comparedData.put("COMPARISON_RESULT", "{ \"EMS_DASHBOARD_SET\": [{\"DASHBOARD_SET_ID\": \"888888\",\"TENANT_ID\": 1565220054,\"SUB_DASHBOARD_ID\": \"9999999\",\"POSITION\": 0,\"CREATION_DATE\": \"2017-03-17 07:46:51.07\",\"LAST_MODIFICATION_DATE\": \"2017-03-17 07:46:51.07\",\"DELETED\": \"0\"}]}");
        comparedData.put("SYNC_RESULT", 1);
        comparedData.put("COMPARISON_DATE", "2017-05-12 15:20:21");
        comparedDataToSync.add(comparedData);
        final Map<String, Object> halfSyncRecord = new HashMap<>();
        halfSyncRecord.put("COMPARISON_RESULT","data...");
        final ZDTTableRowEntity data = new ZDTTableRowEntity();
        new Expectations() {
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.checkHalfSyncRecord(em);
                result = comparedData;
                dataManager.getHalfSyncedComparedData(em, anyString);
                result = new SyncException("error");
            }
        };
        zdtapi.sync();

        new Expectations() {
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.checkHalfSyncRecord(em);
                result = new HalfSyncException("error");
            }
        };
        zdtapi.sync();

        new Expectations() {
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.checkHalfSyncRecord(em);
                result = comparedData;
                dataManager.getHalfSyncedComparedData(em, anyString);
                result = new NoComparedResultException("error");
            }
        };
        zdtapi.sync();
    }

    @Test
    public void testSync2(@Mocked final PersistenceManager persistenceManager,
                          @Mocked final EntityManager em, @Mocked final JsonUtil jsonUtil,final @Mocked ZDTSynchronizer zdtSynchronizer) throws HalfSyncException, IOException, SyncException {
        final List<Map<String, Object>> comparedDataToSyn  = new ArrayList<>();
        final Map<String, Object> map = new HashMap<>();
        map.put("COMPARISON_RESULT","result");
        map.put("COMPARISON_DATE","date");
        comparedDataToSyn.add(map);
        final ZDTTableRowEntity data = new ZDTTableRowEntity();
        List<SavedSearchSearchRowEntity> savedSearchSearch = new ArrayList<>();
        savedSearchSearch.add(new SavedSearchSearchRowEntity());
        data.setSavedSearchSearch(savedSearchSearch);
        new Expectations() {
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.checkHalfSyncRecord(em);
                result = null;
                dataManager.getComparedDataForSync(em, anyString);
                result = comparedDataToSyn;
                JsonUtil.buildNormalMapper();
                result = jsonUtil;
                jsonUtil.fromJson(anyString,ZDTTableRowEntity.class);
                result = data;

            }
        };
        zdtapi.sync();

        //test sync Exception
        new Expectations() {
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.checkHalfSyncRecord(em);
                result = null;
                dataManager.getComparedDataForSync(em, anyString);
                result = comparedDataToSyn;
                JsonUtil.buildNormalMapper();
                result = jsonUtil;
                jsonUtil.fromJson(anyString,ZDTTableRowEntity.class);
                result = data;
                zdtSynchronizer.sync((ZDTTableRowEntity)any);
                result = new SyncException("error");

            }
        };
        zdtapi.sync();
    }

    @Test
    public void testGetComparedDateforSync(){
        Deencapsulation.invoke(zdtapi, "getComparedDateforSync", String.class,String.class);
        Deencapsulation.invoke(zdtapi, "getComparedDateforSync", "date1","date2");
    }
  
}
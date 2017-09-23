package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.exception.ZDTException;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.ZDTAPI;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.SavedsearchRowsComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.SavedSearchSearchParamRowEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.SavedSearchSearchRowEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.ZDTTableRowEntity;

import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class ZDTAPITest {
	private ZDTAPI zdtapi = new ZDTAPI();
    @Mocked
    AbstractComparator abstractComparator;
    @Mocked
    InstancesComparedData<CountsEntity> set;
    @Mocked
    InstanceData<CountsEntity> data;
    @Mocked
    CountsEntity count;
    @Mocked
    Throwable throwable;
    @Mocked
    String tenant;
    @Mocked
    String userTenant;
    
    @Test
    public void testSyncOnDF(@Mocked final JsonUtil jsonUtil, @Mocked final LookupClient client1, @Mocked final LookupClient client2) throws IOException{
    	final ZDTTableRowEntity tableRow1 = new ZDTTableRowEntity();
    	tableRow1.setSavedSearchSearch(new ArrayList<SavedSearchSearchRowEntity>());
    	 
        final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	
    	new Expectations() {
    		{ 	
    			abstractComparator.getOMCInstances();
    			result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
    			
    		/*	JsonUtil.buildNormalMapper();
    			result = jsonUtil;
    			jsonUtil.fromJson(anyString,ZDTTableRowEntity.class);
    			result = tableRow1; */
    		}
    	};
        zdtapi.syncOnSSF("tenant");
    }

   @Test
    public void testInnerClasses(){
        ZDTAPI.InstanceCounts counts = new ZDTAPI.InstanceCounts(data);
        counts.getCounts();
        counts.getInstanceName();
        counts.setCounts(count);
        counts.setInstanceName("name");
        ZDTAPI.InstancesComapredCounts instancesComapredCounts = new ZDTAPI.InstancesComapredCounts(counts, counts);
        instancesComapredCounts.getInstance1();
        instancesComapredCounts.getInstance2();
        instancesComapredCounts.setInstance1(counts);
        instancesComapredCounts.setInstance2(counts);
    }

    @Test
    public void testgetSyncStatus() {
    	zdtapi.getSyncStatus("id");
    	zdtapi.getSyncStatus(null);
    }
    @Test
    public void testgetSyncStatusException(final @Mocked SavedsearchRowsComparator savedsearchRowsComparator) throws Exception {
        new Expectations(){
            {
                savedsearchRowsComparator.retrieveSyncStatusForOmcInstance(anyString, null);
                result = new ZDTException(1,"err");
            }
        };
        zdtapi.getSyncStatus("id");
        zdtapi.getSyncStatus(null);
        new Expectations(){
            {
                savedsearchRowsComparator.retrieveSyncStatusForOmcInstance(anyString, null);
                result = new Exception("err");
            }
        };
        zdtapi.getSyncStatus("id");
        zdtapi.getSyncStatus(null);
    }
    
    @Test
    public void testgetCompareStatus() {
    	zdtapi.getSyncStatus("id");

    	zdtapi.getCompareStatus(null);
    }
    @Test
    public void testgetCompareStatusException(final @Mocked SavedsearchRowsComparator savedsearchRowsComparator) throws Exception {
        new Expectations(){
            {
                savedsearchRowsComparator.retrieveComparatorStatusForOmcInstance(anyString, null);
                result = new ZDTException(1,"err");
            }
        };
        zdtapi.getCompareStatus("id");

        zdtapi.getCompareStatus(null);
        new Expectations(){
            {
                savedsearchRowsComparator.retrieveComparatorStatusForOmcInstance(anyString, null);
                result = new Exception("err");
            }
        };
        zdtapi.getCompareStatus("id");

        zdtapi.getCompareStatus(null);
    }
    
    @Test
    public void testSyncOnSSF(@Mocked final JsonUtil jsonUtil, @Mocked final LookupClient client1, @Mocked final LookupClient client2) throws IOException{
    	 
        final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	
    	new Expectations() {
    		{ 	
    			abstractComparator.getOMCInstances();
    			result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
    		}
    	};
        zdtapi.syncOnSSF("tenant");
    }

    //incremental compare
    @Test
    public void testCompareRows(final @Mocked InstanceData instanceData,final @Mocked JsonUtil jsonUtil, final @Mocked LookupClient lookupClient, final @Mocked SavedsearchRowsComparator savedsearchRowsComparator) throws Exception {
        zdtapi.compareRows("tenant",30);

        final ZDTTableRowEntity data = new ZDTTableRowEntity();
        List<SavedSearchSearchRowEntity> savedSearchSearch = new ArrayList<SavedSearchSearchRowEntity>();
        SavedSearchSearchRowEntity savedSearchSearchRowEntity = new SavedSearchSearchRowEntity();
        savedSearchSearchRowEntity.setCategoryId("1234");
        savedSearchSearch.add(savedSearchSearchRowEntity);
       data.setSavedSearchSearch(savedSearchSearch);
        final String tenant = "{\"isCompared\":true,\"tenants\":[\"406626006\"],\"lastComparedDate\":\"2017-09-22 15:15:54.126\"}";
//        InstanceData<ZDTTableRowEntity> instanceData1 = new InstanceData("omc1",lookupClient,data);
////        instanceData1.
//        InstanceData<ZDTTableRowEntity> instanceData2 = new InstanceData("omc2",lookupClient,data);
        final InstancesComparedData<ZDTTableRowEntity> compareResult = new InstancesComparedData(instanceData,instanceData);
        //incremental compare
        new Expectations(){
            {
                savedsearchRowsComparator.retrieveTenants(anyString,anyString, lookupClient);
                result  = tenant;
                savedsearchRowsComparator.getTotalRowForOmcInstance(anyString, anyString, lookupClient, anyString);
                result = 10;
                savedsearchRowsComparator.compare(anyString,anyString,anyString,anyString,anyBoolean,anyString);
                result  =compareResult;
                //FIXME
                instanceData.getData();
                result=data;
                savedsearchRowsComparator.countForComparedRows((ZDTTableRowEntity)any);
                result = 5;
            }
        };
        zdtapi.compareRows("tenant",30);
    }

    //full compare
    @Test
    public void testCompareRows2(final @Mocked InstanceData instanceData, final @Mocked ZDTTableRowEntity data,final @Mocked JsonUtil jsonUtil, final @Mocked LookupClient lookupClient, final @Mocked SavedsearchRowsComparator savedsearchRowsComparator) throws Exception {
        zdtapi.compareRows("tenant", 30);
//        final ZDTTableRowEntity data = new ZDTTableRowEntity();
        List<SavedSearchSearchRowEntity> savedSearchSearch = new ArrayList<SavedSearchSearchRowEntity>();
        SavedSearchSearchRowEntity savedSearchSearchRowEntity = new SavedSearchSearchRowEntity();
        savedSearchSearchRowEntity.setCategoryId("1234");
        savedSearchSearch.add(savedSearchSearchRowEntity);
        data.setSavedSearchSearch(savedSearchSearch);
        final String tenant = "{\"isCompared\":false,\"tenants\":[\"406626006\"],\"lastComparedDate\":\"2017-09-22 15:15:54.126\"}";
//        InstanceData<ZDTTableRowEntity> instanceData1 = new InstanceData("omc1",lookupClient,data);
////        instanceData1.
//        InstanceData<ZDTTableRowEntity> instanceData2 = new InstanceData("omc2",lookupClient,data);
        final InstancesComparedData<ZDTTableRowEntity> compareResult = new InstancesComparedData(instanceData,instanceData);
        new Expectations(){
            {
                savedsearchRowsComparator.retrieveTenants(anyString,anyString, lookupClient);
                result  = tenant;

                savedsearchRowsComparator.getTotalRowForOmcInstance(anyString, anyString, lookupClient, anyString);
                result = 10;
                savedsearchRowsComparator.compare(anyString,anyString,anyString,anyString,anyBoolean,anyString);
                result  =compareResult;
                //FIXME
                instanceData.getData();
                result=data;
                savedsearchRowsComparator.countForComparedRows((ZDTTableRowEntity)any);
                result = 5;
            }
        };
        zdtapi.compareRows("tenant",30);
    }

    @Test
    public void testSyncOnSSF(final @Mocked LookupClient lookupClient,  final @Mocked SavedsearchRowsComparator savedsearchRowsComparator) throws Exception {
        new Expectations(){
            {
                savedsearchRowsComparator.syncForInstance(anyString, null, lookupClient) ;
                result = "successful";
            }
        };
        zdtapi.syncOnSSF("tennat");
    }

   
}




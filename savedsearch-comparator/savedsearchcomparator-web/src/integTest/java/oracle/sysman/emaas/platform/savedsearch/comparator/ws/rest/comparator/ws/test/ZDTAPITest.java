package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.ws.test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.ZDTAPI;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.SavedsearchRowsComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.SavedSearchCategoryRowEntity;
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
    public void testCompareOnSSF(@Mocked final JsonUtil jsonUtil, @Mocked final LookupClient client1, @Mocked final LookupClient client2) throws IOException{
    	final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	//final CountsEntity countsEntity = new CountsEntity(10L, 10L, 10L);
    	new Expectations(){
            {
                abstractComparator.getOMCInstances();
                result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
    	    	
    	   	JsonUtil.buildNormalMapper();
    			result = jsonUtil;
    			jsonUtil.fromJson(anyString,CountsEntity.class);
    			result = count;
    			
            }
        };
        zdtapi.compareOnSSF(tenant, userTenant);
    }

    @Test
    public void testSyncOnDF(@Mocked final JsonUtil jsonUtil, @Mocked final LookupClient client1, @Mocked final LookupClient client2) throws IOException{
    	final ZDTTableRowEntity tableRow1 = new ZDTTableRowEntity();
    	tableRow1.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	 
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
        zdtapi.syncOnSSF("tenant", "userTenant", "type");
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

    
  /*  @Test 
    public void testgetCompareStatusRows(@Mocked final LookupClient client1, @Mocked final LookupClient client2) {
    	
    	
    	ZDTTableRowEntity tableRow1 = new ZDTTableRowEntity();
    	tableRow1.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	
    	ZDTTableRowEntity tableRow2 = new ZDTTableRowEntity();
    	tableRow2.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	
    	InstanceData<ZDTTableRowEntity> instance1 = new InstanceData<ZDTTableRowEntity>("", null,tableRow1,  100);
    	InstanceData<ZDTTableRowEntity> instance2 = new InstanceData<ZDTTableRowEntity>("", null,tableRow2,  100);
    	
    	final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	
    	
    	final InstancesComparedData<ZDTTableRowEntity> comparedData = new InstancesComparedData<ZDTTableRowEntity>(instance1, instance2);  
    	new Expectations() {
    		{ 	
    			abstractComparator.getOMCInstances();
    			result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
    			
    		}
    	};
    	SavedsearchRowsComparator dcc = new SavedsearchRowsComparator();
    	zdtapi.getCompareStatusRows(dcc, comparedData, "full");
    	
    }
  */

    @Test
    public void testCompareRows2(@Mocked final JsonUtil jsonUtil, @Mocked final LookupClient client1, @Mocked final LookupClient client2) throws Exception{
    	final ZDTTableRowEntity tableRow1 = new ZDTTableRowEntity();
    	tableRow1.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	ZDTTableRowEntity tableRow2 = new ZDTTableRowEntity();
    	tableRow2.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
    	InstanceData<ZDTTableRowEntity> instance1 = new InstanceData<ZDTTableRowEntity>("", null,tableRow1,  100);
    	InstanceData<ZDTTableRowEntity> instance2 = new InstanceData<ZDTTableRowEntity>("", null,tableRow2,  100);
    	
    	final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	
    	
    	final InstancesComparedData<ZDTTableRowEntity> comparedData = new InstancesComparedData<ZDTTableRowEntity>(instance1, instance2);  
    	new Expectations() {
    		{ 	
    			abstractComparator.getOMCInstances();
    			result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
    		/*	
    			JsonUtil.buildNormalMapper();
    			result = jsonUtil;
    			jsonUtil.fromJson(anyString,ZDTTableRowEntity.class);
    			result = tableRow1;  */
    		}
    	};
    	SavedsearchRowsComparator dcc = new SavedsearchRowsComparator();
    	zdtapi.compareRows(tenant, userTenant,"full", 5);
    }

    
    @Test
    public void testGetCurrentTime() {
    	zdtapi.getCurrentUTCTime();
    }
    
    @Test
    public void testGetTimeString() {
    	zdtapi.getTimeString(new Date());
    }
    
    @Test
    public void testCompareRows1() {
    	
    	zdtapi.compareRows(tenant, userTenant,"full", 5);
    }
    
    @Test
    public void testCompareRows2(@Mocked final JsonUtil jsonUtil, @Mocked final LookupClient client1, @Mocked final LookupClient client2,
    		@Mocked final Link link) throws Exception{
    
    	final HashMap<String, LookupClient> lookupEntry = new HashMap<String, LookupClient>();
    	   	
    	new Expectations() {
    		{ 	
    			abstractComparator.getOMCInstances();
    			result = lookupEntry;
    			lookupEntry.put("omc1",client1);
    	    	lookupEntry.put("omc2",client2);
    			
    		/*	JsonUtil.buildNormalMapper();
    			result = jsonUtil;
    			jsonUtil.fromJson(anyString,TableRowsEntity.class);
    			result = tableRow1;  */
    		}
    	};
    	
    	zdtapi.compareRows(tenant, userTenant,"full", 5);
    }
    
    @Test
    public void testgetSyncStatus() {
    	zdtapi.getCompareStatus("id", "userName");
    	zdtapi.getCompareStatus(null,null);
    }
    
    @Test
    public void testgetCompareStatus() {
    	zdtapi.getSyncStatus("id", "userName");

    	zdtapi.getCompareStatus(null,null);
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
    		/*	
    			JsonUtil.buildNormalMapper();
    			result = jsonUtil;
    			jsonUtil.fromJson(anyString,TableRowsEntity.class);
    			result = tableRow1;*/
    		}
    	};
        zdtapi.syncOnSSF("tenant", "userTenant", "full");
    }
    
   
}




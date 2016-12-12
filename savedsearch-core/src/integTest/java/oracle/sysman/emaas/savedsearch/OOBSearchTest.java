package oracle.sysman.emaas.savedsearch;

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class OOBSearchTest {
    @Test
    public void test4NewWidgetsAdded() throws EMAnalyticsFwkException {
        //long topologyWidgetId = 3219;
        //long omcCompositeTargetId = 4004;
        //long orchestrationWorkflowSubmissionId = 5004;
        BigInteger omcWorkflowSubmissionId = new BigInteger("4005");
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            /* comment out now, enable again in 1.12
            Search search = searchManager.getSearch(topologyWidgetId);
            Assert.assertNotNull(search,"Topology Widget does not exist.");
            if(search != null) {
                Assert.assertEquals("Topology Widget", search.getName(),"Name for Topology Widget");
                Assert.assertNull(search.getDescription(),"Description for Topology Widget");
            }

            search = searchManager.getSearch(omcCompositeTargetId);
            Assert.assertNotNull(search,"CARD_DEF_omc_composite_target does not exist.");
            if(search != null) {
                Assert.assertEquals("CARD_DEF_omc_composite_target", search.getName(),"Name for CARD_DEF_omc_composite_target");
                Assert.assertNull(search.getDescription(),"Description for CARD_DEF_omc_composite_target");
            }
            */
            
            /* Orchestration is not subscribed by emaastesttenant1 by default, so below test may not work in farm job
            search = searchManager.getSearch(orchestrationWorkflowSubmissionId);
            Assert.assertNotNull(search,"OrchestrationWorkflowSubmission does not exist.");
            if(search != null) {
                Assert.assertEquals("Orchestration Workflow Submission", search.getName(),"Name for Orchestration Workflow Submission");
                Assert.assertNull(search.getDescription(),"Description for Orchestration Workflow Submission");
            }
            */

            Search search = searchManager.getSearch(omcWorkflowSubmissionId);
            Assert.assertNotNull(search,"CARD_DEF_omc_workflow_submission does not exist.");
            if(search != null) {
                Assert.assertEquals("CARD_DEF_omc_workflow_submission", search.getName(),"Name for CARD_DEF_omc_workflow_submission");
                Assert.assertNull(search.getDescription(),"Description for CARD_DEF_omc_workflow_submission");
            }
        }finally{
            TenantContext.clearContext();
        }
    }

    @Test
    public void test4WidgetsUpdated() throws EMAnalyticsFwkException {
    	BigInteger id4000 = new BigInteger("4000");
    	BigInteger id4001 = new BigInteger("4001");
    	BigInteger id4002 = new BigInteger("4002");
    	BigInteger id4003 = new BigInteger("4003");
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            checkUpdatedSearchParam(id4000,new String("{\"id\":null,\"entityType\":\"omc_web_application_server\",\"widgets\":null,\"widgetRefs\":[{\"id\":3030,\"parameters\":null,\"entityCapability\":null,\"accessControl\":{\"userPrivilege\":\"USE_TARGET_ANALYTICS\"}}]}"));
            checkUpdatedSearchParam(id4001,new String("{\"id\":null,\"entityType\":\"omc_relational_database\",\"widgets\":null,\"widgetRefs\":[{\"id\":3029,\"parameters\":null,\"entityCapability\":null,\"accessControl\":{\"userPrivilege\":\"USE_TARGET_ANALYTICS\"}}]}"));
            checkUpdatedSearchParam(id4002,new String("{\"id\":null,\"entityType\":\"omc_host\",\"widgets\":null,\"widgetRefs\":[{\"id\":3031,\"parameters\":null,\"entityCapability\":null,\"accessControl\":{\"userPrivilege\":\"USE_TARGET_ANALYTICS\"}}]}"));
            checkUpdatedSearchParam(id4003,new String("{\"id\":null,\"entityType\":\"omc_entity\",\"widgets\":null,\"widgetRefs\":[{\"id\":2027,\"parameters\":null,\"entityCapability\":{\"type\":\"SERVICE: Log Analytics\",\"name\":\"LOG_COLLECTED\"},\"accessControl\":{\"userPrivilege\":\"SEARCH_LOGS\"}}]}"));
        }finally{
            TenantContext.clearContext();
        }
    }

    private void checkUpdatedSearchParam(BigInteger searchId,String paramStr) throws EMAnalyticsFwkException {
        SearchManager searchManager = SearchManager.getInstance();
        Search search = searchManager.getSearch(searchId);
        List<SearchParameter> searchParameterList = search.getParameters();
        SearchParameter targetSearchParam = null;
        for (SearchParameter searchParameter : searchParameterList) {
            if ("cardef".equals(searchParameter.getName())) {
                targetSearchParam = searchParameter;
                Assert.assertEquals(paramStr, searchParameter.getValue(), "EMS_ANALYTICS_SEARCH_PARAMS NAME=cardef SEARCH_ID=" + searchId);
            }
        }
        Assert.assertNotNull(targetSearchParam,"EMS_ANALYTICS_SEARCH_PARAMS NAME=cardef SEARCH_ID=" + searchId+"not exist.");
    }
}

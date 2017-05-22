package oracle.sysman.emaas.savedsearch;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Test
public class OOBWidgetTest {
    @Test
    public void testOOBWidgetParamNotNull() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            CategoryManager categoryManager = CategoryManager.getInstance();
            SearchManager searchManager = SearchManager.getInstance();
            List<Category> categoryList = categoryManager.getAllCategories();
            for (Category category : categoryList) {
                List<Search> searchList = searchManager.getSearchListByCategoryId(category.getId());
                for (Search search : searchList) {
                    boolean isSystemSearch = search.isSystemSearch();
                    boolean isWidget = search.getIsWidget();
                    if (isSystemSearch && isWidget) {
                        List<String> nameList = new ArrayList<>();
                        for(SearchParameter searchParam : search.getParameters()){
                            if("WIDGET_KOC_NAME".equals(searchParam.getName())||"WIDGET_VIEWMODEL".equals(searchParam.getName())||"WIDGET_TEMPLATE".equals(searchParam.getName())){
                                if (!nameList.contains(searchParam.getName())) {
                                    nameList.add(searchParam.getName());
                                    Assert.assertNotNull(searchParam.getValue());
                                } else {
                                    Assert.fail("Duplicate Parameter: " + searchParam.getName());
                                }
                            }
                        }
                        Assert.assertEquals(3, nameList.size());
                    }
                }
            }
        }finally{
            TenantContext.clearContext();
        }
    }
    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testCOSWidgetDelete() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            String[] searchIds = {"5009", "5007", "5006", "5008", "5010", "5011", "5012"};
            for (String searchId : searchIds) {
                Search search = searchManager.getSearch(new BigInteger(searchId));
                Assert.assertNull(search);
            }
        }finally{
            TenantContext.clearContext();
        }
    }

    @Test
    public void testTopSourcesParam() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            Search search = searchManager.getSearch(new BigInteger("2000"));
            boolean isExist = false;
            for(SearchParameter searchParam : search.getParameters()){
                if("VISUALIZATION_TYPE_KEY".equals(searchParam.getName()) ){
                    isExist = true;
                    break;
                }
            }
            Assert.assertTrue(isExist);
        }finally{
            TenantContext.clearContext();
        }
    }

    @Test
    public void testOrchestrationWidgetExecutionDetails() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            Search search = searchManager.getSearch(new BigInteger("5020"));
            boolean isExist = false;
            isExist = "Execution Details".equals(search.getName());
            Assert.assertTrue(isExist);
        }finally{
            TenantContext.clearContext();
        }
    }
    @Test
    public void testOrchestrationOverView() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            Search search = searchManager.getSearch(new BigInteger("5005"));
            boolean isExist = false;
            isExist = "Overview".equals(search.getName());
            Assert.assertTrue(isExist);
        }finally{
            TenantContext.clearContext();
        }
    }


    @Test
    public void testSecurityDNSWidget() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            Search search = searchManager.getSearch(new BigInteger("3300"));
            boolean isExist;
            isExist = "Total DNS Messages".equals(search.getName());
            Assert.assertTrue(isExist, "Total DNS Messages does not exist");

            search = searchManager.getSearch(new BigInteger("3301"));
            isExist = "Unique DNS Queries".equals(search.getName());
            Assert.assertTrue(isExist, "Unique DNS Queries does not exist");

            search = searchManager.getSearch(new BigInteger("3302"));
            isExist = "Top 10 DNS Domains".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 DNS Domains does not exist");

            search = searchManager.getSearch(new BigInteger("3303"));
            isExist = "Top 10 DNS Sources".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 DNS Sources does not exist");

            search = searchManager.getSearch(new BigInteger("3304"));
            isExist = "Top 10 DNS Sources with TXT Lookup".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 DNS Sources with TXT Lookup does not exist");

            search = searchManager.getSearch(new BigInteger("3305"));
            isExist = "Top 10 DNS Non-Standard TLDs".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 DNS Non-Standard TLDs does not exist");

            search = searchManager.getSearch(new BigInteger("3306"));
            isExist = "DNS Queries Per Domain".equals(search.getName());
            Assert.assertTrue(isExist, "DNS Queries Per Domain does not exist");

            search = searchManager.getSearch(new BigInteger("3307"));
            isExist = "DNS Responses by Type".equals(search.getName());
            Assert.assertTrue(isExist, "DNS Responses by Type does not exist");

            search = searchManager.getSearch(new BigInteger("3308"));
            isExist = "Top 10 Denied Sources".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 Denied Sources does not exist");

            search = searchManager.getSearch(new BigInteger("3328"));
            isExist = "Top 10 Oracle DBs by Anomalies".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 Oracle DBs by Anomalies does not exist");
        }finally{
            TenantContext.clearContext();
        }
    }


    @Test
    public void testSecurityMySQLWidget() throws EMAnalyticsFwkException {
        TenantContext.setContext(new TenantInfo(
                TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            SearchManager searchManager = SearchManager.getInstance();
            Search search = searchManager.getSearch(new BigInteger("3401"));
            boolean isExist;
            isExist = "Top 10 MySQL DBs by Threats".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 MySQL DBs by Threats does not exist");

            search = searchManager.getSearch(new BigInteger("3409"));
            isExist = "Top 10 MySQL DBs with Schema Changes".equals(search.getName());
            Assert.assertTrue(isExist, "Top 10 MySQL DBs with Schema Changes does not exist");
        }finally{
            TenantContext.clearContext();
        }
    }



}

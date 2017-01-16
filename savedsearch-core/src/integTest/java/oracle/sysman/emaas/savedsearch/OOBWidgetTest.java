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
            String[] searchIds = {"5009", "5007", "5006"};
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
}

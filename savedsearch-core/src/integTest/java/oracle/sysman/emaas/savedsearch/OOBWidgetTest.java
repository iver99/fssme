package oracle.sysman.emaas.savedsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

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
                        String value = null;
                        List<String> nameList = new ArrayList<>();
                        for(SearchParameter searchParam : search.getParameters()){
                            if("WIDGET_KOC_NAME".equals(searchParam.getName())||"WIDGET_VIEWMODEL".equals(searchParam.getName())||"WIDGET_TEMPLATE".equals(searchParam.getName())){
                                if (!nameList.contains(searchParam.getName())) {
                                    nameList.add(searchParam.getName());
                                    value = searchParam.getValue() != null ? searchParam.getValue() : value;
                                } else {
                                    Assert.fail();
                                }
                            }
                        }
                        Assert.assertEquals(3, nameList.size());
                        Assert.assertNotNull(value);
                    }
                }
            }
        }finally{
            TenantContext.clearContext();
        }
    }

}

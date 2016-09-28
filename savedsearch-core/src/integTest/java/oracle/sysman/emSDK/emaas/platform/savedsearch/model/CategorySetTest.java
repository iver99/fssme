package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/24.
 */
@Test(groups = {"s1"})
public class CategorySetTest {
    private CategorySet categorySet = new CategorySet();
    private FolderSet folderSet = new FolderSet();
    private  SearchSet searchSet = new SearchSet();
    @Test
    public void testGetCategorySet() throws Exception {
        Assert.assertNotNull(categorySet.getCategorySet());
        categorySet.setCategorySet(new ArrayList<ImportCategoryImpl>());
        Assert.assertNotNull(categorySet.getCategorySet());
    }

    @Test
    public void testGetFolderSet(){
        Assert.assertNotNull(folderSet.getFolderSet());
        folderSet.setFolderSet(new ArrayList<FolderDetails>());
        Assert.assertNotNull(folderSet.getFolderSet());
    }

    @Test
    public void testGetSearcchSet(){
        Assert.assertNotNull(searchSet.getSearchSet());
        searchSet.setSearchSet(new ArrayList<ImportSearchImpl>());
        Assert.assertNotNull(searchSet.getSearchSet());
    }

    @Test
    public void testGetContext(){
        Assert.assertNull(TenantContext.getContext());
        TenantContext.setContext(new TenantInfo("Oracle", 1L));
        Assert.assertNotNull(TenantContext.getContext());
        TenantContext.clearContext();
    }

    @Test
    public void getManagetInstance() throws EMAnalyticsFwkException {
        Assert.assertNotNull(FolderManager.getInstance());
        Assert.assertNotNull(SearchManager.getInstance());
        Assert.assertNotNull(CategoryManager.getInstance());
        Assert.assertNotNull(WidgetManager.getInstance());
    }

    @Test
    public void getSearchParamAttri(){
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setAttributes("");
        Assert.assertEquals("", searchParameter.getAttributes());
    }
}
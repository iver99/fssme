package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;

import org.codehaus.jackson.node.ObjectNode;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.codehaus.jettison.json.JSONException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/22/2016.
 */
@Test(groups={"s2"})
public class CategoryAPITest {
    @Mocked
    UriInfo uriInfo;
    @Mocked
    CategoryManager categoryManager;
    @Mocked
    Category category;
    @Mocked
    URI uri;
    @Mocked
    EntityJsonUtil entityJsonUtil;
    @Mocked
    DependencyStatus dependencyStatus;
    private CategoryAPI categoryAPI = new CategoryAPI();

    @BeforeMethod
	public void setUp(){
		Deencapsulation.setField(categoryAPI, "uri", uriInfo);
	}

    @Test
    public void testGetCategory() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory((BigInteger) any);
                result = category;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getFullCategoryJsonObj((URI)any, (Category)any);
                result = new ObjectNode(null);
            }
        };
        categoryAPI.getCategory(BigInteger.ONE);
    }
    @Mocked
    Throwable throwable;
    @Test
    public void testGetCategoryEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory((BigInteger) any);
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getCategory(BigInteger.ONE);

    }

    @Test
    public void getCategoryByName() throws EMAnalyticsFwkException {
        categoryAPI.getCategoryByName(null);
        categoryAPI.getCategoryByName("");
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result =categoryManager;
                categoryManager.getCategory(anyString);
                result = category;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getFullCategoryJsonObj((URI)any, (Category)any);
                result = new ObjectNode(null);
            }
        };
        categoryAPI.getCategoryByName("name");
    }

    @Test
    public void testGetCategoryByNameEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        categoryAPI.getCategoryByName(null);
        categoryAPI.getCategoryByName("");
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result =categoryManager;
                categoryManager.getCategory(anyString);
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getCategoryByName("name");
    }
    @Mocked
    SearchManager searchManager;
    @Mocked
    Search search;
    @Test
    public void testGetSearchesByCategory() throws EMAnalyticsFwkException, JSONException {
        categoryAPI.getSearchesByCategory(null, "true");
        categoryAPI.getSearchesByCategory(BigInteger.ONE.negate(), "true");
        final List<Search> searches = new ArrayList<>();
        searches.add(search);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                CategoryManager.getInstance();
                result = categoryManager;
                searchManager.getSystemSearchListByCategoryId((BigInteger) any);
                result = searches;
                searchManager.getSearchListByCategoryId((BigInteger) any);
                result = searches;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getFullSearchJsonObj((URI)any, (Search)any, null, false);
                result = new ObjectNode(null);
            }
        };
        categoryAPI.getSearchesByCategory(BigInteger.ONE, "true");
        categoryAPI.getSearchesByCategory(BigInteger.ONE, null);
    }

    @Test
    public void testGetSearchesByCategoryEMAnalyticsFwkException() throws EMAnalyticsFwkException, JSONException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                CategoryManager.getInstance();
                result = categoryManager;
                searchManager.getSystemSearchListByCategoryId((BigInteger) any);
                result =  new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getSearchesByCategory(BigInteger.ONE, "true");
        categoryAPI.getSearchesByCategory(BigInteger.ONE, null);
    }

    @Test
    public void testGetSearchesByCategoryEMAnalyticsFwkException2() throws EMAnalyticsFwkException, JSONException {
        final List<Search> searches = new ArrayList<>();
        searches.add(search);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                CategoryManager.getInstance();
                result = categoryManager;
                searchManager.getSystemSearchListByCategoryId((BigInteger) any);
                result = searches;
                searchManager.getSearchListByCategoryId((BigInteger) any);
                result = searches;
                uriInfo.getBaseUri();
                result =  new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getSearchesByCategory(BigInteger.ONE, "true");
        categoryAPI.getSearchesByCategory(BigInteger.ONE, null);
    }

}
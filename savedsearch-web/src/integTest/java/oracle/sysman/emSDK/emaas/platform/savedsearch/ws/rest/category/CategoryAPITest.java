package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/22/2016.
 */
@Test(groups={"s2"})
public class CategoryAPITest {
    private CategoryAPI categoryAPI = new CategoryAPI();
    @BeforeMethod
	public void setUp() throws Exception {
		Deencapsulation.setField(categoryAPI, "uri", TestHelper.mockUriInfo());
	}
    
    @Test
    public void testGetCategory() throws Exception {

        new Expectations(){{
            new MockUp<CategoryManagerImpl>()
            {
                @Mock
                public Category getCategory(BigInteger categoryId) throws EMAnalyticsFwkException
                {
                    return new CategoryImpl();
                }
            };
            }
        };
        Assert.assertNotNull(categoryAPI.getCategory(new BigInteger("111")));
    }

    @Test    public void testGetCategoryByName() throws Exception {
        new Expectations(){{
            new MockUp<CategoryManagerImpl>()
            {
                @Mock
                public Category getCategory(String name) throws EMAnalyticsFwkException
                {
                    return new CategoryImpl();
                }
            };
        }
        };
        Assert.assertNotNull(categoryAPI.getCategoryByName("name"));
        Assert.assertNotNull(categoryAPI.getCategoryByName(null));
        Assert.assertNotNull(categoryAPI.getCategoryByName(""));

    }

    @Test
    public void testGetSearchesByCategory() throws Exception {
        Assert.assertNotNull(categoryAPI.getSearchesByCategory(null,""));
        Assert.assertNotNull(categoryAPI.getSearchesByCategory(BigInteger.ZERO,""));
    }
    @Test
    public void testGetSearchesByCategory2nd() throws Exception {
        final List<Search> searches= new ArrayList<Search>();
        searches.add(new SearchImpl());
        new Expectations(){
            {
                new MockUp<CategoryManagerImpl>(){
                    @Mock
                    public Category getCategory(BigInteger categoryId) throws EMAnalyticsFwkException
                    {
                        return  new CategoryImpl();
                    }
                };

                new MockUp<SearchManagerImpl>(){
                    @Mock
                    public List<Search> getSystemSearchListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
                    {
                        return  searches;
                    }
                    @Mock
                    public List<Search> getSearchListByCategoryId(BigInteger categoryId) throws EMAnalyticsFwkException
                    {
                        return  searches;
                    }
                };
            }
        };
        Assert.assertNotNull(categoryAPI.getSearchesByCategory(new BigInteger("1111"),""));
        Assert.assertNotNull(categoryAPI.getSearchesByCategory(new BigInteger("1111"),null));

    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/22/2016.
 */
@Test(groups={"s2"})
public class CategoryAPITest {
    private CategoryAPI categoryAPI = new CategoryAPI();
    @BeforeMethod
    public void setUp() throws Exception {
          UriInfo uri = new UriInfo() {
            @Override
            public String getPath() {
                return null;
            }

            @Override
            public String getPath(boolean b) {
                return null;
            }

            @Override
            public List<PathSegment> getPathSegments() {
                return null;
            }

            @Override
            public List<PathSegment> getPathSegments(boolean b) {
                return null;
            }

            @Override
            public URI getRequestUri() {
                return null;
            }

            @Override
            public UriBuilder getRequestUriBuilder() {
                return null;
            }

            @Override
            public URI getAbsolutePath() {
                return null;
            }

            @Override
            public UriBuilder getAbsolutePathBuilder() {
                return null;
            }

            @Override
            public URI getBaseUri() {
                return null;
            }

            @Override
            public UriBuilder getBaseUriBuilder() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getPathParameters() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getPathParameters(boolean b) {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getQueryParameters() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getQueryParameters(boolean b) {
                return null;
            }

            @Override
            public List<String> getMatchedURIs() {
                return null;
            }

            @Override
            public List<String> getMatchedURIs(boolean b) {
                return null;
            }

            @Override
            public List<Object> getMatchedResources() {
                return null;
            }

            @Override
            public URI resolve(URI uri) {
                return null;
            }

            @Override
            public URI relativize(URI uri) {
                return null;
            }
        };
        Deencapsulation.setField(categoryAPI, "uri", uri);
        }
    @Test
    public void testGetCategory() throws Exception {

        new Expectations(){{
            new MockUp<CategoryManagerImpl>()
            {
                @Mock
                public Category getCategory(long categoryId) throws EMAnalyticsFwkException
                {
                    return new CategoryImpl();
                }
            };
            }
        };
        Assert.assertNotNull(categoryAPI.getCategory(111));
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
        Assert.assertNotNull(categoryAPI.getSearchesByCategory("",""));
        Assert.assertNotNull(categoryAPI.getSearchesByCategory(null,""));
        Assert.assertNotNull(categoryAPI.getSearchesByCategory("11111s",""));
        Assert.assertNotNull(categoryAPI.getSearchesByCategory("0",""));
    }
    @Test
    public void testGetSearchesByCategory2nd() throws Exception {
        final List<Search> searches= new ArrayList<Search>();
        searches.add(new SearchImpl());
        new Expectations(){
            {
                new MockUp<CategoryManagerImpl>(){
                    @Mock
                    public Category getCategory(long categoryId) throws EMAnalyticsFwkException
                    {
                        return  new CategoryImpl();
                    }
                };

                new MockUp<SearchManagerImpl>(){
                    @Mock
                    public List<Search> getSystemSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
                    {
                        return  searches;
                    }
                    @Mock
                    public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
                    {
                        return  searches;
                    }
                };
            }
        };
        Assert.assertNotNull(categoryAPI.getSearchesByCategory("1111",""));
        Assert.assertNotNull(categoryAPI.getSearchesByCategory("1111",null));

    }
}
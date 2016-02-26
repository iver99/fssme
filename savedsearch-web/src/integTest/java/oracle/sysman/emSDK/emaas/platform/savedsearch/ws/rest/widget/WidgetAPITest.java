package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TenantSubscriptionUtil;
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
 * Created by xidai on 2/26/2016.
 */
@Test(groups = {"s2"})
public class WidgetAPITest {
    private WidgetAPI widgetAPI = new WidgetAPI();
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

                URI uril = null;
                try{
                    uril = new URI("");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return uril;
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
        Deencapsulation.setField(widgetAPI, "uri", uri);
    }

    @Test
    public void testGetAllWidgets() throws Exception {
        String userTenant = "OAM_REMOTE_USER.userName";
        String widgetGroupId = "10";
        boolean includeDashboardIneligible = true;
       final List<Category> list = new ArrayList<Category>();
        for(int i = 0;i<3;i++){
            CategoryImpl category =new CategoryImpl();
            category.setId(10);
            list.add(new CategoryImpl());
        }

        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> getAllCategories() throws EMAnalyticsFwkException
            {
                return list;
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "widgetGroupId=11&includeDashboardIneligible=true";
            }
        };
        widgetAPI.getAllWidgets(widgetAPI.uri,userTenant,widgetGroupId,includeDashboardIneligible);
    }

    @Test
    public void testGetAllWidgets2nd(@Mocked final TenantSubscriptionUtil anyTSU) throws Exception {
        String userTenant = "OAM_REMOTE_USER.userName";
        String widgetGroupId = "10";
        boolean includeDashboardIneligible = false;
        final List<Category> list = new ArrayList<Category>();
        for(int i = 0;i<3;i++){
            CategoryImpl category =new CategoryImpl();
            category.setId(10);
            list.add(category);
        }
        final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setName("DASHBOARD_INELIGIBLE");
        searchParameter.setValue("1");
        for(int i = 0;i<3;i++){
            parameters.add(searchParameter);
        }
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<3;i++){
            SearchImpl search =new SearchImpl();
             search.setParameters(parameters);
            searches.add(search);
        }
        new Expectations(){
            {
                TenantSubscriptionUtil.getTenantSubscribedCategories(anyString,anyBoolean);
                result = list;
            }
        };
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> getAllCategories() throws EMAnalyticsFwkException
            {
                return list;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getWidgetListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "widgetGroupId=10&includeDashboardIneligible=true";
            }
        };
        widgetAPI.getAllWidgets(widgetAPI.uri,userTenant,widgetGroupId,includeDashboardIneligible);
    }

    @Test
    public void testGetAllWidgets3th(@Mocked final TenantSubscriptionUtil anyTSU) throws Exception {
        String userTenant = "OAM_REMOTE_USER.userName";
        String widgetGroupId = "10";
        boolean includeDashboardIneligible = false;
        final List<Category> list = new ArrayList<Category>();
        for(int i = 0;i<3;i++){
            CategoryImpl category =new CategoryImpl();
            category.setId(10);
            list.add(category);
        }
        final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setName("DASHBOARD_INELIGIBLE");
        searchParameter.setValue("1");
        for(int i = 0;i<3;i++){
            parameters.add(searchParameter);
        }
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<3;i++){
            SearchImpl search =new SearchImpl();
            search.setParameters(parameters);
            searches.add(search);
        }
        new Expectations(){
            {
                TenantSubscriptionUtil.getTenantSubscribedCategories(anyString,anyBoolean);
                result = list;
            }
        };        ;
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> getAllCategories() throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return list;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getWidgetListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return searches;
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {

                return "widgetGroupId=10&includeDashboardIneligible=true";
            }
        };
        widgetAPI.getAllWidgets(widgetAPI.uri,userTenant,widgetGroupId,includeDashboardIneligible);
    }
    @Test
    public void testGetAllWidgets4th(@Mocked final TenantSubscriptionUtil anyTSU) throws Exception {
        String userTenant = "OAM_REMOTE_USER.userName";
        String widgetGroupId = "10";
        boolean includeDashboardIneligible = false;
        final List<Category> list = new ArrayList<Category>();
        for(int i = 0;i<3;i++){
            CategoryImpl category =new CategoryImpl();
            category.setId(10);
            list.add(category);
        }
        final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setName("DASHBOARD_INELIGIBLE");
        searchParameter.setValue("1");
        for(int i = 0;i<3;i++){
            parameters.add(searchParameter);
        }
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<3;i++){
            SearchImpl search =new SearchImpl();
            search.setParameters(parameters);
            searches.add(search);
        }
        new Expectations(){
            {
                TenantSubscriptionUtil.getTenantSubscribedCategories(anyString,anyBoolean);
                result = list;
            }
        };        ;
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> getAllCategories() throws EMAnalyticsFwkException,Exception
            {
                if(true){throw new Exception();}
                return list;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getWidgetListByCategoryId(long categoryId) throws EMAnalyticsFwkException,Exception
            {
                if(true){throw new Exception();}
                return searches;
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {

                return "widgetGroupId=10&includeDashboardIneligible=true";
            }
        };
        widgetAPI.getAllWidgets(widgetAPI.uri,userTenant,widgetGroupId,includeDashboardIneligible);
    }

    @Test
    public void testGetWidgetScreenshotById() throws Exception {
        new MockUp<SearchManagerImpl>(){
            @Mock
            public String getWidgetScreenshotById(long widgetId) throws EMAnalyticsFwkException
            {
                return "10";
            }
        };
        widgetAPI.getWidgetScreenshotById(10L);
    }

    @Test
    public void testGetWidgetScreenshotById2nd() throws Exception {
        new MockUp<SearchManagerImpl>(){
            @Mock
            public String getWidgetScreenshotById(long widgetId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return "10";
            }
        };
        widgetAPI.getWidgetScreenshotById(10L);
    }
    @Test
    public void testGetWidgetScreenshotById3th() throws Exception {
        new MockUp<SearchManagerImpl>(){
            @Mock
            public String getWidgetScreenshotById(long widgetId) throws EMAnalyticsFwkException,Exception
            {
                if(true){throw new Exception("");}
                return "10";
            }
        };
        widgetAPI.getWidgetScreenshotById(10L);
    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import mockit.Mock;
import mockit.MockUp;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s1"})
public class SearchAPITest {
    private SearchAPI api = new SearchAPI();
    @BeforeMethod
    public void setUp() throws Exception {

        UriInfo uri = new UriInfo() {
            @Override
            public String getPath() {
                return null;
            }

            @Override
            public String getPath(boolean decode) {
                return null;
            }

            @Override
            public List<PathSegment> getPathSegments() {
                return null;
            }

            @Override
            public List<PathSegment> getPathSegments(boolean decode) {
                return null;
            }

            @Override
            public URI getRequestUri(){
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
            public MultivaluedMap<String, String> getPathParameters(boolean decode) {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getQueryParameters() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
                return null;
            }

            @Override
            public List<String> getMatchedURIs() {
                return null;
            }

            @Override
            public List<String> getMatchedURIs(boolean decode) {
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

        api.uri = uri;

    }
    @Test
    public void testCreateSearch() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch2nd() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                if(true){
                    throw new EMAnalyticsWSException(new Throwable());
                }
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch3th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch5th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name"," ");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch6th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","<> ");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch4th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name"," ");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch7th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch8th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());

        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch9th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch10th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");

        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch11th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");

        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");

        inputJson.put("folder",folder);
        inputJson.put("type","SOMthing");
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch12th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");

        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder","folder");
        inputJson.put("parameters",parameter);
        new MockUp<SearchManagerImpl>(){
            @Mock
            public Search saveSearch(Search search) throws EMAnalyticsFwkException,EMAnalyticsWSException
            {
                return new SearchImpl();
            }

        };
        api.createSearch(inputJson);
    }
    @Test
    public void testDeleteSearch() throws Exception {
        new MockUp<SearchManagerImpl>(){
            @Mock
            public void deleteSearch(long searchId, boolean permanently) throws EMAnalyticsFwkException
            {

            }
        };
        Assert.assertNotNull(api.deleteSearch(100L));

    }
    @Test
    public void testDeleteSearch2nd() throws Exception {
        new MockUp<SearchManagerImpl>(){
            @Mock
            public void deleteSearch(long searchId, boolean permanently) throws EMAnalyticsFwkException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
            }
        };
        Assert.assertNotNull(api.deleteSearch(100L));

    }
    @Test
    public void testEditSearch() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new MockUp<SearchManagerImpl>() {
            @Mock
            private Search getSearch(long searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException {
             return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }

        };
        Assert.assertNotNull(api.editSearch(inputJson,"",10L));
    }
    @Test
    public void testEditSearch2nd() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new MockUp<SearchManagerImpl>() {
            @Mock
            private Search getSearch(long searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException {
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }

        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",10L));
    }

    @Test
    public void testEditSearch3th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new MockUp<SearchManagerImpl>() {
            @Mock
            private Search getSearch(long searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }

        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",10L));
    }
    @Test
    public void testEditSearch4th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new MockUp<SearchManagerImpl>() {
            @Mock
            private Search getSearch(long searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException ,EMAnalyticsWSException{
                if(true){
                    throw new EMAnalyticsWSException(new Throwable());
                }
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }

        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",10L));
    }
    @Test
    public void testEditSearch5th() throws Exception {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type"," ");
        p1.put("value","ALL");
        p2.put("name"," ");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new MockUp<SearchManagerImpl>() {
            @Mock
            private Search getSearch(long searchId, boolean loadWidgetOnly) throws EMAnalyticsFwkException ,EMAnalyticsWSException{

                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }

        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",10L));
    }


    @Test
    public void testEditSearchAccessDate() throws Exception {
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
            {
                return new Date();
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryId=11";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(10L,true));
    }
    @Test
    public void testEditSearchAccessDate2nd() throws Exception {
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new Date();
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryId=11";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(10L,true));
    }
    @Test
    public void testEditSearchAccessDate3th() throws Exception {
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new Date();
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return null;
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(10L,true));
    }
    @Test
    public void testEditSearchAccessDate4th() throws Exception {
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new Date();
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "searchId = 1";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(10L,false));
    }
    @Test
    public void testEditSearchAccessDate5th() throws Exception {
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Search editSearch(Search search) throws EMAnalyticsFwkException
            {
                return new SearchImpl();
            }
            @Mock
            public Date modifyLastAccessDate(long searchId) throws EMAnalyticsFwkException
            {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new Date();
            }
        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "searchId";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(10L,true));
    }
    @Test
    public void testGetSearch() throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path","path","path"};
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException {
                return new SearchImpl();
            }
        };
        new MockUp<FolderManagerImpl>(){

            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                return folder;
            }
            @Mock
            public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException
            {
                return path;
            }
        };
        new MockUp<SearchSummaryImpl>(){
            @Mock
            public Integer getFolderId(){
                return 1;
            }

        };

        Assert.assertNotNull(api.getSearch(10L,true));
    }
    @Test
    public void testGetSearch2nd() throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path","path","path"};
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<SearchManagerImpl>() {
            @Mock
            public Search getSearch(long searchId) throws EMAnalyticsFwkException {
                if(true){
                    throw new EMAnalyticsFwkException(new Throwable());
                }
                return new SearchImpl();
            }
        };
        new MockUp<FolderManagerImpl>(){

            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                return folder;
            }
            @Mock
            public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException
            {
                return path;

            }
        };
        new MockUp<SearchSummaryImpl>(){
            @Mock
            public Integer getFolderId(){
                return 1;
            }

        };

        Assert.assertNotNull(api.getSearch(10L,true));
    }
    @Test
    public void testUpdateLastAccessTime() throws Exception {

    }
}
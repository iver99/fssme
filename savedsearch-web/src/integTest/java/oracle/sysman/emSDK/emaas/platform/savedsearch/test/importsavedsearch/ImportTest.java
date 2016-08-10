package oracle.sysman.emSDK.emaas.platform.savedsearch.test.importsavedsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class ImportTest {
    static String HOSTNAME;
    static String portno;
    static String serveruri;
    static String authToken;
    static String TENANT_ID_OPC1;
    static String TENANT_ID1;

    private static final String FOLDER_XML = "Folder.xml";
    private static final String CATEGORY_XML = "Category.xml";
    private static final String SEARCH_XML = "Search.xml";

//	@AfterClass
//	public static void afterTest()
//	{
//
//	}

    public static URL getResource(String resourceName, Class callingClass) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null && resourceName.startsWith("/")) {
            //certain classloaders need it without the leading /
            url = Thread.currentThread().getContextClassLoader().getResource(resourceName.substring(1));
        }

        ClassLoader cluClassloader = ImportTest.class.getClassLoader();
        if (cluClassloader == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if (url == null) {
            url = cluClassloader.getResource(resourceName);
        }
        if (url == null && resourceName.startsWith("/")) {
            //certain classloaders need it without the leading /
            url = cluClassloader.getResource(resourceName.substring(1));
        }

        if (url == null) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                url = cl.getResource(resourceName);
            }
        }

        if (url == null) {
            url = callingClass.getResource(resourceName);
        }

        if (url == null && resourceName != null && resourceName.charAt(0) != '/') {
            return ImportTest.getResource('/' + resourceName, callingClass);
        }

        return url;
    }

    public static InputStream getResourceAsStream(String resourceName, Class callingClass) throws IOException {
        URL url = ImportTest.getResource(resourceName, callingClass);

        return url != null ? url.openStream() : null;

    }

    public static List<URL> getResources(String resourceName, Class callingClass) throws IOException {
        List<URL> ret = new ArrayList<URL>();
        Enumeration<URL> urls = new Enumeration<URL>() {
            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public URL nextElement() {
                return null;
            }

        };
        urls = Thread.currentThread().getContextClassLoader().getResources(resourceName);
        if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
            //certain classloaders need it without the leading /
            urls = Thread.currentThread().getContextClassLoader().getResources(resourceName.substring(1));
        }

        ClassLoader cluClassloader = ImportTest.class.getClassLoader();
        if (cluClassloader == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if (!urls.hasMoreElements()) {
            urls = cluClassloader.getResources(resourceName);
        }
        if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
            //certain classloaders need it without the leading /
            urls = cluClassloader.getResources(resourceName.substring(1));
        }

        if (!urls.hasMoreElements()) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                urls = cl.getResources(resourceName);
            }
        }

        if (!urls.hasMoreElements()) {
            URL url = callingClass.getResource(resourceName);
            if (url != null) {
                ret.add(url);
            }
        }
        while (urls.hasMoreElements()) {
            ret.add(urls.nextElement());
        }

        if (ret.isEmpty() && resourceName != null && resourceName.charAt(0) != '/') {
            return ImportTest.getResources('/' + resourceName, callingClass);
        }
        return ret;
    }

    @BeforeClass
    public static void setUp() {
        CommonTest ct = new CommonTest();
        HOSTNAME = ct.getHOSTNAME();
        portno = ct.getPortno();
        serveruri = ct.getServeruri();
        authToken = ct.getAuthToken();
        TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
        TENANT_ID_OPC1 = ct.getTenant();
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        if (br != null) {
            br.close();

        }
        return sb.toString();

    }

    @Test
    /**
     * Import Categories
     */
    public void importCategories() throws Exception {

        InputStream stream = ImportTest.getResourceAsStream(CATEGORY_XML, ImportTest.class);

        String jsonString1 = ImportTest.getStringFromInputStream(stream);
        Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/importcategories");
        Assert.assertEquals(res1.getStatusCode(), 200);
        JSONArray arrfld = new JSONArray(res1.getBody().asString());
        for (int index = 0; index < arrfld.length(); index++) {
            JSONObject jsonObj = arrfld.getJSONObject(index);
            Assert.assertTrue(verifyCategory(jsonObj.getInt("id"), jsonObj.getString("name")) == true);
            Assert.assertTrue(jsonObj.getInt("id") > 1);
        }
    }

    @Test
    /**
     * import categories with invalid format
     */
    public void importCategories_invalidformat() {
        Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body("").when().post("/importcategories");
        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(res.asString(), "Please specify input with valid format");
    }

    @Test
    /**
     * import folders with invalid format
     */
    public void importFolder_invalidformat() {
        Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body("").when().post("/importfolders");
        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(res.asString(), "Please specify input with valid format");
    }

    @Test
    /**
     * Import folders
     */
    public void importFolders() throws IOException, JSONException {
        InputStream stream = ImportTest.getResourceAsStream(FOLDER_XML, ImportTest.class);
        String jsonString1 = ImportTest.getStringFromInputStream(stream);
        Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/importfolders");
        Assert.assertEquals(res1.getStatusCode(), 200);
        JSONArray arrfld = new JSONArray(res1.getBody().asString());
        for (int index = 0; index < arrfld.length(); index++) {
            JSONObject jsonObj = arrfld.getJSONObject(index);
            Assert.assertTrue(verifyFolder(jsonObj.getInt("id"), jsonObj.getString("name")) == true);
            Assert.assertTrue(deleteFolder(jsonObj.getInt("id")) == true);
        }
    }

    @Test
    /**
     * Import Searches
     */
    public void importSearches() throws Exception {
        InputStream stream = ImportTest.getResourceAsStream(SEARCH_XML, ImportTest.class);
        String jsonString1 = ImportTest.getStringFromInputStream(stream);
        Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/importsearches");

        Set<Integer> folderList1 = new HashSet<Integer>();
        Set<Integer> searchList1 = new HashSet<Integer>();
        JSONArray arrfld = new JSONArray(res1.getBody().asString());
        for (int index = 0; index < arrfld.length(); index++) {
            JSONObject jsonObj = arrfld.getJSONObject(index);
            Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/" + jsonObj.getInt("id"));
            JsonPath jp = res.jsonPath();

            if (res.getStatusCode() == 200) {
                Assert.assertTrue(deleteSearch(jsonObj.getInt("id")) == true);

                searchList1.add(jsonObj.getInt("id"));
                if ((int) jp.getMap("folder").get("id") > 1) {

                    if (!folderList1.contains((int) jp.getMap("folder").get("id"))) {
                        folderList1.add((int) jp.getMap("folder").get("id"));
                    }
                }
            }
        }

        Set<Integer> folderList = new HashSet<Integer>();
        Set<Integer> slist = new HashSet<Integer>();
        res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(getSearchDef(-1, 1)).when().post("/importsearches");
        arrfld = new JSONArray(res1.getBody().asString());
        for (int index = 0; index < arrfld.length(); index++) {
            JSONObject jsonObj = arrfld.getJSONObject(index);

            res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/" + jsonObj.getInt("id"));
            JsonPath jp = res1.jsonPath();
            if ((int) jp.getMap("folder").get("id") > 1) {
                folderList.add((int) jp.getMap("folder").get("id"));
            }
            slist.add(jsonObj.getInt("id"));
            res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).body(getSearchDef(jsonObj.getInt("id"), 1)).when()
                    .post("/importsearches");
            arrfld = new JSONArray(res1.getBody().asString());
            jsonObj = arrfld.getJSONObject(index);
            res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/" + jsonObj.getInt("id"));
            jp = res1.jsonPath();
            if ((int) jp.getMap("folder").get("id") > 1) {
                folderList.add((int) jp.getMap("folder").get("id"));
            }
        }

        res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(getSearchDef(-1, 2)).when().post("/importsearches");
        arrfld = new JSONArray(res1.getBody().asString());
        for (int index = 0; index < arrfld.length(); index++) {
            JSONObject jsonObj = arrfld.getJSONObject(index);

            res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/" + jsonObj.getInt("id"));
            JsonPath jp = res1.jsonPath();
            if ((int) jp.getMap("folder").get("id") > 1) {
                folderList.add((int) jp.getMap("folder").get("id"));
            }

            res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).body(getSearchDef(jsonObj.getInt("id"), 2)).when()
                    .post("/importsearches");
            arrfld = new JSONArray(res1.getBody().asString());
            jsonObj = arrfld.getJSONObject(index);
            res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/" + jsonObj.getInt("id"));
            jp = res1.jsonPath();
            if ((int) jp.getMap("folder").get("id") > 1) {
                folderList.add((int) jp.getMap("folder").get("id"));
            }
        }

        for (Integer tmp : slist) {
            if (tmp > 1) {
                Assert.assertTrue(deleteSearch(tmp) == true);
            }
        }
        for (Integer tmp : folderList) {
            if (tmp > 1) {
                Assert.assertTrue(deleteFolder(tmp) == true);
            }
        }
    }

    @Test
    /**
     * import searches with invalid format
     */
    public void importSearches_invalidformat() {
        Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body("").when().post("/importsearches");
        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(res.asString(), "Please specify input with valid format");
    }

    @Test
    public void testMultipleSearch() {

    }

    private boolean deleteFolder(int myfolderID) {
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + myfolderID);
        return res1.getStatusCode() == 204;

    }

    private boolean deleteSearch(int mySearchId) {
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/search/" + mySearchId);
        return res1.getStatusCode() == 204;
    }

    private String getSearchDef(long id, long option) {

        String xml = "";
        if (option == 1) {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
            xml = xml + "<SearchSet>";
            xml = xml + "<Search>";
            if (id != -1) {
                xml = xml + "<Id>" + id + "</Id>";
            }
            xml = xml + "     <Name>Test_Multiplewe</Name> ";
            xml = xml + "     <SearchParameters>";
            xml = xml + "      <SearchParameter>";
            xml = xml + "<Name>Param1</Name> ";
            xml = xml + "<Type>STRING</Type>";

            xml = xml + "       </SearchParameter>";
            xml = xml + "         <SearchParameter>";
            xml = xml + "      			<Name>Param2</Name> ";
            xml = xml + "                <Type>CLOB</Type>";

            xml = xml + "       </SearchParameter>";
            xml = xml + "     </SearchParameters>          ";
            xml = xml + "     <Category>";
            xml = xml + "       <Name>Cat12_1</Name>";
            xml = xml + "       <ProviderName>MySearchProvider</ProviderName>";
            xml = xml + "       <ProviderVersion>1.0</ProviderVersion>";
            xml = xml + "       <ProviderAssetRoot>assetRoot</ProviderAssetRoot>";
            xml = xml + "     </Category>     ";
            xml = xml + "     <Folder>";
            xml = xml + "      <Name>fol123</Name>";
            xml = xml + "      <UiHidden>false</UiHidden>";
            xml = xml + "    </Folder>    ";
            xml = xml + "  </Search>    ";
            xml = xml + "</SearchSet>  ";
        }

        if (option == 2) {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
            xml = xml + "<SearchSet>";
            xml = xml + "<Search>";
            if (id != -1) {
                xml = xml + "<Id>" + id + "</Id>";
            }
            xml = xml + "     <Name>Test_Multiplewe</Name> ";
            xml = xml + "     <SearchParameters>";
            xml = xml + "      <SearchParameter>";
            xml = xml + "<Name>Param1</Name> ";
            xml = xml + "<Type>STRING</Type>";

            xml = xml + "       </SearchParameter>";
            xml = xml + "         <SearchParameter>";
            xml = xml + "      			<Name>Param2</Name> ";
            xml = xml + "                <Type>CLOB</Type>";

            xml = xml + "       </SearchParameter>";
            xml = xml + "     </SearchParameters>          ";
            xml = xml + "     <CategoryId>1</CategoryId>";
            xml = xml + "     <FolderId>1</FolderId>";
            xml = xml + "  </Search>    ";
            xml = xml + "</SearchSet>  ";
        }

        return xml;

    }

    private Boolean verifyCategory(int mycatID, String mycatName) {
        Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + mycatID);
        JsonPath jp = res1.jsonPath();
        if (res1.getStatusCode() != 200) {
            return false;
        }
        if (!jp.get("name").equals(mycatName)) {
            return false;
        }
        if (jp.get("href").equals(serveruri + "/category/" + mycatID)) {
            return false;
        }
        if (jp.get("createdOn") == null || "".equals(jp.get("createdOn"))) {
            return false;
        }
        return true;

    }

    private boolean verifyFolder(int myfolderID, String myfolderName) {
        Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/folder/" + myfolderID);
        JsonPath jp = res1.jsonPath();
        if (res1.getStatusCode() != 200) {
            return false;
        }
        if (!jp.get("name").equals(myfolderName)) {
            return false;
        }
        if (jp.get("href").equals(serveruri + "/folder/" + myfolderID)) {
            return false;
        }
        if (!jp.get("createdOn").equals(jp.get("lastModifiedOn"))) {
            return false;
        }
        if (jp.get("systemFolder").equals(true)) {
            return false;
        }
        return true;
    }

    private boolean verifySearch(int mysearchID) {
        return true;
    }

}

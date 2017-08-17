package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder;

import java.math.BigInteger;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/22/2016.
 */
@Test(groups={"s2"})
public class FolderAPITest {
	private static final BigInteger TEST_ID = new BigInteger("1111");
    private FolderAPI folderAPI = new FolderAPI();
    @BeforeMethod
    public void setUp() {

        Deencapsulation.setField(folderAPI, "uri", TestHelper.mockUriInfo());
    }
    @Test
    public void testCreateFolder() throws JSONException {
        Assert.assertNotNull(folderAPI.createFolder(new JSONObject()));
    }

    @Mocked
    FolderManager folderManager;
    @Mocked
    Folder folder;
    @Mocked
    DependencyStatus dependencyStatus;
    @Test(groups={"s2"})
    public void testCreateFolder2nd() throws JSONException, EMAnalyticsFwkException {
        final JSONObject folderoj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        folderoj.put("name","name");
        folderoj.put("description","desctription");
        parentFolder.put("id", BigInteger.TEN);
        folderoj.put("parentFolder",parentFolder);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.saveFolder((Folder)any);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.createFolder(folderoj));
    }

    public void testCreateFolderEMAnalyticsFwkException() throws JSONException, EMAnalyticsFwkException {
        final JSONObject folderoj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        folderoj.put("name","name");
        folderoj.put("description","desctription");
        parentFolder.put("id", BigInteger.TEN);
        folderoj.put("parentFolder",parentFolder);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.saveFolder((Folder)any);
                result = new EMAnalyticsFwkException(throwable);
            }
        };

        Assert.assertNotNull(folderAPI.createFolder(folderoj));
    }

    @Test
    public void testDelete() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.deleteFolder((BigInteger) any, anyBoolean);
            }

        };
        Assert.assertNotNull(folderAPI.delete(TEST_ID));
    }
    @Mocked
    Throwable throwable;
    @Test
    public void testDelete2nd() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.deleteFolder((BigInteger) any, anyBoolean);
                result = new EMAnalyticsFwkException(1,throwable);
            }
        };
        Assert.assertNotNull(folderAPI.delete(TEST_ID));
    }
    @Test
    public void testEditFolder() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.put("name","name");
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.getFolder((BigInteger) any);
                result = new EMAnalyticsFwkException(2,throwable);
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,TEST_ID));

    }
    @Test
    public void testEditFolder2nd() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.put("name","name");
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.getFolder((BigInteger) any);
                result = new EMAnalyticsWSException(throwable);
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,TEST_ID));
    }
    @Test
    public void testEditFolder3th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        parentFolder.put("id", BigInteger.TEN);
        folderobj.put("name","name");
        folderobj.put("parentFolder",parentFolder);

        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder((BigInteger) any);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,TEST_ID));
    }
    @Test
    public void testEditFolder4th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.put("name","");
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,TEST_ID));
    }
    @Test
    public void testEditFolder5th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name",null);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder((BigInteger) any);
                result = folder;
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,TEST_ID));
    }
    @Test
    public void testEditFolder6th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        parentFolder.put("id", BigInteger.ZERO);
        folderobj.put("name","name");
        folderobj.put("parentFolder",parentFolder);

        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder((BigInteger) any);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,TEST_ID));
    }
    @Test
    public void testGetFolder() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.getFolder((BigInteger) any);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.getFolder(new BigInteger("1111")));
    }
    
    @Test
    public void testGetFolderByName1() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.getFoldersByName(anyString, (BigInteger)any);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.getFolderByName("name",new BigInteger("1111")));
    }
    
    @Test
    public void testGetFolderByName2() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.getFoldersByName(anyString, (BigInteger)any);
                result = null;
            }
        };
        folderAPI.getFolderByName("name",new BigInteger("1111"));
    }
    
    
    @Test
    public void testGetFolder2nd() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                folderManager.getFolder((BigInteger) any);
                result =  new EMAnalyticsFwkException(1, throwable);
            }

        };
        Assert.assertNotNull(folderAPI.getFolder(TEST_ID));
    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

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
    private FolderAPI folderAPI = new FolderAPI();
    @BeforeMethod
    public void setUp(){
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
    @Test(groups={"s2"})
    public void testCreateFolder2nd() throws JSONException, EMAnalyticsFwkException {
        final JSONObject folderoj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        folderoj.append("name","name");
        folderoj.append("description","desctription");
        parentFolder.append("id",10);
        folderoj.put("parentFolder",parentFolder);
        new Expectations(){
            {
                folderManager.saveFolder((Folder)any);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.createFolder(folderoj));
    }

    @Test
    public void testDelete() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folderManager.deleteFolder(anyLong, anyBoolean);
            }

        };
        Assert.assertNotNull(folderAPI.delete(1111));
    }
    @Mocked
    Throwable throwable;
    @Test
    public void testDelete2nd() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folderManager.deleteFolder(anyLong, anyBoolean);
                result = new EMAnalyticsFwkException(1,throwable);
            }
        };
        Assert.assertNotNull(folderAPI.delete(1111));
    }
    @Test
    public void testEditFolder() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name","name");
        new Expectations(){
            {
                folderManager.getFolder(anyLong);
                result = new EMAnalyticsFwkException(2,throwable);
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));

    }
    @Test
    public void testEditFolder2nd() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name","name");
        new Expectations(){
            {
                folderManager.getFolder(anyLong);
                result = new EMAnalyticsWSException(throwable);
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder3th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        parentFolder.append("id",10);
        folderobj.append("name","name");
        folderobj.put("parentFolder",parentFolder);

        new Expectations(){
            {
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder(anyLong);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder4th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name","");
        new Expectations(){
            {
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder(anyLong);
                result = folder;
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder5th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name",null);
        new Expectations(){
            {
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder(anyLong);
                result = folder;
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder6th() throws JSONException, EMAnalyticsFwkException {
        JSONObject folderobj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        parentFolder.append("id",0);
        folderobj.append("name","name");
        folderobj.put("parentFolder",parentFolder);

        new Expectations(){
            {
                folderManager.updateFolder((Folder) any);
                result = folder;
                folderManager.getFolder(anyLong);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testGetFolder() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folderManager.getFolder(anyLong);
                result = folder;
            }
        };
        Assert.assertNotNull(folderAPI.getFolder(1111));
    }
    @Test
    public void testGetFolder2nd() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folderManager.getFolder(anyLong);
                result =  new EMAnalyticsFwkException(1, throwable);
            }

        };
        Assert.assertNotNull(folderAPI.getFolder(1111));
    }
}
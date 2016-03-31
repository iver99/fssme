package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

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
    public void setUp() throws Exception {
        Deencapsulation.setField(folderAPI, "uri", TestHelper.mockUriInfo());
    }
    @Test
    public void testCreateFolder() throws Exception {
        Assert.assertNotNull(folderAPI.createFolder(new JSONObject()));
    }

    @Test(groups={"s2"})
    public void testCreateFolder2nd() throws Exception {
        final JSONObject folderoj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        folderoj.append("name","name");
        folderoj.append("description","desctription");
        parentFolder.append("id",10);
        folderoj.put("parentFolder",parentFolder);
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder saveFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.createFolder(folderoj));
    }

    @Test
    public void testDelete() throws Exception {
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public void deleteFolder(long folderId, boolean permanently) throws EMAnalyticsFwkException
                    {
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.delete(1111));
    }

    @Test
    public void testDelete2nd() throws Exception {
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public void deleteFolder(long folderId, boolean permanently) throws EMAnalyticsFwkException
                    {
                        throw new EMAnalyticsFwkException(1, new Throwable());
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.delete(1111));
    }
    @Test
    public void testEditFolder() throws Exception {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name","name");
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {
                        if(true)
                        throw new EMAnalyticsFwkException(2,new Throwable());

                            return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));

    }
    @Test
    public void testEditFolder2nd() throws Exception {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name","name");
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException,EMAnalyticsWSException
                    {
                        if(true)
                            throw new EMAnalyticsWSException(new Throwable());

                        return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder3th() throws Exception {
        JSONObject folderobj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        parentFolder.append("id",10);
        folderobj.append("name","name");
        folderobj.put("parentFolder",parentFolder);

        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {

                        return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder4th() throws Exception {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name","");
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {

                        return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder5th() throws Exception {
        JSONObject folderobj = new JSONObject();
        folderobj.append("name",null);
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {

                        return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testEditFolder6th() throws Exception {
        JSONObject folderobj = new JSONObject();
        JSONObject parentFolder = new JSONObject();
        parentFolder.append("id",0);
        folderobj.append("name","name");
        folderobj.put("parentFolder",parentFolder);
        System.out.print(folderobj.optJSONObject("parentFolder"));

        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {

                        return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.editFolder(folderobj,111111));
    }
    @Test
    public void testGetFolder() throws Exception {
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder updateFolder(Folder folder) throws EMAnalyticsFwkException
                    {
                        return folder;
                    }
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {

                        return new FolderImpl();
                    }
                };
            }

            };
            Assert.assertNotNull(folderAPI.getFolder(1111));
    }
    @Test
    public void testGetFolder2nd() throws Exception {
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){

                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException
                    {   if(true)
                        throw new EMAnalyticsFwkException(1,new Throwable());
                        return new FolderImpl();
                    }
                };
            }

        };
        Assert.assertNotNull(folderAPI.getFolder(1111));
    }
}
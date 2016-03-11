package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups={"s2"})
public class ImportFolderSetTest {
    private  ImportFolderSet importFolderSet = new ImportFolderSet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";

    @Test
    public void testImportsFolders(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./folder.xsd");
        final  InputStream stream = url.openStream();
        final List<FolderDetails> folderDetailses = new ArrayList<FolderDetails>();
        FolderDetails folderDetails = new FolderDetails();
        folderDetails.setName("name");
        folderDetailses.add(folderDetails);
        final List<FolderImpl> folderlist = new ArrayList<FolderImpl>();
        FolderImpl folder = new FolderImpl();
        folder.setName("name");
        folder.setId(10);
        folderlist.add(folder);
        final FolderManagerImpl folderManager = FolderManagerImpl.getInstance();
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new FolderSet();
            }
        };
        new MockUp<FolderManagerImpl>(){
            @Mock
            public List<FolderImpl> saveMultipleFolders(List<FolderDetails> folders) throws Exception
            {
                return folderlist;
            }
        };
        new MockUp<FolderSet>(){
            @Mock
            public List<FolderDetails> getFolderSet()
            {
                return folderDetailses;
            }

        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));

    }
    @Test
    public void testImportsFolders2nd(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./folder.xsd");
        final  InputStream stream = url.openStream();
        final List<FolderDetails> folderDetailses = new ArrayList<FolderDetails>();
        FolderDetails folderDetails = new FolderDetails();
        folderDetails.setName("name");
        folderDetailses.add(folderDetails);
        final List<FolderImpl> folderlist = new ArrayList<FolderImpl>();
        FolderImpl folder = new FolderImpl();
        folder.setName("name");
        folder.setId(10);
        folderlist.add(folder);
        final FolderManagerImpl folderManager = FolderManagerImpl.getInstance();
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new FolderSet();
            }
        };
        new MockUp<FolderManagerImpl>(){
            @Mock
            public List<FolderImpl> saveMultipleFolders(List<FolderDetails> folders) throws Exception
            {
                if(true){throw new Exception();}
                return folderlist;
            }
        };
        new MockUp<FolderSet>(){
            @Mock
            public List<FolderDetails> getFolderSet()
            {
                return folderDetailses;
            }

        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));

    }

    @Test
    public void testImportsFolders3th(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./folder.xsd");
        final InputStream stream = url.openStream();
        final List<FolderDetails> folderDetailses = new ArrayList<FolderDetails>();
        FolderDetails folderDetails = new FolderDetails();
        folderDetails.setName("name");
        folderDetailses.add(folderDetails);
        final List<FolderImpl> folderlist = new ArrayList<FolderImpl>();
        FolderImpl folder = new FolderImpl();
        folder.setName("name");
        folder.setId(10);
        folderlist.add(folder);
        final FolderManagerImpl folderManager = FolderManagerImpl.getInstance();
        new Expectations() {
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil, "unmarshal", withAny(new StringReader(xml)), withAny(stream), withAny(JAXBContext.newInstance()));
                result = new FolderSet();
            }
        };
        new MockUp<FolderManagerImpl>() {
            @Mock
            public List<FolderImpl> saveMultipleFolders(List<FolderDetails> folders) throws Exception {
                if (true) {
                    throw new ImportException();
                }
                return folderlist;
            }
        };
        new MockUp<FolderSet>() {
            @Mock
            public List<FolderDetails> getFolderSet() {
                return folderDetailses;
            }

        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));
         }
    }

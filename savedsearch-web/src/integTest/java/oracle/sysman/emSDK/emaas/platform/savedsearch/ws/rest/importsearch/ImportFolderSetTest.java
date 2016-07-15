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
    @Mocked
    InputStream inputStream;
    @Mocked
    FolderSet folderSet;
    @Mocked
    FolderManagerImpl folderManagerImpl;
    @Mocked
    JAXBContext jaxbContext;
    @Test
    public void testImportsFolders(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        final List<FolderDetails> folderDetailses = new ArrayList<FolderDetails>();
        final FolderDetails folderDetails = new FolderDetails();
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
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                folderManagerImpl.saveMultipleFolders(folderDetailses);
                folderSet.getFolderSet();
                result = folderDetails;
            }
        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));

    }
    @Test
    public void testImportsFolders2nd(@Mocked final JAXBUtil anyJaxbutil, @Mocked final Exception exception) throws Exception {
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
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                folderManagerImpl.saveMultipleFolders(folderDetailses);
                folderSet.getFolderSet();
                result = folderDetailses;
            }
        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));

    }

    @Test
    public void testImportsFolders3th(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
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
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                folderManagerImpl.saveMultipleFolders(folderDetailses);
                folderSet.getFolderSet();
                result = folderDetailses;
            }
        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));
         }
    }


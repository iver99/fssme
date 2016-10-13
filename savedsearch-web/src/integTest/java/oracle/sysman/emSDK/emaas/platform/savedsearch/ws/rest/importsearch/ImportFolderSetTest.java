package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
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

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups={"s2"})
public class ImportFolderSetTest {
    private static final BigInteger TEST_ID_10 = BigInteger.TEN;
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
        folder.setId(TEST_ID_10);
        folderlist.add(folder);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                FolderManagerImpl.getInstance();
                result = folderManagerImpl;
                folderManagerImpl.saveMultipleFolders(folderDetailses);
                result = folderlist;
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
        folder.setId(TEST_ID_10);
        folderlist.add(folder);
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
        folder.setId(TEST_ID_10);
        folderlist.add(folder);
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


    @Test
    public void testImportsFoldersEmpty(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        final List<FolderDetails> folderDetailses = new ArrayList<FolderDetails>();
        final FolderDetails folderDetails = new FolderDetails();
        folderDetails.setName("name");
        folderDetailses.add(folderDetails);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                folderSet.getFolderSet();
                result = Collections.emptyList();
            }
        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));
    }

    @Mocked
    Throwable throwable;
    @Test
    public void testImportsFoldersImportException(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                folderSet.getFolderSet();
                result = new ImportException(throwable);
            }
        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));
    }

    @Test
    public void testImportsFolderstException(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = folderSet;
                folderSet.getFolderSet();
                result = new Exception(throwable);
            }
        };
        Assert.assertNotNull(importFolderSet.importsFolders(xml));
    }
}


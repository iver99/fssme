package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ParameterDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = {"s1"})
public class ImportCategoryImplTest {

    ImportCategoryImpl importCategory;

    @BeforeMethod
    public void setUp() throws Exception {
        importCategory = new ImportCategoryImpl();
    }

    @Test
    public void testGetCategoryDetails() throws Exception {
        importCategory.getCategoryDetails();

        ImportCategoryImpl.Parameters parameters = new ImportCategoryImpl.Parameters();
        parameters.getParameter().add(new ParameterDetails());
        parameters.getParameter().add(new ParameterDetails());
        importCategory.setParameters(parameters);
        importCategory.getCategoryDetails();
    }

    @Test
    public void testGetDescription() throws Exception {
        importCategory.setDescription("descriptionxx");
        Assert.assertEquals(importCategory.getDescription(),"descriptionxx");
    }

    @Test
    public void testGetFolderDet() throws Exception {
        importCategory.setName("namexx");
        importCategory.setDescription("desxx");
        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), ImportCategoryImpl.class,importCategory);
        importCategory.setFolderDet(jaxbElement);
        Assert.assertEquals(importCategory.getFolderDet(),jaxbElement);
    }

    @Test
    public void testGetFolderDetails() throws Exception {

        importCategory.setFolderDet(null);
        importCategory.getFolderDetails();

        FolderDetails folderDetails = new FolderDetails();
        folderDetails.setName("namexx");
        folderDetails.setDescription("desxx");
        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), FolderDetails.class,folderDetails);
        importCategory.setFolderDet(jaxbElement);
        importCategory.getFolderDetails();

//        folderDetails.setName("namexx");
//        folderDetails.setDescription("desxx");
//        JAXBElement<Integer> jaxbElement1 = new JAXBElement(new QName("eee","ee"), Integer.class,new Integer(2));
//        jaxbElement1.setValue(333);
//        importCategory.setFolderDet(jaxbElement);
//        importCategory.getFolderDetails();
    }

    @Test
    public void testGetId() throws Exception {
        Integer integer = new Integer(111);
        importCategory.setId(integer);
        Assert.assertEquals(importCategory.getId(),integer);
    }

    @Test
    public void testGetName() throws Exception {
        importCategory.setName("namexx");
        Assert.assertEquals(importCategory.getName(),"namexx");
    }

    @Test
    public void testGetParameters() throws Exception {
        ImportCategoryImpl.Parameters parameters = new ImportCategoryImpl.Parameters();
        importCategory.setParameters(parameters);
        Assert.assertEquals(importCategory.getParameters(),parameters);
    }

    @Test
    public void testGetProviderAssetRoot() throws Exception {
        importCategory.setProviderAssetRoot("providerAssetRootxx");
        Assert.assertEquals(importCategory.getProviderAssetRoot(),"providerAssetRootxx");
    }

    @Test
    public void testGetProviderDiscovery() throws Exception {
        importCategory.setProviderDiscovery("providerDiscoveryxx");
        Assert.assertEquals(importCategory.getProviderDiscovery(),"providerDiscoveryxx");
    }

    @Test
    public void testGetProviderName() throws Exception {
        importCategory.setProviderName("providerNamexx");
        Assert.assertEquals(importCategory.getProviderName(),"providerNamexx");
    }

    @Test
    public void testGetProviderVersion() throws Exception {
        importCategory.setProviderVersion("providerVersionxx");
        Assert.assertEquals(importCategory.getProviderVersion(),"providerVersionxx");
    }
}
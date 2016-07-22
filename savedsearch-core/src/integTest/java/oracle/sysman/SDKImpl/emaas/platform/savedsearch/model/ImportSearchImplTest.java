package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigInteger;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.CategoryDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.SearchParameterDetails;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = {"s1"})
public class ImportSearchImplTest {

    ImportSearchImpl importSearch;

    @BeforeMethod
    public void setUp() throws Exception {
        importSearch = new ImportSearchImpl();

    }

    @Test
    public void testGetCategoryDet() throws Exception {
        importSearch.setName("namexx");
        importSearch.setDescription("desxx");
        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), ImportSearchImpl.class,importSearch);
        importSearch.setCategoryDet(jaxbElement);
        Assert.assertEquals(importSearch.getCategoryDet(),jaxbElement);
    }

    @Test
    public void testGetCategoryDetails() throws Exception {
//        importSearch.getCategoryDetails();

//        importSearch.setName("namexx");
//        importSearch.setDescription("desxx");
//        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), ImportSearchImpl.class,importSearch);
//        importSearch.setCategoryDet(jaxbElement);
//        ImportSearchImpl.SearchParameters parameters = new ImportSearchImpl.SearchParameters();
//        parameters.getSearchParameter().add(new SearchParameterDetails());
//        parameters.getSearchParameter().add(new SearchParameterDetails());
//        importSearch.setSearchParameters(parameters);
//        importSearch.getCategoryDetails();
        importSearch.setName("namexx");
        importSearch.setDescription("desxx");
        CategoryDetails categoryDetails = new CategoryDetails();
        categoryDetails.setParameters(new CategoryDetails.Parameters());
        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), CategoryDetails.class,categoryDetails);
        importSearch.setCategoryDet(jaxbElement);
        importSearch.getCategoryDetails();

    }

    @Test
    public void testGetDescription() throws Exception {
        importSearch.setDescription("descriptionxx");
        Assert.assertEquals(importSearch.getDescription(),"descriptionxx");
    }

    @Test
    public void testGetFolderDet() throws Exception {
        importSearch.setName("namexx");
        importSearch.setDescription("desxx");
        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), ImportSearchImpl.class,importSearch);
        importSearch.setFolderDet(jaxbElement);
        Assert.assertEquals(importSearch.getFolderDet(),jaxbElement);
    }

    @Test
    public void testGetFolderDetails() throws Exception {
        FolderDetails folderDetails = new FolderDetails();
        folderDetails.setName("namexx");
        folderDetails.setDescription("desxx");
        JAXBElement<?> jaxbElement = new JAXBElement(new QName("eee","ee"), FolderDetails.class,folderDetails);
        importSearch.setFolderDet(jaxbElement);
        importSearch.getFolderDetails();
    }

    @Test
    public void testGetId() throws Exception {
        BigInteger integer = new BigInteger("111");
        importSearch.setId(integer);
        Assert.assertEquals(importSearch.getId(),integer);
    }

    @Test
    public void testGetIsWidget() throws Exception {
        importSearch.setIsWidget(true);
        Assert.assertTrue(importSearch.getIsWidget());
    }

    @Test
    public void testGetMetadata() throws Exception {
        importSearch.setMetadata("metadataxx");
        Assert.assertEquals(importSearch.getMetadata(),"metadataxx");
    }

    @Test
    public void testGetName() throws Exception {
        importSearch.setName("namexx");
        Assert.assertEquals(importSearch.getName(),"namexx");
    }

    @Test
    public void testGetQueryStr() throws Exception {
        importSearch.setQueryStr("QueryStrxx");
        Assert.assertEquals(importSearch.getQueryStr(),"QueryStrxx");
    }

    @Test
    public void testGetSearch() throws Exception {
        ImportSearchImpl.SearchParameters searchParameter = new ImportSearchImpl.SearchParameters();
        searchParameter.getSearchParameter().add(new SearchParameterDetails());
        searchParameter.getSearchParameter().add(new SearchParameterDetails());
        importSearch.setSearchParameters(searchParameter);
        Assert.assertNotNull(importSearch.getSearch());
    }

    @Test
    public void testGetSearchParameters() throws Exception {
        ImportSearchImpl.SearchParameters searchParameter = new ImportSearchImpl.SearchParameters();
        importSearch.setSearchParameters(searchParameter);
        Assert.assertEquals(importSearch.getSearchParameters(),searchParameter);
    }

    @Test
    public void testIsLocked() throws Exception {
        importSearch.setLocked(true);
        Assert.assertTrue(importSearch.isLocked());
    }

    @Test
    public void testIsUiHidden() throws Exception {
        importSearch.setUiHidden(true);
        Assert.assertTrue(importSearch.isUiHidden());
    }
}
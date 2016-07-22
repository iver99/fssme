package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class ImportCategorySetTest {
    private static final BigInteger TEST_ID_10 = BigInteger.TEN;
	private ImportCategorySet importCategorySet = new ImportCategorySet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";

    @Test
    public void testImportsCategories(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./category.xsd");
        final  InputStream stream = url.openStream();
        final List<ImportCategoryImpl> list = new ArrayList<ImportCategoryImpl>();
        ImportCategoryImpl importCategory =new ImportCategoryImpl();
        importCategory.setId(TEST_ID_10);
        importCategory.setName("name");
        list.add(importCategory);
        final  List<Category> categoryList = new ArrayList<Category>();
        CategoryImpl category = new CategoryImpl();
        category.setId(TEST_ID_10);
        category.setName("name");
        categoryList.add(category);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new CategorySet();
            }
        };
        new MockUp<CategorySet>(){
            @Mock
            public List<ImportCategoryImpl> getCategorySet() {
                return list;
            }
        };
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> saveMultipleCategories(List<ImportCategoryImpl> categories)
            {
                return categoryList;
            }

        };
        Assert.assertNotNull(importCategorySet.importsCategories(xml));
    }
    @Test
    public void testImportsCategories2nd(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./category.xsd");
        final  InputStream stream = url.openStream();
        final List<ImportCategoryImpl> list = new ArrayList<ImportCategoryImpl>();
        ImportCategoryImpl importCategory =new ImportCategoryImpl();
        importCategory.setId(TEST_ID_10);
        importCategory.setName("name");
        list.add(importCategory);
        final  List<Category> categoryList = new ArrayList<Category>();
        CategoryImpl category = new CategoryImpl();
        category.setId(TEST_ID_10);
        category.setName("name");
        categoryList.add(category);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new CategorySet();
            }
        };
        new MockUp<CategorySet>(){
            @Mock
            public List<ImportCategoryImpl> getCategorySet() {
                return list;
            }
        };
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> saveMultipleCategories(List<ImportCategoryImpl> categories) throws Exception {
                if(true){throw new Exception();}
                return categoryList;
            }

        };
        Assert.assertNotNull(importCategorySet.importsCategories(xml));
    }

    @Test
    public void testImportsCategories3th(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./category.xsd");
        final  InputStream stream = url.openStream();
        final List<ImportCategoryImpl> list = new ArrayList<ImportCategoryImpl>();
        ImportCategoryImpl importCategory =new ImportCategoryImpl();
        importCategory.setId(TEST_ID_10);
        importCategory.setName("name");
        list.add(importCategory);
        final  List<Category> categoryList = new ArrayList<Category>();
        CategoryImpl category = new CategoryImpl();
        category.setId(TEST_ID_10);
        category.setName("name");
        categoryList.add(category);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new CategorySet();
            }
        };
        new MockUp<CategorySet>(){
            @Mock
            public List<ImportCategoryImpl> getCategorySet() {
                return list;
            }
        };
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public List<Category> saveMultipleCategories(List<ImportCategoryImpl> categories) throws Exception {
                if(true){throw new ImportException();}
                return categoryList;
            }

        };
        Assert.assertNotNull(importCategorySet.importsCategories(xml));
    }


}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;
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
 * Created by xidai on 2/29/2016.
 */
@Test(groups="s2")
public class ImportSearchSetTest {
    private static final BigInteger TEST_ID_10 = BigInteger.TEN;
	private ImportSearchSet importSearchSet =new ImportSearchSet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";
    @Test
    public void testImportSearches(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./search.xsd");
        final  InputStream stream = url.openStream();
        final List<ImportSearchImpl> importSearchList = new ArrayList<ImportSearchImpl>();
        ImportSearchImpl importSearch = new ImportSearchImpl();
        importSearch.setName("name");
        importSearch.setId(TEST_ID_10);
        importSearchList.add(importSearch);
        final List<Search> list = new ArrayList<Search>();
        SearchImpl search = new SearchImpl();
        search.setName("name");
        search.setId(TEST_ID_10);
        list.add(search);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new SearchSet();
            }
        };
        new MockUp<SearchSet>(){
            @Mock
            public List<ImportSearchImpl> getSearchSet() {
                return importSearchList;
            }
        };

        new MockUp<ImportSearchImpl>(){
            @Mock
            public Object getCategoryDetails()
            {   CategoryImpl category = new CategoryImpl();
                category.setId(TEST_ID_10);
                category.setName("name");
                return category;
            }
            @Mock
            public Object getFolderDetails()
            {
                FolderImpl folder = new FolderImpl();
                folder.setId(TEST_ID_10);
                folder.setName("name");
                return folder;
            }
        };

        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList, boolean isOobSearch) throws Exception
            {
                return list;
            }

        };
        Assert.assertNotNull(importSearchSet.importSearches(xml,"SSF_OOB"));

    }

    @Test
    public void testImportSearches2nd(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./search.xsd");
        final  InputStream stream = url.openStream();
        final List<ImportSearchImpl> importSearchList = new ArrayList<ImportSearchImpl>();
        ImportSearchImpl importSearch = new ImportSearchImpl();
        importSearch.setName("name");
        importSearch.setId(TEST_ID_10);
        importSearchList.add(importSearch);
        final List<Search> list = new ArrayList<Search>();
        SearchImpl search = new SearchImpl();
        search.setName("name");
        search.setId(TEST_ID_10);
        list.add(search);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result = new SearchSet();
            }
        };
        new MockUp<SearchSet>(){
            @Mock
            public List<ImportSearchImpl> getSearchSet() {
                return importSearchList;
            }
        };

        new MockUp<ImportSearchImpl>(){
            @Mock
            public Object getCategoryDetails()
            {   CategoryImpl category = new CategoryImpl();
                category.setId(TEST_ID_10);
                category.setName("name");
                return category;
            }
            @Mock
            public Object getFolderDetails()
            {
                FolderImpl folder = new FolderImpl();
                folder.setId(TEST_ID_10);
                folder.setName("name");
                return folder;
            }
        };

        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList, boolean isOobSearch) throws Exception
            {
                if(true){throw new Exception();}
                return list;
            }

        };
        Assert.assertNotNull(importSearchSet.importSearches(xml,"SSF_OOB"));

    }

    @Test
    public void testImportSearches3th(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        URL url = ImportCategorySetTest.class.getResource("./search.xsd");
        final  InputStream stream = url.openStream();
        final List<ImportSearchImpl> importSearchList = new ArrayList<ImportSearchImpl>();
        ImportSearchImpl importSearch = new ImportSearchImpl();
        importSearch.setName("name");
        importSearch.setId(TEST_ID_10);
        importSearchList.add(importSearch);
        final List<Search> list = new ArrayList<Search>();
        SearchImpl search = new SearchImpl();
        search.setName("name");
        search.setId(TEST_ID_10);
        list.add(search);
        new Expectations(){
            {
                anyJaxbutil.getJAXBContext(ObjectFactory.class);
                result = JAXBContext.newInstance();
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(stream),withAny(JAXBContext.newInstance()));
                result =  new ImportException();
            }
        };
        new MockUp<SearchSet>(){
            @Mock
            public List<ImportSearchImpl> getSearchSet() {
                return importSearchList;
            }
        };

        new MockUp<ImportSearchImpl>(){
            @Mock
            public Object getCategoryDetails()
            {   CategoryImpl category = new CategoryImpl();
                category.setId(TEST_ID_10);
                category.setName("name");
                return category;
            }
            @Mock
            public Object getFolderDetails()
            {
                FolderImpl folder = new FolderImpl();
                folder.setId(TEST_ID_10);
                folder.setName("name");
                return folder;
            }
        };

        new MockUp<Throwable>(){
            @Mock
            public void printStackTrace(){
            }

        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> saveMultipleSearch(List<ImportSearchImpl> searchList, boolean isOobSearch) throws Exception
            {
                if(true){throw new ImportException();
                }
                return list;
            }

        };

        Assert.assertNotNull(importSearchSet.importSearches(xml,"SSF_OOB"));

    }
}
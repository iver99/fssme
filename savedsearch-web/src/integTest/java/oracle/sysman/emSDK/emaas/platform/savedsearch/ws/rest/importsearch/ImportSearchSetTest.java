package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBElement;

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups="s2")
public class ImportSearchSetTest {
    private static final BigInteger TEST_ID_10 = BigInteger.TEN;
	private ImportSearchSet importSearchSet =new ImportSearchSet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";
    @Mocked
    InputStream inputStream;
    @Mocked
    SearchSet searchSet;
    @Mocked
    ImportSearchImpl importSearchImpl;
    @Mocked
    SearchManagerImpl searchManager;
    @Mocked
    JAXBContext jaxbContext;
    @Test
    public void testImportSearches(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        final List<ImportSearchImpl> importSearchList = new ArrayList<ImportSearchImpl>();
        final ImportSearchImpl importSearch = new ImportSearchImpl();
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
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = searchSet;
                searchSet.getSearchSet();
                result = importSearchList;

            }
        };
        Assert.assertNotNull(importSearchSet.importSearches(xml,"SSF_OOB"));

    }

    @Test
    public void testImportSearches2nd(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
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
                result = jaxbContext;
                Deencapsulation.invoke(anyJaxbutil,"unmarshal",withAny(new StringReader(xml)),withAny(inputStream),withAny(jaxbContext));
                result = searchSet;
                searchSet.getSearchSet();
                result = importSearchList;
            }
        };
        Assert.assertNotNull(importSearchSet.importSearches(xml,"SSF_OOB"));

    }

    @Test
    public void testValidateData(@Mocked final JAXBElement jaxbElement) throws Exception {
        List<ImportSearchImpl> list = new ArrayList<>();
        ImportSearchImpl importSearch = new ImportSearchImpl();
        importSearch.setName("name");
        list.add(importSearch);
        Deencapsulation.invoke(importSearchSet,"validateData",list);
//        importSearch.setName(null);
//        importSearch.setCategoryDet(jaxbElement);
//        Deencapsulation.invoke(importSearchSet,"validateData",list);
//        importSearch.setCategoryDet(null);
//        importSearch.setFolderDet(jaxbElement);
//        Deencapsulation.invoke(importSearchSet,"validateData",list);
    }
}

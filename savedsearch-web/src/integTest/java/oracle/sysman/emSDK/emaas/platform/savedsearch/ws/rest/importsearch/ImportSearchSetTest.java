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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups="s2")
public class ImportSearchSetTest {
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
        importSearch.setId(10);
        importSearchList.add(importSearch);
        final List<Search> list = new ArrayList<Search>();
        SearchImpl search = new SearchImpl();
        search.setName("name");
        search.setId(10);
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
        importSearch.setId(10);
        importSearchList.add(importSearch);
        final List<Search> list = new ArrayList<Search>();
        SearchImpl search = new SearchImpl();
        search.setName("name");
        search.setId(10);
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
    public void testImportSearches3th(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        final List<ImportSearchImpl> importSearchList = new ArrayList<ImportSearchImpl>();
        ImportSearchImpl importSearch = new ImportSearchImpl();
        importSearch.setName("name");
        importSearch.setId(10);
        importSearchList.add(importSearch);
        final List<Search> list = new ArrayList<Search>();
        SearchImpl search = new SearchImpl();
        search.setName("name");
        search.setId(10);
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
}

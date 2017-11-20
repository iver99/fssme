package oracle.sysman.emaas.platform.savedsearch.metadata;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiadai on 2017/5/8.
 */
@Test(groups = {"s2"})
public class MetaDataRetrieverTest {
    private MetaDataRetriever metaDataRetriever;
    @Mocked
    RegistryLookupUtil registryLookupUtil;
    @Mocked
    RegistryLookupUtil.VersionedLink versionedLink;
    @Mocked
    RestClient restClient;
    @Test
    public void testGetOobWidgetListByServiceName() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, null);
                result = versionedLink;
                versionedLink.getHref();
                result = "href";
                versionedLink.getAuthToken();
                result = "auth";
                restClient.get(anyString,anyString, anyString);
                result = "";
            }
        };
        metaDataRetriever = new MetaDataRetriever();
        metaDataRetriever.getOobWidgetListByServiceName("savedSearch");

    }

    @Test
    public void getResourceBundleByService() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, null);
                result = versionedLink;
                versionedLink.getHref();
                result = "href";
                versionedLink.getAuthToken();
                result = "auth";
                restClient.get(anyString,anyString, anyString);
                result = "";
            }
        };
        metaDataRetriever = new MetaDataRetriever();
        metaDataRetriever.getResourceBundleByService("savedSearch");

    }

    @Test
    public void testSetWidgetDefaultValue(){
        metaDataRetriever = new MetaDataRetriever();
        List<SearchImpl> SearchList= new ArrayList<>();
        SearchImpl search = new SearchImpl();
        SearchList.add(search);
        String servicename = "ApmUI";
        List<Object> result = Deencapsulation.invoke(metaDataRetriever,"setWidgetDefaultValue",SearchList,servicename);
        SearchImpl afterSearch = (SearchImpl) result.get(0);
        Assert.assertEquals(afterSearch.getCategoryId(),new BigInteger("4"));
        Assert.assertEquals(afterSearch.getFolderId(),new BigInteger("5"));
    }

    public void testSetDefaultResourceBundleValue(){

        metaDataRetriever = new MetaDataRetriever();
        List<EmsResourceBundle> SearchList= new ArrayList<>();
        EmsResourceBundle emsRBinstance = new EmsResourceBundle();
        emsRBinstance.setLanguageCode("en");
        SearchList.add(emsRBinstance);
        String servicename = "ApmUI";
        List<Object> result = Deencapsulation.invoke(metaDataRetriever,"setDefaultResourceBundleValue",SearchList,servicename);
        EmsResourceBundle afterSearch = (EmsResourceBundle) result.get(0);
        Assert.assertEquals(afterSearch.getServiceName(),"APM");
        Assert.assertEquals(afterSearch.getCountryCode(),"US");
    }
}
package oracle.sysman.emaas.platform.savedsearch.metadata;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

import org.testng.annotations.Test;

/**
 * Created by xiadai on 2017/5/8.
 */
@Test(groups = {"s2"})
public class MetaDataRetrieverTest {
    private MetaDataRetriever metaDataRetriever;
    @Mocked
    RegistryLookupUtil registryLookupUtil;
    @Mocked
    VersionedLink versionedLink;
    @Mocked
    RestClient restClient;
    @Test
    public void testGetOobWidgetListByServiceName() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                RegistryLookupUtil.getServiceInternalHttpLink(anyString, anyString, anyString, null);
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
                RegistryLookupUtil.getServiceInternalHttpLink(anyString, anyString, anyString, null);
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
}
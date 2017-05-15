package oracle.sysman.emaas.platform.savedsearch.metadata;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

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
    public void testGetOobWidgetListByServiceName() throws Exception {
        new Expectations(){
            {
                RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, null);
                result = versionedLink;
                versionedLink.getHref();
                result = "href";
                versionedLink.getAuthToken();
                result = "auth";
                restClient.get(anyString,anyString, anyString);
                result = "response";
            }
        };
        metaDataRetriever = new MetaDataRetriever();
        metaDataRetriever.getOobWidgetListByServiceName("savedSearch");

    }

}
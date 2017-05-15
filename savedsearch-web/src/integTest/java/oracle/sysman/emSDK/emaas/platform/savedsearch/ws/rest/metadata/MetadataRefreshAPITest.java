package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2017/5/5.
 */
@Test(groups = {"s2"})
public class MetadataRefreshAPITest {
    private MetadataRefreshAPI metadataRefreshAPI;
    @Mocked
    MetaDataRetriever metaDataRetriever;
    @Mocked
    MetaDataStorer metaDataStorer;
    @Test
    public void testRefreshOOB() throws Exception {
        String serviceName = "serviceName";
        final List<SearchImpl> oobWidgetList = new ArrayList<>();
        new Expectations(){
            {
                metaDataRetriever.getOobWidgetListByServiceName(anyString);
                result = oobWidgetList;
                MetaDataStorer.storeOobWidget(oobWidgetList);
            }
        };
        metadataRefreshAPI = new MetadataRefreshAPI();
        metadataRefreshAPI.refreshOOB(serviceName);

    }

}
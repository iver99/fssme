package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
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
    @Mocked
    DependencyStatus dependencyStatus;
    @Test
    public void testRefreshOOB() throws Exception {
        String serviceName = "serviceName";
        final List<SearchImpl> oobWidgetList = new ArrayList<>();
        new Expectations(){
            {
                DependencyStatus.getInstance();
                result = dependencyStatus;
                dependencyStatus.isDatabaseUp();
                result = true;
                metaDataRetriever.getOobWidgetListByServiceName(anyString);
                result = oobWidgetList;
                MetaDataStorer.storeOobWidget(oobWidgetList);
            }
        };
        metadataRefreshAPI = new MetadataRefreshAPI();
        metadataRefreshAPI.refreshOOB(serviceName);

    }

    @Mocked
    SearchManager searchManager;
    @Test
    public void testGetWidgetByName() throws EMAnalyticsFwkException {
        final String widgetName = "widgetName";
        final List<Search> searches = new ArrayList<>();
        searches.add(new SearchImpl());
        new Expectations(){
            {
                DependencyStatus.getInstance();
                result = dependencyStatus;
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getWidgetByName(widgetName);
                result = searches;
            }
        };
        metadataRefreshAPI = new MetadataRefreshAPI();
        metadataRefreshAPI.getWidgetByName(widgetName);
    }
}
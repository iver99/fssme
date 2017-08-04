package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.OobRefreshRunnable;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.tool.InternalToolAPI;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.SanitizedInstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;

import org.testng.annotations.Test;

/**
 * Created by xiadai on 2017/5/5.
 */
@Test(groups = {"s2"})
public class MetadataRefreshAPITest {
    private InternalToolAPI internalToolAPI;
    @Mocked
    MetaDataRetriever metaDataRetriever;
    @Mocked
    MetaDataStorer metaDataStorer;
    @Mocked
    DependencyStatus dependencyStatus;

    @Mocked
    SearchManager searchManager;
    @Test
    public void testRefreshOOB(){
        MetadataRefreshAPI metadataRefreshAPI = new MetadataRefreshAPI();
        metadataRefreshAPI.refreshOOB("ITA");
    }
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
        internalToolAPI = new InternalToolAPI();
        internalToolAPI.getWidgetByName(widgetName);
    }

    @Test
    public void testRefreshNLS(){
        MetadataRefreshAPI metadataRefreshAPI = new MetadataRefreshAPI();
        metadataRefreshAPI.refreshNLS("ServiceName");
    }
    @Test
    public void testOobRefreshRunnable(@Mocked MetaDataStorer metaDataStorer, @Mocked final MetaDataRetriever metaDataRetriever, @Mocked final SanitizedInstanceInfo sanitizedInstance,
            @Mocked final LookupManager lookupManager, @Mocked final LookupClient lookupClient, @Mocked final InstanceInfo instanceInfo) throws Exception {
        final List<Link> linkList = new ArrayList<Link>();
        Link link = new Link();
        link.withHref("href");
        link.withRel("rel");
        linkList.add(link);
        
        new Expectations(){
            {
                DependencyStatus.getInstance();
                result = dependencyStatus;
                dependencyStatus.isDatabaseUp();
                result = true;
                LookupManager.getInstance();
                result = lookupManager;
                lookupManager.getLookupClient();
                result = lookupClient;
                lookupClient.getInstance((InstanceInfo) any);
                result = instanceInfo;
                lookupClient.getSanitizedInstanceInfo((InstanceInfo) any);
                result = sanitizedInstance;
                sanitizedInstance.getLinks(anyString);
                result = linkList;
            }
        };
        
        OobRefreshRunnable oobRefreshRunnable = new OobRefreshRunnable();
        oobRefreshRunnable.run();
        oobRefreshRunnable.setServiceName("serviceName");
        oobRefreshRunnable.run();
    }
}
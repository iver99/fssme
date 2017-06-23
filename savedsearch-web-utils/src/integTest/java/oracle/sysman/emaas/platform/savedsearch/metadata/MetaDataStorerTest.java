package oracle.sysman.emaas.platform.savedsearch.metadata;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ResourceBundleManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2017/5/8.
 */
@Test(groups = {"s2"})
public class MetaDataStorerTest {
    @Mocked
    SearchManager searchManager;

    @Mocked
    ResourceBundleManager resourceBundleManager;

    @Test
    public void testStoreOobWidget() throws EMAnalyticsFwkException {
        final List<SearchImpl> searches = new ArrayList<>();
        searches.add(new SearchImpl());
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchIdsByCategory((BigInteger)any);
                result = null;
                searchManager.storeOobWidget((List)any, (List)any);
            }
        };
        MetaDataStorer.storeOobWidget(searches);
    }

    @Test
    public void testStoreResourceBundle() throws EMAnalyticsFwkException {
        List<EmsResourceBundle> emsResourceBundles = new ArrayList<>();
        emsResourceBundles.add(new EmsResourceBundle());
        new Expectations(){
            {
                ResourceBundleManager.getInstance();
                result = resourceBundleManager;
                resourceBundleManager.storeResourceBundle(anyString, (List)any);
            }
        };
        MetaDataStorer.storeResourceBundle(emsResourceBundles);
    }

}
package oracle.sysman.emaas.platform.savedsearch.metadata;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
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


    @Test
    public void testStoreOobWidget() throws Exception {
        final List<SearchImpl> searchList = new ArrayList<>();
        final SearchImpl search = new SearchImpl();
        search.setCategoryId(new BigInteger("8"));
        searchList.add(search);
        final List<BigInteger> searchIds = new ArrayList<>();
        searchIds.add(new BigInteger("8"));
                new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchIdsByCategory((BigInteger)any);
                result = searchIds;
                searchManager.cleanSearchesPermanentlyById(searchIds);
                searchManager.saveOobSearch((Search)any);
            }
        };
        MetaDataStorer.storeOobWidget(searchList);
    }

}
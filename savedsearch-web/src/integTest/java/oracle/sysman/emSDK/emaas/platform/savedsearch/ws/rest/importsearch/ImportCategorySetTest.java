package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class ImportCategorySetTest {
    private ImportCategorySet importCategorySet = new ImportCategorySet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";

    @Test
    public void testImportsCategories() throws Exception {
       final List<ImportCategoryImpl> list = new ArrayList<ImportCategoryImpl>();
        list.add(new ImportCategoryImpl());
        importCategorySet.importsCategories(xml);
    }
}
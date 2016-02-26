package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class ImportCategorySetTest {
    private ImportCategorySet importCategorySet = new ImportCategorySet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";

    @Test
    public void testImportsCategories() throws Exception {

    }
}
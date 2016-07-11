package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.assetroot;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/7/11.
 */
@Test(groups={"s2"})
public class AssetRootTest {
    private AssetRoot assetRoot = new AssetRoot();
    @Mocked
    private SearchManager searchManager;
    @Mocked
    private Search search;
    @Mocked
    private CategoryManager categoryManager;
    @Mocked
    private Category category;
    @Mocked
    private RegistryLookupUtil registryLookupUtil;
    @Mocked
    private TenantContext tenantContext;
    @Mocked
    private Link link;
    @Test
    public void testGetAssetRoot() throws Exception {
        new Expectations(){
            {
                searchManager.getSearch(anyLong);
                result = search;
                categoryManager.getCategory(anyLong);
                result = category;
                search.getCategoryId();
                result = 1L;
                category.getProviderName();
                result = "LoganService";
                category.getProviderAssetRoot();
                result = "assetroot";
                category.getProviderVersion();
                result = "1.0";
                TenantContext.getContext().gettenantName();
                result = "emasstesttenant1";
                RegistryLookupUtil.getServiceExternalLink(anyString,anyString,anyString,anyString);
                result = link;
                RegistryLookupUtil.replaceWithVanityUrl(link,anyString,anyString);
                result = link;
            }
        };
        Assert.assertEquals(200,assetRoot.getAssetRoot(1L).getStatus());
    }

}
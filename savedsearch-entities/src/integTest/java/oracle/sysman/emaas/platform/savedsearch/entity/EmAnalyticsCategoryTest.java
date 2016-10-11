package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;


/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s1"})
public class EmAnalyticsCategoryTest {

    private EmAnalyticsCategory emAnalyticsCategory;

    @BeforeClass
    public void setUp(){
        emAnalyticsCategory = new EmAnalyticsCategory();
    }

    @Test (groups = {"s1"})
    public void testGetCategoryId() {
        BigInteger id = new BigInteger("1234123412341234");
        emAnalyticsCategory.setCategoryId(id);
        Assert.assertEquals(id,emAnalyticsCategory.getCategoryId());
    }

    @Test (groups = {"s1"})
    public void testGetCreationDate(){
        Date now = new Date();
        emAnalyticsCategory.setCreationDate(now);
        Assert.assertEquals(now,emAnalyticsCategory.getCreationDate());
    }

    @Test (groups = {"s1"})
    public void testGetDeleted(){
        BigInteger id = new BigInteger("1234123412341234");
        emAnalyticsCategory.setDeleted(id);
        Assert.assertEquals(id,emAnalyticsCategory.getDeleted());
    }

    @Test (groups = {"s1"})
    public void testGetDescription(){
        String description = "descriptionxx";
        emAnalyticsCategory.setDescription(description);
        Assert.assertEquals(description,emAnalyticsCategory.getDescription());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionNlsid(){
        String Nlsid = "nlsidxx";
        emAnalyticsCategory.setDescriptionNlsid(Nlsid);
        Assert.assertEquals(Nlsid,emAnalyticsCategory.getDescriptionNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionSubsystem(){
        String subsystem = "subsystemxx";
        emAnalyticsCategory.setDescriptionSubsystem(subsystem);
        Assert.assertEquals(subsystem,emAnalyticsCategory.getDescriptionSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsCategoryParams(){
        emAnalyticsCategory.setEmAnalyticsCategoryParams(null);
        Assert.assertFalse(CollectionUtils.hasElements(emAnalyticsCategory.getEmAnalyticsCategoryParams()));

        HashSet<EmAnalyticsCategoryParam> emAnalyticsCategoryParams= new HashSet<>();
        emAnalyticsCategory.setEmAnalyticsCategoryParams(emAnalyticsCategoryParams);
        Assert.assertEquals(emAnalyticsCategoryParams,emAnalyticsCategory.getEmAnalyticsCategoryParams());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsFolder(){
        EmAnalyticsFolder emaFolder = new EmAnalyticsFolder();
        emAnalyticsCategory.setEmAnalyticsFolder(emaFolder);
        Assert.assertEquals(emaFolder,emAnalyticsCategory.getEmAnalyticsFolder());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsSearches(){
        HashSet<EmAnalyticsSearch> emAnalyticsSearches = new HashSet<EmAnalyticsSearch>();
        emAnalyticsCategory.setEmAnalyticsSearches(emAnalyticsSearches);
        Assert.assertEquals(emAnalyticsSearches,emAnalyticsCategory.getEmAnalyticsSearches());
    }

    @Test (groups = {"s1"})
    public void testGetEmPluginId(){
        String plugid = "pluginidxx";
        emAnalyticsCategory.setEmPluginId(plugid);
        Assert.assertEquals(plugid,emAnalyticsCategory.getEmPluginId());
    }

    @Test (groups = {"s1"})
    public void testGetName(){
        String name = "namexx";
        emAnalyticsCategory.setName(name);
        Assert.assertEquals(name,emAnalyticsCategory.getName());
    }

    @Test (groups = {"s1"})
    public void testGetNameNlsid(){
        String nameNlsid = "nameNlsidxx";
        emAnalyticsCategory.setNameNlsid(nameNlsid);
        Assert.assertEquals(nameNlsid,emAnalyticsCategory.getNameNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetNameSubsystem(){
        String nameSubsystem = "nameSubsystemxx";
        emAnalyticsCategory.setNameSubsystem(nameSubsystem);
        Assert.assertEquals(nameSubsystem,emAnalyticsCategory.getNameSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetOwner(){
        String owner = "ownerxx";
        emAnalyticsCategory.setOwner(owner);
        Assert.assertEquals(owner,emAnalyticsCategory.getOwner());
    }

    @Test (groups = {"s1"})
    public void testGetProviderAssetRoot(){
        String providerAssetRoot = "providerAssetRootxx";
        emAnalyticsCategory.setProviderAssetRoot(providerAssetRoot);
        Assert.assertEquals(providerAssetRoot,emAnalyticsCategory.getProviderAssetRoot());
    }

    @Test (groups = {"s1"})
    public void testGetProviderDiscovery(){
        String providerDiscovery = "providerdiscoveryxx";
        emAnalyticsCategory.setProviderDiscovery(providerDiscovery);
        Assert.assertEquals(providerDiscovery,emAnalyticsCategory.getProviderDiscovery());
    }

    @Test (groups = {"s1"})
    public void testGetProviderName(){
        String providerName = "providerNamexx";
        emAnalyticsCategory.setProviderName(providerName);
        Assert.assertEquals(providerName,emAnalyticsCategory.getProviderName());
    }

    @Test (groups = {"s1"})
    public void testGetProviderVersion(){
        String providerVersion = "providerVersion";
        emAnalyticsCategory.setProviderVersion(providerVersion);
        Assert.assertEquals(providerVersion,emAnalyticsCategory.getProviderVersion());
    }
    
    @Test(groups = {"s1"})
    public void testGetDASHBOARDINELIGIBLE(){
        String dashboardIneligible = "1";
        emAnalyticsCategory.setdashboardIneligible(dashboardIneligible);
        Assert.assertEquals("1", emAnalyticsCategory.getDASHBOARDINELIGIBLE());
    }
}
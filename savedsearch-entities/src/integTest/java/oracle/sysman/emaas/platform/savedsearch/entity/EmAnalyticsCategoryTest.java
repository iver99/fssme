package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s1"})
public class EmAnalyticsCategoryTest {

    private EmAnalyticsCategory emAnalyticsCategory;

    @BeforeClass
    public void setUp() throws Exception {
        emAnalyticsCategory = new EmAnalyticsCategory();
    }

    @Test (groups = {"s1"})
    public void testGetCategoryId() throws Exception {
        long id = 1234123412341234L;
        emAnalyticsCategory.setCategoryId(id);
        Assert.assertEquals(id,emAnalyticsCategory.getCategoryId());
    }

    @Test (groups = {"s1"})
    public void testGetCreationDate() throws Exception {
        Date now = new Date();
        emAnalyticsCategory.setCreationDate(now);
        Assert.assertEquals(now,emAnalyticsCategory.getCreationDate());
    }

    @Test (groups = {"s1"})
    public void testGetDeleted() throws Exception {
        long id = 1234123412341234L;
        emAnalyticsCategory.setDeleted(id);
        Assert.assertEquals(id,emAnalyticsCategory.getDeleted());
    }

    @Test (groups = {"s1"})
    public void testGetDescription() throws Exception {
        String description = "descriptionxx";
        emAnalyticsCategory.setDescription(description);
        Assert.assertEquals(description,emAnalyticsCategory.getDescription());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionNlsid() throws Exception {
        String Nlsid = "nlsidxx";
        emAnalyticsCategory.setDescriptionNlsid(Nlsid);
        Assert.assertEquals(Nlsid,emAnalyticsCategory.getDescriptionNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionSubsystem() throws Exception {
        String subsystem = "subsystemxx";
        emAnalyticsCategory.setDescriptionSubsystem(subsystem);
        Assert.assertEquals(subsystem,emAnalyticsCategory.getDescriptionSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsCategoryParams() throws Exception {
        emAnalyticsCategory.setEmAnalyticsCategoryParams(null);
        Assert.assertFalse(CollectionUtils.hasElements(emAnalyticsCategory.getEmAnalyticsCategoryParams()));

        HashSet<EmAnalyticsCategoryParam> emAnalyticsCategoryParams= new HashSet<>();
        emAnalyticsCategory.setEmAnalyticsCategoryParams(emAnalyticsCategoryParams);
        Assert.assertEquals(emAnalyticsCategoryParams,emAnalyticsCategory.getEmAnalyticsCategoryParams());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsFolder() throws Exception {
        EmAnalyticsFolder emaFolder = new EmAnalyticsFolder();
        emAnalyticsCategory.setEmAnalyticsFolder(emaFolder);
        Assert.assertEquals(emaFolder,emAnalyticsCategory.getEmAnalyticsFolder());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsSearches() throws Exception {
        HashSet<EmAnalyticsSearch> emAnalyticsSearches = new HashSet<EmAnalyticsSearch>();
        emAnalyticsCategory.setEmAnalyticsSearches(emAnalyticsSearches);
        Assert.assertEquals(emAnalyticsSearches,emAnalyticsCategory.getEmAnalyticsSearches());
    }

    @Test (groups = {"s1"})
    public void testGetEmPluginId() throws Exception {
        String plugid = "pluginidxx";
        emAnalyticsCategory.setEmPluginId(plugid);
        Assert.assertEquals(plugid,emAnalyticsCategory.getEmPluginId());
    }

    @Test (groups = {"s1"})
    public void testGetName() throws Exception {
        String name = "namexx";
        emAnalyticsCategory.setName(name);
        Assert.assertEquals(name,emAnalyticsCategory.getName());
    }

    @Test (groups = {"s1"})
    public void testGetNameNlsid() throws Exception {
        String nameNlsid = "nameNlsidxx";
        emAnalyticsCategory.setNameNlsid(nameNlsid);
        Assert.assertEquals(nameNlsid,emAnalyticsCategory.getNameNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetNameSubsystem() throws Exception {
        String nameSubsystem = "nameSubsystemxx";
        emAnalyticsCategory.setNameSubsystem(nameSubsystem);
        Assert.assertEquals(nameSubsystem,emAnalyticsCategory.getNameSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetOwner() throws Exception {
        String owner = "ownerxx";
        emAnalyticsCategory.setOwner(owner);
        Assert.assertEquals(owner,emAnalyticsCategory.getOwner());
    }

    @Test (groups = {"s1"})
    public void testGetProviderAssetRoot() throws Exception {
        String providerAssetRoot = "providerAssetRootxx";
        emAnalyticsCategory.setProviderAssetRoot(providerAssetRoot);
        Assert.assertEquals(providerAssetRoot,emAnalyticsCategory.getProviderAssetRoot());
    }

    @Test (groups = {"s1"})
    public void testGetProviderDiscovery() throws Exception {
        String providerDiscovery = "providerdiscoveryxx";
        emAnalyticsCategory.setProviderDiscovery(providerDiscovery);
        Assert.assertEquals(providerDiscovery,emAnalyticsCategory.getProviderDiscovery());
    }

    @Test (groups = {"s1"})
    public void testGetProviderName() throws Exception {
        String providerName = "providerNamexx";
        emAnalyticsCategory.setProviderName(providerName);
        Assert.assertEquals(providerName,emAnalyticsCategory.getProviderName());
    }

    @Test (groups = {"s1"})
    public void testGetProviderVersion() throws Exception {
        String providerVersion = "providerVersion";
        emAnalyticsCategory.setProviderVersion(providerVersion);
        Assert.assertEquals(providerVersion,emAnalyticsCategory.getProviderVersion());
    }
}
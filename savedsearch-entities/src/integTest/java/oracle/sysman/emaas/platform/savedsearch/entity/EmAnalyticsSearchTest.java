package oracle.sysman.emaas.platform.savedsearch.entity;

import com.sun.xml.internal.ws.message.ByteArrayAttachment;
import com.sun.xml.internal.ws.policy.AssertionSet;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

import static org.testng.Assert.*;

/**
 * @author qianqi
 * @since 16-2-18.
 */
public class EmAnalyticsSearchTest {
    private EmAnalyticsSearch emAnalyticsSearch;

    @BeforeMethod
    public void setUp() throws Exception {
        emAnalyticsSearch = new EmAnalyticsSearch();
        emAnalyticsSearch.setId(123412341234L);
        emAnalyticsSearch.setOwner("ownerxx");
    }

    @Test (groups = {"s1"})
    public void testGetAccessDate() throws Exception {
        Assert.assertNull(emAnalyticsSearch.getAccessDate());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        Assert.assertEquals(now,emAnalyticsSearch.getAccessDate());
    }

    @Test (groups = {"s1"})
    public void testGetAccessedBy() throws Exception {
        Assert.assertNull(emAnalyticsSearch.getAccessedBy());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        Assert.assertEquals(emAnalyticsSearch.getLastAccess().getAccessedBy(),emAnalyticsSearch.getAccessedBy());
    }

    @Test (groups = {"s1"})
    public void testGetCreationDate() throws Exception {
        Date now = new Date();
        emAnalyticsSearch.setCreationDate(now);
        Assert.assertEquals(now,emAnalyticsSearch.getCreationDate());
    }

    @Test (groups = {"s1"})
    public void testGetDeleted() throws Exception {
        long deleted = 123412341234L;
        emAnalyticsSearch.setDeleted(deleted);
        Assert.assertEquals(deleted,emAnalyticsSearch.getDeleted());
    }

    @Test (groups = {"s1"})
    public void testGetDescription() throws Exception {
        String description = "descriptionxx";
        emAnalyticsSearch.setDescription(description);
        Assert.assertEquals(description,emAnalyticsSearch.getDescription());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionNlsid() throws Exception {
        String descriptionNlsid = "descriptionNlsidxx";
        emAnalyticsSearch.setDescriptionNlsid(descriptionNlsid);
        Assert.assertEquals(descriptionNlsid,emAnalyticsSearch.getDescriptionNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionSubsystem() throws Exception {
        String descriptionSubsystem = "descriptionSubsystemxx";
        emAnalyticsSearch.setDescriptionSubsystem(descriptionSubsystem);
        Assert.assertEquals(descriptionSubsystem,emAnalyticsSearch.getDescriptionSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsCategory() throws Exception {
        EmAnalyticsCategory emAnalyticsCategory = new EmAnalyticsCategory();
        emAnalyticsSearch.setEmAnalyticsCategory(emAnalyticsCategory);
        Assert.assertEquals(emAnalyticsCategory,emAnalyticsSearch.getEmAnalyticsCategory());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsFolder() throws Exception {
        EmAnalyticsFolder emaFolder = new EmAnalyticsFolder();
        emAnalyticsSearch.setEmAnalyticsFolder(emaFolder);
        Assert.assertEquals(emaFolder,emAnalyticsSearch.getEmAnalyticsFolder());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsSearchParams() throws Exception {
        emAnalyticsSearch.setEmAnalyticsSearchParams(null);
        Assert.assertNotNull(emAnalyticsSearch.getEmAnalyticsSearchParams());

        HashSet<EmAnalyticsSearchParam> emAnalyticsSearches = new HashSet<EmAnalyticsSearchParam>();
        emAnalyticsSearch.setEmAnalyticsSearchParams(emAnalyticsSearches);
        Assert.assertEquals(emAnalyticsSearches,emAnalyticsSearch.getEmAnalyticsSearchParams());
    }

    @Test (groups = {"s1"})
    public void testGetEmPluginId() throws Exception {
        String plugid = "pluginidxx";
        emAnalyticsSearch.setEmPluginId(plugid);
        Assert.assertEquals(plugid,emAnalyticsSearch.getEmPluginId());
    }

    @Test (groups = {"s1"})
    public void testGetId() throws Exception {
        long id = 1234123412341234L;
        emAnalyticsSearch.setId(id);
        Assert.assertEquals(id,emAnalyticsSearch.getId());
    }

    @Test (groups = {"s1"})
    public void testGetIsLocked() throws Exception {
        BigDecimal isLocked = BigDecimal.valueOf(1.22);
        emAnalyticsSearch.setIsLocked(isLocked);
        Assert.assertEquals(isLocked,emAnalyticsSearch.getIsLocked());
    }

    @Test (groups = {"s1"})
    public void testGetIsWidget() throws Exception {
        long isWidget = 333L;
        emAnalyticsSearch.setIsWidget(isWidget);
        Assert.assertEquals(isWidget,emAnalyticsSearch.getIsWidget());
    }

    @Test (groups = {"s1"})
    public void testGetLastAccess() throws Exception {
        Assert.assertNull(emAnalyticsSearch.getLastAccess());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        Assert.assertNotNull(emAnalyticsSearch.getLastAccess());
    }

    @Test (groups = {"s1"})
    public void testGetLastModificationDate() throws Exception {
        Date now = new Date();
        emAnalyticsSearch.setLastModificationDate(now);
        Assert.assertEquals(now,emAnalyticsSearch.getLastModificationDate());
    }

    @Test (groups = {"s1"})
    public void testGetLastModifiedBy() throws Exception {
        String lastModifiedBy = "lastModifiedByxx";
        emAnalyticsSearch.setLastModifiedBy(lastModifiedBy);
        Assert.assertEquals(lastModifiedBy,emAnalyticsSearch.getLastModifiedBy());
    }

    @Test (groups = {"s1"})
    public void testGetMetadataClob() throws Exception {
        String metadataClob = "metadataClobxx";
        emAnalyticsSearch.setMetadataClob(metadataClob);
        Assert.assertEquals(metadataClob,emAnalyticsSearch.getMetadataClob());
    }

    @Test (groups = {"s1"})
    public void testGetName() throws Exception {
        String name = "namexx";
        emAnalyticsSearch.setName(name);
        Assert.assertEquals(name,emAnalyticsSearch.getName());
    }

    @Test (groups = {"s1"})
    public void testGetNameNlsid() throws Exception {
        String nameNlsid = "nameMlsidxx";
        emAnalyticsSearch.setNameNlsid(nameNlsid);
        Assert.assertEquals(nameNlsid,emAnalyticsSearch.getNameNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetNameSubsystem() throws Exception {
        String nameSubsystem = "nameSubsystemxx";
        emAnalyticsSearch.setNameSubsystem(nameSubsystem);
        Assert.assertEquals(nameSubsystem,emAnalyticsSearch.getNameSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetObjectId() throws Exception {
        Assert.assertNull(emAnalyticsSearch.getObjectId());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        emAnalyticsSearch.getAccessDate();
        Assert.assertNotNull(emAnalyticsSearch.getObjectId());
    }

    @Test (groups = {"s1"})
    public void testGetObjectType() throws Exception {
        Assert.assertNull(emAnalyticsSearch.getObjectType());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        emAnalyticsSearch.getAccessDate();
        Assert.assertNotNull(emAnalyticsSearch.getObjectType());
    }

    @Test (groups = {"s1"})
    public void testGetOwner() throws Exception {
        String owner = "ownerxx";
        emAnalyticsSearch.setOwner(owner);
        Assert.assertEquals(owner,emAnalyticsSearch.getOwner());
    }

    @Test (groups = {"s1"})
    public void testGetSearchDisplayStr() throws Exception {
        String searchDisplayStr = "searchDisplayStrxx";
        emAnalyticsSearch.setSearchDisplayStr(searchDisplayStr);
        Assert.assertEquals(searchDisplayStr,emAnalyticsSearch.getSearchDisplayStr());
    }

    @Test (groups = {"s1"})
    public void testGetSearchGuid() throws Exception {
        byte[] searchGuid = {1,1,2,2,3,3};
        emAnalyticsSearch.setSearchGuid(searchGuid);
        Assert.assertEquals(searchGuid,emAnalyticsSearch.getSearchGuid());
    }

    @Test (groups = {"s1"})
    public void testGetSystemSearch() throws Exception {
        BigDecimal systemSearch = BigDecimal.valueOf(123412341234L);
        emAnalyticsSearch.setSystemSearch(systemSearch);
        Assert.assertEquals(systemSearch,emAnalyticsSearch.getSystemSearch());
    }

    @Test (groups = {"s1"})
    public void testGetUiHidden() throws Exception {
        BigDecimal uiHidden = BigDecimal.valueOf(123412341234L);
        emAnalyticsSearch.setUiHidden(uiHidden);
        Assert.assertEquals(uiHidden,emAnalyticsSearch.getUiHidden());
    }
}
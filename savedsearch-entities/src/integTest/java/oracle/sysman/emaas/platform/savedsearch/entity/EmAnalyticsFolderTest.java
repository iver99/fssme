package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s1"})
public class EmAnalyticsFolderTest {
    private EmAnalyticsFolder emAnalyticsFolder;

    @BeforeMethod
    public void setUp(){
        emAnalyticsFolder = new EmAnalyticsFolder();

    }

    @Test (groups = {"s1"})
    public void testGetCreationDate(){
        Date now = new Date();
        emAnalyticsFolder.setCreationDate(now);
        Assert.assertEquals(now,emAnalyticsFolder.getCreationDate());
    }

    @Test (groups = {"s1"})
    public void testGetDeleted() {
        BigInteger deleted = new BigInteger("123412341234");
        emAnalyticsFolder.setDeleted(deleted);
        Assert.assertEquals(deleted,emAnalyticsFolder.getDeleted());
    }

    @Test (groups = {"s1"})
    public void testGetDescription(){
        String description = "descriptionxx";
        emAnalyticsFolder.setDescription(description);
        Assert.assertEquals(description,emAnalyticsFolder.getDescription());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionNlsid(){
        String descriptionNlsid = "descriptionNlsidxx";
        emAnalyticsFolder.setDescriptionNlsid(descriptionNlsid);
        Assert.assertEquals(descriptionNlsid,emAnalyticsFolder.getDescriptionNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionSubsystem(){
        String descriptionSubsystem = "descriptionSubsystemxx";
        emAnalyticsFolder.setDescriptionSubsystem(descriptionSubsystem);
        Assert.assertEquals(descriptionSubsystem,emAnalyticsFolder.getDescriptionSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsCategories(){
        Set<EmAnalyticsCategory> emAnalyticsCategories = new HashSet<EmAnalyticsCategory>();
        emAnalyticsFolder.setEmAnalyticsCategories(emAnalyticsCategories);
        Assert.assertEquals(emAnalyticsCategories,emAnalyticsFolder.getEmAnalyticsCategories());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsFolder(){
        emAnalyticsFolder.setEmAnalyticsFolder(emAnalyticsFolder);
        Assert.assertEquals(emAnalyticsFolder,emAnalyticsFolder.getEmAnalyticsFolder());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsFolders(){
        Set<EmAnalyticsFolder> emAnalyticsFolders = new HashSet<EmAnalyticsFolder>();
        emAnalyticsFolder.setEmAnalyticsFolders(emAnalyticsFolders);
        Assert.assertEquals(emAnalyticsFolders,emAnalyticsFolder.getEmAnalyticsFolders());
    }

    @Test (groups = {"s1"})
    public void testGetEmPluginId(){
        String emPluginId = "emPluginIdxx";
        emAnalyticsFolder.setEmPluginId(emPluginId);
        Assert.assertEquals(emPluginId,emAnalyticsFolder.getEmPluginId());
    }

    @Test (groups = {"s1"})
    public void testGetFolderId() {
        BigInteger folderId = new BigInteger("123412341234");
        emAnalyticsFolder.setFolderId(folderId);
        Assert.assertEquals(folderId,emAnalyticsFolder.getFolderId());
    }

    @Test (groups = {"s1"})
    public void testGetLastModificationDate(){
        Date now = new Date();
        emAnalyticsFolder.setLastModificationDate(now);
        Assert.assertEquals(now,emAnalyticsFolder.getLastModificationDate());
    }

    @Test (groups = {"s1"})
    public void testGetLastModifiedBy(){
        String lastModifiedBy = "lastModifiedByxx";
        emAnalyticsFolder.setLastModifiedBy(lastModifiedBy);
        Assert.assertEquals(lastModifiedBy,emAnalyticsFolder.getLastModifiedBy());
    }

    @Test (groups = {"s1"})
    public void testGetName(){
        String name = "namexx";
        emAnalyticsFolder.setName(name);
        Assert.assertEquals(name,emAnalyticsFolder.getName());
    }

    @Test (groups = {"s1"})
    public void testGetNameNlsid(){
        String nameNlsid = "nameNlsidxx";
        emAnalyticsFolder.setNameNlsid(nameNlsid);
        Assert.assertEquals(nameNlsid,emAnalyticsFolder.getNameNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetNameSubsystem(){
        String nameSubsystem = "nameSubsystemxx";
        emAnalyticsFolder.setNameSubsystem(nameSubsystem);
        Assert.assertEquals(nameSubsystem,emAnalyticsFolder.getNameSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetOwner(){
        String owner = "ownerxx";
        emAnalyticsFolder.setOwner(owner);
        Assert.assertEquals(owner,emAnalyticsFolder.getOwner());
    }

    @Test (groups = {"s1"})
    public void testGetSystemFolder(){
        BigDecimal systemFolder = BigDecimal.valueOf(12341234L);
        emAnalyticsFolder.setSystemFolder(systemFolder);
        Assert.assertEquals(systemFolder,emAnalyticsFolder.getSystemFolder());
    }

    @Test (groups = {"s1"})
    public void testGetUiHidden(){
        BigDecimal uiHidden = BigDecimal.valueOf(12341234L);
        emAnalyticsFolder.setUiHidden(uiHidden);
        Assert.assertEquals(uiHidden,emAnalyticsFolder.getUiHidden());
    }
}
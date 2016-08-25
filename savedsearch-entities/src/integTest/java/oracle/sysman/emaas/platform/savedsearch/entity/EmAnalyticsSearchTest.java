package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s1"})
public class EmAnalyticsSearchTest {
    private EmAnalyticsSearch emAnalyticsSearch;

    @BeforeMethod
    public void setUp(){
        emAnalyticsSearch = new EmAnalyticsSearch();
        emAnalyticsSearch.setId(123412341234L);
        emAnalyticsSearch.setOwner("ownerxx");
    }

    @Test (groups = {"s1"})
    public void testGetAccessDate(){
        Assert.assertNull(emAnalyticsSearch.getAccessDate());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        Assert.assertEquals(now,emAnalyticsSearch.getAccessDate());
    }

    @Test (groups = {"s1"})
    public void testGetAccessedBy(){
        Assert.assertNull(emAnalyticsSearch.getAccessedBy());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        Assert.assertEquals(emAnalyticsSearch.getLastAccess().getAccessedBy(),emAnalyticsSearch.getAccessedBy());
    }

    @Test (groups = {"s1"})
    public void testGetCreationDate(){
        Date now = new Date();
        emAnalyticsSearch.setCreationDate(now);
        Assert.assertEquals(now,emAnalyticsSearch.getCreationDate());
    }

    @Test (groups = {"s1"})
    public void testGetDeleted(){
        long deleted = 123412341234L;
        emAnalyticsSearch.setDeleted(deleted);
        Assert.assertEquals(deleted,emAnalyticsSearch.getDeleted());
    }

    @Test (groups = {"s1"})
    public void testGetDescription(){
        String description = "descriptionxx";
        emAnalyticsSearch.setDescription(description);
        Assert.assertEquals(description,emAnalyticsSearch.getDescription());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionNlsid(){
        String descriptionNlsid = "descriptionNlsidxx";
        emAnalyticsSearch.setDescriptionNlsid(descriptionNlsid);
        Assert.assertEquals(descriptionNlsid,emAnalyticsSearch.getDescriptionNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetDescriptionSubsystem(){
        String descriptionSubsystem = "descriptionSubsystemxx";
        emAnalyticsSearch.setDescriptionSubsystem(descriptionSubsystem);
        Assert.assertEquals(descriptionSubsystem,emAnalyticsSearch.getDescriptionSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsCategory(){
        EmAnalyticsCategory emAnalyticsCategory = new EmAnalyticsCategory();
        emAnalyticsSearch.setEmAnalyticsCategory(emAnalyticsCategory);
        Assert.assertEquals(emAnalyticsCategory,emAnalyticsSearch.getEmAnalyticsCategory());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsFolder(){
        EmAnalyticsFolder emaFolder = new EmAnalyticsFolder();
        emAnalyticsSearch.setEmAnalyticsFolder(emaFolder);
        Assert.assertEquals(emaFolder,emAnalyticsSearch.getEmAnalyticsFolder());
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsSearchParams(){
        emAnalyticsSearch.setEmAnalyticsSearchParams(null);
        Assert.assertNotNull(emAnalyticsSearch.getEmAnalyticsSearchParams());

        HashSet<EmAnalyticsSearchParam> emAnalyticsSearches = new HashSet<>();
        emAnalyticsSearch.setEmAnalyticsSearchParams(emAnalyticsSearches);
        Assert.assertEquals(emAnalyticsSearches,emAnalyticsSearch.getEmAnalyticsSearchParams());
    }

    @Test (groups = {"s1"})
    public void testGetEmPluginId(){
        String plugid = "pluginidxx";
        emAnalyticsSearch.setEmPluginId(plugid);
        Assert.assertEquals(plugid,emAnalyticsSearch.getEmPluginId());
    }

    @Test (groups = {"s1"})
    public void testGetId(){
        long id = 1234123412341234L;
        emAnalyticsSearch.setId(id);
        Assert.assertEquals(id,emAnalyticsSearch.getId());
    }

    @Test (groups = {"s1"})
    public void testGetIsLocked(){
        BigDecimal isLocked = BigDecimal.valueOf(1.22);
        emAnalyticsSearch.setIsLocked(isLocked);
        Assert.assertEquals(isLocked,emAnalyticsSearch.getIsLocked());
    }

    @Test (groups = {"s1"})
    public void testGetIsWidget(){
        long isWidget = 333L;
        emAnalyticsSearch.setIsWidget(isWidget);
        Assert.assertEquals(isWidget,emAnalyticsSearch.getIsWidget());
    }

    @Test (groups = {"s1"})
    public void testGetLastAccess(){
        Assert.assertNull(emAnalyticsSearch.getLastAccess());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        Assert.assertNotNull(emAnalyticsSearch.getLastAccess());
    }

    @Test (groups = {"s1"})
    public void testGetLastModificationDate(){
        Date now = new Date();
        emAnalyticsSearch.setLastModificationDate(now);
        Assert.assertEquals(now,emAnalyticsSearch.getLastModificationDate());
    }

    @Test (groups = {"s1"})
    public void testGetLastModifiedBy(){
        String lastModifiedBy = "lastModifiedByxx";
        emAnalyticsSearch.setLastModifiedBy(lastModifiedBy);
        Assert.assertEquals(lastModifiedBy,emAnalyticsSearch.getLastModifiedBy());
    }

    @Test (groups = {"s1"})
    public void testGetMetadataClob(){
        String metadataClob = "metadataClobxx";
        emAnalyticsSearch.setMetadataClob(metadataClob);
        Assert.assertEquals(metadataClob,emAnalyticsSearch.getMetadataClob());
    }

    @Test (groups = {"s1"})
    public void testGetName(){
        String name = "namexx";
        emAnalyticsSearch.setName(name);
        Assert.assertEquals(name,emAnalyticsSearch.getName());
    }

    @Test (groups = {"s1"})
    public void testGetNameNlsid(){
        String nameNlsid = "nameMlsidxx";
        emAnalyticsSearch.setNameNlsid(nameNlsid);
        Assert.assertEquals(nameNlsid,emAnalyticsSearch.getNameNlsid());
    }

    @Test (groups = {"s1"})
    public void testGetNameSubsystem(){
        String nameSubsystem = "nameSubsystemxx";
        emAnalyticsSearch.setNameSubsystem(nameSubsystem);
        Assert.assertEquals(nameSubsystem,emAnalyticsSearch.getNameSubsystem());
    }

    @Test (groups = {"s1"})
    public void testGetObjectId(){
        Assert.assertNull(emAnalyticsSearch.getObjectId());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        emAnalyticsSearch.getAccessDate();
        Assert.assertNotNull(emAnalyticsSearch.getObjectId());
    }

    @Test (groups = {"s1"})
    public void testGetObjectType(){
        Assert.assertNull(emAnalyticsSearch.getObjectType());

        Date now = new Date();
        emAnalyticsSearch.setAccessDate(now);
        emAnalyticsSearch.getAccessDate();
        Assert.assertNotNull(emAnalyticsSearch.getObjectType());
    }

    @Test (groups = {"s1"})
    public void testGetOwner(){
        String owner = "ownerxx";
        emAnalyticsSearch.setOwner(owner);
        Assert.assertEquals(owner,emAnalyticsSearch.getOwner());
    }

    @Test (groups = {"s1"})
    public void testGetSearchDisplayStr(){
        String searchDisplayStr = "searchDisplayStrxx";
        emAnalyticsSearch.setSearchDisplayStr(searchDisplayStr);
        Assert.assertEquals(searchDisplayStr,emAnalyticsSearch.getSearchDisplayStr());
    }

    @Test (groups = {"s1"})
    public void testGetSearchGuid(){
        byte[] searchGuid = {1,1,2,2,3,3};
        emAnalyticsSearch.setSearchGuid(searchGuid);
        Assert.assertEquals(searchGuid,emAnalyticsSearch.getSearchGuid());
    }

    @Test (groups = {"s1"})
    public void testGetSystemSearch(){
        BigDecimal systemSearch = BigDecimal.valueOf(123412341234L);
        emAnalyticsSearch.setSystemSearch(systemSearch);
        Assert.assertEquals(systemSearch,emAnalyticsSearch.getSystemSearch());
    }

    @Test (groups = {"s1"})
    public void testGetUiHidden(){
        BigDecimal uiHidden = BigDecimal.valueOf(123412341234L);
        emAnalyticsSearch.setUiHidden(uiHidden);
        Assert.assertEquals(uiHidden,emAnalyticsSearch.getUiHidden());
    }

    @Test (groups = {"s1"})
    public void testGetDASHBOARDINELIGIBLE(){
        emAnalyticsSearch.setDASHBOARDINELIGIBLE("1");
        Assert.assertEquals("1", emAnalyticsSearch.getDASHBOARDINELIGIBLE());
    }

    @Test (groups = {"s1"})
    public void testGetNAMEWIDGETSOURCE(){
        emAnalyticsSearch.setNAMEWIDGETSOURCE("namesource");
        Assert.assertEquals("namesource", emAnalyticsSearch.getNAMEWIDGETSOURCE());
    }

    @Test (groups = {"s1"})
    public void testGetPROVIDERASSETROOT(){
        emAnalyticsSearch.setPROVIDERASSETROOT("assetroot");
        Assert.assertEquals("assetroot", emAnalyticsSearch.getPROVIDERASSETROOT());
    }

    @Test (groups = {"s1"})
    public void testGetPROVIDERNAME(){
        emAnalyticsSearch.setPROVIDERNAME("name");
        Assert.assertEquals("name", emAnalyticsSearch.getPROVIDERNAME());
    }

    @Test (groups = {"s1"})
    public void testGetPROVIDERVERSION(){
        emAnalyticsSearch.setPROVIDERVERSION("version");
        Assert.assertEquals("version", emAnalyticsSearch.getPROVIDERVERSION());
    }
    @Test(groups = {"s1"})
    public void testGetWidgetParams(){
        emAnalyticsSearch.setWIDGETDEFAULTHEIGHT(1L);
        emAnalyticsSearch.getWIDGETDEFAULTHEIGHT();
        emAnalyticsSearch.setWIDGETDEFAULTWIDTH(1L);
        emAnalyticsSearch.getWIDGETDEFAULTWIDTH();
        emAnalyticsSearch.setWIDGETGROUPNAME("groupname");
        emAnalyticsSearch.getWIDGETGROUPNAME();
        emAnalyticsSearch.setWIDGETICON("icon");
        emAnalyticsSearch.getWIDGETICON();
        emAnalyticsSearch.setWIDGETKOCNAME("kocname");
        emAnalyticsSearch.getWIDGETKOCNAME();
        emAnalyticsSearch.setWIDGETLINKEDDASHBOARD(1L);
        emAnalyticsSearch.getWIDGETLINKEDDASHBOARD();
        emAnalyticsSearch.setWIDGETSCREENSHOTHREF("href");
        emAnalyticsSearch.getWIDGETSCREENSHOTHREF();
        emAnalyticsSearch.setWIDGETSUPPORTTIMECONTROL("control");
        emAnalyticsSearch.getWIDGETSUPPORTTIMECONTROL();
        emAnalyticsSearch.setWIDGETTEMPLATE("widget_template");
        emAnalyticsSearch.getWIDGETTEMPLATE();
        emAnalyticsSearch.setWIDGETVIEWMODEL("model");
        emAnalyticsSearch.getWIDGETVIEWMODEL();
    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import org.testng.annotations.Test;

/**
 * Created by chehao on 9/23/2017 16:21.
 */
@Test(groups = { "s1" })
public class SavedSearchFolderRowEntityTest {
    @Test
    public void testSavedSearchFolderRowEntity(){
        SavedSearchFolderRowEntity s = new SavedSearchFolderRowEntity();
        s.equals(s);
        s.equals(null);
        s.equals("");

        SavedSearchFolderRowEntity s2 = new SavedSearchFolderRowEntity();
        s.equals(s2);

        s2.setCreationDate("");
        s2.setDeleted("");
        s2.setDescriptionNlsid("");
        s2.setDescriptionSubsystem("");
        s2.setEmPluginId("");
        s2.setFolderId("");
        s2.setLastModificationDate("");
        s2.setLastModifiedBy("");
        s2.setName("");
        s2.setNameNlsid("");
        s2.setNameSubsystem("");
        s2.setOwner("");
        s2.setParentId("");
        s2.setSystemFolder(0);
        s2.setUiHidden(0);
        s2.setTenantId(0L);

        s.equals(s2);
        s2.equals(s);

        s.setCreationDate("");
        s.setDeleted("");
        s.setDescriptionNlsid("");
        s.setDescriptionSubsystem("");
        s.setEmPluginId("");
        s.setFolderId("");
        s.setLastModificationDate("");
        s.setLastModifiedBy("");
        s.setName("");
        s.setNameNlsid("");
        s.setNameSubsystem("");
        s.setOwner("");
        s.setParentId("");
        s.setSystemFolder(0);
        s.setUiHidden(0);
        s.setTenantId(0L);

        s.equals(s2);
        s2.equals(s);

        s2.setCreationDate("");
        s2.setDeleted("");
        s2.setDescriptionNlsid("");
        s2.setDescriptionSubsystem("");
        s2.setEmPluginId("");
        s2.setFolderId("");
        s2.setLastModificationDate("");
        s2.setLastModifiedBy("");
        s2.setName("");
        s2.setNameNlsid("");
        s2.setNameSubsystem("");
        s2.setOwner("");
        s2.setParentId("");
        s2.setSystemFolder(0);
        s2.setUiHidden(1);
        s2.setTenantId(0L);

        s.equals(s2);
        s2.equals(s);


    }
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import org.testng.annotations.Test;

/**
 * Created by chehao on 9/23/2017 12:51.
 */
@Test(groups = { "s1" })
public class SavedSearchSearchRowEntityTest {
    @Test
    public void testSavedSearchSearchRowEntity(){
        SavedSearchSearchRowEntity s = new SavedSearchSearchRowEntity();

        SavedSearchSearchRowEntity s2 = new SavedSearchSearchRowEntity();

        s.equals(s);
        s.equals(s2);
        s.equals(null);
        s.equals("1");

        s2.setTenantId(0L);
        s2.setSystemSearch(0);
        s2.setSearchId("");
        s2.setSearchDisplayStr("");
        s2.setProviderVersion("");
        s2.setProviderName("");
        s2.setProviderAssetRoot("");
        s2.setLastModifiedBy("");
        s2.setFolderId("");
        s2.setDashboardIneligible("");
        s2.setCreationDate("");
        s2.setCategoryId("");
        s2.setDeleted("");
        s2.setDescription("");
        s2.setIsLocked(0);
        s2.setIsWidget(0);
        s2.setLastModificationDate("");
        s2.setWidgetViewModel("");
        s2.setWidgetTemplate("");
        s2.setWidgetScreenshotHref("");
        s2.setWidgetIcon("");
        s2.setWidgetKocName("");
        s2.setWidgetLinkedDashboard(0L);
        s2.setWidgetSupportTimeControl("");
        s2.setUiHidden(0);
        s2.setOwner("");
        s2.setSearchGuid("");
        s2.setName("");

        s.equals(s2);
        s2.equals(s);

        s.setTenantId(0L);
        s.setSystemSearch(0);
        s.setSearchId("");
        s.setSearchDisplayStr("");
        s.setProviderVersion("");
        s.setProviderName("");
        s.setProviderAssetRoot("");
        s.setLastModifiedBy("");
        s.setFolderId("");
        s.setDashboardIneligible("");
        s.setCreationDate("");
        s.setCategoryId("");
        s.setDeleted("");
        s.setDescription("");
        s.setIsLocked(0);
        s.setIsWidget(0);
        s.setLastModificationDate("");
        s.setWidgetViewModel("");
        s.setWidgetTemplate("");
        s.setWidgetScreenshotHref("");
        s.setWidgetIcon("");
        s.setWidgetKocName("");
        s.setWidgetLinkedDashboard(0L);
        s.setWidgetSupportTimeControl("");
        s.setUiHidden(0);
        s.setOwner("");
        s.setSearchGuid("");
        s.setName("");

        s.equals(s2);
        s2.equals(s);

        s2.setTenantId(0L);
        s2.setSystemSearch(01);
        s2.setSearchId("");
        s2.setSearchDisplayStr("");
        s2.setProviderVersion("");
        s2.setProviderName("");
        s2.setProviderAssetRoot("");
        s2.setLastModifiedBy("");
        s2.setFolderId("");
        s2.setDashboardIneligible("");
        s2.setCreationDate("");
        s2.setCategoryId("");
        s2.setDeleted("");
        s2.setDescription("");
        s2.setIsLocked(0);
        s2.setIsWidget(0);
        s2.setLastModificationDate("");
        s2.setWidgetViewModel("1");
        s2.setWidgetTemplate("");
        s2.setWidgetScreenshotHref("");
        s2.setWidgetIcon("");
        s2.setWidgetKocName("");
        s2.setWidgetLinkedDashboard(0L);
        s2.setWidgetSupportTimeControl("");
        s2.setUiHidden(1);
        s2.setOwner("");
        s2.setSearchGuid("");
        s2.setName("");

        s.equals(s2);
        s2.equals(s);




    }
}

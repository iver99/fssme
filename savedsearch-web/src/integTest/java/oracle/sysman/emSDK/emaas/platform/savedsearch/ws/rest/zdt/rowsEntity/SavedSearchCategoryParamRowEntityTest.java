package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2017/1/12.
 */
@Test(groups = {"s1"})
public class SavedSearchCategoryParamRowEntityTest {
    private SavedSearchCategoryParamRowEntity savedSearchCategoryParamRowEntity = new SavedSearchCategoryParamRowEntity(null, null, null, null,null, null);
    @Test
    public void testEquals() throws Exception {
        savedSearchCategoryParamRowEntity.equals(null);
        savedSearchCategoryParamRowEntity.equals(savedSearchCategoryParamRowEntity);
        savedSearchCategoryParamRowEntity.equals(new Object());
        savedSearchCategoryParamRowEntity.equals(new SavedSearchCategoryParamRowEntity(null, null, null, null,null, null));
        savedSearchCategoryParamRowEntity.hashCode();
        savedSearchCategoryParamRowEntity.toString();
        savedSearchCategoryParamRowEntity.setCategoryId(null);
        savedSearchCategoryParamRowEntity.getCategoryId();
        savedSearchCategoryParamRowEntity.setCreationDate(null);
        savedSearchCategoryParamRowEntity.getCreationDate();
        savedSearchCategoryParamRowEntity.setLastModificationDate(null);
        savedSearchCategoryParamRowEntity.getLastModificationDate();
        savedSearchCategoryParamRowEntity.setName(null);
        savedSearchCategoryParamRowEntity.getName();
        savedSearchCategoryParamRowEntity.setTenantId(null);
        savedSearchCategoryParamRowEntity.getTenantId();
        savedSearchCategoryParamRowEntity.setValue(null);
        savedSearchCategoryParamRowEntity.getValue();
    }
    private SavedSearchCategoryRowEntity savedSearchCategoryRowEntity = new SavedSearchCategoryRowEntity(null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null);
    @Test
    public void testSavedSearchCategoryRowEntity(){
        savedSearchCategoryRowEntity.equals(null);
        savedSearchCategoryRowEntity.equals(savedSearchCategoryParamRowEntity);
        savedSearchCategoryRowEntity.equals(new SavedSearchCategoryRowEntity(null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null));
        savedSearchCategoryRowEntity.equals(new Object());
        savedSearchCategoryRowEntity.setCategoryId(null);
        savedSearchCategoryRowEntity.getCategoryId();
        savedSearchCategoryRowEntity.setDescription(null);
        savedSearchCategoryRowEntity.getDescription();
        savedSearchCategoryRowEntity.setDescriptionSubsystem(null);
        savedSearchCategoryRowEntity.getDescriptionSubsystem();
        savedSearchCategoryRowEntity.setEmPluginId(null);
        savedSearchCategoryRowEntity.getEmPluginId();
        savedSearchCategoryRowEntity.setName(null);
        savedSearchCategoryRowEntity.getName();
        savedSearchCategoryRowEntity.setNameNlsid(null);
        savedSearchCategoryRowEntity.getNameNlsid();
        savedSearchCategoryRowEntity.setNameSubsystem(null);
        savedSearchCategoryRowEntity.getNameSubsystem();
        savedSearchCategoryRowEntity.setOwner(null);
        savedSearchCategoryRowEntity.getOwner();
        savedSearchCategoryRowEntity.setDeleted(null);
        savedSearchCategoryRowEntity.getDeleted();
        savedSearchCategoryRowEntity.setProviderName(null);
        savedSearchCategoryRowEntity.getProviderName();
        savedSearchCategoryRowEntity.setProviderVersion(null);
        savedSearchCategoryRowEntity.getProviderVersion();
        savedSearchCategoryRowEntity.setProviderDiscovery(null);
        savedSearchCategoryRowEntity.getProviderDiscovery();
        savedSearchCategoryRowEntity.setProviderAssetRoot(null);
        savedSearchCategoryRowEntity.getProviderAssetRoot();
        savedSearchCategoryRowEntity.setLastModificationDate(null);
        savedSearchCategoryRowEntity.getLastModificationDate();
        savedSearchCategoryRowEntity.setTenantId(null);
        savedSearchCategoryRowEntity.getTenantId();
        savedSearchCategoryRowEntity.setCreationDate(null);
        savedSearchCategoryRowEntity.getCreationDate();
        savedSearchCategoryRowEntity.setDefaultFolderId(null);
        savedSearchCategoryRowEntity.getDefaultFolderId();
        savedSearchCategoryRowEntity.setDashboardIneligible(null);
        savedSearchCategoryRowEntity.getDashboardIneligible();
        savedSearchCategoryRowEntity.setDescriptionNlsid(null);
        savedSearchCategoryRowEntity.getDescriptionNlsid();
        savedSearchCategoryRowEntity.hashCode();
        savedSearchCategoryRowEntity.toString();
    }

    private SavedSearchFolderRowEntity savedSearchFolderRowEntity = new SavedSearchFolderRowEntity(null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null, null);
    @Test
    public void testSavedSearchFolderRowEntity(){
        savedSearchFolderRowEntity.equals(null);
        savedSearchFolderRowEntity.equals(new Object());
        savedSearchFolderRowEntity.equals(savedSearchFolderRowEntity);
        savedSearchFolderRowEntity.equals(new SavedSearchFolderRowEntity(null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null, null));
        savedSearchFolderRowEntity.setDescriptionNlsid(null);
        savedSearchFolderRowEntity.getDescriptionNlsid();
        savedSearchFolderRowEntity.setDescription(null);
        savedSearchFolderRowEntity.getDescription();
        savedSearchFolderRowEntity.setTenantId(null);
        savedSearchFolderRowEntity.getTenantId();
        savedSearchFolderRowEntity.setName(null);
        savedSearchFolderRowEntity.getName();
        savedSearchFolderRowEntity.setLastModificationDate(null);
        savedSearchFolderRowEntity.getLastModificationDate();
        savedSearchFolderRowEntity.setCreationDate(null);
        savedSearchFolderRowEntity.getCreationDate();
        savedSearchFolderRowEntity.setDeleted(null);
        savedSearchFolderRowEntity.getDeleted();
        savedSearchFolderRowEntity.setDescriptionSubsystem(null);
        savedSearchFolderRowEntity.getDescriptionSubsystem();
        savedSearchFolderRowEntity.setEmPluginId(null);
        savedSearchFolderRowEntity.getEmPluginId();
        savedSearchFolderRowEntity.setFolderId(null);
        savedSearchFolderRowEntity.getFolderId();
        savedSearchFolderRowEntity.setNameNlsid(null);
        savedSearchFolderRowEntity.getNameNlsid();
        savedSearchFolderRowEntity.setNameSubsystem(null);
        savedSearchFolderRowEntity.getNameSubsystem();
        savedSearchFolderRowEntity.setParentId(null);
        savedSearchFolderRowEntity.getParentId();
        savedSearchFolderRowEntity.setOwner(null);
        savedSearchFolderRowEntity.getOwner();
        savedSearchFolderRowEntity.setUiHidden(null);
        savedSearchFolderRowEntity.getUiHidden();
        savedSearchFolderRowEntity.setSystemFolder(null);
        savedSearchFolderRowEntity.getSystemFolder();
        savedSearchFolderRowEntity.setLastModifiedBy(null);
        savedSearchFolderRowEntity.getLastModifiedBy();
        savedSearchFolderRowEntity.hashCode();
        savedSearchFolderRowEntity.toString();

    }

    private SavedSearchSchemaVerRowEntity savedSearchSchemaVerRowEntity = new SavedSearchSchemaVerRowEntity(null, null, null, null);

    @Test
    public void testSavedSearchSchemaVerRowEntity(){
        savedSearchSchemaVerRowEntity.equals(null);
        savedSearchSchemaVerRowEntity.equals(new Object());
        savedSearchSchemaVerRowEntity.equals(savedSearchSchemaVerRowEntity);
        savedSearchSchemaVerRowEntity.equals(new SavedSearchSchemaVerRowEntity(null, null, null, null));
        savedSearchSchemaVerRowEntity.setCreationDate(null);
        savedSearchSchemaVerRowEntity.getCreationDate();
        savedSearchSchemaVerRowEntity.setLastModificationDate(null);
        savedSearchSchemaVerRowEntity.getLastModificationDate();
        savedSearchSchemaVerRowEntity.setMajor(null);
        savedSearchSchemaVerRowEntity.getMajor();
        savedSearchSchemaVerRowEntity.setMinor(null);
        savedSearchSchemaVerRowEntity.getMinor();
        savedSearchSchemaVerRowEntity.hashCode();
        savedSearchSchemaVerRowEntity.toString();
    }

    private SavedSearchSearchParamRowEntity savedSearchSearchParamRowEntity = new SavedSearchSearchParamRowEntity(null, null, null, null, null, null, null, null, null,0);
    @Test
    public void testSavedSearchSearchParamRowEntity(){
        savedSearchSearchParamRowEntity.equals(null);
        savedSearchSearchParamRowEntity.equals(new Object());
        savedSearchSearchParamRowEntity.equals(savedSearchSearchParamRowEntity);
        savedSearchSearchParamRowEntity.equals( new SavedSearchSearchParamRowEntity(null, null, null, null, null, null, null, null, null,0));
        savedSearchSearchParamRowEntity.setLastModificationDate(null);
        savedSearchSearchParamRowEntity.setCreationDate(null);
        savedSearchSearchParamRowEntity.setName(null);
        savedSearchSearchParamRowEntity.setLastModificationDate(null);
        savedSearchSearchParamRowEntity.setParamAttributes(null);
        savedSearchSearchParamRowEntity.setParamType(null);
        savedSearchSearchParamRowEntity.setParamValueClob(null);
        savedSearchSearchParamRowEntity.setParamValueStr(null);
        savedSearchSearchParamRowEntity.setSearchId(null);
        savedSearchSearchParamRowEntity.setTenantId(null);
        savedSearchSearchParamRowEntity.getLastModificationDate();
        savedSearchSearchParamRowEntity.getCreationDate();
        savedSearchSearchParamRowEntity.getName();
        savedSearchSearchParamRowEntity.getLastModificationDate();
        savedSearchSearchParamRowEntity.getParamAttributes();
        savedSearchSearchParamRowEntity.getParamType();
        savedSearchSearchParamRowEntity.getParamValueClob();
        savedSearchSearchParamRowEntity.getParamValueStr();
        savedSearchSearchParamRowEntity.getSearchId();
        savedSearchSearchParamRowEntity.getTenantId();
        savedSearchSearchParamRowEntity.getTenantId();
        savedSearchSearchParamRowEntity.hashCode();
        savedSearchSearchParamRowEntity.toString();
    }
    private SavedSearchSearchRowEntity savedSearchSearchRowEntity = new SavedSearchSearchRowEntity(null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null);
    @Test
    public void testSavedSearchSearchRowEntity(){
        savedSearchSearchRowEntity.equals(null);
        savedSearchSearchRowEntity.equals(savedSearchSearchRowEntity);
        savedSearchSearchRowEntity.equals(new Object());
        savedSearchSearchRowEntity.equals(new SavedSearchSearchRowEntity(null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null));
        savedSearchSearchRowEntity.setSearchId(null);
        savedSearchSearchRowEntity.setDescription(null);
       
        savedSearchSearchRowEntity.setIsLocked(null);
        savedSearchSearchRowEntity.setMetadataClob(null);
        savedSearchSearchRowEntity.setName(null);
        savedSearchSearchRowEntity.setOwner(null);
        savedSearchSearchRowEntity.setSearchDisplayStr(null);
        savedSearchSearchRowEntity.setSystemSearch(null);
        savedSearchSearchRowEntity.setUiHidden(null);
        savedSearchSearchRowEntity.setDeleted(null);
        savedSearchSearchRowEntity.setIsWidget(null);
        savedSearchSearchRowEntity.setCreationDate(null);
        savedSearchSearchRowEntity.setLastModificationDate(null);
        savedSearchSearchRowEntity.setNameWidgetSource(null);
        savedSearchSearchRowEntity.setWidgetGroupName(null);
        savedSearchSearchRowEntity.setWidgetScreenshotHref(null);
        savedSearchSearchRowEntity.setWidgetIcon(null);
        savedSearchSearchRowEntity.setWidgetKocName(null);
        savedSearchSearchRowEntity.setWidgetViewModel(null);
        savedSearchSearchRowEntity.setWidgetTemplate(null);
        savedSearchSearchRowEntity.setWidgetSupportTimeControl(null);
        savedSearchSearchRowEntity.setWidgetLinkedDashboard(null);
        savedSearchSearchRowEntity.setWidgetDefaulWidth(null);
        savedSearchSearchRowEntity.setWidgetDefaultHeight(null);
        savedSearchSearchRowEntity.setDashboardIneligible(null);
        savedSearchSearchRowEntity.setTenantId(null);
        savedSearchSearchRowEntity.setFolderId(null);
        savedSearchSearchRowEntity.setCategoryId(null);
        savedSearchSearchRowEntity.setProviderName(null);
        savedSearchSearchRowEntity.setProviderVersion(null);
        savedSearchSearchRowEntity.setProviderAssetRoot(null);
        savedSearchSearchRowEntity.setLastModifiedBy(null);
        savedSearchSearchRowEntity.getSearchId();
        savedSearchSearchRowEntity.getDescription();
        savedSearchSearchRowEntity.getIsLocked();
        savedSearchSearchRowEntity.getMetadataClob();
        savedSearchSearchRowEntity.getName();
        savedSearchSearchRowEntity.getOwner();
        savedSearchSearchRowEntity.getSearchDisplayStr();
        savedSearchSearchRowEntity.getSystemSearch();
        savedSearchSearchRowEntity.getUiHidden();
        savedSearchSearchRowEntity.getDeleted();
        savedSearchSearchRowEntity.getIsWidget();
        savedSearchSearchRowEntity.getCreationDate();
        savedSearchSearchRowEntity.getLastModificationDate();
        savedSearchSearchRowEntity.getNameWidgetSource();
        savedSearchSearchRowEntity.getWidgetGroupName();
        savedSearchSearchRowEntity.getWidgetScreenshotHref();
        savedSearchSearchRowEntity.getWidgetIcon();
        savedSearchSearchRowEntity.getWidgetKocName();
        savedSearchSearchRowEntity.getWidgetViewModel();
        savedSearchSearchRowEntity.getWidgetTemplate();
        savedSearchSearchRowEntity.getWidgetSupportTimeControl();
        savedSearchSearchRowEntity.getWidgetLinkedDashboard();
        savedSearchSearchRowEntity.getWidgetDefaulWidth();
        savedSearchSearchRowEntity.getWidgetDefaultHeight();
        savedSearchSearchRowEntity.getDashboardIneligible();
        savedSearchSearchRowEntity.getTenantId();
        savedSearchSearchRowEntity.getFolderId();
        savedSearchSearchRowEntity.getCategoryId();
        savedSearchSearchRowEntity.getProviderName();
        savedSearchSearchRowEntity.getProviderVersion();
        savedSearchSearchRowEntity.getProviderAssetRoot();
        savedSearchSearchRowEntity.getLastModifiedBy();
        savedSearchSearchRowEntity.hashCode();
        savedSearchSearchRowEntity.toString();


    }
    private ZDTTableRowEntity zdtTableRowEntity = new ZDTTableRowEntity(null, null, null, null, null, null);
    @Test
    public void testZDTTableRowEntity(){
        zdtTableRowEntity.equals(zdtTableRowEntity);
        zdtTableRowEntity.equals(null);
        zdtTableRowEntity.equals(new Object());
        zdtTableRowEntity.equals(new ZDTTableRowEntity(null, null, null, null, null, null));
        zdtTableRowEntity.setSavedSearchCategory(null);
        zdtTableRowEntity.setSavedSearchCategoryParams(null);
        zdtTableRowEntity.setSavedSearchFoldersy(null);
        zdtTableRowEntity.setSavedSearchSchemaVer(null);
        zdtTableRowEntity.setSavedSearchSearch(null);
        zdtTableRowEntity.setSavedSearchSearchParams(null);
        zdtTableRowEntity.getSavedSearchCategory();
        zdtTableRowEntity.getSavedSearchCategoryParams();
        zdtTableRowEntity.getSavedSearchFoldersy();
        zdtTableRowEntity.getSavedSearchSchemaVer();
        zdtTableRowEntity.getSavedSearchSearch();
        zdtTableRowEntity.getSavedSearchSearchParams();

        zdtTableRowEntity.hashCode();
        zdtTableRowEntity.toString();
    }
}
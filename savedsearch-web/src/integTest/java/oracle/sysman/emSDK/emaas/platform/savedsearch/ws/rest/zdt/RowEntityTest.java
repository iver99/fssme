package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class RowEntityTest {
	SavedSearchFolderRowEntity folderEntity = new SavedSearchFolderRowEntity("1", "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", "lastModifiedBy",
			"lastModificationDate", "name", "nameNlsid",
			"nameSubsystem", "owner", new Integer("1"),
			new Integer("1"), "1", 1L,
			"1", "creationDate");
	SavedSearchFolderRowEntity folderEntity2 = new SavedSearchFolderRowEntity("1", "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", "lastModifiedBy",
			"lastModificationDate", "name", "nameNlsid",
			"nameSubsystem", "owner", new Integer("1"),
			new Integer("1"), "1", 1L,
			"1", "creationDate");
	SavedSearchSearchParamRowEntity searchParam = new SavedSearchSearchParamRowEntity("1","name",
			"paramAttributes", 1L, "paramValueClob",
			"paramValueStr", 1L, "creationDate",
			"lastModificationDate", 0);
	SavedSearchSearchParamRowEntity searchParam2 = new SavedSearchSearchParamRowEntity("1","name",
			"paramAttributes", 1L, "paramValueClob",
			"paramValueStr", 1L, "creationDate",
			"lastModificationDate", 0);
	SavedSearchSearchRowEntity searchEntity = new SavedSearchSearchRowEntity("1", "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", new Integer("1"), "lastModifiedBy",
			"metadataClob", "name", "nameNlsid",
			"nameSubsystem", "owner", "searchDisplayStr",
			"searchGuid", new Integer("1"), new Integer("1"),
			"1", new Integer("1"), "creationDate",
			"lastModificationDate", "nameWidgetSource",
			"widgetGroupName", "widgetScreenshotHref",
			"widgetIcon", "widgetKocName", "widgetViewModel",
			" widgetTemplate", "widgetSupportTimeControl",
			1L, 1L,
			1L, "dashboardIneligible",
			1L, "1", "1",
			"providerName", "providerVersion",
			"providerAssetRoot");
	SavedSearchSearchRowEntity searchEntity2 = new SavedSearchSearchRowEntity("1", "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", new Integer("1"), "lastModifiedBy",
			"metadataClob", "name", "nameNlsid",
			"nameSubsystem", "owner", "searchDisplayStr",
			"searchGuid", new Integer("1"), new Integer("1"),
			"1", new Integer("1"), "creationDate",
			"lastModificationDate", "nameWidgetSource",
			"widgetGroupName", "widgetScreenshotHref",
			"widgetIcon", "widgetKocName", "widgetViewModel",
			" widgetTemplate", "widgetSupportTimeControl",
			1L, 1L,
			1L, "dashboardIneligible",
			1L, "1", "1",
			"providerName", "providerVersion",
			"providerAssetRoot");
	
	@Test
	public void testEquals() {
		List<SavedSearchFolderRowEntity> folderList1 = new ArrayList<SavedSearchFolderRowEntity>();
		folderList1.add(folderEntity);
		List<SavedSearchSearchParamRowEntity> searchParamList1 = new ArrayList<SavedSearchSearchParamRowEntity>();
		searchParamList1.add(searchParam);
		List<SavedSearchSearchRowEntity> searchList1 = new ArrayList<SavedSearchSearchRowEntity>();
		searchList1.add(searchEntity);
		
		ZDTTableRowEntity zdtTaleRow1 = new ZDTTableRowEntity(
				folderList1,
				searchParamList1,
				searchList1);
		
		List<SavedSearchFolderRowEntity> folderList2 = new ArrayList<SavedSearchFolderRowEntity>();
		folderList2.add(folderEntity2);
		List<SavedSearchSearchParamRowEntity> searchParamList2 = new ArrayList<SavedSearchSearchParamRowEntity>();
		searchParamList2.add(searchParam2);
		List<SavedSearchSearchRowEntity> searchList2= new ArrayList<SavedSearchSearchRowEntity>();
		searchList2.add(searchEntity2);
		
		ZDTTableRowEntity zdtTaleRow2 = new ZDTTableRowEntity(
				folderList2,
				searchParamList2,
				searchList2);
		
		Assert.assertEquals(zdtTaleRow1,zdtTaleRow2);
		searchParam.setSearchId("2");
		Assert.assertNotEquals(zdtTaleRow1,zdtTaleRow2);

	}
	
	
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSchemaVerRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class RowEntityTest {
	SavedSearchCategoryParamRowEntity categoryParamEntity = new SavedSearchCategoryParamRowEntity(new BigInteger("1"),
			"name","value", 1L, "creationDate",
			"lastModificationDate");
	SavedSearchCategoryParamRowEntity categoryParamEntity2 = new SavedSearchCategoryParamRowEntity(new BigInteger("1"),
			"name1","value1", 2L, "creationDate1",
			"lastModificationDate1");
	SavedSearchCategoryRowEntity categoryEntity = new SavedSearchCategoryRowEntity(new BigInteger("1"),
			"description", "descriptionNlsid",
			"descriptionSubsystem", "emPluginId", "name",
			"nameNlsid", "nameSubsystem", "owner",
			new BigInteger("1"), "providerName", "providerVersion",
			"providerDiscovery", "providerAssetRoot",
			"lastModificationDate", 1L, "creationDate",
			2l, "dashboardIneligible");
	SavedSearchCategoryRowEntity categoryEntity2 = new SavedSearchCategoryRowEntity(new BigInteger("2"),
			"description1", "descriptionNlsid1",
			"descriptionSubsystem1", "emPluginId1", "name1",
			"nameNlsid1", "nameSubsystem1", "owner1",
			new BigInteger("2"), "providerName1", "providerVersion1",
			"providerDiscovery1", "providerAssetRoot1",
			"lastModificationDate1", 2L, "creationDate1",
			2l, "dashboardIneligible");
	SavedSearchFolderRowEntity folderEntity = new SavedSearchFolderRowEntity(new BigInteger("1"), "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", "lastModifiedBy",
			"lastModificationDate", "name", "nameNlsid",
			"nameSubsystem", "owner", new Integer("1"),
			new Integer("1"), new BigInteger("1"), 1L,
			new BigInteger("1"), "creationDate");
	SavedSearchFolderRowEntity folderEntity2 = new SavedSearchFolderRowEntity(new BigInteger("1"), "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", "lastModifiedBy",
			"lastModificationDate", "name", "nameNlsid",
			"nameSubsystem", "owner", new Integer("1"),
			new Integer("1"), new BigInteger("1"), 1L,
			new BigInteger("1"), "creationDate");
	SavedSearchSchemaVerRowEntity schemaVer = new SavedSearchSchemaVerRowEntity(1L, 1L,
			"creationDate", "lastModificationDate");
	SavedSearchSchemaVerRowEntity schemaVer2 = new SavedSearchSchemaVerRowEntity(1L, 1L,
			"creationDate", "lastModificationDate");
	SavedSearchSearchParamRowEntity searchParam = new SavedSearchSearchParamRowEntity(new BigInteger("1"),"name",
			"paramAttributes", 1L, "paramValueClob",
			"paramValueStr", 1L, "creationDate",
			"lastModificationDate");
	SavedSearchSearchParamRowEntity searchParam2 = new SavedSearchSearchParamRowEntity(new BigInteger("1"),"name",
			"paramAttributes", 1L, "paramValueClob",
			"paramValueStr", 1L, "creationDate",
			"lastModificationDate");
	SavedSearchSearchRowEntity searchEntity = new SavedSearchSearchRowEntity(new BigInteger("1"), "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", new Integer("1"), "lastModifiedBy",
			"metadataClob", "name", "nameNlsid",
			"nameSubsystem", "owner", "searchDisplayStr",
			"searchGuid", new Integer("1"), new Integer("1"),
			new BigInteger("1"), new Integer("1"), "creationDate",
			"lastModificationDate", "nameWidgetSource",
			"widgetGroupName", "widgetScreenshotHref",
			"widgetIcon", "widgetKocName", "widgetViewModel",
			" widgetTemplate", "widgetSupportTimeControl",
			1L, 1L,
			1L, "dashboardIneligible",
			1L, new BigInteger("1"), new BigInteger("1"),
			"providerName", "providerVersion",
			"providerAssetRoot");
	SavedSearchSearchRowEntity searchEntity2 = new SavedSearchSearchRowEntity(new BigInteger("1"), "description",
			"descriptionNlsid", "descriptionSubsystem",
			"emPluginId", new Integer("1"), "lastModifiedBy",
			"metadataClob", "name", "nameNlsid",
			"nameSubsystem", "owner", "searchDisplayStr",
			"searchGuid", new Integer("1"), new Integer("1"),
			new BigInteger("1"), new Integer("1"), "creationDate",
			"lastModificationDate", "nameWidgetSource",
			"widgetGroupName", "widgetScreenshotHref",
			"widgetIcon", "widgetKocName", "widgetViewModel",
			" widgetTemplate", "widgetSupportTimeControl",
			1L, 1L,
			1L, "dashboardIneligible",
			1L, new BigInteger("1"), new BigInteger("1"),
			"providerName", "providerVersion",
			"providerAssetRoot");
	
	@Test
	public void testEquals() {
		List<SavedSearchCategoryParamRowEntity> categoryParamList1 = new ArrayList<SavedSearchCategoryParamRowEntity>();
		categoryParamList1.add(categoryParamEntity);
		List<SavedSearchCategoryRowEntity> categoryList1 = new ArrayList<SavedSearchCategoryRowEntity>();
		categoryList1.add(categoryEntity);
		List<SavedSearchFolderRowEntity> folderList1 = new ArrayList<SavedSearchFolderRowEntity>();
		folderList1.add(folderEntity);
		List<SavedSearchSchemaVerRowEntity> schemaVerList1 = new ArrayList<SavedSearchSchemaVerRowEntity>();
		schemaVerList1.add(schemaVer);
		List<SavedSearchSearchParamRowEntity> searchParamList1 = new ArrayList<SavedSearchSearchParamRowEntity>();
		searchParamList1.add(searchParam);
		List<SavedSearchSearchRowEntity> searchList1 = new ArrayList<SavedSearchSearchRowEntity>();
		searchList1.add(searchEntity);
		
		ZDTTableRowEntity zdtTaleRow1 = new ZDTTableRowEntity(categoryList1,
				categoryParamList1,
				folderList1,
				schemaVerList1,
				searchParamList1,
				searchList1);
		
		List<SavedSearchCategoryParamRowEntity> categoryParamList2 = new ArrayList<SavedSearchCategoryParamRowEntity>();
		categoryParamList2.add(categoryParamEntity2);
		List<SavedSearchCategoryRowEntity> categoryList2 = new ArrayList<SavedSearchCategoryRowEntity>();
		categoryList2.add(categoryEntity2);
		List<SavedSearchFolderRowEntity> folderList2 = new ArrayList<SavedSearchFolderRowEntity>();
		folderList2.add(folderEntity2);
		List<SavedSearchSchemaVerRowEntity> schemaVerList2 = new ArrayList<SavedSearchSchemaVerRowEntity>();
		schemaVerList2.add(schemaVer2);
		List<SavedSearchSearchParamRowEntity> searchParamList2 = new ArrayList<SavedSearchSearchParamRowEntity>();
		searchParamList2.add(searchParam2);
		List<SavedSearchSearchRowEntity> searchList2= new ArrayList<SavedSearchSearchRowEntity>();
		searchList2.add(searchEntity2);
		
		ZDTTableRowEntity zdtTaleRow2 = new ZDTTableRowEntity(categoryList2,
				categoryParamList2,
				folderList2,
				schemaVerList2,
				searchParamList2,
				searchList2);
		
		Assert.assertNotEquals(zdtTaleRow1,zdtTaleRow2);
	}
	
	
}

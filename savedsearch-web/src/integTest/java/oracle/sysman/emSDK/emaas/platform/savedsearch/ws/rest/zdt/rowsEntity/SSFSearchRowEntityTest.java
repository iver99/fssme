package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;
@Test(groups = { "s1" })
public class SSFSearchRowEntityTest {

	@Test
	public void testEquals() {
		SavedSearchSearchRowEntity entity1 = new SavedSearchSearchRowEntity("1", "description",
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
		SavedSearchSearchRowEntity entity2 = new SavedSearchSearchRowEntity("1", "description",
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
		Assert.assertEquals(entity1,entity2);
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setDashboardIneligible("ineligible");
		entity2.setDashboardIneligible("ineligible2");
		Assert.assertNotEquals(entity1,entity2);
		
		entity1.setDeleted("1");
		entity2.setDeleted("1");
		Assert.assertNotEquals(entity1,entity2);
		
		entity1.setDescription("des");
		entity2.setDescription("des");
		entity2.setDashboardIneligible("ineligible");
		Assert.assertEquals(entity1,entity2);
	
		entity1.setIsLocked(1);
		entity2.setIsLocked(1);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setIsWidget(1);
		entity2.setIsWidget(1);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1,entity2);
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		
		entity1.setCreationDate("creationDate");
		entity1.setDashboardIneligible("dashboardIneligible");
		entity1.setDescription("description");
		entity1.setIsLocked(1);
		entity1.setIsWidget(1);
		entity1.setNameWidgetSource("nameWidgetSource");
		entity1.setOwner("owner");
		entity1.setMetadataClob("metadataClob");
		entity1.setUiHidden(1);
		entity1.setWidgetViewModel("widgetViewModel");
		entity1.setWidgetTemplate("widgetTemplate");;
		entity1.setWidgetSupportTimeControl("widgetSupportTimeControl");
		entity1.setWidgetScreenshotHref("widgetScreenshotHref");
		entity1.setWidgetLinkedDashboard(1l);
		entity1.setWidgetKocName("widgetKocName");
		entity1.setWidgetIcon("widgetIcon");
		entity1.setWidgetGroupName("widgetGroupName");
		entity1.setWidgetDefaulWidth(1l);
		entity1.setWidgetDefaultHeight(1l);
		
		entity1.getCreationDate();
		entity1.getDashboardIneligible();
		entity1.getWidgetViewModel();
		entity1.getWidgetTemplate();
		entity1.getWidgetSupportTimeControl();
		entity1.getWidgetScreenshotHref();
		entity1.getWidgetLinkedDashboard();
		entity1.getWidgetKocName();
		entity1.getWidgetIcon();
		entity1.getWidgetGroupName();
		entity1.getWidgetDefaulWidth();
		entity1.getWidgetDefaultHeight();
		entity1.getUiHidden();
		entity1.getTenantId();
		entity1.getCreationDate();
		entity1.getDashboardIneligible();
		entity1.getDeleted();
		entity1.getDescription();
		
	}
	
	@Test
	public void testHashcode() {
		SavedSearchSearchRowEntity entity1 = new SavedSearchSearchRowEntity("1", "description",
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
		SavedSearchSearchRowEntity entity2 = new SavedSearchSearchRowEntity("1", "description",
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
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDashboardIneligible("ineligible");
		entity2.setDashboardIneligible("ineligible2");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDeleted("1");
		entity2.setDeleted("1");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDescription("des");
		entity2.setDescription("des");
		entity2.setDashboardIneligible("ineligible");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		entity1.setIsLocked(1);
		entity2.setIsLocked(1);
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setIsWidget(1);
		entity2.setIsWidget(1);
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
	}
}

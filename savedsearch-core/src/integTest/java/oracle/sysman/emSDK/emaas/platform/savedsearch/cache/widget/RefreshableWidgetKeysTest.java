package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.widget;

import org.testng.Assert;
import org.testng.annotations.Test;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.DefaultKey;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

public class RefreshableWidgetKeysTest
{
	@Test
	public void getUserName()
	{
		Tenant t = new Tenant(1L);
		RefreshableWidgetKeys rwk1 = new RefreshableWidgetKeys(t, "1", true, "user");
		Assert.assertEquals(rwk1.getUserName(), "user");
		RefreshableWidgetKeys rwk2 = new RefreshableWidgetKeys(t, "1", true, null);
		Assert.assertEquals(rwk2.getUserName(), null);
	}

	@Test
	public void testEquals()
	{
		Tenant t = new Tenant(1L);
		RefreshableWidgetKeys rwk1 = new RefreshableWidgetKeys(t, "1", true, "user");
		RefreshableWidgetKeys rwk2 = new RefreshableWidgetKeys(t, "1", true, "user");
		Assert.assertTrue(rwk1.equals(rwk1));
		Assert.assertTrue(rwk1.equals(rwk2));
		DefaultKey dk = new DefaultKey(t, "1");
		Assert.assertFalse(rwk1.equals(dk));
		Assert.assertFalse(rwk1.equals(null));
		RefreshableWidgetKeys rwk3 = new RefreshableWidgetKeys(null, "1", true, "user");
		Assert.assertFalse(rwk1.equals(rwk3));
		RefreshableWidgetKeys rwk4 = new RefreshableWidgetKeys(t, null, true, "user");
		Assert.assertFalse(rwk1.equals(rwk4));
		RefreshableWidgetKeys rwk5 = new RefreshableWidgetKeys(t, "1", null, "user");
		Assert.assertFalse(rwk1.equals(rwk5));
		RefreshableWidgetKeys rwk6 = new RefreshableWidgetKeys(t, "1", true, null);
		Assert.assertFalse(rwk1.equals(rwk6));
		Tenant t2 = new Tenant(2L);
		RefreshableWidgetKeys rwk7 = new RefreshableWidgetKeys(t2, "1", true, "user");
		Assert.assertFalse(rwk1.equals(rwk7));
	}

	@Test
	public void testHashCode()
	{
		Tenant t = new Tenant(1L);
		RefreshableWidgetKeys rwk1 = new RefreshableWidgetKeys(t, "1", true, "user");
		RefreshableWidgetKeys rwk2 = new RefreshableWidgetKeys(t, "1", true, "user");
		Assert.assertEquals(rwk1.hashCode(), rwk1.hashCode());
		Assert.assertEquals(rwk1.hashCode(), rwk2.hashCode());
		DefaultKey dk = new DefaultKey(t, "1");
		Assert.assertNotEquals(rwk1.hashCode(), dk.hashCode());
		RefreshableWidgetKeys rwk3 = new RefreshableWidgetKeys(null, "1", true, "user");
		Assert.assertNotEquals(rwk1.hashCode(), rwk3.hashCode());
		RefreshableWidgetKeys rwk4 = new RefreshableWidgetKeys(t, null, true, "user");
		Assert.assertNotEquals(rwk1.hashCode(), rwk4.hashCode());
		RefreshableWidgetKeys rwk5 = new RefreshableWidgetKeys(t, "1", null, "user");
		Assert.assertNotEquals(rwk1.hashCode(), rwk5.hashCode());
		RefreshableWidgetKeys rwk6 = new RefreshableWidgetKeys(t, "1", true, null);
		Assert.assertNotEquals(rwk1.hashCode(), rwk6.hashCode());
		Tenant t2 = new Tenant(2L);
		RefreshableWidgetKeys rwk7 = new RefreshableWidgetKeys(t2, "1", true, "user");
		Assert.assertNotEquals(rwk1.hashCode(), rwk7.hashCode());
	}
}

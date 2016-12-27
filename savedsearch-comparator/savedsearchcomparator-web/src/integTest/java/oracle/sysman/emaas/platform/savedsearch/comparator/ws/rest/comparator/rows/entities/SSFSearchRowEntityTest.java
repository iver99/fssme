package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;
@Test(groups = { "s1" })
public class SSFSearchRowEntityTest {

	@Test
	public void testEquals() {
		SavedSearchSearchRowEntity entity1 = new SavedSearchSearchRowEntity();
		SavedSearchSearchRowEntity entity2 = new SavedSearchSearchRowEntity();
		Assert.assertEquals(entity1,entity2);
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setDashboardIneligible("ineligible");
		entity2.setDashboardIneligible("ineligible2");
		Assert.assertNotEquals(entity1,entity2);
		
		entity1.setDeleted(new BigInteger("1"));
		entity2.setDeleted(new BigInteger("1"));
		Assert.assertNotEquals(entity1,entity2);
		
		entity1.setDescription("des");
		entity2.setDescription("des");
		entity2.setDashboardIneligible("ineligible");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setDescriptionNlsid("des");
		entity2.setDescriptionNlsid("des2");
		Assert.assertNotEquals(entity1,entity2);
		
		
		entity1.setDescriptionSubsystem("desSubSys");
		Assert.assertNotEquals(entity1,entity2);
		entity2.setDescriptionSubsystem("desSubSys");
		Assert.assertNotEquals(entity1,entity2);
		
		entity2.setDescriptionNlsid("des");
		entity1.setIsLocked(1);
		entity2.setIsLocked(1);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setIsWidget(1);
		entity2.setIsWidget(1);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1,entity2);
		
		
	}
	
	@Test
	public void testHashcode() {
		SavedSearchSearchRowEntity entity1 = new SavedSearchSearchRowEntity();
		SavedSearchSearchRowEntity entity2 = new SavedSearchSearchRowEntity();
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDashboardIneligible("ineligible");
		entity2.setDashboardIneligible("ineligible2");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDeleted(new BigInteger("1"));
		entity2.setDeleted(new BigInteger("1"));
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDescription("des");
		entity2.setDescription("des");
		entity2.setDashboardIneligible("ineligible");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDescriptionNlsid("des");
		entity2.setDescriptionNlsid("des2");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
		
		entity1.setDescriptionSubsystem("desSubSys");
		Assert.assertNotEquals(entity1,entity2);
		entity2.setDescriptionSubsystem("desSubSys");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
		
		entity2.setDescriptionNlsid("des");
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

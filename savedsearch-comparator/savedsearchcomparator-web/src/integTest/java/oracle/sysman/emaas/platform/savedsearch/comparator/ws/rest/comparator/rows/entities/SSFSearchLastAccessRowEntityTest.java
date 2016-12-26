package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import org.testng.Assert;
import org.testng.annotations.Test;
@Test(groups = { "s1" })
public class SSFSearchLastAccessRowEntityTest {

	@Test
	public void testEquals() {
		SavedSearchLastAccessRowEntity entity1 = new SavedSearchLastAccessRowEntity();
		SavedSearchLastAccessRowEntity entity2 = new SavedSearchLastAccessRowEntity();
		Assert.assertEquals(entity1, entity2);
		
		entity1.setAccessDate("2016-07-22 08:23:32.517");
		entity2.setAccessDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1, entity2);
		
		entity2.setAccessDate("2016-07-22 08:23:32.517");
		entity1.setAccessedBy("me");
		entity2.setAccessedBy("me");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setObjectId(1L);
		entity2.setObjectId(2L);
		Assert.assertNotEquals(entity1, entity2);
		
		entity2.setObjectId(1L);
		entity1.setObjectType(1L);
		entity2.setObjectType(1L);
		Assert.assertEquals(entity1, entity2);
		
		entity1.setTenantId(1L);
		entity2.setTenantId(5L);
		Assert.assertNotEquals(entity1, entity2);		
		
	}
	
	@Test
	public void testHashcode() {
		SavedSearchLastAccessRowEntity entity1 = new SavedSearchLastAccessRowEntity();
		SavedSearchLastAccessRowEntity entity2 = new SavedSearchLastAccessRowEntity();
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setAccessDate("2016-07-22 08:23:32.517");
		entity2.setAccessDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		
		entity2.setAccessDate("2016-07-22 08:23:32.517");
		entity1.setAccessedBy("me");
		entity2.setAccessedBy("me");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setObjectId(1L);
		entity2.setObjectId(2L);
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		
		entity2.setObjectId(1L);
		entity1.setObjectType(1L);
		entity2.setObjectType(1L);
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setTenantId(1L);
		entity2.setTenantId(5L);
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());		
	}
}

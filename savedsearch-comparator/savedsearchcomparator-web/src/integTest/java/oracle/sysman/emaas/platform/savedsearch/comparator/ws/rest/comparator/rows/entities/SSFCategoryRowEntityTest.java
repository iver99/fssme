package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author pingwu
 *
 */
public class SSFCategoryRowEntityTest {
	

	@Test(groups = {"s1"})
	public void testEquals(){
		SavedSearchCategoryRowEntity entity1 = new SavedSearchCategoryRowEntity();
		SavedSearchCategoryRowEntity entity2 = new SavedSearchCategoryRowEntity();
		Assert.assertEquals(entity1,entity2);
		
		entity1.setCategoryId(1L);
		entity2.setCategoryId(1L);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setDeleted(1L);
		entity2.setDeleted(1L);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setDescription("des");
		entity2.setDescription("des");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setDescriptionSubsystem("desSubsystem");
		entity2.setDescriptionSubsystem("desSubsystem");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setName("category");
		entity2.setName("category");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setOwner("emadmin");
		entity2.setOwner("emadmin");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setProviderName("providerName");
		entity2.setProviderName("providerName");
		Assert.assertEquals(entity1,entity2);
		
		entity1.setTenantId(1L);
		entity2.setTenantId(1L);
		Assert.assertEquals(entity1,entity2);
		
		entity1.setNameNlsid("entity1Des");
		entity2.setNameNlsid("entity2Des");
		Assert.assertNotEquals(entity1, entity2);
			
	}
	
	@Test(groups = {"s1"})
	public void testHashcode(){
		SavedSearchCategoryRowEntity entity1 = new SavedSearchCategoryRowEntity();
		SavedSearchCategoryRowEntity entity2 = new SavedSearchCategoryRowEntity();
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setCategoryId(1L);
		entity2.setCategoryId(1L);
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDeleted(1L);
		entity2.setDeleted(1L);
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDescription("des");
		entity2.setDescription("des");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setDescriptionSubsystem("desSubsystem");
		entity2.setDescriptionSubsystem("desSubsystem");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setName("category");
		entity2.setName("category");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setOwner("emadmin");
		entity2.setOwner("emadmin");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setProviderName("providerName");
		entity2.setProviderName("providerName");
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setTenantId(1L);
		entity2.setTenantId(1L);
		Assert.assertEquals(entity1.hashCode(),entity2.hashCode());
		
		entity1.setNameNlsid("entity1Des");
		entity2.setNameNlsid("entity2Des");
		Assert.assertNotEquals(entity1.hashCode(),entity2.hashCode());
	
	}
	
}

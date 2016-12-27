package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class SSFCategoryParamRowEntityTest {
	
	@Test
	public void testEquals() {
		SavedSearchCategoryParamRowEntity entity1 = new SavedSearchCategoryParamRowEntity();
		SavedSearchCategoryParamRowEntity entity2 = new SavedSearchCategoryParamRowEntity();
		entity1.setCategoryId(new BigInteger("1"));
		entity2.setCategoryId(new BigInteger("1"));
		Assert.assertEquals(entity1, entity2);
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1, entity2);
		entity1.setCreationDate("2016-07-22 08:23:32.120");
		Assert.assertNotEquals(entity1, entity2);
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertNotEquals(entity1, entity2);
		entity2.setCreationDate("2016-07-22 08:23:32.120");
		Assert.assertNotEquals(entity1, entity2);
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setName("name");
		entity2.setName("name");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setTenantId(1L);
		entity2.setTenantId(1L);
		Assert.assertEquals(entity1, entity2);
		
		entity1.setValue("value");
		entity2.setValue("value");
		Assert.assertEquals(entity1, entity2);
	}
	
	@Test
	public void testHashcode() {
		SavedSearchCategoryParamRowEntity entity1 = new SavedSearchCategoryParamRowEntity();
		SavedSearchCategoryParamRowEntity entity2 = new SavedSearchCategoryParamRowEntity();
		entity1.setCategoryId(new BigInteger("1"));
		entity2.setCategoryId(new BigInteger("1"));
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		entity1.setCreationDate("2016-07-22 08:23:32.120");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		entity2.setCreationDate("2016-07-22 08:23:32.120");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setName("name");
		entity2.setName("name");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setTenantId(1L);
		entity2.setTenantId(1L);
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setValue("value");
		entity2.setValue("value");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
	}
}

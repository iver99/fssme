package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import org.testng.Assert;
import org.testng.annotations.Test;
@Test(groups = { "s1" })
public class SSFSearchParamRowEntityTest {

	@Test
	public void testEquals() {
		SavedSearchSearchParamRowEntity entity = new SavedSearchSearchParamRowEntity("searchId", "name","paramAttributes", 2L,
				"paramValueClob","paramValueStr", 123L, "2016-07-22 08:23:32.517","2016-07-22 08:23:32.517", 1);
		Assert.assertEquals(entity.getCreationDate(),"2016-07-22 08:23:32.517");
		entity.getDeleted();
		entity.getLastModificationDate();
		entity.getName();
		entity.getParamAttributes();
		entity.getParamValueClob();
		entity.getParamValueStr();
		entity.getSearchId();
		entity.getTenantId();
		entity.setDeleted(0);
		
		SavedSearchSearchParamRowEntity entity1 = new SavedSearchSearchParamRowEntity();
		SavedSearchSearchParamRowEntity entity2 = new SavedSearchSearchParamRowEntity();
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1, entity2);
		
		entity1.setName("name");
		entity2.setName("name");
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setParamAttributes("paramAttr");
		entity2.setParamAttributes("paramAttr");
		Assert.assertEquals(entity1, entity2);
		
		entity1.setParamType(1L);
		entity2.setParamType(1L);
		Assert.assertEquals(entity1, entity2);
		
		entity1.setParamValueClob("paramClob");
		entity2.setParamValueClob("paramClob");
		Assert.assertEquals(entity1, entity2);
						
		entity1.setParamValueStr("paramStr");
		entity2.setParamValueStr("paramStrs");
		Assert.assertNotEquals(entity1, entity2);
		
		entity1.setTenantId(1L);
		entity2.setTenantId(1L);
		Assert.assertNotEquals(entity1, entity2);
	}
	
	@Test
	public void testHashcode() {
		SavedSearchSearchParamRowEntity entity1 = new SavedSearchSearchParamRowEntity();
		SavedSearchSearchParamRowEntity entity2 = new SavedSearchSearchParamRowEntity();
		entity1.setCreationDate("2016-07-22 08:23:32.517");
		entity2.setCreationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setLastModificationDate("2016-07-22 08:23:32.517");
		entity2.setLastModificationDate("2016-07-22 08:23:32.518");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setName("name");
		entity2.setName("name");
		entity2.setLastModificationDate("2016-07-22 08:23:32.517");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setParamAttributes("paramAttr");
		entity2.setParamAttributes("paramAttr");
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setParamType(1L);
		entity2.setParamType(1L);
		Assert.assertEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setParamValueClob("paramClob");
		entity2.setParamValueClob("paramClob");
		Assert.assertEquals(entity1, entity2);
						
		entity1.setParamValueStr("paramStr");
		entity2.setParamValueStr("paramStrs");
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
		
		entity1.setTenantId(1L);
		entity2.setTenantId(1L);
		Assert.assertNotEquals(entity1.hashCode(), entity2.hashCode());
	}
}

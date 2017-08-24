package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class CountsEntityTest {

	@Test
	public void testCountsEntity() {
		CountsEntity entity = new CountsEntity(1L, 2L, 3L, 4L, 5L);
		Assert.assertEquals(entity.getCountOfCategory().longValue(), 1L);
		Assert.assertEquals(entity.getCountOfFolders().longValue(), 2L);
		Assert.assertEquals(entity.getCountOfSearch().longValue(), 3L);
		
		entity.setCountOfCategory(2L);
		entity.setCountOfFolders(2L);
		entity.setCountOfSearch(2L);
		
		entity.setCountOfCategoryPrams(0L);
		entity.setCountOfSearchParams(0L);
		entity.getCountOfCategoryPrams();
		entity.getCountOfSearchParams();
	}
}

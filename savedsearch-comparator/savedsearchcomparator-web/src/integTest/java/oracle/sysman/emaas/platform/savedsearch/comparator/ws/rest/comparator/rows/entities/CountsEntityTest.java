package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class CountsEntityTest {

	@Test
	public void testCountsEntity() {
		CountsEntity entity = new CountsEntity(1L, 2L, 3L);
		Assert.assertEquals(entity.getCountOfCategory().longValue(), 1L);
		Assert.assertEquals(entity.getCountOfFolder().longValue(), 2L);
		Assert.assertEquals(entity.getCountOfSearch().longValue(), 3L);
		
		entity.setcountOfCategory(2L);
		entity.setcountOfFolder(2L);
		entity.setcountOfSearch(2L);
	}
}

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.SavedsearchCountsComparator;

import org.testng.annotations.Test;

public class SavedsearchCountsComparatorTest {
	
	@Test
	public void testcompareInstancesCounts() {
		CountsEntity entity1 = new CountsEntity();
		entity1.setcountOfCategory(11L);
		entity1.setcountOfFolder(12L);
		entity1.setcountOfSearch(13L);
		
		CountsEntity entity2 = new CountsEntity();
		entity2.setcountOfCategory(15L);
		entity2.setcountOfFolder(16L);
		entity2.setcountOfSearch(17L);
		
		SavedsearchCountsComparator comparator = new SavedsearchCountsComparator();
		comparator.compareInstancesCounts("key1", null, entity1,
				"key2", null, entity2);
	}

}

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.ws.test;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.ZDTStatusRowEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ZDTStatusRowEntityTest {
	
	@Test
	public void testZdtComparatorRowsEntityGetterSetter() {
		ZDTStatusRowEntity entity = new ZDTStatusRowEntity( "comparisonDate", 
				"comparisonType",
				"nextComparisonDate", 
				0.99);
		
		Assert.assertEquals(entity.getComparisonDate(), "comparisonDate");
		Assert.assertEquals(entity.getComparisonType(), "comparisonType");
		Assert.assertEquals(entity.getNextComparisonDate(), "nextComparisonDate");
		Assert.assertEquals(entity.getDivergencePercentage(), 0.99);
		
		entity.setComparisonDate("");
		entity.setComparisonType("");
		entity.setDivergencePercentage(0.0);
		entity.setNextComparisonDate("");
	}

}

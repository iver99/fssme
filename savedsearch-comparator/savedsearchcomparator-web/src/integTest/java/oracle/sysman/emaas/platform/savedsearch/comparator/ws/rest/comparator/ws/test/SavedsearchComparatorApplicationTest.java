package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.ws.test;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.SavedsearchComparatorApplication;

import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class SavedsearchComparatorApplicationTest {

	@Test
	public void testGetClasses() {
		SavedsearchComparatorApplication comApp = new SavedsearchComparatorApplication();
		comApp.getClasses();
	}
}

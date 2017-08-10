package oracle.sysman.emaas.platform.savedsearch.comparator.ws.timer;

import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class SavedsearchComparatorServiceManagerTest {
	
	@Test
	public void testGetName() {
		SavedsearchComparatorServiceManager manager = new SavedsearchComparatorServiceManager();
		manager.getName();
	}
	
	@Test
	public void testpostStart() throws Exception {
		SavedsearchComparatorServiceManager manager = new SavedsearchComparatorServiceManager();
		manager.postStart(null);
	}
	
/*	@Test
	public void testPreStop() throws Exception {
		SavedsearchComparatorServiceManager manager = new SavedsearchComparatorServiceManager();
		manager.preStop(null);
	}
*/
}

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.timer;

import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class SavedsearchComparatorHandlerNotificationTest {
	@Test
	public void testHandleNotification() {
		SavedsearchComparatorHandlerNotification handler = new SavedsearchComparatorHandlerNotification();
		handler.handleNotification(null, null);
	}

}

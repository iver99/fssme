package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle;

import java.io.IOException;

import org.testng.annotations.Test;

/**
 * Created by guochen on 3/24/17.
 */
@Test(groups = { "s2" })
public class SSFLifeCycleNotificationTest {
	@Test
	public void testNotifyChange() throws IOException {
		final SSFLifeCycleNotification lcn = new SSFLifeCycleNotification();
		// null input
		lcn.notify(null);
		lcn.notify(SSFLifeCycleNotifyEntity.SSFNotificationType.UP);
	}
}

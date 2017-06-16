package oracle.sysman.emaas.platform.savedsearch.comparator.ws.timer;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.ZDTAPI;

public class SavedsearchComparatorHandlerNotification implements NotificationListener{

	private ZDTAPI api;
	private static String TENANT = "";
	private static String USER = "";
	private final static Logger LOGGER = LogManager.getLogger(SavedsearchComparatorHandlerNotification.class);
	@Override
	public void handleNotification(Notification arg0, Object arg1) {
		LOGGER.info("******start to handle comparator*******");
		
	}

}

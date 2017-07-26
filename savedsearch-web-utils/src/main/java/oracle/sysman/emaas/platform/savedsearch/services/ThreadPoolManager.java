package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.Date;

import javax.management.InstanceNotFoundException;
import javax.management.NotificationListener;

import oracle.sysman.emaas.platform.savedsearch.threadpool.ParallelThreadPool;
import oracle.sysman.emaas.platform.savedsearch.threadpool.ThreadPoolStatusNotification;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.application.ApplicationLifecycleEvent;
import weblogic.management.timer.Timer;

/**
 * Created by chehao on 2017/2/17 13:59.
 */
public class ThreadPoolManager implements ApplicationServiceManager {

    private static final Logger LOGGER = LogManager.getLogger(ThreadPoolManager.class);
    private static final long PERIOD = Timer.ONE_SECOND * 120;
    private Timer timer;
    private Integer notificationId;

    @Override
    public String getName() {
        return "SavedSearch: Thread Pool Service";
    }

    @Override
    public void postStart(ApplicationLifecycleEvent evt) throws Exception {
        LOGGER.info("Post-starting thread pool.");
        ParallelThreadPool.init();
        timer = new Timer();
        NotificationListener notification = new ThreadPoolStatusNotification();
        timer.addNotificationListener(notification, null, null);
        Date timerTriggerAt = new Date(new Date().getTime() + 10000L);
        notificationId = timer.addNotification("SavedSearchThreadPoolTimer", null, notification, timerTriggerAt, PERIOD, 0);
        timer.start();
        LOGGER.info("Timer for savedsearch thread pool checking started. notificationId={}", notificationId);
    }

    @Override
    public void postStop(ApplicationLifecycleEvent evt) throws Exception {
        //do nothing
    }

    @Override
    public void preStart(ApplicationLifecycleEvent evt) throws Exception {
        //do nothing
    }

    @Override
    public void preStop(ApplicationLifecycleEvent evt) throws Exception {
        LOGGER.info("Pre-stopping thread pool service");
        try {
            timer.stop();
            timer.removeNotification(notificationId);
            LOGGER.info("Timer for savedsearch thread pool checking stopped. notificationId={}", notificationId);
        } catch (InstanceNotFoundException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        LOGGER.info("Pre-closing thread pool.");
        ParallelThreadPool.close();
        LOGGER.debug("Pre-stopped thread pool");
    }
}

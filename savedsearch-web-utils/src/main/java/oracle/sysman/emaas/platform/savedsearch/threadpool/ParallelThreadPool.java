package oracle.sysman.emaas.platform.savedsearch.threadpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chehao on 2017/2/13 22:20.
 */
public class ParallelThreadPool {
    private final static Logger LOGGER = LogManager.getLogger(ParallelThreadPool.class);
    private static ThreadPoolExecutor pool = null;
    private static long rejectTaskNumber = 0l;
    private static final int CORE_SIZE = 5;
    private static final int MAX_SIZE = 10;
    private static final int DEFAULT_QUEUE_SIZE = 10;

    public static  ThreadPoolExecutor  getThreadPool(){
        if(pool != null){
            LOGGER.info("Thread pool instance is not null, returning...");
            return pool;
        }
        init();
        return pool;
    }

    public static void close(){
        LOGGER.info("SavedSearch: Thread pool with size {} is closing!");
        pool.shutdownNow();
        if(!pool.isTerminated() || !pool.isShutdown()){
            LOGGER.error("Not all tasks in thread pool have completed or executor is not shutdown yet!!! ");
        }else{
            LOGGER.info("All tasks in thread pool have completed and executor is shutdown successfully...");
        }
        // set pool to null directly to avoid EMCPDF-3922 issue.
        pool = null;
    }

    public static void init(){
        if(pool != null){
            LOGGER.info("pool instance is not null, no initialization action is needed!");
            return;
        }
        pool = new ThreadPoolExecutor(CORE_SIZE,			//Core thread size
                MAX_SIZE,								//max thread size
                60 * 10,											//keep alived time for idle thread
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE),	 //bounded queue with capacity
                new CustomThreadFactory(),			//default thread factory
                new CustomRejectedExecutionHandler()		//Custom Rejected execution handler
                );
        //core threads can be timedout too
        pool.allowCoreThreadTimeOut(Boolean.TRUE);
        // pre start one thread in pool
        pool.prestartCoreThread();
        LOGGER.info("SavedSearch: Thread pool with core size {} and max size {} and queue size {} is initialized!", CORE_SIZE, MAX_SIZE ,DEFAULT_QUEUE_SIZE);
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LOGGER.error("#{} Queue of thread pool is full! Task {} is rejected from {}",rejectTaskNumber++, r.toString(), executor.toString());
            /*throw new RejectedExecutionException("Task " + r.toString() +
                    " rejected from " +
                    );*/
        }
    }

    /**
     * The default thread factory
     */
    static class CustomThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "SavedSearch thread pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}

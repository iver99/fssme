package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread;

/**
 * Created by xiadai on 2017/6/14.
 */
public abstract class MetadataRefreshRunnable implements Runnable{
    protected String serviceName;
    public void setServiceName(String serviceName){ this.serviceName = serviceName; }
}

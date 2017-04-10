package oracle.sysman.emSDK.emaas.platform.savedsearch.model.subscription2;



import java.util.List;

/**
 * Created by chehao on 3/30/17.
 */
public class TenantSubscriptionInfo {

    private List<SubscriptionApps> subscriptionAppsList;
    private List<AppsInfo> appsInfoList;

    public List<AppsInfo> getAppsInfoList() {
        return appsInfoList;
    }

    public void setAppsInfoList(List<AppsInfo> appsInfoList) {
        this.appsInfoList = appsInfoList;
    }

    public List<SubscriptionApps> getSubscriptionAppsList() {
        return subscriptionAppsList;
    }

    public void setSubscriptionAppsList(List<SubscriptionApps> subscriptionAppsList) {
        this.subscriptionAppsList = subscriptionAppsList;
    }

}

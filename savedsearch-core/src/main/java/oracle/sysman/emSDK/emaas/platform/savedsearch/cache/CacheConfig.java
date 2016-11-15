package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import java.util.ResourceBundle;

/**
 * Created by chehao on 2016/10/26.
 */
public class CacheConfig {
    static ResourceBundle conf = ResourceBundle.getBundle("cache_config");
    //all cache group's expiration time
    public static final Integer ADMIN_LINK_CACHE_EXPIRE_TIME = Integer.valueOf(conf.getString("ADMIN_LINK_CACHE_EXPIRE_TIME"));
    public static final Integer CLOUD_SERVICE_LINK_EXPIRE_TIME = Integer.valueOf(conf.getString("CLOUD_SERVICE_LINK_EXPIRE_TIME"));
    public static final Integer HOME_LINK_EXPIRE_TIME = Integer.valueOf(conf.getString("HOME_LINK_EXPIRE_TIME"));
    public static final Integer VISUAL_ANALYZER_LINK_EXPIRE_TIME = Integer.valueOf(conf.getString("VISUAL_ANALYZER_LINK_EXPIRE_TIME"));
    public static final Integer SERVICE_EXTERNAL_LINK_EXPIRE_TIME = Integer.valueOf(conf.getString("SERVICE_EXTERNAL_LINK_EXPIRE_TIME"));
    public static final Integer SERVICE_INTERNAL_LINK_EXPIRE_TIME = Integer.valueOf(conf.getString("SERVICE_INTERNAL_LINK_EXPIRE_TIME"));
    public static final Integer VANITY_BASE_URL_EXPIRE_TIME = Integer.valueOf(conf.getString("VANITY_BASE_URL_EXPIRE_TIME"));
    public static final Integer DOMAINS_DATA_EXPIRE_TIME = Integer.valueOf(conf.getString("DOMAINS_DATA_EXPIRE_TIME"));
    public static final Integer TENANT_APP_MAPPING_EXPIRE_TIME = Integer.valueOf(conf.getString("TENANT_APP_MAPPING_EXPIRE_TIME"));
    public static final Integer TENANT_SUBSCRIBED_SERVICES_EXPIRE_TIME = Integer.valueOf(conf.getString("TENANT_SUBSCRIBED_SERVICES_EXPIRE_TIME"));
    public static final Integer SCREENSHOT_EXPIRE_TIME = Integer.valueOf(conf.getString("SCREENSHOT_EXPIRE_TIME"));
    public static final Integer SSO_LOGOUT_EXPIRE_TIME = Integer.valueOf(conf.getString("SSO_LOGOUT_URL_EXPIRE_TIME"));

    public static final Integer ETERNAL_EXPIRE_TIME = Integer.valueOf(conf.getString("DEFAULT_EXPIRE_TIME"));
    public static final Integer DEFAULT_EXPIRE_TIME = Integer.valueOf(conf.getString("DEFAULT_EXPIRE_TIME"));

    //all cache group's cache capacity
    public static final Integer ADMIN_LINK_CACHE_CAPACITY = Integer.valueOf(conf.getString("ADMIN_LINK_CACHE_CAPACITY"));
    public static final Integer CLOUD_SERVICE_LINK_CAPACITY = Integer.valueOf(conf.getString("CLOUD_SERVICE_LINK_CAPACITY"));
    public static final Integer HOME_LINK_EXPIRE_CAPACITY = Integer.valueOf(conf.getString("HOME_LINK_EXPIRE_CAPACITY"));
    public static final Integer VISUAL_ANALYZER_LINK_CAPACITY = Integer.valueOf(conf.getString("VISUAL_ANALYZER_LINK_CAPACITY"));
    public static final Integer SERVICE_EXTERNAL_LINK_CAPACITY = Integer.valueOf(conf.getString("SERVICE_EXTERNAL_LINK_CAPACITY"));
    public static final Integer SERVICE_INTERNAL_LINK_CAPACITY = Integer.valueOf(conf.getString("SERVICE_INTERNAL_LINK_CAPACITY"));
    public static final Integer VANITY_BASE_URL_CAPACITY = Integer.valueOf(conf.getString("VANITY_BASE_URL_CAPACITY"));
    public static final Integer DOMAINS_DATA_CAPACITY = Integer.valueOf(conf.getString("DOMAINS_DATA_CAPACITY"));
    public static final Integer TENANT_APP_MAPPING_CAPACITY = Integer.valueOf(conf.getString("TENANT_APP_MAPPING_CAPACITY"));
    public static final Integer TENANT_SUBSCRIBED_SERVICES_CAPACITY = Integer.valueOf(conf.getString("TENANT_SUBSCRIBED_SERVICES_CAPACITY"));
    public static final Integer SCREENSHOT_CAPACITY = Integer.valueOf(conf.getString("SCREENSHOT_CAPACITY"));
    public static final Integer SSO_LOGOUT_CAPACITY = Integer.valueOf(conf.getString("SSO_LOGOUT_CAPACITY"));

    public static final Integer ETERNAL_CAPACITY = Integer.valueOf(conf.getString("DEFAULT_CAPACITY"));
    public static final Integer DEFAULT_CAPACITY = Integer.valueOf(conf.getString("DEFAULT_CAPACITY"));
}

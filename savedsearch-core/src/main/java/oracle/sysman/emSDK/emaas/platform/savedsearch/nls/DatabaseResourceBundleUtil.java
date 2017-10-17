package oracle.sysman.emSDK.emaas.platform.savedsearch.nls;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by xiadai on 2017/6/14.
 */
public class DatabaseResourceBundleUtil {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseResourceBundleUtil.class);


    public static final String APM_PROVIDER_APMUI = "ApmUI";
    public static final String ITA_PROVIDER_EMCI = "emcitas-ui-apps";
    public static final String ITA_PROVIDER_TA = "TargetAnalytics";
    public static final String LA_PROVIDER_LS = "LogAnalyticsUI";
    public static final String OCS_PROVIDER_OCS = "CosUIService";
    public static final String APM_STRING = "APM";
    public static final String ITA_SRING = "ITAnalytics";
    public static final String LA_STRING = "LogAnalytics";
    public static final String ORCHESTRATION_STRING = "Orchestration";
    public static final String UDE_STRING = "UDE";

    public static String getTranslatedString(String widgetProvider, String key) {
        String appType = null;
        if(StringUtil.isEmpty(widgetProvider)) return key;
        switch(widgetProvider) {
            case APM_PROVIDER_APMUI:
                appType = APM_STRING;
                break;
            case LA_PROVIDER_LS:
                appType = LA_STRING;
                break;
            case OCS_PROVIDER_OCS:
                appType = ORCHESTRATION_STRING;
                break;
            case ITA_PROVIDER_EMCI:
                appType = ITA_SRING;
                break;
            case ITA_PROVIDER_TA:
                appType = UDE_STRING;
                break;
            default:
                break;
        }
        if(appType == null) {
            LOGGER.warn("Widget provider called {} can not be matched to any of existing application type.", widgetProvider);
            return key;
        }

        DatabaseResourceBundle rb = null;
        try {
            rb = (DatabaseResourceBundle) ResourceBundle.getBundle(appType, TenantContext.getLocale(),
                    DatabaseResourceBundleUtil.class.getClassLoader(), new DatabaseResourceBundleControl());
        } catch (Exception ex) {
            LOGGER.warn("Fail to translating '{}' for service {} because: {}", key, appType,
                    ex.getLocalizedMessage());
        }
        if (rb != null) {
            try {
                return rb.getString(key);
            }
            catch (MissingResourceException ex) {
                LOGGER.warn("No translation for '{}' for service {} because: {}", key, appType,
                        ex.getLocalizedMessage());
            }
        }
        return key;
    }

    public static Locale generateLocale(String languageCode) {
        Locale locale = Locale.US;
        String[] array = languageCode.split("-");
        if(array.length > 1) {
            if("zh".equals(array[0]) && "CN".equals(array[1])) {
                locale = new Locale("zh", "Hans");
            } else {
                locale = new Locale(array[0], array[1]);
            }
        } else {
            switch(languageCode) {
                case "en":
                    locale = Locale.US;
                    break;
                case "fr":
                    locale = Locale.FRANCE;
                    break;
                case "ko":
                    locale = Locale.KOREA;
                    break;
                case "zh":
                    locale = new Locale("zh", "Hans");
                    break;
                default:
                    break;
            }
        }
        return locale;
    }
}

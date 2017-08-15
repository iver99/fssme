package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify;

import java.io.IOException;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

public class OOBWidgetExpiredNotification {
    private static final Logger LOGGER = LogManager.getLogger(OOBWidgetExpiredNotification.class);
    public static final String REL = "expire/widgetcache";

    public void notify(String fromService) {
        LOGGER.info("Start to notify that the OOB widgets have been expired!");
        List<VersionedLink> links = null;
        try {
            links = RegistryLookupUtil.getAllServicesInternalLinksByRel(REL);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        if (links == null || links.isEmpty()) {
            LOGGER.info("Didn't find any links for rel={}", REL);
            return;
        }
        RestClient rc = new RestClient();
        for (VersionedLink link : links) {
            // TODO: will be replaced by the RestClient impl from dashboards-sdk once it could be used by SSF
            try {
                LOGGER.info("POST to {} to notify OOB widgets expired!", link.getHref());
                rc.post(link.getHref(), fromService, "CloudServices", link.getAuthToken());
            } catch (UniformInterfaceException e) {
                ClientResponse res = e.getResponse();
                if (res != null && res.getStatus() == 204) { // this is expected result
                    LOGGER.info("Got expected 204 status code for link {}", link.getHref());
                } else { // for other scenarios, we just log an error and continue the next notification
                    LOGGER.error("Fail to notify {} - ", link.getHref(), e);
                }
            }
        }
    }

}

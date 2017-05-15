package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//JUST FOR TEST TO BE DELETED
@Path("oob")
public class MetadataAPI {
	@GET
	@Path("widgets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOobDashboards() {
        LogUtil.getInteractionLogger().info("Service calling to (GET) WIDGETS");
	    StringBuilder myMock = new StringBuilder();
	    InputStream realtimeStreamsInfoStream = MetadataAPI.class.getResourceAsStream("/widgets.json");
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(realtimeStreamsInfoStream,"UTF-8"));
            while ((line = br.readLine()) != null) {
            	myMock.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
	    return Response.ok().entity(myMock.toString()).build();
	}
}

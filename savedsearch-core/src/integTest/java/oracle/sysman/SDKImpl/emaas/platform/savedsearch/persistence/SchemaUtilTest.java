package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = "s2")
public class SchemaUtilTest {


    @Test
    public void testGetSchemaUrlsNull(){
        Assert.assertNull(SchemaUtil.getSchemaUrls(null));

        SchemaUtil.getSchemaUrls("{\"items\":[{\"virtualEndpoints\":[\"https\",\"xxx\"],\"canonicalEndpoints\":[\"https\",\"xxx\"]},{\"virtualEndpoints\":[\"https\",\"xxx\"],\"canonicalEndpoints\":[\"https\",\"xxx\"]}]}");
    }

    @Test
    public void testGetSchemaUrlsNull1(@Mocked JSONUtil jsonUtil) throws IOException, JSONException {
        new Expectations(){
            {
                JSONUtil.fromJsonToList(anyString,(Class)any,"items");
                result = new ArrayList<>();
            }
        };
        Assert.assertNull(SchemaUtil.getSchemaUrls("json"));
    }

//    @Test
//    public void testGetSchemaUrls_notNull(@Mocked JSONUtil jsonUtil,@Mocked final SchemaUtil.SchemaDeploymentUrls schemaDeploymentUrls){
//        final List<SchemaUtil.SchemaDeploymentUrls> sdlist = new ArrayList<>();
//        sdlist.add(schemaDeploymentUrls);
//        sdlist.add(schemaDeploymentUrls);
//
//        new Expectations(){
//            {
//                JSONUtil.fromJsonToList(anyString,(Class)any,"items");
//                result = sdlist;
//                schemaDeploymentUrls.getCanonicalEndpoints();
//                returns("https","xxx");
//                schemaDeploymentUrls.getVirtualEndpoints();
//                returns("https","xxx");
//            }
//        };
//        SchemaUtil.getSchemaUrls("json");
//        SchemaUtil.getSchemaUrls("json");
//    }

    @Test
    public void testGetSchemaUrls(){
        String json = "{name:'name'}";
        try {
            SchemaUtil.getSchemaUrls(json);
        }catch (Exception e){

        };
    }



    @Test
    public void testGetSchemaUserBySoftwareName_null(){
        SchemaUtil.getSchemaUserBySoftwareName(null,null);

        SchemaUtil.getSchemaUserBySoftwareName("json","softwareName");

        SchemaUtil.getSchemaUserBySoftwareName("[{\"schemaUser\":\"userxx\",\"softwareName\":\"softwarexx\"},{\"schemaUser\":\"user\",\"softwareName\":\"software\"}]","software");

     }

    @Test
    public void testGetSchemaUserBySoftwareName_null1(@Mocked JSONUtil jsonUtil) throws IOException {
        new Expectations(){
            {
                JSONUtil.fromJsonToList(anyString,(Class)any);
                result = new ArrayList<>();
            }
        };
        SchemaUtil.getSchemaUserBySoftwareName("json","softwareName");
    }

//    @Test
//    public void testGetSchemaUserBySoftwareName_notNull(@Mocked JSONUtil jsonUtil,@Mocked final SchemaUtil.SchemaDeployment schemaDeployment){
//        final List<SchemaUtil.SchemaDeployment> sdlist = new ArrayList<>();
//        sdlist.add(schemaDeployment);
//        sdlist.add(schemaDeployment);
//
//        new Expectations(){
//            {
//                JSONUtil.fromJsonToList(anyString,(Class)any);
//                result = sdlist;
//                schemaDeployment.getSoftwareName();
//                returns("softwareName","xxx");
//            }
//        };
//        SchemaUtil.getSchemaUserBySoftwareName("json","softwareName");
//        SchemaUtil.getSchemaUserBySoftwareName("json","softwareName");
//    }

}
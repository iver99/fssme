package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emInternalSDK.rproxy.lookup.CloudLookupException;
import oracle.sysman.emInternalSDK.rproxy.lookup.CloudLookups;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by chehao on 2017/5/18 10:14.
 */
@Test (groups = {"s2"})
public class AbstractComparatorTest {


    @Test
    public void testGetOMCInstances(final @Mocked CloudLookups anyCloudLookups) throws CloudLookupException {
        AbstractComparator ac = new AbstractComparator(){
            @Override
            public HashMap<String, LookupClient> getOMCInstances() {
                return super.getOMCInstances();
            }

            @Override
            protected Link getSingleInstanceUrl(LookupClient lc, String rel, String protocol) throws Exception {
                return super.getSingleInstanceUrl(lc, rel, protocol);
            }
        };
        final HashMap<String, LookupClient> entrySet = new HashMap<>();
//        entrySet.add()
        new Expectations(){
            {
                anyCloudLookups.getCloudLookupClients();
                result = entrySet;


            }
        };
        ac.getOMCInstances();
    }

    @Test
    public void testGetSingleInstanceUrl(final @Mocked InstanceQuery anyInstanceQuery, final @Mocked InstanceInfo anyInstanceInfo, final @Mocked LookupClient anyLookupClient) throws Exception {
        AbstractComparator ac = new AbstractComparator(){
            @Override
            public HashMap<String, LookupClient> getOMCInstances() {
                return super.getOMCInstances();
            }

            @Override
            protected Link getSingleInstanceUrl(LookupClient lc, String rel, String protocol) throws Exception {
                return super.getSingleInstanceUrl(lc, rel, protocol);
            }
        };
        final List<InstanceInfo> instanceList = new ArrayList<>();

        new Expectations(){
            {
                /*anyLookupClient.lookup(anyInstanceQuery);
                result = instanceList;*/
            }
        };
        ac.getSingleInstanceUrl(anyLookupClient,"rel","protocal");
        ac.getSingleInstanceUrl(anyLookupClient,null,"protocal");
        ac.getSingleInstanceUrl(anyLookupClient,"rel",null);
        ac.getSingleInstanceUrl(null,"rel","protocal");
    }

}

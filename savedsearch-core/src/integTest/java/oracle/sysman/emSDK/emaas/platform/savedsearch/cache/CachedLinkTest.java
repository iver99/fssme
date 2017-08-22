package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class CachedLinkTest {
    @Test
    public void testCachedLink() {
        Link link = new Link();
        link.withHref("href").withRel("rel");
        CachedLink cl = new CachedLink(link);
        Assert.assertEquals(cl.getHref(), "href");
        Assert.assertEquals(cl.getRel(), "rel");
        Assert.assertEquals(cl.getLink().getHref(), "href");
    }


}

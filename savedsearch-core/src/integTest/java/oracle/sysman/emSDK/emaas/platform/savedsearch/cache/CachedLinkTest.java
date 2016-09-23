package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/21.
 */
@Test(groups = {"s1"})
public class CachedLinkTest {
    private Link link = new Link();
    private CachedLink cachedLink;

    @Test
    public void testGetHref() throws Exception {
        link.withHref("href");
        cachedLink = new CachedLink(link);
        Assert.assertEquals("href",cachedLink.getHref());
    }

    @Test
    public void testGetLink() throws Exception {
        link.withHref("href");
        cachedLink = new CachedLink(link);
        Assert.assertEquals(link.getHref(), cachedLink.getLink().getHref());
    }

    @Test
    public void testGetRel() throws Exception {
        link.withRel("rel");
        cachedLink = new CachedLink(link);
        Assert.assertEquals("rel",cachedLink.getRel());

    }

}
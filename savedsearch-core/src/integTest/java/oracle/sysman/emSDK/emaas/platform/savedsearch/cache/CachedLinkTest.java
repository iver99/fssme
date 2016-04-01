package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class CachedLinkTest {

    CachedLink cachedLink;
    String href = "hrefxx";
    String rel = "relxx";
    Link link = new Link();

    @BeforeMethod
    public void setUp() throws Exception {
        link.withHref(href);
        link.withRel(rel);
        cachedLink = new CachedLink(link);
    }

    @Test
    public void testGetHref() throws Exception {
        Assert.assertEquals(cachedLink.getHref(),href);
    }

    @Test
    public void testGetLink() throws Exception {
        Assert.assertEquals(cachedLink.getLink().getHref(),link.getHref());
        Assert.assertEquals(cachedLink.getLink().getRel(),link.getRel());
    }

    @Test
    public void testGetRel() throws Exception {
        Assert.assertEquals(cachedLink.getRel(),rel);
    }
}
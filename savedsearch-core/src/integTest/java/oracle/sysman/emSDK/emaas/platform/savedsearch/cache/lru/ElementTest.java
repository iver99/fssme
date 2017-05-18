package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import org.testng.annotations.Test;

/**
 * Created by chehao on 2017/5/18 10:40.
 */
@Test (groups = {"s2"})
public class ElementTest {

    @Test
    public void testEquals(){
        Element e1 = new Element("key","value");
        Element e2 = new Element("key","value");
        e1.equals(e2);
        Element e3 = new Element(null,"value");
        e1.equals(e3);
        Element e4 = new Element("key",null);
        e1.equals(e4);
        e1.equals(null);
    }
}

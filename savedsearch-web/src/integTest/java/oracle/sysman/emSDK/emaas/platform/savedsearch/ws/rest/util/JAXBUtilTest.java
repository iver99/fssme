package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;

/**
 * Created by xidai on 2/26/2016.
 */
public class JAXBUtilTest {
    private JAXBUtil jaxbUtil;
    @Test
    public void testGetJAXBContext() throws Exception {
        JAXBContext jaxbContext = JAXBUtil.getJAXBContext(ObjectFactory.class);
    }

    @Test
    public void testUnmarshal() throws Exception {

    }
}
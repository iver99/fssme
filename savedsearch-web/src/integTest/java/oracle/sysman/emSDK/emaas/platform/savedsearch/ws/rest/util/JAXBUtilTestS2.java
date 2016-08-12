package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups={"s2"})
public class JAXBUtilTestS2 {
    @Mocked
    Reader reader;
    @Mocked
    InputStream inputStream;
    @Mocked
    JAXBContext jaxbContext;
    @Mocked
    SchemaFactory schemaFactory;
    @Test
    public void testGetJAXBContext() throws JAXBException {
        JAXBContext jaxbContext = JAXBUtil.getJAXBContext(ObjectFactory.class);
    }

    @Test
    public void testUnmarshal() throws JAXBException, ImportException, SAXException {

        new Expectations(){
            {
                SchemaFactory.newInstance(anyString);
                result = schemaFactory;
            }
        };
        JAXBUtil.unmarshal(reader,inputStream,jaxbContext);
    }

    @Mocked
    Throwable throwable;
    @Test(expectedExceptions = {UnmarshalException.class})
    public void testUnmarshalFFor() throws JAXBException, ImportException, SAXException {

        new Expectations(){
            {
                SchemaFactory.newInstance(anyString);
                result = new UnmarshalException(throwable);
            }
        };

            JAXBUtil.unmarshal(reader, inputStream, jaxbContext);
    }

    @Test(expectedExceptions = {JAXBException.class})
    public void testUnmarshalFForValidationEventHandler() throws JAXBException, ImportException, SAXException {
        new Expectations() {
            {
                SchemaFactory.newInstance(anyString);
                result = new JAXBException(throwable);
            }
        };
            JAXBUtil.unmarshal(reader, inputStream, jaxbContext);
    }
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

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
    private JAXBUtil jaxbUtil;
    @Mocked
    Reader reader;
    @Mocked
    InputStream inputStream;
    @Mocked
    JAXBContext jaxbContext;
    @Mocked
    SchemaFactory schemaFactory;
    @Test
    public void testGetJAXBContext() throws Exception {
        JAXBContext jaxbContext = JAXBUtil.getJAXBContext(ObjectFactory.class);
    }

    @Test
    public void testUnmarshal() throws Exception {

        new Expectations(){
            {
                SchemaFactory.newInstance(anyString);
                result = schemaFactory;
            }
        };
        JAXBUtil.unmarshal(reader,inputStream,jaxbContext);
    }

    @Test
    public void testUnmarshalFFor() throws Exception {

        new Expectations(){
            {
                SchemaFactory.newInstance(anyString);
                result = new UnmarshalException(new Throwable());
            }
        };
        new MockUp<Throwable>(){
            @Mock
            public void printStackTrace(){
            }

        };
        try {
            JAXBUtil.unmarshal(reader, inputStream, jaxbContext);
        }catch(Exception e){
            Assert.assertTrue(true);}
    }

    @Test
    public void testUnmarshalFForValidationEventHandler() throws Exception {
        new Expectations() {
            {
                SchemaFactory.newInstance(anyString);
                result = new JAXBException(new Throwable());
            }
        };
        new MockUp<Throwable>(){
            @Mock
            public void printStackTrace(){
            }

        };
        try {
            JAXBUtil.unmarshal(reader, inputStream, jaxbContext);
        }catch(Exception e){}
    }
}
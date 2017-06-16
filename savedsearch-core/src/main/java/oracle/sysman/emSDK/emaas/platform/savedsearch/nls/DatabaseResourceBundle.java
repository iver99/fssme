package oracle.sysman.emSDK.emaas.platform.savedsearch.nls;

import org.apache.logging.log4j.core.util.Charsets;
import sun.util.ResourceBundleEnumeration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by xiadai on 2017/6/15.
 */
public class DatabaseResourceBundle extends ResourceBundle{
    private Map<String, Object> lookup;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public DatabaseResourceBundle(InputStream inStream) throws IOException {
        Properties props = new Properties();
        props.load(new InputStreamReader(inStream, Charsets.UTF_8));
        lookup = new HashMap(props);
    }

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#getKeys()
     */
    @Override
    public Enumeration<String> getKeys() {
        ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(lookup.keySet(), parent != null ? parent.getKeys() : null);
    }

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
     */
    @Override
    protected Object handleGetObject(String key) {
        if (key == null) {
            return null;
        }
        return lookup.get(key);
    }
}

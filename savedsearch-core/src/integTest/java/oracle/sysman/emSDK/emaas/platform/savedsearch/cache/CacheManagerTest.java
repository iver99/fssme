package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import com.tangosol.coherence.component.util.CacheHandler;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.MapListener;
import com.tangosol.util.ValueExtractor;
import mockit.Expectations;
import mockit.Mocked;
import org.testng.Assert;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class CacheManagerTest {

    CacheManager cacheManager = CacheManager.getInstance();

    @Test
    public void testGetCache() throws Exception {
        Assert.assertNull(cacheManager.getCache(""));
        cacheManager.getCache("cachexx");
        Assert.assertNull(cacheManager.getCache(null));
    }

    @Test
    public void testGetCacheables() throws Exception {
        cacheManager.getCacheable("cacheNamexx",new Keys());
        cacheManager.getCacheable("cacheNamexx",new Keys(),null);
        cacheManager.getCacheable("cacheNamexx","keyxx");
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx",new Keys());
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx",new Keys(),null);
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx","keyxx");
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx","keyxx",null);
    }

    @Test
    public void testGetCacheable() throws Exception {
        cacheManager.getCacheable(new Tenant("tenantxx"),null,new Keys(),null);
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx",new Keys(),null);
    }

    @Test(groups = {"s2"})
    public void testGetCacheable_Mocked(@Mocked CacheFactory cacheFactory,@Mocked final ICacheFetchFactory iCacheFetchFactory) throws Exception {
        new Expectations(){
            {
                CacheFactory.getCache(anyString);
                result = new NamedCache() {
                    @Override
                    public String getCacheName() {
                        return null;
                    }

                    @Override
                    public CacheService getCacheService() {
                        return null;
                    }

                    @Override
                    public boolean isActive() {
                        return false;
                    }

                    @Override
                    public void release() {

                    }

                    @Override
                    public void destroy() {

                    }

                    @Override
                    public Object put(Object o, Object o1, long l) {
                        return null;
                    }

                    @Override
                    public Map getAll(Collection collection) {
                        return null;
                    }

                    @Override
                    public boolean lock(Object o, long l) {
                        return false;
                    }

                    @Override
                    public boolean lock(Object o) {
                        return false;
                    }

                    @Override
                    public boolean unlock(Object o) {
                        return false;
                    }

                    @Override
                    public Object invoke(Object o, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Map invokeAll(Collection collection, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Map invokeAll(Filter filter, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Object aggregate(Collection collection, EntryAggregator entryAggregator) {
                        return null;
                    }

                    @Override
                    public Object aggregate(Filter filter, EntryAggregator entryAggregator) {
                        return null;
                    }

                    @Override
                    public void addMapListener(MapListener mapListener) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener) {

                    }

                    @Override
                    public void addMapListener(MapListener mapListener, Object o, boolean b) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener, Object o) {

                    }

                    @Override
                    public void addMapListener(MapListener mapListener, Filter filter, boolean b) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener, Filter filter) {

                    }

                    @Override
                    public Set keySet(Filter filter) {
                        return null;
                    }

                    @Override
                    public Set entrySet(Filter filter) {
                        return null;
                    }

                    @Override
                    public Set entrySet(Filter filter, Comparator comparator) {
                        return null;
                    }

                    @Override
                    public void addIndex(ValueExtractor valueExtractor, boolean b, Comparator comparator) {

                    }

                    @Override
                    public void removeIndex(ValueExtractor valueExtractor) {

                    }

                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean containsKey(Object key) {
                        return false;
                    }

                    @Override
                    public boolean containsValue(Object value) {
                        return false;
                    }

                    @Override
                    public Object get(Object key) {
                        return null;
                    }

                    @Override
                    public Object put(Object key, Object value) {
                        return null;
                    }

                    @Override
                    public Object remove(Object key) {
                        return null;
                    }

                    @Override
                    public void putAll(Map m) {

                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public Set keySet() {
                        return null;
                    }

                    @Override
                    public Collection values() {
                        return null;
                    }

                    @Override
                    public Set<Map.Entry> entrySet() {
                        return null;
                    }
                };
                iCacheFetchFactory.fetchCachable((Object)any);
                returns(new Object(),null);
            }
        };
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx",new Keys(),iCacheFetchFactory);
        cacheManager.getCacheable(new Tenant("tenantxx"),"cacheNamexx",new Keys(),iCacheFetchFactory);
    }

    @Test
    public void testGetInternalKey() throws Exception {
        cacheManager.getInternalKey(new Tenant("tenantxx"),new Keys());
    }

    @Test
    public void testPutCache() throws Exception {
        Object value = new Object();
        Assert.assertEquals(cacheManager.putCache("keyyy",value),value);
    }

    @Test
    public void testPutCacheable() throws Exception {
        Object value = new Object();
        cacheManager.putCacheable("cacheNameyy",new Keys(),value);
        cacheManager.putCacheable("cacheNameyy","keyyy",value);
        cacheManager.putCacheable(new Tenant("tenantyy"),"cacheNameyy","keyyy",value);
        cacheManager.putCacheable(new Tenant("tenantyy"),"",new Keys(),value);
    }

    @Test(groups = {"s2"})
    public void testPutCacheable_Mocked(@Mocked CacheFactory cacheFactory) throws Exception {
        new Expectations() {
            {
                CacheFactory.getCache(anyString);
                result = new NamedCache() {
                    @Override
                    public String getCacheName() {
                        return null;
                    }

                    @Override
                    public CacheService getCacheService() {
                        return null;
                    }

                    @Override
                    public boolean isActive() {
                        return false;
                    }

                    @Override
                    public void release() {

                    }

                    @Override
                    public void destroy() {

                    }

                    @Override
                    public Object put(Object o, Object o1, long l) {
                        return null;
                    }

                    @Override
                    public Map getAll(Collection collection) {
                        return null;
                    }

                    @Override
                    public boolean lock(Object o, long l) {
                        return false;
                    }

                    @Override
                    public boolean lock(Object o) {
                        return false;
                    }

                    @Override
                    public boolean unlock(Object o) {
                        return false;
                    }

                    @Override
                    public Object invoke(Object o, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Map invokeAll(Collection collection, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Map invokeAll(Filter filter, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Object aggregate(Collection collection, EntryAggregator entryAggregator) {
                        return null;
                    }

                    @Override
                    public Object aggregate(Filter filter, EntryAggregator entryAggregator) {
                        return null;
                    }

                    @Override
                    public void addMapListener(MapListener mapListener) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener) {

                    }

                    @Override
                    public void addMapListener(MapListener mapListener, Object o, boolean b) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener, Object o) {

                    }

                    @Override
                    public void addMapListener(MapListener mapListener, Filter filter, boolean b) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener, Filter filter) {

                    }

                    @Override
                    public Set keySet(Filter filter) {
                        return null;
                    }

                    @Override
                    public Set entrySet(Filter filter) {
                        return null;
                    }

                    @Override
                    public Set entrySet(Filter filter, Comparator comparator) {
                        return null;
                    }

                    @Override
                    public void addIndex(ValueExtractor valueExtractor, boolean b, Comparator comparator) {

                    }

                    @Override
                    public void removeIndex(ValueExtractor valueExtractor) {

                    }

                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean containsKey(Object key) {
                        return false;
                    }

                    @Override
                    public boolean containsValue(Object value) {
                        return false;
                    }

                    @Override
                    public Object get(Object key) {
                        return null;
                    }

                    @Override
                    public Object put(Object key, Object value) {
                        return null;
                    }

                    @Override
                    public Object remove(Object key) {
                        return null;
                    }

                    @Override
                    public void putAll(Map m) {

                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public Set keySet() {
                        return null;
                    }

                    @Override
                    public Collection values() {
                        return null;
                    }

                    @Override
                    public Set<Map.Entry> entrySet() {
                        return null;
                    }
                };
            }
        };
        cacheManager.putCacheable(new Tenant("tenantyy"),"",new Keys(),new Object());
    }

    @Test
    public void testRemoveCacheable() throws Exception {
        String cacheName = "cacheNamezz";
        Keys keys = new Keys();
        Tenant tenant = new Tenant("tenantzz");
        String key = "keyzz";

        cacheManager.removeCacheable(cacheName,keys);
        cacheManager.removeCacheable(tenant,cacheName,keys);
        cacheManager.removeCacheable(tenant,cacheName,key);
    }

    @Test(groups = {"s2"})
    public void testRemoveCacheable_Mocked(@Mocked CacheFactory cacheFactory) throws Exception {
        new Expectations() {
            {
                CacheFactory.getCache(anyString);
                result = new NamedCache() {
                    @Override
                    public String getCacheName() {
                        return null;
                    }

                    @Override
                    public CacheService getCacheService() {
                        return null;
                    }

                    @Override
                    public boolean isActive() {
                        return false;
                    }

                    @Override
                    public void release() {

                    }

                    @Override
                    public void destroy() {

                    }

                    @Override
                    public Object put(Object o, Object o1, long l) {
                        return null;
                    }

                    @Override
                    public Map getAll(Collection collection) {
                        return null;
                    }

                    @Override
                    public boolean lock(Object o, long l) {
                        return false;
                    }

                    @Override
                    public boolean lock(Object o) {
                        return false;
                    }

                    @Override
                    public boolean unlock(Object o) {
                        return false;
                    }

                    @Override
                    public Object invoke(Object o, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Map invokeAll(Collection collection, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Map invokeAll(Filter filter, EntryProcessor entryProcessor) {
                        return null;
                    }

                    @Override
                    public Object aggregate(Collection collection, EntryAggregator entryAggregator) {
                        return null;
                    }

                    @Override
                    public Object aggregate(Filter filter, EntryAggregator entryAggregator) {
                        return null;
                    }

                    @Override
                    public void addMapListener(MapListener mapListener) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener) {

                    }

                    @Override
                    public void addMapListener(MapListener mapListener, Object o, boolean b) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener, Object o) {

                    }

                    @Override
                    public void addMapListener(MapListener mapListener, Filter filter, boolean b) {

                    }

                    @Override
                    public void removeMapListener(MapListener mapListener, Filter filter) {

                    }

                    @Override
                    public Set keySet(Filter filter) {
                        return null;
                    }

                    @Override
                    public Set entrySet(Filter filter) {
                        return null;
                    }

                    @Override
                    public Set entrySet(Filter filter, Comparator comparator) {
                        return null;
                    }

                    @Override
                    public void addIndex(ValueExtractor valueExtractor, boolean b, Comparator comparator) {

                    }

                    @Override
                    public void removeIndex(ValueExtractor valueExtractor) {

                    }

                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean containsKey(Object key) {
                        return false;
                    }

                    @Override
                    public boolean containsValue(Object value) {
                        return false;
                    }

                    @Override
                    public Object get(Object key) {
                        return null;
                    }

                    @Override
                    public Object put(Object key, Object value) {
                        return null;
                    }

                    @Override
                    public Object remove(Object key) {
                        return null;
                    }

                    @Override
                    public void putAll(Map m) {

                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public Set keySet() {
                        return null;
                    }

                    @Override
                    public Collection values() {
                        return null;
                    }

                    @Override
                    public Set<Map.Entry> entrySet() {
                        return null;
                    }
                };
            }
        };
        cacheManager.removeCacheable(new Tenant("tenantzz"),"",new Keys());
    }

    @Test
    public void testSetKeyGenerator() throws Exception {
        cacheManager.setKeyGenerator(new DefaultKeyGenerator());
    }
}
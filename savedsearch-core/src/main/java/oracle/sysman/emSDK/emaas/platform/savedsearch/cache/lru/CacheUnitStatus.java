package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by chehao on 2016/10/26.
 */
public class CacheUnitStatus {
    private static final Logger LOGGER= LogManager.getLogger(CacheUnitStatus.class);
    private Long requestCount=0L;
    private Long hitCount=0L;

    private Integer capacity;
    private Integer usage=0;

    private Long evictionCount =0L;
//    private Long eliminateExpirationCount=0L;
//    private Long eliminateCapacityCount=0L;

    public CacheUnitStatus(Integer capacity) {
        this.capacity = capacity;
    }

    public CacheUnitStatus() {
    }

    public Long getEvictionCount() {
        return evictionCount;
    }

    public void setEvictionCount(Long evictionCount) {
        this.evictionCount = evictionCount;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    /**
     * get cache groups hit rate
     * @return
     */
    public Double getHitRate(){
        return (double) (hitCount / requestCount);
        //TODO change to percentage later
    }

    /**
     * get cache groups usage rate
     * @return
     */
    public Double getUsageRate(){
        return (double) (usage / capacity);
        //TODO change to percentage later
    }
}

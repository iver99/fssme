package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru;


public class TimeUtil {
    private TimeUtil() {
    }
    public static long toMillis(int timeInSecs)
    {
        return (long)timeInSecs * 1000L;
    }
}


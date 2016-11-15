package oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache;

/**
 * Created by chehao on 2016/11/15.
 */
public class CacheException extends Exception{
    private final Integer errorCode;

    public CacheException(Integer errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public CacheException(Integer errorCode, String message, Throwable t)
    {
        super(message, t);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode()
    {
        return errorCode;
    }
}
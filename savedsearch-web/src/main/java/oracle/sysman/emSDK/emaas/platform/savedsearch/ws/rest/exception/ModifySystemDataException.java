package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception;

/**
 * Created by chehao on 10/26/2017.
 */
public class ModifySystemDataException extends Exception{
    private static final long serialVersionUID = 1L;

    public ModifySystemDataException()
    {
        super();
    }

    public ModifySystemDataException(String message)
    {
        super(message);
    }

    public ModifySystemDataException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ModifySystemDataException(Throwable cause)
    {
        super(cause);
    }
}

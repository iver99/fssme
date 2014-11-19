package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.exception;

public class UpdateSearchException extends Exception
{
    private static final long serialVersionUID = 1L;

    public UpdateSearchException ()
    {
        super();
    }

    public UpdateSearchException (String message)
    {
        super(message);
    }

    public UpdateSearchException (String message, Throwable cause)
    {
        super(message, cause);
    }

    public UpdateSearchException (Throwable cause)
    {
        super(cause);
    }
}

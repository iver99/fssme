package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception;

public class ImportException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ImportException()
	{
		super();
	}

	public ImportException(String message)
	{
		super(message);
	}

	public ImportException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ImportException(Throwable cause)
	{
		super(cause);
	}
}

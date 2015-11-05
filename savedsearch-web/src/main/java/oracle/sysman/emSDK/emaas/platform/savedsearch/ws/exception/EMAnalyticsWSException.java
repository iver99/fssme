package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception;

public class EMAnalyticsWSException extends Exception
{

	private static final String ERROR_KEY_PREFIX = "EM_ANALYTICS_WS_EXCEPTION_";
	private static final String EMAN_FWK_EXCEPTIONS_PROPERTIES_FILE = "location of properties file";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3921597403907875124L;

	//related to category input JSON Object
	public static final int JSON_CATEGORY_DEFAULT_FOLDER_ID_MISSING = 70010;
	public static final int JSON_CATEGORY_PARAM_NAME_MISSING = 70011;
	public static final int JSON_CATEGORY_NAME_MISSING = 70012;

	//related to search input JSON object
	public static final int JSON_SEARCH_NAME_MISSING = 70020;
	public static final int JSON_SEARCH_PARAM_NAME_MISSING = 70021;
	public static final int JSON_SEARCH_DISPLAY_NAME_MISSING = 70022;
	public static final int JSON_SEARCH_FOLDER_ID_MISSING = 70023;
	public static final int JSON_SEARCH_CATEGORY_ID_MISSING = 70024;
	public static final int JSON_SEARCH_PARAM_TYPE_MISSING = 70025;
	public static final int JSON_SEARCH_PARAM_TYPE_INVALID = 70026;

	//related to folder input JSON object
	public static final int JSON_FOLDER_NAME_MISSING = 70030;

	//related to conversion of JSON to Object and Object to JSON
	public static final int JSON_OBJECT_TO_JSON_EXCEPTION = 70040;
	public static final int JSON_JSON_TO_OBJECT = 70041;

	public static final int JSON_TENANT_ID_MISSING = 70042;

	public static final int JSON_INVALID_CHAR = 70045;

	private int errorCode;
	private int statusCode;
	private Object[] errorParam;

	public EMAnalyticsWSException(int errorCode, Throwable throwable)
	{
		super(throwable);
		this.errorCode = errorCode;
	}

	public EMAnalyticsWSException(String message, int errorCode)
	{
		super(message, null);
		this.errorCode = errorCode;

	}

	public EMAnalyticsWSException(String message, int errorCode, Object[] errorparam)
	{
		super(message);
		this.errorCode = errorCode;
		errorParam = errorparam;
	}

	public EMAnalyticsWSException(String message, int errorCode, Object[] errorparam, Throwable throwable)
	{
		super(message, throwable);
		this.errorCode = errorCode;
		errorParam = errorparam;
	}

	public EMAnalyticsWSException(String message, int errorCode, Throwable throwable)
	{
		super(message, throwable);
		this.errorCode = errorCode;

	}

	public EMAnalyticsWSException(Throwable throwable)
	{
		super(throwable);
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public Object[] getErrorParam()
	{
		return errorParam;
	}

	@Override
	public String getLocalizedMessage()
	{
		/*
		if(errorCode != -1){
			
		    ResourceBundle resourceBundle =  ResourceBundle.getBundle(EMAN_FWK_EXCEPTIONS_PROPERTIES_FILE, EMExecutionContext.getExecutionContext().getLocale());
		    String msg = resourceBundle.getString(ERROR_KEY_PREFIX+errorCode);
		    if (errorParams!=null && errorParams.length > 0)
		    {
		        msg = MessageFormat.format(msg, errorParams);
		    }
		    return msg;
		    
		}
		else
		*/
		return getMessage();
	}

	public int getStatusCode()
	{

		switch (errorCode) {
			case JSON_CATEGORY_DEFAULT_FOLDER_ID_MISSING:
				statusCode = 400;
				break;
			case JSON_CATEGORY_PARAM_NAME_MISSING:
				statusCode = 400;
				break;
			case JSON_CATEGORY_NAME_MISSING:
				statusCode = 400;
				break;
			case JSON_SEARCH_NAME_MISSING:
				statusCode = 400;
				break;
			case JSON_SEARCH_PARAM_NAME_MISSING:
				statusCode = 400;
				break;
			case JSON_SEARCH_DISPLAY_NAME_MISSING:
				statusCode = 400;
				break;
			case JSON_SEARCH_FOLDER_ID_MISSING:
				statusCode = 400;
				break;
			case JSON_SEARCH_CATEGORY_ID_MISSING:
				statusCode = 400;
				break;
			case JSON_FOLDER_NAME_MISSING:
				statusCode = 400;
				break;
			case JSON_SEARCH_PARAM_TYPE_INVALID:
				statusCode = 400;
				break;
			case JSON_SEARCH_PARAM_TYPE_MISSING:
				statusCode = 400;
				break;
			case JSON_OBJECT_TO_JSON_EXCEPTION:
				statusCode = 500;
				break;
			case JSON_JSON_TO_OBJECT:
				statusCode = 500;
				break;
			case JSON_INVALID_CHAR:
				statusCode = 400;
				break;

		}
		return statusCode;
	}
}

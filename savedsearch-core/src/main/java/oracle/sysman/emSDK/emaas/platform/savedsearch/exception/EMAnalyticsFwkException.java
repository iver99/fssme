package oracle.sysman.emSDK.emaas.platform.savedsearch.exception;

/* $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/EMAnalyticsFwkException.java /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:00 saurgarg Exp $ */

/* Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    harshk      12/18/13 - Creation
 */

/**
 * The class <code>EMAnalyticsFwkException</code> represents a class of exceptions thrown by EM Analytics Application sub-sysetm.
 * 
 * @version $Header:
 *          emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/EMAnalyticsFwkException
 *          .java /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:00 saurgarg Exp $
 * @author saurgarg
 * @since 13.1.0.0
 */
public class EMAnalyticsFwkException extends Exception
{

	private static final long serialVersionUID = -1837015774340642137L;

	private static final String ERROR_KEY_PREFIX = "EM_ANALYTICS_FWK_EXCEPTION_";
	private static final String EMAN_FWK_EXCEPTIONS_PROPERTIES_FILE = "oracle.sysman.emSDK.core.emanalytics.api.rsc.EMAnalyticsFwkExceptionMsg";

	/**
	 * Exception codes begin
	 */

	//generic exceptions across the area 
	public static final int ERR_GENERIC = 10000;
	public static final int ERR_NO_CONTEXT = 10001;
	public static final int ERR_INCOMPLETE_PARAMS = 10002;
	public static final int ERR_DATA_SOURCE_DETAILS = 10003;

	//search mgmt exceptions
	public static final int ERR_CREATE_SEARCH = 20020;
	public static final int ERR_SEARCH_DUP_NAME = 20021;
	public static final int ERR_SEARCH_INVALID_FOLDER = 20022;
	public static final int ERR_SEARCH_INVALID_CATEGORY = 20023;
	public static final int ERR_UPDATE_SEARCH = 20030;
	public static final int ERR_DELETE_SEARCH = 20040;
	public static final int ERR_GET_SEARCH = 20050;
	public static final int ERR_GET_SEARCH_FOR_ID = 20060;

	//search privilege management exceptions
	public static final int ERR_GET_GRANTEE_ROLES_FOR_SEARCH = 20080;
	public static final int ERR_GET_GRANTEE_USERS_FOR_SEARCH = 20081;
	public static final int ERR_ADD_GRANTEE_FOR_SEARCH = 20090;
	public static final int ERR_REVOKE_GRANTEE_FOR_SEARCH = 20091;

	//folder mgmt exceptions
	public static final int ERR_CREATE_FOLDER = 30020;
	public static final int ERR_FOLDER_DUP_NAME = 30021;
	public static final int ERR_FOLDER_INVALID_PARENT = 30022;
	public static final int ERR_UPDATE_FOLDER = 30030;
	public static final int ERR_DELETE_FOLDER = 30040;
	public static final int ERR_GET_FOLDER = 30050;
	public static final int ERR_GET_FOLDER_FOR_ID = 30060;
	public static final int ERR_GET_FOLDER_BY_NAME = 30070;

	//category mgmt exceptions
	public static final int ERR_GET_CATEGORIES = 40050;
	public static final int ERR_GET_CATEGORY_BY_NAME = 40051;
	public static final int ERR_GET_CATEGORY_BY_NAME_NOT_EXIST = 40052;
	public static final int ERR_GET_CATEGORY_BY_ID = 40053;
	public static final int ERR_GET_CATEGORY_BY_ID_NOT_EXIST = 40054;
	public static final int ERR_CREATE_CATEGORY = 40055;
	public static final int ERR_DUPLICATE_CATEGORY_NAME = 40056;
	public static final int ERR_CATEGORY_INVALID_FOLDER = 40057;
	public static final int ERR_DELETE_CATEGORY = 40060;
	public static final int ERR_UPDATE_CATEGORY = 40070;

	//upgrade data
	public static final int ERR_UPGRADE_DATA = 50010;

	public static final int ERR_EMPTY_TENANT_ID = 50011;

	public static final int ERR_VALID_TENANT_ID = 500112;

	public static final int ERR_VALID_USER_NAME = 500113;

	public static final int ERR_VALID_OAM_HEADER = 500114;
<<<<<<< HEAD

	public static final int JSON_OBJECT_TO_JSON_EXCEPTION = 70040;

	public static final int JSON_JSON_TO_OBJECT = 70041;

=======
>>>>>>> emcpssf183_sb
	/**
	 * Exception codes end
	 */

	private int errorCode;
	private Object[] errorParams;

	private int statusCode;

	/**
	 * Instantiates a new exception with a known error code and base exception.
	 * 
	 * @param errorCode
	 * @param throwable
	 */
	public EMAnalyticsFwkException(int errorCode, Throwable throwable)
	{
		super(throwable);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new exception with default message, error code and base exception.
	 * 
	 * @param errorMsg
	 * @param errorCode
	 * @param errorParams
	 */
	public EMAnalyticsFwkException(String errorMsg, int errorCode, Object[] errorParams)
	{
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorParams = errorParams;
	}

	/**
	 * Instantiates a new exception with default message, error code , params and base exception.
	 * 
	 * @param errorMsg
	 * @param errorCode
	 * @param errorParams
	 * @param throwable
	 */
	public EMAnalyticsFwkException(String errorMsg, int errorCode, Object[] errorParams, Throwable throwable)
	{
		super(errorMsg, throwable);
		this.errorCode = errorCode;
		this.errorParams = errorParams;
	}

	/**
	 * Instantiates a new exception with base exception.
	 * 
	 * @param throwable
	 */
	public EMAnalyticsFwkException(Throwable throwable)
	{
		super(throwable);
	}

	/**
	 * Returns the error code.
	 * 
	 * @return error code
	 */
	public int getErrorCode()
	{
		return errorCode;
	}

	/**
	 * Returns the error params.
	 * 
	 * @return error params
	 */
	public Object[] getErrorParams()
	{
		return errorParams;
	}

	/**
	 * Returns the localized message for the exception (if it has an error code), else returns default message.
	 * 
	 * @return localized message for the exception
	 */
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

		//status codes related to folder exception 
			case EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME:
				statusCode = 400;
				break;
			case EMAnalyticsFwkException.ERR_CREATE_FOLDER:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_UPDATE_FOLDER:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_DELETE_FOLDER:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_GET_FOLDER:
				statusCode = 500;
				break;

			//status codes related to search exceptions
			case EMAnalyticsFwkException.ERR_CREATE_SEARCH:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_DELETE_SEARCH:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_UPDATE_SEARCH:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_GET_SEARCH:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME:
				statusCode = 400;
				break;
			case EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID:
				statusCode = 404;
				break;
			//Status Codes related to category exceptions
			case EMAnalyticsFwkException.ERR_DELETE_CATEGORY:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_CREATE_CATEGORY:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_UPDATE_CATEGORY:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID_NOT_EXIST:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_DUPLICATE_CATEGORY_NAME:
				statusCode = 400;
				break;
			case EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER:
				statusCode = 404;
				break;
			case EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME:
				statusCode = 404;
				break;

			//status code related to generic error
			case EMAnalyticsFwkException.ERR_GENERIC:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_NO_CONTEXT:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_INCOMPLETE_PARAMS:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.ERR_UPGRADE_DATA:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION:
				statusCode = 500;
				break;
			case EMAnalyticsFwkException.JSON_JSON_TO_OBJECT:
				statusCode = 500;
				break;
		}
		return statusCode;
	}

}

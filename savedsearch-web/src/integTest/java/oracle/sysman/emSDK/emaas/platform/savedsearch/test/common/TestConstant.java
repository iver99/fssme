/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.test.common;

import oracle.sysman.qatool.uifwk.utils.Utils;

/**
 * @author vinjoshi
 */
public class TestConstant
{
	public static final String TENANT_ID_OPC1 = "1";
	public static final String TENANT_ID_OPC2 = "2";
	public static final String TENANT_ID_OPC3 = "3";
	public static String TENANT_ID_OPC0;
	
	static  
	{  try{
		 TENANT_ID_OPC0 = Utils.getProperty("TENANT_ID");
	}catch(Exception e){
		  TENANT_ID_OPC0 = "emaastesttenant1";
		
	}	
	}
	public static final String SSF_HEADER = "ssfheadertest";
	public static final String TENANT_ID1 = "1.User1";
	public static final String TENANT_ID2 = "2.User2";
	public static final String TENANT_ID3 = "3.User3";
	public static final String TENANT_ID0 = "emaastesttenant1.User0";
	public static final String SSF_HEADER_VALUE = "ssfheadertestvalue";
	public static final String OAM_HEADER = "OAM_REMOTE_USER";
	public static final String X_HEADER = "X-REMOTE-USER";

}

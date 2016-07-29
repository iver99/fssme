Rem
Rem emaas_savesearch_seed_data.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for category and default folder
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ADUAN       07/05/16 - Add new category for Security Analytics
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_COUNT                                 NUMBER;
  V_CATEGORY_ID                           NUMBER(38,0);
  V_NAME                                  VARCHAR2(64 BYTE);
  V_DESCRIPTION                           VARCHAR2(256 BYTE);
  V_OWNER                                 VARCHAR2(64 BYTE);
  V_CREATION_DATE                         TIMESTAMP(6);
  V_NAME_NLSID                            VARCHAR2(256 BYTE);
  V_NAME_SUBSYSTEM                        VARCHAR2(64 BYTE);
  V_DESCRIPTION_NLSID                     VARCHAR2(256 BYTE);
  V_DESCRIPTION_SUBSYSTEM                 VARCHAR2(64 BYTE);
  V_EM_PLUGIN_ID                          VARCHAR2(64 BYTE);
  V_DEFAULT_FOLDER_ID                     NUMBER(38,0);
  V_PROVIDER_NAME                         VARCHAR2(64 BYTE);
  V_PROVIDER_VERSION                      VARCHAR2(64 BYTE);
  V_PROVIDER_ASSET_ROOT                   VARCHAR2(64 BYTE);
  V_TENANT_ID                             NUMBER(38,0) := '&TENANT_ID';
  V_FOLDER_ID				  NUMBER(38,0);
  V_PARENT_ID				  NUMBER(38,0);
  V_LAST_MODIFICATION_DATE	          TIMESTAMP(6);
  V_LAST_MODIFIED_BY			  VARCHAR2(64 BYTE);
  V_SYSTEM_FOLDER		          NUMBER(1,0);
  V_UI_HIDDEN				  NUMBER(1,0);
  V_DELETED			          NUMBER(38,0);

BEGIN
  SELECT COUNT(CATEGORY_ID) INTO V_COUNT FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=6 AND TENANT_ID=V_TENANT_ID;
  IF (V_COUNT<1) THEN
    V_FOLDER_ID				:=   7;
    V_NAME				:=   'Security Analytics Searches';
    V_PARENT_ID				:=   1;
    V_DESCRIPTION			:=   'Security Analytics Root Folder';
    V_CREATION_DATE			:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_OWNER				:=   'ORACLE';
    V_LAST_MODIFICATION_DATE	        :=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFIED_BY			:=   'ORACLE';
    V_NAME_NLSID			:=   'ALL_SECURITYANALYTICS_SEARCHES_NAME';
    V_NAME_SUBSYSTEM			:=   'EMANALYTICS';
    V_DESCRIPTION_NLSID		        :=   'ALL_SECURITYANALYTICS_SEARCHES_DESC';
    V_DESCRIPTION_SUBSYSTEM	        :=   'EMANALYTICS';
    V_SYSTEM_FOLDER			:=   1;
    V_EM_PLUGIN_ID			:=   'oracle.sysman.core';
    V_UI_HIDDEN				:=   0;

  INSERT INTO EMS_ANALYTICS_FOLDERS(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,TENANT_ID)
                            VALUES(V_FOLDER_ID,V_NAME,V_PARENT_ID,V_DESCRIPTION,V_CREATION_DATE,V_OWNER,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_NAME_NLSID,V_NAME_SUBSYSTEM,V_DESCRIPTION_NLSID,V_DESCRIPTION_SUBSYSTEM,V_SYSTEM_FOLDER,V_EM_PLUGIN_ID,V_UI_HIDDEN,V_TENANT_ID);

  V_CATEGORY_ID              :=   6;
  V_NAME                     :=   'Security Analytics';
  V_DESCRIPTION              :=   'Search Category for Security Analytics';
  V_OWNER		     :=   'ORACLE';
  V_CREATION_DATE            :=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
  V_NAME_NLSID               :=   'SECURITYANALYTICS_CATEGORY_NAME';
  V_NAME_SUBSYSTEM           :=   'EMANALYTICS';
  V_DESCRIPTION_NLSID        :=   'SECURITYANALYTICS_CATEGORY_DESC';
  V_DESCRIPTION_SUBSYSTEM    :=   'EMANALYTICS';
  V_EM_PLUGIN_ID             :=   'oracle.sysman.core';
  V_DEFAULT_FOLDER_ID        :=   7;
  V_PROVIDER_NAME            :=   'SecurityAnalyticsUI';
  V_PROVIDER_VERSION         :=   '1.0';
  V_PROVIDER_ASSET_ROOT      :=   'home';

  INSERT INTO EMS_ANALYTICS_CATEGORY(CATEGORY_ID,NAME,DESCRIPTION,OWNER,CREATION_DATE,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,EM_PLUGIN_ID,DEFAULT_FOLDER_ID,PROVIDER_NAME,PROVIDER_VERSION,PROVIDER_ASSET_ROOT,TENANT_ID)
                              VALUES(V_CATEGORY_ID,V_NAME,V_DESCRIPTION,V_OWNER,V_CREATION_DATE,V_NAME_NLSID,V_NAME_SUBSYSTEM,V_DESCRIPTION_NLSID,V_DESCRIPTION_SUBSYSTEM,V_EM_PLUGIN_ID,V_DEFAULT_FOLDER_ID,V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT,V_TENANT_ID);
  
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('New folder, category and categrory parameters for Security Analytics have been created successfully!');
ELSE
  DBMS_OUTPUT.PUT_LINE('Folder and category for Security Analytics had been created before, no need to create again.');
END IF;

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to create folder and category for Security Analytics due to error '||SQLERRM);
    RAISE;
END;
/


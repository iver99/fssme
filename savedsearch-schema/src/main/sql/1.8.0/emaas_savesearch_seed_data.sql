SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE TENANT_ID ='&1'
DECLARE
  V_COUNT number;
  V_CATEGORY_ID                           NUMBER(38,0)             ;
  V_NAME                                  VARCHAR2(64 BYTE)        ;
  V_DESCRIPTION                           VARCHAR2(256 BYTE)       ;
  V_OWNER                                 VARCHAR2(64 BYTE)        ;
  V_CREATION_DATE                         TIMESTAMP(6)             ;
  V_NAME_NLSID                            VARCHAR2(256 BYTE)       ;
  V_NAME_SUBSYSTEM                        VARCHAR2(64 BYTE)        ;
  V_DESCRIPTION_NLSID                     VARCHAR2(256 BYTE)       ;
  V_DESCRIPTION_SUBSYSTEM                 VARCHAR2(64 BYTE)        ;
  V_EM_PLUGIN_ID                          VARCHAR2(64 BYTE)        ;
  V_DEFAULT_FOLDER_ID                     NUMBER(38,0)             ;
  V_PROVIDER_NAME                         VARCHAR2(64 BYTE)        ;
  V_PROVIDER_VERSION                      VARCHAR2(64 BYTE)        ;
  V_PROVIDER_ASSET_ROOT                   VARCHAR2(64 BYTE)        ;
  V_TENANT_ID                             NUMBER(38,0)   :=  '&TENANT_ID';

  V_PARAM_NAME                            VARCHAR2(64 BYTE)        ;
  V_PARAM_VALUE                           VARCHAR2(1024 BYTE)      ;

  V_FOLDER_ID					 NUMBER(38,0)          ;
  V_PARENT_ID					 NUMBER(38,0)          ;
  V_LAST_MODIFICATION_DATE	     TIMESTAMP(6)          ;
  V_LAST_MODIFIED_BY			 VARCHAR2(64 BYTE)     ;
  V_SYSTEM_FOLDER				 NUMBER(1,0)           ;
  V_UI_HIDDEN					 NUMBER(1,0)           ;
  V_DELETED					     NUMBER(38,0)          ;
BEGIN

SELECT COUNT(CATEGORY_ID) INTO V_COUNT FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID=5 AND TENANT_ID=V_TENANT_ID;
IF (V_COUNT<1) THEN
 V_FOLDER_ID				:=   6;
 V_NAME						:=   'Target Card Searches';
 V_PARENT_ID				:=   1;
 V_DESCRIPTION				:=   'Target Card Root Folder';
 V_CREATION_DATE			:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
 V_OWNER					:=   'ORACLE';
 V_LAST_MODIFICATION_DATE	:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
 V_LAST_MODIFIED_BY			:=   'ORACLE';
 V_NAME_NLSID				:=   'ALL_TARGETCARD_SEARCHES_NAME';
 V_NAME_SUBSYSTEM			:=   'EMANALYTICS';
 V_DESCRIPTION_NLSID		:=   'ALL_TARGETCARD_SEARCHES_DESC';
 V_DESCRIPTION_SUBSYSTEM	:=   'EMANALYTICS';
 V_SYSTEM_FOLDER			:=   1;
 V_EM_PLUGIN_ID				:=   'oracle.sysman.core';
 V_UI_HIDDEN				:=   1;

 Insert into
 EMS_ANALYTICS_FOLDERS
 (FOLDER_ID,
  NAME,
  PARENT_ID,
  DESCRIPTION,
  CREATION_DATE,
  OWNER,
  LAST_MODIFICATION_DATE,
  LAST_MODIFIED_BY,
  NAME_NLSID,
  NAME_SUBSYSTEM,
  DESCRIPTION_NLSID,
  DESCRIPTION_SUBSYSTEM,
  SYSTEM_FOLDER,
  EM_PLUGIN_ID,
  UI_HIDDEN,
  TENANT_ID)
  values
  (V_FOLDER_ID,
   V_NAME,
   V_PARENT_ID,
   V_DESCRIPTION,
   V_CREATION_DATE,
   V_OWNER,
   V_LAST_MODIFICATION_DATE,
   V_LAST_MODIFIED_BY,
   V_NAME_NLSID,
   V_NAME_SUBSYSTEM,
   V_DESCRIPTION_NLSID,
   V_DESCRIPTION_SUBSYSTEM,
   V_SYSTEM_FOLDER,
   V_EM_PLUGIN_ID,
   V_UI_HIDDEN,
   V_TENANT_ID);


  V_CATEGORY_ID              :=   5;
  V_NAME                     :=   'Target Card';
  V_DESCRIPTION              :=   'Search Category for Target Card';
  V_OWNER					 :=   'ORACLE';
  V_CREATION_DATE            :=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
  V_NAME_NLSID               :=   'TARGETCARD_CATEGORY_NAME';
  V_NAME_SUBSYSTEM           :=   'EMANALYTICS';
  V_DESCRIPTION_NLSID        :=   'TARGETCARD_CATEGORY_DESC';
  V_DESCRIPTION_SUBSYSTEM    :=   'EMANALYTICS';
  V_EM_PLUGIN_ID             :=   'oracle.sysman.core';
  V_DEFAULT_FOLDER_ID        :=   6;
  V_PROVIDER_NAME            :=  'TargetCard';
  V_PROVIDER_VERSION         :=   '1.0';
  V_PROVIDER_ASSET_ROOT      :=   'home';


  Insert into EMS_ANALYTICS_CATEGORY (
   CATEGORY_ID,
   NAME,
   DESCRIPTION,
   OWNER,
   CREATION_DATE,
   NAME_NLSID,
   NAME_SUBSYSTEM,
   DESCRIPTION_NLSID,
   DESCRIPTION_SUBSYSTEM,
   EM_PLUGIN_ID,
   DEFAULT_FOLDER_ID,
   PROVIDER_NAME,
   PROVIDER_VERSION,
   PROVIDER_ASSET_ROOT,
   TENANT_ID
   )values (
   V_CATEGORY_ID,
   V_NAME,
   V_DESCRIPTION,
   V_OWNER,
   V_CREATION_DATE,
   V_NAME_NLSID,
   V_NAME_SUBSYSTEM,
   V_DESCRIPTION_NLSID,
   V_DESCRIPTION_SUBSYSTEM,
   V_EM_PLUGIN_ID,
   V_DEFAULT_FOLDER_ID,
   V_PROVIDER_NAME ,
   V_PROVIDER_VERSION,
   V_PROVIDER_ASSET_ROOT,
   V_TENANT_ID);

   V_PARAM_NAME              := 'DASHBOARD_INELIGIBLE';
   V_PARAM_VALUE             := 1;
  Insert into EMS_ANALYTICS_CATEGORY_PARAMS(
   CATEGORY_ID,
   NAME,
   PARAM_VALUE,
   TENANT_ID
   )values(
   V_CATEGORY_ID,
   V_PARAM_NAME,
   V_PARAM_VALUE,
   V_TENANT_ID
   );
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('New folder,category and categrory param for Target Card have been created successfully!');
ELSE
  DBMS_OUTPUT.PUT_LINE('Folder Or Category for Target Card had been created before, no need to create again');
END IF;

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to create folder and category for Target Card  due to '||SQLERRM);
    RAISE;
END;
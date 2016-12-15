Rem
Rem emaas_savesearch_seed_data_ude.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_ude.sql 
Rem
Rem    DESCRIPTION
Rem      Create new folder to store UDE real-time usage statistics
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    ADUAN       09/13/16 - Add new folder
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_COUNT                                 NUMBER;
  V_NAME                                  VARCHAR2(64 BYTE);
  V_DESCRIPTION                           VARCHAR2(256 BYTE);
  V_OWNER                                 VARCHAR2(64 BYTE);
  V_CREATION_DATE                         TIMESTAMP(6);
  V_NAME_NLSID                            VARCHAR2(256 BYTE);
  V_NAME_SUBSYSTEM                        VARCHAR2(64 BYTE);
  V_DESCRIPTION_NLSID                     VARCHAR2(256 BYTE);
  V_DESCRIPTION_SUBSYSTEM                 VARCHAR2(64 BYTE);
  V_EM_PLUGIN_ID                          VARCHAR2(64 BYTE);
  V_TENANT_ID                             NUMBER(38,0);
  V_IS_WIDGET                             NUMBER(38,0);
  V_FOLDER_ID				  NUMBER(38,0);
  V_PARENT_ID				  NUMBER(38,0);
  V_LAST_MODIFICATION_DATE	          TIMESTAMP(6);
  V_LAST_MODIFIED_BY			  VARCHAR2(64 BYTE);
  V_SYSTEM_FOLDER		          NUMBER(1,0);
  V_UI_HIDDEN				  NUMBER(1,0);  
  V_TID                   NUMBER(38,0) := '&TENANT_ID';
  CURSOR TENANT_CURSOR IS
    SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_FOLDERS ORDER BY TENANT_ID;
BEGIN
  OPEN TENANT_CURSOR;
  LOOP
     IF (V_TID<>-1) THEN
        V_TENANT_ID:=V_TID;
     ELSE
       FETCH TENANT_CURSOR INTO V_TENANT_ID;
       EXIT WHEN TENANT_CURSOR%NOTFOUND;     
     END IF;
  SELECT COUNT(FOLDER_ID) INTO V_COUNT FROM EMS_ANALYTICS_FOLDERS WHERE FOLDER_ID = 401 AND TENANT_ID=V_TENANT_ID;
  IF (V_COUNT<1) THEN
    V_FOLDER_ID				:=   401;
    V_NAME					:=   'UDE Type-Ahead Feature';
    V_PARENT_ID				:=   4;
    V_DESCRIPTION			:=   'Folder for UDE Type-Ahead Feature';
    V_CREATION_DATE			:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_OWNER					:=   'ORACLE';
    V_LAST_MODIFICATION_DATE	:=   SYS_EXTRACT_UTC(SYSTIMESTAMP);
    V_LAST_MODIFIED_BY			:=   'ORACLE';
    V_NAME_NLSID			:=   'UDE_TYPE_AHEAD_FEATURE';
    V_NAME_SUBSYSTEM		:=   'EMANALYTICS';
    V_DESCRIPTION_NLSID		:=   'UDE_TYPE_AHEAD_FEATURE_DESC';
    V_DESCRIPTION_SUBSYSTEM	:=   'EMANALYTICS';
    V_SYSTEM_FOLDER			:=   1;
    V_EM_PLUGIN_ID			:=   'oracle.sysman.core';
    V_UI_HIDDEN				:=   0;

  INSERT INTO EMS_ANALYTICS_FOLDERS(FOLDER_ID,NAME,PARENT_ID,DESCRIPTION,CREATION_DATE,OWNER,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_FOLDER,EM_PLUGIN_ID,UI_HIDDEN,TENANT_ID)
                            VALUES(V_FOLDER_ID,V_NAME,V_PARENT_ID,V_DESCRIPTION,V_CREATION_DATE,V_OWNER,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_NAME_NLSID,V_NAME_SUBSYSTEM,V_DESCRIPTION_NLSID,V_DESCRIPTION_SUBSYSTEM,V_SYSTEM_FOLDER,V_EM_PLUGIN_ID,V_UI_HIDDEN,V_TENANT_ID);

  DBMS_OUTPUT.PUT_LINE('New folder for UDE have been created successfully! tenant_id='||V_TENANT_ID);
ELSE
  DBMS_OUTPUT.PUT_LINE('Folder for UDE had been created before, no need to create again. tenant_id='||V_TENANT_ID);
END IF;
  SELECT COUNT(FOLDER_ID) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 4005 AND IS_WIDGET=1 AND TENANT_ID=V_TENANT_ID;
  IF (V_COUNT=1) THEN
    V_IS_WIDGET			:=   0;

  UPDATE EMS_ANALYTICS_SEARCH
  SET IS_WIDGET=V_IS_WIDGET
  WHERE SEARCH_ID = 4005 AND TENANT_ID=V_TENANT_ID;

  DBMS_OUTPUT.PUT_LINE('Target card: CARD_DEF_omc_workflow_submission have been updated successfully! tenant_id='||V_TENANT_ID);
ELSE
  DBMS_OUTPUT.PUT_LINE('Target card: CARD_DEF_omc_workflow_submission have been updated before, no need to update again. tenant_id='||V_TENANT_ID);
END IF;

     IF (V_TID<>-1) THEN
        EXIT;
     END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to create folder for UDE due to error '||SQLERRM);
    RAISE;
END;
/
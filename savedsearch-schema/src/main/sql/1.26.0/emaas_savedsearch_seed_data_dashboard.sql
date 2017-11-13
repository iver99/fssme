Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savedsearch_seed_data_dashboard.sql
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    MIAYU       11/09/17 -  add new category and folder to support new service DashboardUI
Rem

SET FEEDBACK ON
SET SERVEROUTPUT ON
DECLARE
V_FOLDER_ID                  NUMBER(38,0)          ;
V_NAME                       VARCHAR2(64 BYTE)     ;
V_PARENT_ID                  NUMBER(38,0)          ;
V_DESCRIPTION                VARCHAR2(256 BYTE)    ;
V_CREATION_DATE              TIMESTAMP(6)          ;
V_OWNER                      VARCHAR2(64 BYTE)     ;
V_LAST_MODIFICATION_DATE     TIMESTAMP(6)          ;
V_LAST_MODIFIED_BY           VARCHAR2(64 BYTE)     ;
V_NAME_NLSID                 VARCHAR2(256 BYTE)    ;
V_NAME_SUBSYSTEM             VARCHAR2(64 BYTE)     ;
V_DESCRIPTION_NLSID          VARCHAR2(256 BYTE)    ;
V_DESCRIPTION_SUBSYSTEM      VARCHAR2(64 BYTE)     ;
V_SYSTEM_FOLDER              NUMBER(1,0)           ;
V_EM_PLUGIN_ID               VARCHAR2(64 BYTE)     ;
V_UI_HIDDEN                  NUMBER(1,0)           ;
V_TENANT_ID                  NUMBER(38,0)          ;
V_TIMESTAMP_STR              VARCHAR2(64 BYTE);

V_CATEGORY_ID                NUMBER(38,0);
V_DEFAULT_FOLDER_ID          NUMBER(38,0);
V_PROVIDER_NAME              VARCHAR2(64 BYTE);
V_PROVIDER_VERSION           VARCHAR2(64 BYTE);
V_PROVIDER_ASSET_ROOT        VARCHAR2(64 BYTE);

V_SEARCH_ID                           NUMBER                  ;
V_SYSTEM_SEARCH                       NUMBER                  ;
V_IS_LOCKED                           NUMBER                  ;
V_DELETED                             NUMBER                  ;
V_IS_WIDGET                           NUMBER                  ;
V_WIDGET_SOURCE                       VARCHAR2(64 BYTE);
V_METADATA                            CLOB;
V_SEARCH_STR                          CLOB;
V_WIDGET_GROUP_NAME                   EMS_ANALYTICS_SEARCH.WIDGET_GROUP_NAME%TYPE;
V_WIDGET_ICON                         EMS_ANALYTICS_SEARCH.WIDGET_ICON%TYPE;
V_WIDGET_KOC_NAME                     EMS_ANALYTICS_SEARCH.WIDGET_KOC_NAME%TYPE;
V_WIDGET_VIEWMODEL                    EMS_ANALYTICS_SEARCH.WIDGET_VIEWMODEL%TYPE;
V_WIDGET_TEMPLATE                     EMS_ANALYTICS_SEARCH.WIDGET_TEMPLATE%TYPE;
V_WIDGET_SUPPORT_TIME_CONTROL         EMS_ANALYTICS_SEARCH.WIDGET_SUPPORT_TIME_CONTROL%TYPE;
V_DASHBOARD_INELIGIBLE                EMS_ANALYTICS_SEARCH.DASHBOARD_INELIGIBLE%TYPE;
V_WIDGET_LINKED_DASHBOARD             EMS_ANALYTICS_SEARCH.WIDGET_LINKED_DASHBOARD%TYPE;
V_WIDGET_DEFAULT_WIDTH                EMS_ANALYTICS_SEARCH.WIDGET_DEFAULT_WIDTH%TYPE;
V_WIDGET_DEFAULT_HEIGHT               EMS_ANALYTICS_SEARCH.WIDGET_DEFAULT_HEIGHT%TYPE;

V_COUNT NUMBER;
BEGIN
 V_TENANT_ID := -11;
 V_TIMESTAMP_STR             := '2017-11-09T00:00:00.000Z';
 V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
 V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
 
 -- Dashboard Folder
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_FOLDERS WHERE FOLDER_ID = 11;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('The folder for Dashboard has been added before, no need to add again!');
 ELSE
 V_FOLDER_ID                :=   11;
 V_NAME                     :=   'Dashboard Searches';
 V_PARENT_ID                :=   1;
 V_DESCRIPTION              :=   'Dashboard Root Folder';
 V_OWNER                    :=   'ORACLE';
 V_LAST_MODIFIED_BY         :=   'ORACLE';
 V_NAME_NLSID               :=   null;
 V_NAME_SUBSYSTEM           :=   null;
 V_DESCRIPTION_NLSID        :=   null;
 V_DESCRIPTION_SUBSYSTEM    :=   null;
 V_SYSTEM_FOLDER            :=   1;
 V_EM_PLUGIN_ID             :=   'oracle.sysman.core';
 V_UI_HIDDEN                :=   0;

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
  DBMS_OUTPUT.PUT_LINE('The folder for Dashboard has been added successfully!');
 END IF;
 
 -- Dashboard Category
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_CATEGORY WHERE CATEGORY_ID = 10;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('The category data for Dashboard has been added before, no need to add again!');
 ELSE
 V_CATEGORY_ID              := 10;
 V_NAME                     := 'Dashboard';
 V_DESCRIPTION              := 'Search Category for Dashboard';
 V_OWNER                    := 'ORACLE';
 V_NAME_NLSID               := null;
 V_NAME_SUBSYSTEM           := null;
 V_DESCRIPTION_NLSID        := null;
 V_DESCRIPTION_SUBSYSTEM    := null;
 V_EM_PLUGIN_ID             := 'oracle.sysman.core';
 V_DEFAULT_FOLDER_ID        := 11;
 V_PROVIDER_NAME            := 'Dashboard-UI';
 V_PROVIDER_VERSION         := '1.0';
 V_PROVIDER_ASSET_ROOT      := 'assetRoot';

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
DBMS_OUTPUT.PUT_LINE('The category for Dashboard has been added successfully!');
END IF;

-- HTML Widget definition
       SELECT COUNT(1) into v_count FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID = 1000 AND TENANT_ID = V_TENANT_ID;
       IF v_count > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Dashboard HTML Widget already exists, no need to add');
       ELSE
       V_SEARCH_ID                          :=1000;
       V_NAME                               :='HTML Widget';
       V_OWNER                              :='ORACLE';
       V_LAST_MODIFIED_BY                   :='ORACLE';
       V_DESCRIPTION                        :='HTML Widget provided by Oracle';
       V_FOLDER_ID                          :=11; 
       V_CATEGORY_ID                        :=10; 
       V_SYSTEM_SEARCH                      :=1;
       V_IS_LOCKED                          :=0;
       V_UI_HIDDEN                          :=0;
       V_DELETED                            :=0;
       V_IS_WIDGET                          :=1;
       V_METADATA                           :=null;
       V_SEARCH_STR                         :='';
       V_WIDGET_SOURCE                      :='0';
       V_WIDGET_GROUP_NAME                  :='Dashboard';
       V_WIDGET_ICON                        :=null;
       V_WIDGET_KOC_NAME                    :='EMCPDF_HTMLWIDGET_V1';
       V_WIDGET_VIEWMODEL                   :='/js/widgets/htmlwidget/js/htmlwidget.js';
       V_WIDGET_TEMPLATE                    :='/js/widgets/htmlwidget/htmlwidget.html';
       V_WIDGET_SUPPORT_TIME_CONTROL        :=0;
       V_DASHBOARD_INELIGIBLE               :=0;
       V_PROVIDER_NAME                      :='Dashboard-UI';
       V_PROVIDER_VERSION                   :='1.0';
       V_PROVIDER_ASSET_ROOT                :='assetRoot';
       V_WIDGET_LINKED_DASHBOARD            :=null;
       V_WIDGET_DEFAULT_WIDTH               :=4;
       V_WIDGET_DEFAULT_HEIGHT              :=2;
       INSERT INTO EMS_ANALYTICS_SEARCH (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,
                                         SYSTEM_SEARCH,IS_LOCKED,UI_HIDDEN,DELETED,IS_WIDGET,TENANT_ID,METADATA_CLOB,SEARCH_DISPLAY_STR,WIDGET_SOURCE,
                                         WIDGET_GROUP_NAME,WIDGET_ICON,WIDGET_KOC_NAME,WIDGET_VIEWMODEL,WIDGET_TEMPLATE,WIDGET_SUPPORT_TIME_CONTROL,
                                         WIDGET_LINKED_DASHBOARD,WIDGET_DEFAULT_WIDTH,WIDGET_DEFAULT_HEIGHT,DASHBOARD_INELIGIBLE,PROVIDER_NAME,PROVIDER_VERSION,
                                         PROVIDER_ASSET_ROOT)
       VALUES (V_SEARCH_ID,V_NAME,V_OWNER,V_CREATION_DATE,V_LAST_MODIFICATION_DATE,V_LAST_MODIFIED_BY,V_DESCRIPTION,V_FOLDER_ID,V_CATEGORY_ID,V_SYSTEM_SEARCH,
               V_IS_LOCKED,V_UI_HIDDEN,V_DELETED,V_IS_WIDGET,V_TENANT_ID,V_METADATA,V_SEARCH_STR,V_WIDGET_SOURCE,V_WIDGET_GROUP_NAME,V_WIDGET_ICON,V_WIDGET_KOC_NAME,
               V_WIDGET_VIEWMODEL,V_WIDGET_TEMPLATE,V_WIDGET_SUPPORT_TIME_CONTROL,V_WIDGET_LINKED_DASHBOARD,V_WIDGET_DEFAULT_WIDTH,V_WIDGET_DEFAULT_HEIGHT,V_DASHBOARD_INELIGIBLE,
               V_PROVIDER_NAME,V_PROVIDER_VERSION,V_PROVIDER_ASSET_ROOT);
       DBMS_OUTPUT.PUT_LINE('The Dashboard HTML Widget has been added successfully!');
       END IF;
       
COMMIT;
EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('FAILED to add new category and HTML Widget for Dashboard : '||SQLERRM);
  RAISE;
END;
/

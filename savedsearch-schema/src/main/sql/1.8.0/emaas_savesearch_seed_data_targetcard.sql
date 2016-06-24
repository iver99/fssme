Rem
Rem emaas_savesearch_seed_data_targetcard.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_targetcard.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for category and default folder
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    Miao     06/21/16 - Created
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

---add 4 OOB Target Card mappings
DECLARE
  v_count number;
  v_searchid_1 number:=4000;
  v_searchid_2 number:=4001;
  v_searchid_3 number:=4002;
  v_searchid_4 number:=4003;

BEGIN


    SELECT COUNT(1) INTO v_count FROM EMS_ANALYTICS_SEARCH WHERE search_id=v_searchid_1 AND TENANT_ID  ='&TENANT_ID';
    IF (v_count=0) THEN
        Insert into EMS_ANALYTICS_SEARCH
        (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID,IS_WIDGET)
        values (v_searchid_1,'CARD_DEF_omc_weblogic_j2eeserver','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,6,5,null,null,null,null,1,null,0,null,null,0,'&TENANT_ID',0);

        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_1,'cardef',null,2,null,'{"id":null,"entityType":"omc_weblogic_j2eeserver","widgets":null,"widgetRefs":[{"id":3030,"parameters":null}]}','&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_1,'entityType',null,1,'omc_weblogic_j2eeserver',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_1,'isCardDef',null,1,'true',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_1,'version',null,1,'1001',null,'&TENANT_ID');
        
        insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) values (v_searchid_1,'ORACLE',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID');        

        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_1||') is added');  
    ELSE
      DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_1||') exists, no need to add again');  
    END IF;  

    SELECT COUNT(1) INTO v_count FROM EMS_ANALYTICS_SEARCH WHERE search_id=v_searchid_2 AND TENANT_ID  ='&TENANT_ID';
    IF (v_count=0) THEN
        Insert into EMS_ANALYTICS_SEARCH
        (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID,IS_WIDGET)
        values (v_searchid_2,'CARD_DEF_omc_oracle_db','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,6,5,null,null,null,null,1,null,0,null,null,0,'&TENANT_ID',0);

        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_2,'cardef',null,2,null,'{"id":null,"entityType":"omc_oracle_db","widgets":null,"widgetRefs":[{"id":3029,"parameters":null}]}','&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_2,'entityType',null,1,'omc_oracle_db',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_2,'isCardDef',null,1,'true',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_2,'version',null,1,'1001',null,'&TENANT_ID');
      
        insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) values (v_searchid_2,'ORACLE',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID');        

        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_2||') is added');  
    ELSE
      DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_2||') exists, no need to add again');  
    END IF;  
  
    SELECT COUNT(1) INTO v_count FROM EMS_ANALYTICS_SEARCH WHERE search_id=v_searchid_3 AND TENANT_ID  ='&TENANT_ID';
    IF (v_count=0) THEN
        Insert into EMS_ANALYTICS_SEARCH
        (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID,IS_WIDGET)
        values (v_searchid_3,'CARD_DEF_omc_host','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,6,5,null,null,null,null,1,null,0,null,null,0,'&TENANT_ID',0);

        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_3,'cardef',null,2,null,'{"id":null,"entityType":"omc_host_linux","widgets":null,"widgetRefs":[{"id":3031,"parameters":null}]}','&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_3,'entityType',null,1,'omc_host_linux',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_3,'isCardDef',null,1,'true',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_3,'version',null,1,'1001',null,'&TENANT_ID');
      
        insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) values (v_searchid_3,'ORACLE',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID');        

        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_3||') is added');  
    ELSE
      DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_3||') exists, no need to add again');  
    END IF;  
     
    SELECT COUNT(1) INTO v_count FROM EMS_ANALYTICS_SEARCH WHERE search_id=v_searchid_4 AND TENANT_ID  ='&TENANT_ID';
    IF (v_count=0) THEN
        Insert into EMS_ANALYTICS_SEARCH
        (SEARCH_ID,NAME,OWNER,CREATION_DATE,LAST_MODIFICATION_DATE,LAST_MODIFIED_BY,DESCRIPTION,FOLDER_ID,CATEGORY_ID,NAME_NLSID,NAME_SUBSYSTEM,DESCRIPTION_NLSID,DESCRIPTION_SUBSYSTEM,SYSTEM_SEARCH,EM_PLUGIN_ID,IS_LOCKED,METADATA_CLOB,SEARCH_DISPLAY_STR,UI_HIDDEN,TENANT_ID,IS_WIDGET)
        values (v_searchid_4,'CARD_DEF_omc_entity','ORACLE',SYS_EXTRACT_UTC(SYSTIMESTAMP),SYS_EXTRACT_UTC(SYSTIMESTAMP),'ORACLE',null,6,5,null,null,null,null,1,null,0,null,null,0,'&TENANT_ID',0);

        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_4,'cardef',null,2,null,'{"id":null,"entityType":"omc_entity","widgets":null,"widgetRefs":[{"id":2027,"parameters":null}]}','&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_4,'entityType',null,1,'omc_entity',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_4,'isCardDef',null,1,'true',null,'&TENANT_ID');
        Insert into EMS_ANALYTICS_SEARCH_PARAMS (SEARCH_ID,NAME,PARAM_ATTRIBUTES,PARAM_TYPE,PARAM_VALUE_STR,PARAM_VALUE_CLOB,TENANT_ID) values (v_searchid_4,'version',null,1,'1001',null,'&TENANT_ID');
      
        insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID) values (v_searchid_4,'ORACLE',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID');        

        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_4||') is added');  
    ELSE
      DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_4||') exists, no need to add again');  
    END IF;      
    
    COMMIT;

EXCEPTION
WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to add 4 TA OOB Target Card mappings due to '||SQLERRM);   
    RAISE;  
    
END;
/
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

DECLARE
  v_count number;
  v_searchid_3 number:=4002;

BEGIN  
    SELECT COUNT(1) INTO v_count FROM EMS_ANALYTICS_SEARCH WHERE search_id=v_searchid_3 AND TENANT_ID  ='&TENANT_ID';
    IF (v_count=1) THEN
        Update EMS_ANALYTICS_SEARCH_PARAMS
        SET PARAM_VALUE_CLOB='{"id":null,"entityType":"omc_host","widgets":null,"widgetRefs":[{"id":3031,"parameters":null}]}'
        WHERE TENANT_ID='&TENANT_ID' AND NAME='cardef' AND SEARCH_ID=v_searchid_3;

        Update EMS_ANALYTICS_SEARCH_PARAMS
        SET PARAM_VALUE_STR='omc_host'
        WHERE TENANT_ID='&TENANT_ID' AND NAME='entityType' AND SEARCH_ID=v_searchid_3;
        
        DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_3||') is updated');  
    ELSE
      DBMS_OUTPUT.PUT_LINE('OOB Target Card mapping (id='||v_searchid_3||') does not exist, no need to update');  
    END IF;      
    COMMIT;

EXCEPTION
WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to add 1 TA OOB Target Card[id=4002] mappings due to '||SQLERRM);   
    RAISE;     
END;
/
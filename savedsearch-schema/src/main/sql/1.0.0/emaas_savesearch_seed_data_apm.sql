Rem
Rem emaas_savesearch_seed_data_apm.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_savesearch_seed_data_apm.sql 
Rem
Rem    DESCRIPTION
Rem      Seed data for Application Performance Monitoring Cloud Service
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    miayu       04/21/15 - move OOB folder for APMCS to emaas_savesearch_seed_data.sql. Only OOB SEARCHES are expected to be in this file
Rem    evazhang    10/16/14 - Add OOB sub-folders for APMCS
Rem    miayu       10/11/14 - Created for APMCS
Rem

--place holder now
DEFINE TENANT_ID ='&1'
DECLARE
V_COUNT NUMBER;
BEGIN
 SELECT COUNT(1) INTO  V_COUNT FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID = '&TENANT_ID' AND OBJECT_ID >= 5000 AND OBJECT_ID <= 5999;
 IF V_COUNT > 0 THEN
    DBMS_OUTPUT.PUT_LINE('last access data 5000 to 5999 has been added before, no need to add again for ' || '&TENANT_ID');
 ELSE
insert into EMS_ANALYTICS_LAST_ACCESS(OBJECT_ID,ACCESSED_BY,OBJECT_TYPE,ACCESS_DATE,TENANT_ID)
select SEARCH_ID,'SYSMAN',2,SYS_EXTRACT_UTC(SYSTIMESTAMP),'&TENANT_ID' from EMS_ANALYTICS_SEARCH where search_id>=5000 and search_id<=5999 and TENANT_ID ='&TENANT_ID';
END IF;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('>>>SSF DML ERROR<<<');
    DBMS_OUTPUT.PUT_LINE('Failed to add last access data due to error '||SQLERRM);
    RAISE;
END;
/
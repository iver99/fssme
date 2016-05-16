Rem
Rem emaas_savesearch_seed_data.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, Oracle and/or its affiliates. 
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
Rem    Miao     02/26/16 - Created
Rem

DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  V_COUNT number;
BEGIN

SELECT COUNT(1) INTO V_COUNT from EMS_ANALYTICS_CATEGORY where TENANT_ID='&TENANT_ID' AND CATEGORY_ID=3;
IF (V_COUNT>0) THEN
  UPDATE EMS_ANALYTICS_CATEGORY SET PROVIDER_NAME='emcitas-ui-apps' WHERE TENANT_ID='&TENANT_ID' and CATEGORY_ID=3;
  UPDATE EMS_ANALYTICS_SEARCH_PARAMS SET PARAM_VALUE_STR='emcitas-ui-apps' WHERE TENANT_ID='&TENANT_ID' and NAME='PROVIDER_NAME' and PARAM_VALUE_STR='EmcitasApplications';
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Provider name of ITA has been upgraded to emcitas-ui-apps for tenant: &TENANT_ID successfully! Upgraded records: '||V_COUNT);
ELSE
  DBMS_OUTPUT.PUT_LINE('Provider name of ITA has been upgraded to emcitas-ui-apps for tenant: &TENANT_ID before, no need to upgrade again');
END IF;

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to upgrade provider name of ITA to emcitas-ui-apps for tenant: &TENANT_ID due to '||SQLERRM);
    RAISE;
END;
/


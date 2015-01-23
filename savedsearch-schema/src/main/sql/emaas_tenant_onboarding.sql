Rem 
Rem
Rem emaas_tenant_onboarding.sql
Rem
Rem Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_tenant_onboarding.sql
Rem
Rem    DESCRIPTION
Rem      EM Analytics framework data upgrade sql file.
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
     
WHENEVER SQLERROR EXIT ROLLBACK
SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE  TENANT_ID = '&1'
DECLARE	
      V_RootFolder EMS_ANALYTICS_FOLDERS%rowtype;
       BEGIN
-- If the root folder not present then insert the OOB serches , otherwise do nothing 
-- for given tenant.
       SELECT * INTO V_RootFolder FROM EMS_ANALYTICS_FOLDERS WHERE FOLDER_ID =1 AND TENANT_ID ='&TENANT_ID';
       DBMS_OUTPUT.PUT_LINE('OOB searches for &TENANT_ID is already present');        
       RAISE_APPLICATION_ERROR(-20000, ' OOB searches for &TENANT_ID is already present');
    EXCEPTION	
         WHEN NO_DATA_FOUND THEN
	  DBMS_OUTPUT.PUT_LINE('INSERING OOB searches for &TENANT_ID ');
    END;
/

@emaas_savesearch_seed_data.sql &TENANT_ID
@emaas_savesearch_seed_data_la.sql  &TENANT_ID
@emaas_savesearch_seed_data_ta.sql  &TENANT_ID
@emaas_savesearch_seed_data_apm.sql  &TENANT_ID
commit;

/

BEGIN
 DBMS_OUTPUT.PUT_LINE('Inserting OOB searches for &TENANT_ID is completed');
END;
/

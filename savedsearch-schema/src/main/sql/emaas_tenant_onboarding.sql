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
Rem      Tenant onboarding file for Widgets Frameowrk - tenantid is passed by RM & we call seed data with 
Rem      that tenant id & data gets inserted for that tenant
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem 	sdhamdhe  8/26/2015     Made changes for LCM rep manager cut-over
Rem    vinjoshi   1/23/1015 	Created
     
WHENEVER SQLERROR EXIT ROLLBACK
SET FEEDBACK ON
SET SERVEROUTPUT ON
DEFINE  TENANT_ID = '&1'
DEFINE EMSAAS_SQL_ROOT = '&2'

DECLARE	
      V_RootFolder EMS_ANALYTICS_FOLDERS%rowtype;
       Valid_Input NUMBER;
       BEGIN
-- If the root folder not present then insert the OOB serches , otherwise do nothing 
-- for given tenant.
	BEGIN

	Valid_Input := TO_NUMBER( &TENANT_ID);

	EXCEPTION 
	WHEN VALUE_ERROR THEN
	 RAISE_APPLICATION_ERROR(-21000, ' Please  specify valid internale tenant id');
	END;
	
       SELECT * INTO V_RootFolder FROM EMS_ANALYTICS_FOLDERS WHERE FOLDER_ID =1 AND TENANT_ID ='&TENANT_ID';
       DBMS_OUTPUT.PUT_LINE('OOB searches for &TENANT_ID is already present');        
       RAISE_APPLICATION_ERROR(-20000, ' OOB searches for &TENANT_ID is already present');
    EXCEPTION	
         WHEN NO_DATA_FOUND THEN
	  DBMS_OUTPUT.PUT_LINE('INSERING OOB searches for &TENANT_ID ');
    END;
/

@&EMSAAS_SQL_ROOT/1.0.0/emaas_savesearch_seed_data.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.0.0/emaas_savesearch_seed_data_la.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.0.0/emaas_savesearch_seed_data_ta.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.0.0/emaas_savesearch_seed_data_apm.sql  &TENANT_ID

BEGIN

UPDATE EMS_ANALYTICS_CATEGORY
SET PROVIDER_NAME = 'LoganService' ,
    PROVIDER_VERSION ='0.1'	
    WHERE CATEGORY_ID = 1 AND TENANT_ID ='&TENANT_ID';


UPDATE EMS_ANALYTICS_CATEGORY
SET PROVIDER_NAME = 'TargetAnalytics' ,
    PROVIDER_VERSION ='1.0'	
    WHERE CATEGORY_ID = 2 AND TENANT_ID ='&TENANT_ID';



UPDATE EMS_ANALYTICS_CATEGORY
SET PROVIDER_NAME = 'ApmUI' ,
    PROVIDER_VERSION ='0.1',
    PROVIDER_ASSET_ROOT ='home'		
    WHERE CATEGORY_ID = 4 AND TENANT_ID ='&TENANT_ID';

UPDATE EMS_ANALYTICS_CATEGORY
SET PROVIDER_NAME = 'EmcitasApplications' ,
    PROVIDER_VERSION ='0.1',
    PROVIDER_ASSET_ROOT ='assetRoot'		
    WHERE CATEGORY_ID = 3 AND TENANT_ID ='&TENANT_ID';
END;
/

COMMIT;


@&EMSAAS_SQL_ROOT/1.0.25/emaas_savesearch_seed_data_ta.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.0.25/emaas_savesearch_seed_data_la.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.4.0/emaas_savesearch_seed_data.sql  &TENANT_ID

BEGIN
  DBMS_OUTPUT.PUT_LINE('Inserting OOB searches for &TENANT_ID is completed');
END;
/

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
SET VERIFY OFF
DEFINE  TENANT_ID = '&1'
DEFINE EMSAAS_SQL_ROOT = '&2'

DECLARE	
  V_RootFolder EMS_ANALYTICS_FOLDERS%rowtype;
  Valid_Input NUMBER;
--If the root folder not present then insert the OOB serches , otherwise do nothing 
--for given tenant.
  BEGIN
    Valid_Input := TO_NUMBER( &TENANT_ID);

	EXCEPTION 
	WHEN VALUE_ERROR THEN
	  RAISE_APPLICATION_ERROR(-21000, ' Please  specify valid internale tenant id');
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

@&EMSAAS_SQL_ROOT/1.4.0/emaas_savesearch_seed_data_la.sql &TENANT_ID

@&EMSAAS_SQL_ROOT/1.5.0/emaas_savesearch_seed_data.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.7.1/emaas_savesearch_seed_data_ta.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.7.5/emaas_savesearch_seed_data_ita.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.8.0/emaas_savesearch_seed_data.sql &TENANT_ID

@&EMSAAS_SQL_ROOT/1.8.0/emaas_savesearch_seed_data_la.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.8.0/emaas_savesearch_seed_data_ta.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.8.0/emaas_savesearch_seed_data_targetcard.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_la.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_ta_remove_brownfield.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_targetcard.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.0/emaas_savesearch_seed_data_params.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_la.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_remove_brownfield.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_ocs.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.9.1/emaas_savesearch_seed_data_ui_gallery.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.10.0/emaas_savesearch_seed_data_la.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.10.0/emaas_savesearch_seed_data_ta.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.10.0/emaas_savesearch_seed_data.sql  &TENANT_ID

@&EMSAAS_SQL_ROOT/1.11.0/emaas_savesearch_seed_data_ta.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.11.0/emaas_savesearch_seed_data_ocs.sql  &TENANT_ID
@&EMSAAS_SQL_ROOT/1.12.0/emaas_savesearch_seed_data_ude.sql &TENANT_ID

@&EMSAAS_SQL_ROOT/1.13.0/emaas_savesearch_seed_data.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.13.0/emaas_savesearch_seed_data_targetcard.sql &TENANT_ID

@&EMSAAS_SQL_ROOT/1.14.0/emaas_savedsearch_seed_data_cos.sql &TENANT_ID

@&EMSAAS_SQL_ROOT/1.15.0/emaas_savesearch_seed_data_ta.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.15.0/emaas_savedsearch_cos_widget_update.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.15.0/emaas_savedsearch_seed_data_cos.sql &TENANT_ID
@&EMSAAS_SQL_ROOT/1.15.0/emaas_savedsearch_seed_data_la.sql &TENANT_ID

/**
--PLEASE READ!! 
--NO DDL IS ALLOWED in this file!! See EMCPDF-3333/EMCPSSF-465
**/

BEGIN
  DBMS_OUTPUT.PUT_LINE('Inserting OOB searches for &TENANT_ID is completed');
END;
/

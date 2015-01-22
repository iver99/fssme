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
     

	DEFINE  TENANT_ID = '&1'

	DECLARE	
     	 V_Version EMS_ANALYTICS_SCHEMA_VER_SSF%rowtype;
	 
	 success number(6) := 0;
	
	BEGIN
	 -- If the version matches then upgrade existing tables , otherwise it will throws exception and in exception block insert OOB 
	 -- for given tenant.
         SELECT * INTO V_Version FROM EMS_ANALYTICS_SCHEMA_VER_SSF WHERE MAJOR= 0 AND MINOR =5;
         success := 1;
	 dbms_output.put_line ('Upgrade exsting schema ');
         @./upgrade.sql &TENANT_ID
         dbms_output.put_line ('Upgraded successfully');
	EXCEPTION 

         WHEN NO_DATA_FOUND then
	 IF(success <> 1 ) then 
             dbms_output.put_line ('Inserting OOB for given tenant');

	   @emaas_savesearch_seed_data.sql &TENANT_ID
           @emaas_savesearch_seed_data_apm.sql &TENANT_ID
           @emaas_savesearch_seed_data_la.sql &TENANT_ID
           @emaas_savesearch_seed_data_ta.sql  &TENANT_ID
		
 	 END IF;
          
	END;


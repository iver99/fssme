Rem ----------------------------------------------------------------
Rem Upgrade from 0.59 (this first version with multiple tenancy support) to latest
Rem Note:
Rem   Upgrade from any version below 0.59 is not supported any more.
Rem
Rem 1. update schema change
Rem 2. update seed data
Rem    Extract unique teant IDs from tables EMS_ANALYTICS_FOLDERS & append that ID next to TA DML  upgrade file & run that file
Rem 3/25/2015	MIAYU	    clean files
Rem 2/23/2015	VINJOSHI	Created file
Rem 8/26/2015	SDHAMDHE	Updated for LCM Rep manager cutover changes EMCPSSF-184
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.0.25/upgrade_impl_ddl.sql


SET HEADING OFF
SET FEEDBACK OFF
SPOOL upgrade_impl_dml_tmp.sql
SELECT DISTINCT '@&EMSAAS_SQL_ROOT/1.0.25/upgrade_impl_dml.sql ' || TENANT_ID  FROM EMS_ANALYTICS_FOLDERS ORDER BY '@&EMSAAS_SQL_ROOT/1.0.25/upgrade_impl_dml.sql ' || TENANT_ID ;
SPOOL OFF

SET HEADING ON
SET FEEDBACK ON

WHENEVER SQLERROR EXIT ROLLBACK

@&EMSAAS_SQL_ROOT/1.0.25/upgrade_impl_dml_tmp.sql


Rem ----------------------------------------------------------------
Rem Upgrade from 1.0.0 (this first version with multiple tenancy support) to latest
Rem Note:
Rem   Upgrade from any version below 1.0.0 is not supported any more.
Rem
Rem 1. update schema change
Rem 2. update seed data
Rem    Extract unique teant IDs from tables EMS_ANALYTICS_FOLDERS & append that ID next to TA DML  upgrade file & run that file
Rem 10/28/2015	MIAYU	    created
Rem 01/15/2016  JNAN        Rename "Data Explorer - Search" to "Data Explorer" in category(EMCPDF-1221)
Rem ----------------------------------------------------------------


SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

SPOOL &EMSAAS_SQL_ROOT/1.5.0/upgrade_impl_dml_tmp.sql
SELECT DISTINCT '@&EMSAAS_SQL_ROOT/1.5.0/upgrade_impl_dml.sql ' || TENANT_ID  FROM EMS_ANALYTICS_FOLDERS ORDER BY '@&EMSAAS_SQL_ROOT/1.5.0/upgrade_impl_dml.sql ' || TENANT_ID ;
SPOOL OFF
SPOOL ON
SET HEADING ON
SET FEEDBACK ON

WHENEVER SQLERROR EXIT ROLLBACK

@&EMSAAS_SQL_ROOT/1.5.0/upgrade_impl_dml_tmp.sql


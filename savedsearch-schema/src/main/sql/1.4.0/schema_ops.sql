Rem ----------------------------------------------------------------
Rem 10/29/2015	Guochen	    clean files
Rem Extract unique teant IDs from tables EMS_DASHBOARD & append that ID next to upgrade implementation file & run that file
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.4.0/upgrade_impl_ddl.sql


SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

SPOOL &EMSAAS_SQL_ROOT/1.4.0/upgrade_impl_dml_tmp.sql
SELECT DISTINCT '@&EMSAAS_SQL_ROOT/1.4.0/upgrade_impl_dml.sql ' || TENANT_ID  FROM EMS_ANALYTICS_SEARCH ORDER BY '@&EMSAAS_SQL_ROOT/1.4.0/upgrade_impl_dml.sql ' || TENANT_ID ;
SPOOL OFF
SPOOL ON
SET HEADING ON
SET FEEDBACK ON

WHENEVER SQLERROR EXIT ROLLBACK

@&EMSAAS_SQL_ROOT/1.4.0/upgrade_impl_dml_tmp.sql


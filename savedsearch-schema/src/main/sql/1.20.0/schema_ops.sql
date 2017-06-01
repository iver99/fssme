Rem ----------------------------------------------------------------
Rem
Rem 04/26/2017	GUOCHEN	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.20.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.20.0/upgrade_impl_dml.sql


Rem ----------------------------------------------------------------
Rem
Rem 07/26/2017	PINGWU	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.22.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.22.0/upgrade_impl_dml.sql


Rem ----------------------------------------------------------------
Rem
Rem 07/03/2017	PINGWU	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.20.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.20.0/upgrade_impl_dml.sql


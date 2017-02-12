Rem ----------------------------------------------------------------
Rem
Rem 02/12/2017	MIAYU	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.16.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.16.0/upgrade_impl_dml.sql


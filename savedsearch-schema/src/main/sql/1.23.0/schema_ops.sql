Rem ----------------------------------------------------------------
Rem
Rem 08/18/2017	REX	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.23.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.23.0/upgrade_impl_dml.sql


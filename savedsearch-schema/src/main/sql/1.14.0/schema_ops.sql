Rem ----------------------------------------------------------------
Rem
Rem 11/14/2016	REX	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.14.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.14.0/upgrade_impl_dml.sql


Rem ----------------------------------------------------------------
Rem Upgrade from 1.0.0 (this first version with multiple tenancy support) to latest
Rem Note:
Rem   Upgrade from any version below 1.0.0 is not supported any more.
Rem
Rem 09/06/2016	REX	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.12.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.12.0/upgrade_impl_dml.sql


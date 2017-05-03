Rem ----------------------------------------------------------------
Rem
Rem 05/03/2017	XIADAI	    created
Rem ----------------------------------------------------------------

@&EMSAAS_SQL_ROOT/1.19.0/upgrade_impl_ddl.sql

SET HEADING OFF
SET FEEDBACK OFF
SET LINESIZE 2000

@&EMSAAS_SQL_ROOT/1.19.0/upgrade_impl_dml.sql


Rem ---------------------
Rem 2/23/2015	VINJOSHI	Created file
Rem 				Extract unique teant IDs from tables EMS_ANALYTICS_FOLDERS & append that ID next to TA DML  upgrade file & run that file
Rem -------------------

SET HEADING OFF
SET FEEDBACK OFF
SPOOL upgrade_058_059_ta_dml_tmp.sql
SELECT DISTINCT '@upgrade_058_059_ta_dml.sql ' || TENANT_ID  FROM EMS_ANALYTICS_FOLDERS ORDER BY '@upgrade_058_059_ta_dml.sql ' || TENANT_ID ;
SPOOL OFF

SET HEADING ON
SET FEEDBACK ON

@./upgrade_058_059_ta_dml_tmp.sql

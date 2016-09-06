Rem
Rem
Rem emaas_drop_tables.sql
Rem
Rem
Rem    NAME
Rem      emaas_drop_column.sql
Rem
Rem    DESCRIPTION
Rem      
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    chehao   9/2/2016  Created

SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  v_count       NUMBER;
  
BEGIN
	--delete incorrected column 'NAME_WIDGET_SOURCE'
		SELECT count(*) into v_count from user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH' AND column_name='NAME_WIDGET_SOURCE';
		IF v_count>0 THEN
			DBMS_OUTPUT.PUT_LINE('Schema object: DELETING COLUMN:NAME_WIDGET_SOURCE IN EMS_ANALYTICS_SEARCH TABLE');
			EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH DROP COLUMN NAME_WIDGET_SOURCE';
		ELSE
			DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH.NAME_WIDGET_SOURCE is not exists, no deletion is needed');
		END IF;
		
		EXCEPTION
	  WHEN OTHERS THEN
		ROLLBACK;
		DBMS_OUTPUT.PUT_LINE('Failed to delete column NAME_WIDGET_SOURCE in EMS_ANALYTICS_SEARCH due to '||SQLERRM);
		RAISE;
   
END;
/


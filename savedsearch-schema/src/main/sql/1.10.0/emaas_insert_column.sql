Rem
Rem
Rem emaas_drop_tables.sql
Rem
Rem
Rem    NAME
Rem      emaas_insert_column.sql
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
	--insert columns NAME_WIDGET_SOURCE in EMS_ANALYTICS_SEARCH table 
		SELECT count(*) into v_count from user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH' AND column_name='NAME_WIDGET_SOURCE';
		IF v_count=0 THEN
			DBMS_OUTPUT.PUT_LINE('Schema object: ADDING COLUMN:NAME_WIDGET_SOURCE INTO EMS_ANALYTICS_SEARCH TABLE');
			EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH ADD "NAME_WIDGET_SOURCE" VARCHAR2(64) DEFAULT 0';
		ELSE
			DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH.NAME_WIDGET_SOURCE exists already, no change is needed');
		END IF;
		
		EXCEPTION
	  WHEN OTHERS THEN
		ROLLBACK;
		DBMS_OUTPUT.PUT_LINE('Failed to add column NAME_WIDGET_SOURCE in EMS_ANALYTICS_SEARCH due to '||SQLERRM);
		RAISE;
   
END;
/


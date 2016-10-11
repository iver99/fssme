Rem
Rem    DDL change during upgrade
Rem    add column CREATION_DATE & LAST_MODIFICATION_DATE to all tables
Rem    MODIFIED   (MM/DD/YY)
Rem    Rex Liang    06/29/2016 - created
Rem
Rem

@&EMSAAS_SQL_ROOT/1.12.0/emaas_savesearch_drop_column.sql

DECLARE
  v_count     INTEGER;
BEGIN
  --add new column 'EMS_ANALYTICS_CATEGORY.LAST_MODIFICATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_CATEGORY' AND column_name='LAST_MODIFICATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_CATEGORY ADD LAST_MODIFICATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_CATEGORY.LAST_MODIFICATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_CATEGORY_PARAMS.CREATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_CATEGORY_PARAMS' AND column_name='CREATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_CATEGORY_PARAMS ADD CREATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_CATEGORY_PARAMS.CREATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_CATEGORY_PARAMS.LAST_MODIFICATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_CATEGORY_PARAMS' AND column_name='LAST_MODIFICATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_CATEGORY_PARAMS ADD LAST_MODIFICATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_CATEGORY_PARAMS.LAST_MODIFICATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_LAST_ACCESS.CREATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_LAST_ACCESS' AND column_name='CREATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_LAST_ACCESS ADD CREATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_LAST_ACCESS.CREATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_LAST_ACCESS.LAST_MODIFICATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_LAST_ACCESS' AND column_name='LAST_MODIFICATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_LAST_ACCESS ADD LAST_MODIFICATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_LAST_ACCESS.LAST_MODIFICATION_DATE exists already, no change is needed');
  END IF;
  
  EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_LAST_ACCESS MODIFY OBJECT_ID NUMBER(*,0)';
  
  --add new column 'EMS_ANALYTICS_SCHEMA_VER_SSF.CREATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SCHEMA_VER_SSF' AND column_name='CREATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SCHEMA_VER_SSF ADD CREATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SCHEMA_VER_SSF.CREATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_SCHEMA_VER_SSF.LAST_MODIFICATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SCHEMA_VER_SSF' AND column_name='LAST_MODIFICATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SCHEMA_VER_SSF ADD LAST_MODIFICATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SCHEMA_VER_SSF.LAST_MODIFICATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_SEARCH_PARAMS.CREATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH_PARAMS' AND column_name='CREATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH_PARAMS ADD CREATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH_PARAMS.CREATION_DATE exists already, no change is needed');
  END IF;
  
  --add new column 'EMS_ANALYTICS_SEARCH_PARAMS.LAST_MODIFICATION_DATE'
  SELECT COUNT(*) INTO v_count FROM user_tab_columns WHERE table_name='EMS_ANALYTICS_SEARCH_PARAMS' AND column_name='LAST_MODIFICATION_DATE';
  IF v_count=0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH_PARAMS ADD LAST_MODIFICATION_DATE TIMESTAMP(6) DEFAULT LOCALTIMESTAMP';
  ELSE
    DBMS_OUTPUT.PUT_LINE('Schema object: EMS_ANALYTICS_SEARCH_PARAMS.LAST_MODIFICATION_DATE exists already, no change is needed');
  END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TRIGGER EMS_ANALYTICS_FOLDERS_TR DISABLE';
  EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -4080 THEN
         RAISE;
      END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TRIGGER EMS_ANALYTICS_CATEGORY_TR DISABLE';
  EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -4080 THEN
         RAISE;
      END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'ALTER TRIGGER EMS_ANALYTICS_SEARCH_TR DISABLE';
  EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -4080 THEN
         RAISE;
      END IF;
END;
/

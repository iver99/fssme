Rem
Rem emaas_savedsearch_seed_data_sec.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      emaas_alter_table.sql
Rem
Rem    DESCRIPTION
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    XIADAI       05/10/17 -  Alter the constraint of tables
Rem
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
V_COUNT NUMBER;
BEGIN
    SELECT COUNT(1) INTO V_COUNT FROM user_constraints WHERE constraint_name = 'EMS_ANALYTICS_SEARCH_FK1' AND table_name = 'EMS_ANALYTICS_SEARCH';
    IF V_COUNT=0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_SEARCH_FK1 does not exist');
    ELSE
        EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH DROP CONSTRAINT EMS_ANALYTICS_SEARCH_FK1';
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_SEARCH_FK1 has been modified to EMS_ANALYTICS_SEARCH');
    END IF;

    SELECT COUNT(1) INTO V_COUNT FROM user_constraints WHERE constraint_name = 'EMS_ANALYTICS_SEARCH_FK2' AND table_name = 'EMS_ANALYTICS_SEARCH';
    IF V_COUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_SEARCH_FK2 does not exist');
    ELSE
        EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_SEARCH DROP CONSTRAINT EMS_ANALYTICS_SEARCH_FK2';
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_SEARCH_FK2 has been modified to EMS_ANALYTICS_SEARCH');
    END IF;

    SELECT COUNT(1) INTO V_COUNT FROM user_constraints WHERE constraint_name = 'EMS_ANALYTICS_CAT_PARAMS_FK1' AND table_name = 'EMS_ANALYTICS_CATEGORY_PARAMS';
    IF V_COUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_CAT_PARAMS_FK1 does not exist');
    ELSE
        EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_CATEGORY_PARAMS DROP CONSTRAINT EMS_ANALYTICS_CAT_PARAMS_FK1';
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_CAT_PARAMS_FK1 has been modified to EMS_ANALYTICS_CATEGORY_PARAMS');
    END IF;

    SELECT COUNT(1) INTO V_COUNT FROM user_constraints WHERE constraint_name = 'EMS_ANALYTICS_FOLDERS_FK1' AND table_name = 'EMS_ANALYTICS_FOLDERS';
    IF V_COUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_FOLDERS_FK1 does not exist');
    ELSE
        EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_FOLDERS DROP CONSTRAINT EMS_ANALYTICS_FOLDERS_FK1';
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_FOLDERS_FK1 has been modified to EMS_ANALYTICS_FOLDERS');
    END IF;

    SELECT COUNT(1) INTO V_COUNT FROM user_constraints WHERE constraint_name = 'EMS_ANALYTICS_CATEGORY_FK1' AND table_name = 'EMS_ANALYTICS_CATEGORY';
    IF V_COUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_CATEGORY_FK1 does not exist');
    ELSE
        EXECUTE IMMEDIATE 'ALTER TABLE EMS_ANALYTICS_CATEGORY DROP CONSTRAINT EMS_ANALYTICS_CATEGORY_FK1';
        DBMS_OUTPUT.PUT_LINE('CONSTRAINT EMS_ANALYTICS_CATEGORY_FK1 has been modified to EMS_ANALYTICS_CATEGORY');
    END IF;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('>>>SSF DML ERROR<<<');
    DBMS_OUTPUT.PUT_LINE('Failed to remove constraint due to error '||SQLERRM);
    RAISE;
END;
/

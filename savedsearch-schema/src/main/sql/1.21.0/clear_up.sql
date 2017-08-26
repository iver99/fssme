SET FEEDBACK ON
SET SERVEROUTPUT ON
DECLARE
    CONST_IS_SYSTEM             CONSTANT    NUMBER:= 1;
    CONST_DEFAULT_TENANT_ID     CONSTANT    NUMBER := -11;
BEGIN
    DELETE FROM EMS_ANALYTICS_LAST_ACCESS WHERE TENANT_ID <> CONST_DEFAULT_TENANT_ID AND OBJECT_ID IN
    (
        SELECT SEARCH_ID FROM EMS_ANALYTICS_SEARCH WHERE SYSTEM_SEARCH = CONST_IS_SYSTEM
    );

    DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS WHERE  TENANT_ID <> CONST_DEFAULT_TENANT_ID AND SEARCH_ID IN
    (
        SELECT SEARCH_ID FROM EMS_ANALYTICS_SEARCH WHERE SYSTEM_SEARCH = CONST_IS_SYSTEM
    );

    DELETE FROM EMS_ANALYTICS_SEARCH WHERE  TENANT_ID <> CONST_DEFAULT_TENANT_ID AND SYSTEM_SEARCH = CONST_IS_SYSTEM;

    DELETE FROM EMS_ANALYTICS_CATEGORY_PARAMS WHERE TENANT_ID <> CONST_DEFAULT_TENANT_ID;
    DELETE FROM EMS_ANALYTICS_CATEGORY WHERE TENANT_ID <> CONST_DEFAULT_TENANT_ID;
    DELETE FROM EMS_ANALYTICS_FOLDERS WHERE TENANT_ID <> CONST_DEFAULT_TENANT_ID AND SYSTEM_FOLDER = CONST_IS_SYSTEM;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('OOB widgets have been cleaned up succesfully!');

    EXCEPTION
    WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to clean OOB widgets due to ' || SQLERRM);
    RAISE;
END;
/
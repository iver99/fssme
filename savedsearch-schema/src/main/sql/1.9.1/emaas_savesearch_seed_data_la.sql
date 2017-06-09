-- Fix issue that WIDGET_VIEWMODEL of WIDGET: 2026/2009/2026 is missing, a regression caused by EMCPSSF-265 
DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
  OOB_2006        CONSTANT EMS_ANALYTICS_SEARCH.SEARCH_ID%TYPE:=2006;
  OOB_2009        CONSTANT EMS_ANALYTICS_SEARCH.SEARCH_ID%TYPE:=2009;
  OOB_2026        CONSTANT EMS_ANALYTICS_SEARCH.SEARCH_ID%TYPE:=2026;  
  V_SEARCH_ID           EMS_ANALYTICS_SEARCH.SEARCH_ID%TYPE;
  V_NAME                EMS_ANALYTICS_SEARCH.NAME%TYPE;
  V_PARAM_ATTRIBUTES    EMS_ANALYTICS_SEARCH_PARAMS.PARAM_ATTRIBUTES%TYPE;
  V_PARAM_TYPE          EMS_ANALYTICS_SEARCH_PARAMS.PARAM_TYPE%TYPE;
  V_PARAM_VALUE_STR     EMS_ANALYTICS_SEARCH_PARAMS.PARAM_VALUE_STR%TYPE;
  V_PARAM_VALUE_CLOB    EMS_ANALYTICS_SEARCH_PARAMS.PARAM_VALUE_CLOB%TYPE;
  V_COUNT                 NUMBER;
  V_TENANT_ID             EMS_ANALYTICS_SEARCH.TENANT_ID%TYPE;
  V_TID                   NUMBER(38,0) := '&TENANT_ID';
  CURSOR TENANT_CURSOR IS
    SELECT DISTINCT TENANT_ID FROM EMS_ANALYTICS_FOLDERS ORDER BY TENANT_ID;
BEGIN

  LOOP
     IF (V_TID<>-1) THEN
        V_TENANT_ID:=V_TID;
     ELSE
       IF NOT TENANT_CURSOR%ISOPEN THEN
        OPEN TENANT_CURSOR;
       END IF;
       FETCH TENANT_CURSOR INTO V_TENANT_ID;
       EXIT WHEN TENANT_CURSOR%NOTFOUND;     
     END IF;
     SELECT COUNT(SEARCH_ID) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID=OOB_2006 AND TENANT_ID=V_TENANT_ID AND WIDGET_VIEWMODEL='0';
     IF (V_COUNT=1) THEN
        V_SEARCH_ID        := OOB_2006;
        V_PARAM_VALUE_STR  := '/js/viewmodel/search/widget/VisualizationWidget.js';
        UPDATE EMS_ANALYTICS_SEARCH
        SET WIDGET_VIEWMODEL=V_PARAM_VALUE_STR
        WHERE SEARCH_ID=V_SEARCH_ID AND TENANT_ID=V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Missing WIDGET_VIEWMODEL is added successfully! tenant_id='||V_TENANT_ID||', search_id='||V_SEARCH_ID);
     ELSE
        DBMS_OUTPUT.PUT_LINE('Missing WIDGET_VIEWMODLE has been added before, no need to add again! tenant_id='||V_TENANT_ID||', search_id='||V_SEARCH_ID);
     END IF;
     SELECT COUNT(SEARCH_ID) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID=OOB_2009 AND TENANT_ID=V_TENANT_ID AND WIDGET_VIEWMODEL='0';
     IF (V_COUNT=1) THEN
        V_SEARCH_ID        := OOB_2009;
        V_PARAM_VALUE_STR  := '/js/viewmodel/search/widget/VisualizationWidget.js';
        UPDATE EMS_ANALYTICS_SEARCH
        SET WIDGET_VIEWMODEL=V_PARAM_VALUE_STR
        WHERE SEARCH_ID=V_SEARCH_ID AND TENANT_ID=V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Missing WIDGET_VIEWMODEL is added successfully! tenant_id='||V_TENANT_ID||', search_id='||V_SEARCH_ID);
     ELSE
        DBMS_OUTPUT.PUT_LINE('Missing WIDGET_VIEWMODLE has been added before, no need to add again! tenant_id='||V_TENANT_ID||', search_id='||V_SEARCH_ID);
     END IF;
     SELECT COUNT(SEARCH_ID) INTO V_COUNT FROM EMS_ANALYTICS_SEARCH WHERE SEARCH_ID=OOB_2026 AND TENANT_ID=V_TENANT_ID AND WIDGET_VIEWMODEL='0';
     IF (V_COUNT=1) THEN
        V_SEARCH_ID        := OOB_2026;
        V_PARAM_VALUE_STR  := '/js/viewmodel/search/widget/VisualizationWidget.js';
        UPDATE EMS_ANALYTICS_SEARCH
        SET WIDGET_VIEWMODEL=V_PARAM_VALUE_STR
        WHERE SEARCH_ID=V_SEARCH_ID AND TENANT_ID=V_TENANT_ID;
        DBMS_OUTPUT.PUT_LINE('Missing WIDGET_VIEWMODEL is added successfully! tenant_id='||V_TENANT_ID||', search_id='||V_SEARCH_ID);
     ELSE
         DBMS_OUTPUT.PUT_LINE('Missing WIDGET_VIEWMODLE has been added before, no need to add again! tenant_id='||V_TENANT_ID||', search_id='||V_SEARCH_ID);
     END IF;     
     IF (V_TID<>-1) THEN
        EXIT;
     END IF;
  END LOOP;
  IF TENANT_CURSOR%ISOPEN THEN
    CLOSE TENANT_CURSOR;
  END IF;

  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to remove 3 out of box widgets for BF due to error '||SQLERRM);
    RAISE;
END;
/

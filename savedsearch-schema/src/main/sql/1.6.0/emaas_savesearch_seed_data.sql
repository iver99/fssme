DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

BEGIN

    UPDATE EMS_ANALYTICS_SEARCH_PARAMS
    SET PARAM_VALUE_CLOB  ='{"version":0.5,"criteria":[{"jsonConstructor":"TargetStatusSearchCriterion","data":{"id":"target_status","_value":null,"dataType":"string","displayName":"Target Status","displayDetails":null,"role":"column","func":null,"UIProperties":{"useForSort":false,"ignoreCase":true}}},{"jsonConstructor":"TargetTypeSearchCriterion","data":{"id":"target_type","_value":{"jsonConstructor":"CriterionExpression","data":{"type":"in","values":[{"jsonConstructor":"CriterionValue","data":{"value":"host","metadata":{"category":false},"displayValue":"Host"}}]}},"dataType":"string","displayName":"Target Type","displayDetails":null,"role":"filter","func":null,"UIProperties":{"useForSort":false}}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_cpuUtil","_value":null,"dataType":"number","displayName":"CPU Utilization (%)","displayDetails":"CPU","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":true},"targetType":"host","unitStr":"%","mcName":"cpuUtil","mgName":"Load","mgDisplayName":"CPU","isKey":0,"groupKeyColumns":[],"isConfig":0,"direction":"DESC","inverseNullOrderBy":true}},{"jsonConstructor":"MetricGroupSearchCriterion","data":{"id":"host_Load_memUsedPct","_value":null,"dataType":"number","displayName":"Memory Utilization (%)","displayDetails":"Memory","role":"column","func":null,"UIProperties":{"dropDownOptions":[{"value":"cigar","label":"Cigar Chart"},{"value":"string","label":"Text"}],"dropDownValue":"cigar","useForSort":false},"targetType":"host","unitStr":"%","mcName":"memUsedPct","mgName":"Load","mgDisplayName":"Memory","isKey":0,"groupKeyColumns":[],"isConfig":0}}]}'
    WHERE SEARCH_ID ='3025'
    AND NAME='TA_CRITERIA'
    AND TENANT_ID='&TENANT_ID';

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Update TA_CRITERIA for SEARCH_ID: 3025 in Schema object: EMS_ANALYTICS_SEARCH_PARAMS successfully for tenant: &TENANT_ID');

EXCEPTION
WHEN OTHERS THEN
  ROLLBACK;
  DBMS_OUTPUT.PUT_LINE('Failed to update TA_CRITERIA on SEARCH_ID: 3025 due to '||SQLERRM);
  RAISE;
END;
/

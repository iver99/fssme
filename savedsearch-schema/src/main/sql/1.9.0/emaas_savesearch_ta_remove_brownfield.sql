DEFINE TENANT_ID ='&1'
SET FEEDBACK ON
SET SERVEROUTPUT ON

DECLARE
BEGIN
  DELETE FROM EMS_ANALYTICS_LAST_ACCESS
  WHERE TENANT_ID = '&TENANT_ID' AND OBJECT_TYPE=2 and OBJECT_ID IN
      (SELECT SEARCH_ID FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' and  SEARCH_ID<10000 and NAME IN 
  ('Database Configuration and Storage by Version'
  ,'Enterprise Overview'
  ,'Host Inventory by Platform'
  ,'Top 25 Databases by Resource Consumption'
  ,'Top 25 Weblogic Servers by Heap Usage'
  ,'Top 25 Weblogic Servers by Load'
  ,'Weblogic Severs by JDK Version'
  ,'Weblogic Servers with their Patch ID'
  ,'Weblogic Servers with small maximum heap'
  ,'Weblogic servers and their ports'
  ,'Databases with autoextend on and size greater than 10GB'
  ,'Top 10 listeners by load'
  ,'host inventory by platform'
  ,'Top 25 databases by resource consumption'
  ,'Database Configuration and Storage by Version'
  ,'Top 25 Weblogic Servers by Heap Usage'
  ,'Top 25 Weblogic Servers by Load'
  ,'Weblogic servers by JDK version'
  ,'Top 5 databases'
  ,'Top 5 hosts'
  ,'Top 5 weblogic servers'
  ,'Top 5 application deployments'
  ,'Target status')
      );
      
  DELETE FROM EMS_ANALYTICS_SEARCH_PARAMS
  WHERE TENANT_ID = '&TENANT_ID' AND SEARCH_ID IN
      (SELECT SEARCH_ID FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' and  SEARCH_ID<10000 and NAME IN 
  ('Database Configuration and Storage by Version'
  ,'Enterprise Overview'
  ,'Host Inventory by Platform'
  ,'Top 25 Databases by Resource Consumption'
  ,'Top 25 Weblogic Servers by Heap Usage'
  ,'Top 25 Weblogic Servers by Load'
  ,'Weblogic Severs by JDK Version'
  ,'Weblogic Servers with their Patch ID'
  ,'Weblogic Servers with small maximum heap'
  ,'Weblogic servers and their ports'
  ,'Databases with autoextend on and size greater than 10GB'
  ,'Top 10 listeners by load'
  ,'host inventory by platform'
  ,'Top 25 databases by resource consumption'
  ,'Database Configuration and Storage by Version'
  ,'Top 25 Weblogic Servers by Heap Usage'
  ,'Top 25 Weblogic Servers by Load'
  ,'Weblogic servers by JDK version'
  ,'Top 5 databases'
  ,'Top 5 hosts'
  ,'Top 5 weblogic servers'
  ,'Top 5 application deployments'
  ,'Target status')
      );
  DELETE FROM EMS_ANALYTICS_SEARCH WHERE TENANT_ID = '&TENANT_ID' and  SEARCH_ID<10000 and NAME IN 
  ('Database Configuration and Storage by Version'
  ,'Enterprise Overview'
  ,'Host Inventory by Platform'
  ,'Top 25 Databases by Resource Consumption'
  ,'Top 25 Weblogic Servers by Heap Usage'
  ,'Top 25 Weblogic Servers by Load'
  ,'Weblogic Severs by JDK Version'
  ,'Weblogic Servers with their Patch ID'
  ,'Weblogic Servers with small maximum heap'
  ,'Weblogic servers and their ports'
  ,'Databases with autoextend on and size greater than 10GB'
  ,'Top 10 listeners by load'
  ,'host inventory by platform'
  ,'Top 25 databases by resource consumption'
  ,'Database Configuration and Storage by Version'
  ,'Top 25 Weblogic Servers by Heap Usage'
  ,'Top 25 Weblogic Servers by Load'
  ,'Weblogic servers by JDK version'
  ,'Top 5 databases'
  ,'Top 5 hosts'
  ,'Top 5 weblogic servers'
  ,'Top 5 application deployments'
  ,'Target status');
  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Remove out of box dashboards for BF successfully');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to remove out of box dashboards for BF due to error '||SQLERRM);
    RAISE;
END;
/

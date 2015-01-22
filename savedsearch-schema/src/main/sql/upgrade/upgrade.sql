DEFINE  TENANT_ID = '&1'

begin
 dbms_output.put_line ('&TENANT_ID');
end;
/

@./upgrade_schema.sql &TENANT_ID




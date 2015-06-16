# Cookbook Name::dataService 
# Recipe:: dataservice_schema
#
# This recipe creates DB schema
#

include_recipe 'cookbook-emcs-emsaas-weblogic::datasource_dependency'

ruby_block "set_LDLibrary" do
  block do
    ENV['LD_LIBRARY_PATH']="#{node["dbhome"]}/lib"
  end
  action :create
end

schema_script_dirname = "#{node["sql_dir"]}"

# Working files created by this recipe
working_dir="#{node["log_dir"]}/#{schema_script_dirname}"
check_schema_basename="check_schema.sql"
check_schema_file="#{working_dir}/#{check_schema_basename}"
schema_exists_file="#{working_dir}/schema_exists"

# Ensures the working directory exists
#
directory working_dir do
  action :create
end

# Copies the script for checking if the schema needs to be deployed
#
cookbook_file "check_schema.sql" do
  path check_schema_file
  action :create_if_missing
end

#
product_schema_name = "SYSEMS_T_#{node["SAAS_schema_id"]}" 
                  loadResourceCredential "Load Product Schema Credential" do
                     credentialType "SchemaCredentials"
                     credentialName "#{product_schema_name}"
                  end

product_schema_password = node["SchemaCredentials"]["#{product_schema_name}"]["userCredential"]
#


# Check existence of schema
#
bash "check_schema" do
  cwd working_dir
  code lazy {<<-EOF
    #Run the sql script to check if schema is there
    export ORACLE_HOME=#{node["dbhome"]}
    export LD_LIBRARY_PATH=#{node["dbhome"]}/lib
    rm -f #{node["log_dir"]}/savedSearchDatasource.log

    echo "----------------- check schema--------------" >> #{node["log_dir"]}/savedSearchDatasource.log
    if [ -e #{schema_exists_file} ] ; then
      rm -f #{schema_exists_file}
    fi
    check_schema_result=`#{node["dbhome"]}/bin/sqlplus -s #{product_schema_name}/#{product_schema_password}@'#{node["database_ConnectString"]}' @#{check_schema_file} | grep "SAVED_SEARCH_SCHEMA_OK=0"`
    echo "check schema result = '$check_schema_result'" >> #{node["log_dir"]}/savedSearchDatasource.log
    if [ -n "$check_schema_result" ] ; then
      echo $check_schema_result > #{schema_exists_file}
    fi
  EOF
  }
end

bash "create_schema" do
  code lazy {<<-EOH

echo "---------------------------- starting create schema--------------" >> #{node["log_dir"]}/savedSearchDatasource.log
if [ ! -e #{schema_exists_file} ] ; then
export ORACLE_HOME=#{node["dbhome"]}

cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}

#Download the sql files
tar xzf #{node["sql_bundle"]}#{node["SAAS_version"]}.tgz

echo "Apps Dir: #{node["apps_dir"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
echo "Service Name: #{node["SAAS_servicename"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
echo "Version: #{node["SAAS_version"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
echo "SQL Dir: #{node["sql_dir"]}" >> #{node["log_dir"]}/savedSearchDatasource.log

echo "Doing explicit privilege grants ====" >> #{node["log_dir"]}/savedSearchDatasource.log

echo "#{node["dbhome"]}/bin/sqlplus #{node["sys_user"]}/#{node["db_syspassword"]}@'#{node["database_ConnectString"]}' as sysdba" >> #{node["log_dir"]}/savedSearchDatasource.log

#{node["dbhome"]}/bin/sqlplus #{node["sys_user"]}/#{node["db_syspassword"]}@'#{node["database_ConnectString"]}' as sysdba << disp > #{node["log_dir"]}/savedSearchsql.txt 2>&1 >> #{node["log_dir"]}/savedSearchDatasource.log
GRANT CREATE TRIGGER TO #{node["SAAS_schema_user"]};
disp

echo "==== Done explicit privilege grants" >> #{node["log_dir"]}/savedSearchDatasource.log

cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["sql_dir"]}
echo "#{node["dbhome"]}/bin/sqlplus #{node["SAAS_schema_user"]}/*******@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
export LD_LIBRARY_PATH=#{node["dbhome"]}/lib
echo "running the script now" >> #{node["log_dir"]}/savedSearchDatasource.log

echo "CWD:" >> #{node["log_dir"]}/savedSearchDatasource.log
pwd >> #{node["log_dir"]}/savedSearchDatasource.log
for file in init.sql
do
#{node["dbhome"]}/bin/sqlplus #{product_schema_name}/#{product_schema_password}@'#{node["database_ConnectString"]}' << eof_sql > #{node["log_dir"]}/savedSearchsql.txt 2>&1 >> #{node["log_dir"]}/savedSearchDatasource.log
@$file
eof_sql
done

else
	#Schema exists - we need to run upgrade scripts - it is expected that upgrade SQLs are made re-entrant
	echo "--------------------------- Schema exists attempting an upgrade --------------" >> #{node["log_dir"]}/savedSearchDatasource.log
	
	export ORACLE_HOME=#{node["dbhome"]}

	cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}

	#Download the sql files
	tar xzf #{node["sql_bundle"]}#{node["SAAS_version"]}.tgz

	echo "Apps Dir: #{node["apps_dir"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
	echo "Service Name: #{node["SAAS_servicename"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
	echo "Version: #{node["SAAS_version"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
	echo "SQL Dir: #{node["sql_dir"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
	cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["sql_dir"]}/upgrade
	
	echo "Running upgrade script now" >> #{node["log_dir"]}/savedSearchDatasource.log
	echo "#{node["dbhome"]}/bin/sqlplus #{node["SAAS_schema_user"]}/*******@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]}" >> #{node["log_dir"]}/savedSearchDatasource.log	
	echo "CWD:" >> #{node["log_dir"]}/savedSearchDatasource.log
	pwd >> #{node["log_dir"]}/savedSearchDatasource.log
	for file in upgrade.sql
		do
			#{node["dbhome"]}/bin/sqlplus #{product_schema_name}/#{product_schema_password}@'#{node["database_ConnectString"]}' << eof_sql > #{node["log_dir"]}/savedSearchsql.txt 2>&1 >> #{node["log_dir"]}/savedSearchDatasource.log
			@$file
eof_sql
			done

	# Done with upgrade block

fi
EOH
}
end


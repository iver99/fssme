# Cookbook Name::dataService 
# Recipe:: dataservice_schema
#
# This recipe creates DB schema
#

#
# Once the database instance is found, process the output
# file to glean the parameters where 
# we can then assign to the node attributes for
# subsequent processing
#
# Then, set the connection information based
# on the looked up database instance
#
ruby_block "get database entity" do
  block do
    if node["is_db_lookup"] == "true"
    node.default["databaseInfos"] = `cat #{node["log_dir"]}/savedSearchDatabaseLookup.out`
    databaseInfos =  node["databaseInfos"]
    databaseInfos = databaseInfos.tr("\n","").split(/;\s*/)
    for databaseInfo in databaseInfos
      index = databaseInfo.index(':')
      if index > 0 and index < databaseInfo.length-1
        infokey = databaseInfo[0,index]
        infovalue = databaseInfo[index+1, databaseInfo.length-index-1]
        puts "debugging with "+infokey+":"+infovalue
        if infokey == "db_host"
          node.default["db_host"] = infovalue
        elsif infokey == "db_port"
          node.default["db_port"] = infovalue
        elsif infokey == "db_sid"
          node.default["db_sid"] = infovalue
        elsif infokey == "sys_password"
          node.default["db_syspassword"] = infovalue
        elsif infokey == "db_service"
          node.default["db_service"] = infovalue
        end
      end
    end
    node.default["db_connectinfo"] = "jdbc:oracle:thin:@#{node["db_host"]}:#{node["db_port"]}:#{node["db_sid"]}"
    node.default["db_sysuser"] = "sys"
  end
  end
  action :create
end

ruby_block "set_LDLibrary" do
  block do
    ENV['LD_LIBRARY_PATH']="#{node["dbhome"]}/lib"
  end
  action :create
end

bash "create_schema" do
  code lazy {<<-EOH
rm -f #{node["log_dir"]}/savedSearchDatasource.log
echo "---------------------------- starting create schema--------------" >> #{node["log_dir"]}/savedSearchDatasource.log
export ORACLE_HOME=#{node["dbhome"]}

cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}

#Download the sql files
tar xzf #{node["sql_bundle"]}#{node["SAAS_version"]}.tgz
cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["sql_dir"]}

echo "db_servicename = #{node["db_service"]}, SAAS_schema_user = #{node["SAAS_schema_user"]}, SYS_password = #{node["db_syspassword"]} db_port=#{node["db_port"]} db_host=#{node["db_host"]} home=#{node["dbhome"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
export LD_LIBRARY_PATH=#{node["dbhome"]}/lib

echo "#{node["dbhome"]}/bin/sqlplus #{node["sys_user"]}/#{node["db_syspassword"]}@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]} as sysdba" >> #{node["log_dir"]}/savedSearchDatasource.log
#{node["dbhome"]}/bin/sqlplus #{node["sys_user"]}/#{node["db_syspassword"]}@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]} as sysdba << disp > #{node["log_dir"]}/savedSearchsql.txt 2>&1 >> #{node["log_dir"]}/savedSearchDatasource.log
CREATE USER #{node["SAAS_schema_user"]} identified by #{node["SAAS_schema_password"]};
ALTER USER #{node["SAAS_schema_user"]} default tablespace users temporary tablespace temp;
GRANT CONNECT,RESOURCE TO #{node["SAAS_schema_user"]};
GRANT CREATE SESSION TO #{node["SAAS_schema_user"]};
GRANT UNLIMITED TABLESPACE TO #{node["SAAS_schema_user"]};
disp
echo "done with creating user" >> #{node["log_dir"]}/savedSearchDatasource.log

echo "running the script now" >> #{node["log_dir"]}/savedSearchDatasource.log
echo "#{node["dbhome"]}/bin/sqlplus #{node["SAAS_schema_user"]}/#{node["SAAS_schema_password"]}@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]}" >> #{node["log_dir"]}/savedSearchDatasource.log
echo "CWD:" >> #{node["log_dir"]}/savedSearchDatasource.log
pwd >> #{node["log_dir"]}/savedSearchDatasource.log
for file in init.sql
do
#{node["dbhome"]}/bin/sqlplus #{node["SAAS_schema_user"]}/#{node["SAAS_schema_password"]}@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]} << eof_sql > #{node["log_dir"]}/savedSearchsql.txt 2>&1 >> #{node["log_dir"]}/savedSearchDatasource.log
@$file
eof_sql
done
EOH
}
end

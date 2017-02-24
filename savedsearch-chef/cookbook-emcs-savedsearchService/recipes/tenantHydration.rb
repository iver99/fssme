# Cookbook Name::savedSearchService 
# Recipe:: tenantHydration
#
# This recipe hydrates the tenant information
#

tenant_hydration_schema_script_dir = "#{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["sql_dir"]}"
tenant_hydration_sql_filename = "emaas_tenant_onboarding.sql"
log_file="#{node["log_dir"]}/savedSearchDatasource_tenant_#{node['internalTenantID']}.log"
history_log_file="#{node["log_dir"]}/savedSearchDatasource.log"

# Couples of assumptions:
#    1. the schema user/password information is passed in
#       explicitly since the SSF schema is dynamically
#       assigned during runtime
#    2. the database information lookup will also be passed in
#       as well, which means that the entity naming and
#       lookup information will be available
#    3. the host where this recipe is run on has the software
#       unbundled
#	   4. the id being passed is INTERNAL TENANT ID - NOT the one that gets passed around in X-USER-IDENTITY....

# Step 0: validate the parameters that must be explicitly passed in
bash "checkTenantID" do
  code <<-EOF
    echo "`date` --- Chef Recipe::tenantHydration --- missing tenantID -- we need internalTenantID" >> #{log_file}
    exit 1;
  EOF
  not_if { node['internalTenantID'] }
end

# remove old log file if exists
ruby_block "remove_old_log" do
  block do
    if File.exists?(history_log_file)
      File.delete(history_log_file)
    end
    if File.exists?(log_file)
      File.delete(log_file)
    end
  end
  action :create
end

#Use lcm db lookup
include_recipe 'cookbook-emcs-lcm-rep-manager-service::db_lookup'

# Set the ORACLE_HOME and LD library so we can run SQLPLUS
ruby_block "set_OracleHome" do
  block do
    ENV['ORACLE_HOME']="#{node["dbhome"]}"
  end
  action :create
end

ruby_block "set_LDLibrary" do
  block do
    ENV['LD_LIBRARY_PATH']="#{node["dbhome"]}/lib"
  end
  action :create
end

# Step 2: unbundle the SQL collection
#   to make sure the scripts used is the one matches up with the binary
bash "unbundle_schema_zipe" do
  code lazy {<<-EOH
    echo "`date` -- Starting to hydrate tenant information for #{node["tenantID"]}" >> #{log_file}
    echo "internal tenant id is #{node["internalTenantID"]}" >> #{log_file}
    cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}
    echo "`date` -- Tenant hydration: always unzip the sql bundle " >> #{log_file}
    tar xzf #{node["sql_bundle"]}#{node["SAAS_version"]}.tgz

    # echo "`date` -- Tenant Hydration: SQL Dir: #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["sql_dir"]}" >> #{log_file}
    # cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/#{node["SAAS_version"]}/#{node["sql_dir"]}

    # echo "`date` -- Tenant Hydration: db_servicename = #{node["db_service"]}, SAAS_schema_user = #{node["SAAS_schema_user"]}, db_port=#{node["db_port"]} db_host=#{node["db_host"]} home=#{node["dbhome"]}" >> #{log_file}
    # export LD_LIBRARY_PATH=#{node["dbhome"]}/lib

    # echo "`date` -- Tenant Hydration: running the script now" >> #{log_file}
    # #{node["dbhome"]}/bin/sqlplus #{node["SAAS_schema_user"]}/#{node["SAAS_schema_password"]}@#{node["db_host"]}:#{node["db_port"]}/#{node["db_service"]} << eof_sql > #{node["log_dir"]}/savedSearchsql.txt 2>&1 >> #{log_file}
    # @emaas_tenant_onboarding.sql #{node["internalTenantID"]}
    # eof_sql
EOH
}
end

#----------------------------------------
# Executing the Tenant Hydration SQL file
execute "run_tenant_hydration_sql" do
    cwd tenant_hydration_schema_script_dir
    command lazy {"#{node["dbhome"]}/bin/sqlplus #{node["db_url"]} @#{tenant_hydration_sql_filename} #{node["internalTenantID"]} #{node["SAAS_schema_sql_root_dir"]} >> #{log_file}"}
end

#Add checks which needs to be done after we hydrate tenant
ruby_block "Runtime checks - Post Tenant Hydration" do
  block do
    puts "************** Inside Runtime checks - Post Tenant Hydration "

    #Check whether there are any SQL errors
      if File.exists?(log_file)
          errors = File.foreach(log_file).grep /^ORA-|^SP2-/
          if errors.count > 0
            puts "Found SQL errors: " + errors[0].to_s
            timestamp = Time.now.strftime("%Y-%m-%d-%H-%M-%S")
            File.rename(log_file, log_file+'.'+timestamp)
            Chef::Application.fatal!("SQL errors found. Please look into: " + log_file);
          else
            File.delete(log_file)
          end
      end
  end
  action :run
end

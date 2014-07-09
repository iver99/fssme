#
# Cookbook Name::dataService
#
# This recipe calls schema recipe to create the DB schema and datasource recipe to create datasource and deploy ear file in weblogic server
#

#If is_db_lookup is set to true, below code is executed for doing looking operation
bash "database entity lookup" do
  cwd "#{node["java_home"]}"
  code <<-EOF
    if [ #{node["is_db_lookup"]} == "true" ]; then
    rm -rf #{node["log_dir"]}/savedSearchDatabaseLookup.out
    echo "bin/java -cp #{node["infra_dir"]}/pregistry/0.1/simple-registry-client.jar oracle.sysman.emaas.platform.simpleclient.EntityNamingService -registryUrls #{node["serviceUrls"]} -domain #{node["SAAS_entityNamingDomain"]} -action getlookup -keys "#{node["SAAS_entityNamingKey"]}:#{node["tenantID"]}" -output #{node["log_dir"]}/savedSearchDatabaseLookup.out" >> #{node["log_dir"]}/lookup
    bin/java -cp #{node["infra_dir"]}/pregistry/0.1/simple-registry-client.jar oracle.sysman.emaas.platform.simpleclient.EntityNamingService -registryUrls #{node["serviceUrls"]} -domain #{node["SAAS_entityNamingDomain"]} -action getlookup -keys "#{node["SAAS_entityNamingKey"]}:#{node["tenantID"]}" -output #{node["log_dir"]}/savedSearchDatabaseLookup.out
    if [ ! -e "#{node["log_dir"]}/savedSearchDatabaseLookup.out" ]
    then
        echo "could not find database entity for #{node["SAAS_entityNamingValue"]}"
        exit 99
    else
        echo "generated file at"
        echo `cat #{node["log_dir"]}/savedSearchDatabaseLookup.out`
    fi
    fi
  EOF
end

#Recipe to create schema
include_recipe 'savedsearchService::savedsearch_schema'

#Recipe to create Datasource
include_recipe 'savedsearchService::savedsearch_eardeploy'

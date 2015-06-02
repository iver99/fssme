#
# Cookbook Name::savedsearchService
#
# This recipe creates the service manager properties file & then calls common recipes to ...
#			1. perform WLS managed server setup
#			2. setup data source for this app
#			3. perform app deploy to managed server setup in #1
#	
#	Refer https://confluence.oraclecorp.com/confluence/display/EMS/Cutover+to+deployment+on+WLS+Managed+Servers for details
#

#Recipe to create schema - this is completely re-entrant, run as many times
include_recipe 'cookbook-emcs-savedsearchService::savedsearch_schema'

#Block to create the servicemanager.properties file
bash "create_servicemanger_properties_file"  do
  code <<-EOH
		echo "Creating SM properties file in #{node["apps_dir"]}/#{node["SAAS_servicename"]}/init"
    mkdir -p #{node["apps_dir"]}/#{node["SAAS_servicename"]}/init
    cd #{node["apps_dir"]}/#{node["SAAS_servicename"]}/init
    echo "version=#{node["SAAS_API_version"]}" > servicemanager.properties
    echo "serviceName=SavedSearch" >> servicemanager.properties
    echo "registryUrls=$SAAS_REGISTRY_URLS" >> servicemanager.properties
    echo "serviceUrls=$SAAS_REGISTRY_URLS" >> servicemanager.properties
    echo "characteristics=SAAS_deploymentUuid:#{node["SAAS_deploymentUuid"]}" >> servicemanager.properties
    EOH
end

#common setup/start for a managed Server
include_recipe 'cookbook-emcs-emsaas-weblogic::managedServer_setup'

#common datasource recipe to setup data source based on properties in ../attributes/default.rb
include_recipe 'cookbook-emcs-emsaas-weblogic::datasource_common'

# common app deployment to managed server
include_recipe 'cookbook-emcs-emsaas-weblogic::managedServer_deployApp'

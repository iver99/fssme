

include_recipe 'cookbook-emcs-lcm-rep-manager-service::db_lookup'

include_recipe 'cookbook-emcs-savedsearchService::savedsearch_schema'

include_recipe 'cookbook-emcs-lcm-rep-manager-service::lcmrepmgr_framework'

delete from ems_analytics_last_access where object_id<10000;
delete from ems_analytics_search_params where search_id<10000;
delete from ems_analytics_search where search_id<10000;
commit;

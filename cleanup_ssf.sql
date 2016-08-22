delete from ems_analytics_last_access where object_id>=10000 and object_type=2;
delete from ems_analytics_search_params where search_id>=10000;
delete from ems_analytics_search where search_id>=10000;
delete from ems_analytics_category_params where category_id>=1000;
delete from ems_analytics_category where category_id>=1000;
delete from ems_analytics_folders where folder_id>=1000;
commit;

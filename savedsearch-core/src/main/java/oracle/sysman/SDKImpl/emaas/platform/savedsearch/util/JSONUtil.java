/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanSerializerFactory;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil
{

	private static class SSFBeanSerializerFactory extends BeanSerializerFactory
	{
		private Object filterId;

		public SSFBeanSerializerFactory(Config config)
		{
			super(config);
		}

		public Object getFilterId()
		{
			return filterId;
		}

		public void setFilterId(Object filterId)
		{
			this.filterId = filterId;
		}

		@Override
		protected Object findFilterId(SerializationConfig config, BasicBeanDescription beanDesc)
		{
			return getFilterId();
		}
	}

	//	@SuppressWarnings("unchecked")
	//	public static Object JSONToObject(JSONObject inputJsonObj, Class classname) throws EMAnalyticsFwkJsonException
	//	{
	//		try {
	//			Object obj = null;
	//			ObjectMapper mapper = new ObjectMapper();
	//			obj = mapper.readValue(inputJsonObj.toString(), classname);
	//			return classname.cast(obj);
	//		}
	//		catch (Exception e) {
	//			throw new EMAnalyticsFwkJsonException("error converting JSONObject to Object",
	//					EMAnalyticsFwkJsonException.JSON_JSON_TO_OBJECT, e);
	//		}
	//	}

	public static JavaType constructParametricType(ObjectMapper mapper, Class<?> parametrized, Class<?>... parameterClasses)
	{
		return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJson(ObjectMapper mapper, String jsonString, JavaType javaType) throws IOException
	{
		if (JSONUtil.isEmpty(jsonString)) {
			return null;
		}

		jsonString = URLDecoder.decode(jsonString, "UTF-8");
		return (T) mapper.readValue(jsonString, javaType);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return (List<T>) JSONUtil.fromJson(mapper, jsonString, JSONUtil.constructParametricType(mapper, List.class, classMeta));
	}

	@SuppressWarnings("unchecked")
        public static <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta, String field) throws IOException,
			JSONException
	{
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		JSONObject json1 = new JSONObject(jsonString);

		try {
			result = json1.getString(field);
		}
		catch (Exception e) {
			return null;
		}
		return (List<T>) JSONUtil.fromJson(mapper, result, JSONUtil.constructParametricType(mapper, List.class, classMeta));
	}

	public static boolean isEmpty(String s)
	{
		if (s == null) {
			return true;
		}
		if ("".equals(s.trim())) {
			return true;
		}
		return false;
	}

	public static JSONObject ObjectToJSONObject(Object object) throws JSONException, EMAnalyticsFwkException
	{
		return JSONUtil.ObjectToJSONObject(object, null);

	}

	public static JSONObject ObjectToJSONObject(Object object, String[] excludedFields) throws JSONException,
			EMAnalyticsFwkException
	{

		return new JSONObject(JSONUtil.ObjectToJSONString(object, excludedFields));
	}

	public static String ObjectToJSONString(Object object) throws EMAnalyticsFwkException
	{
		return JSONUtil.ObjectToJSONString(object, null);
	}

	/**
	 * Convert Object to JSON string.
	 * 
	 * @param object
	 *            the object to convert
	 * @param excludedFieldItems
	 *            fields that will NOT be converted into JSON string
	 * @return converted JSON string
	 * @throws EMAnalyticsFwkJsonException
	 */
	public static String ObjectToJSONString(Object object, String[] excludedFieldItems) throws EMAnalyticsFwkException
	{
		try {

			OutputStream os = new ByteArrayOutputStream();
			ObjectWriter writer;
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
			mapper.setDateFormat(DateUtil.getDateFormatter());
			if (excludedFieldItems != null) {
				final String EXCLUDE_FILTER = "exclude_filter";
				FilterProvider filters = new SimpleFilterProvider().addFilter(EXCLUDE_FILTER,
						SimpleBeanPropertyFilter.serializeAllExcept(excludedFieldItems));
				mapper.setFilters(filters);
				JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(os, JsonEncoding.UTF8);
				SSFBeanSerializerFactory bbFactory = new SSFBeanSerializerFactory(null);
				bbFactory.setFilterId(EXCLUDE_FILTER);
				mapper.setSerializerFactory(bbFactory);
				mapper.writeValue(generator, object);
				return os.toString();
			}
			else {
				writer = mapper.writer();
				writer.writeValue(os, object);
				return os.toString();
			}
		}
		catch (Exception e) {
			throw new EMAnalyticsFwkException("Error converting to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, e);
		}

	}
}

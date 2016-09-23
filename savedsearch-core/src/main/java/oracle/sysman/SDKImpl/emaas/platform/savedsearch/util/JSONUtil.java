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
import java.util.Collections;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanSerializerFactory;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil
{

	private JSONUtil() {
	}

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

	public static ObjectMapper buildNormalMapper()
	{
		ObjectMapper mapper = new ObjectMapper();
		//set inclusion attribute
		mapper.setSerializationInclusion(Inclusion.ALWAYS);
		//ignore those exist in json but not in java
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//forbid the deserialize int for Enum order()
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
		return mapper;
	}

	public static JavaType constructParametricType(ObjectMapper mapper, Class<?> parametrized, Class<?>... parameterClasses)
	{
		return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	public static <T> T fromJson(ObjectMapper mapper, String jsonString, Class<T> classMeta) throws IOException
	{
		if (JSONUtil.isEmpty(jsonString)) {
			return null;
		}

		jsonString = URLDecoder.decode(jsonString, "UTF-8");
		return mapper.readValue(jsonString, classMeta);
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
	public static <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta, String field)
			throws IOException, JSONException
	{
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		JSONObject json1 = new JSONObject(jsonString);

		try {
			result = json1.getString(field);
		}
		catch (Exception e) {
			return Collections.emptyList();
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

	public static ObjectNode objectToJSONObject(Object object) throws JSONException, EMAnalyticsFwkException
	{
		return JSONUtil.objectToJSONObject(object, null);

	}

	public static ObjectNode objectToJSONObject(Object object, String[] excludedFields)
			throws JSONException, EMAnalyticsFwkException
	{
		ObjectNode jNode = null;
		String objString = JSONUtil.objectToJSONString(object, excludedFields);
		ObjectMapper mapper = new ObjectMapper();
		try {
			jNode = mapper.readValue(objString, ObjectNode.class);
		} catch (IOException e) {
			throw new EMAnalyticsFwkException("Error converting to JsonNode",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, e);
		}
		return jNode;
	}

	public static String objectToJSONString(Object object) throws EMAnalyticsFwkException
	{
		return JSONUtil.objectToJSONString(object, null);
	}

	/**
	 * Convert Object to JSON string.
	 *
	 * @param object
	 *            the object to convert
	 * @param excludedFieldItems
	 *            fields that will NOT be converted into JSON string
	 * @return converted JSON string
	 * @throws EMAnalyticsFwkException
	 */
	public static String objectToJSONString(Object object, String[] excludedFieldItems) throws EMAnalyticsFwkException
	{
		try {

			OutputStream os = new ByteArrayOutputStream();
			ObjectWriter writer;
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
			mapper.setDateFormat(DateUtil.getDateFormatter());
			if (excludedFieldItems != null) {
				final String excludeFilter = "exclude_filter";
				FilterProvider filters = new SimpleFilterProvider().addFilter(excludeFilter,
						SimpleBeanPropertyFilter.serializeAllExcept(excludedFieldItems));
				mapper.setFilters(filters);
				JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(os, JsonEncoding.UTF8);
				SSFBeanSerializerFactory bbFactory = new SSFBeanSerializerFactory(null);
				bbFactory.setFilterId(excludeFilter);
				mapper.setSerializerFactory(bbFactory);
				mapper.writeValue(generator, object);
				return ((ByteArrayOutputStream) os).toString("UTF-8");

			}
			else {
				writer = mapper.writer();
				writer.writeValue(os, object);
				return ((ByteArrayOutputStream) os).toString("UTF-8");

			}
		}
		catch (Exception e) {
			throw new EMAnalyticsFwkException("Error converting to JSON string",
					EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION, null, e);
		}

	}
}

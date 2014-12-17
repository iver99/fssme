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
import java.io.OutputStream;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkJsonException;

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

	public static JSONObject ObjectToJSONObject(Object object) throws JSONException, EMAnalyticsFwkJsonException
	{
		return JSONUtil.ObjectToJSONObject(object, null);

	}

	public static JSONObject ObjectToJSONObject(Object object, String[] excludedFields) throws JSONException,
	EMAnalyticsFwkJsonException
	{

		return new JSONObject(JSONUtil.ObjectToJSONString(object, excludedFields));
	}

	public static String ObjectToJSONString(Object object) throws EMAnalyticsFwkJsonException
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
	public static String ObjectToJSONString(Object object, String[] excludedFieldItems) throws EMAnalyticsFwkJsonException
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
			throw new EMAnalyticsFwkJsonException("Error converting to JSON string",
					EMAnalyticsFwkJsonException.JSON_OBJECT_TO_JSON_EXCEPTION, e);
		}

	}
}

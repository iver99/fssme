/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import java.io.IOException;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author aduan
 */
public class JsonUtil
{
	/**
	 * output all
	 */
	public static JsonUtil buildNormalMapper()
	{
		return new JsonUtil(Inclusion.ALWAYS);
	}

	private final ObjectMapper mapper;

	public JsonUtil(Inclusion inclusion)
	{
		mapper = new ObjectMapper();
		//set inclusion attribute
		mapper.setSerializationInclusion(inclusion);
		//ignore those exist in json but not in java
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//forbid the deserialize int for Enum order()
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
	}

	/**
	 * if JSON string is Null or "null"String , return Null. if JSON string is "[]", return empty collection. if read List/Map,
	 * use constructParametricTypeto construct type first.
	 *
	 * @see #constructParametricType(Class, Class...)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) throws IOException
	{
		if (isEmptyString(jsonString)) {
			return null;
		}

		return mapper.readValue(jsonString, clazz);
	}

	public boolean isEmptyString(String str)
	{
		return str == null || "".equals(str.toString());
	}
}

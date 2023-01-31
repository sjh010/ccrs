package com.mobileleader.image.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * json 유틸
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.09.05
 *
 */
public class JsonUtil {
	
	static Gson gson = new Gson();
	static JsonParser parser = new JsonParser();
	
	// Json to Object
	public static <T> T getObject(final String jsonString, final Class<T> objectClass) {
		try {
			if (jsonString != null && jsonString != "") {
				JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
				return gson.fromJson(jsonObject, objectClass);	
			}
		}catch(JsonSyntaxException e) {
			return null;
		}
		return null;
	}
	
	// Json to Object
	public static <T> T getObjectEx(final String jsonString, final Class<T> objectClass) {
		try {
			if (jsonString != null && jsonString != "") {
				JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
				return gson.fromJson(jsonObject, objectClass);
			}
		} catch (JsonSyntaxException e) {
			return null;
		}
		return null;
	}
	
	// Object to Json
	public static String getJsonString(Object object) {
		return gson.toJson(object);
	}
	
	// json string pretty.
	public static String jsonPrettyPrint(String json) {
		String ret = "";
		
		if (json != null && json != "") {
			ObjectMapper mapper = new ObjectMapper();
			Object obj;
			try {
				obj = mapper.readValue(json, Object.class);
				ret = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
				
			} catch (IOException e) {
				ret = null;
			}
		}
		return ret;
	}

	// object to json pretty
	public static String ObjectPrettyPrint(Object object) {
		return jsonPrettyPrint(getJsonString(object));
	}
	
	// jsonObject to Object
	public static <T> T getDataObject(Object object, final Class<T> objectClass) {
		return getObject(getJsonString(object), objectClass);
	}
}


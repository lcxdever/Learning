package com.blackbread.utils;

import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.blackbread.model.User;

public class JsonUtil {
	private static final Logger logger = Logger.getLogger(JsonUtil.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String jsonFromObject(Object object) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, object);
			return writer.toString();
		} catch (Exception e) {
			logger.error("Unable to serialize to json: " + object, e);
			return null;
		}
	}

	public static User userFromJson(String json) {
		return (User) objectFromJson(json, User.class);
	}

	@SuppressWarnings("unchecked")
	static <T> T objectFromJson(String json, Class<?> klass) {
		T object;
		try {
			object = (T) mapper.readValue(json, klass);
		} catch (Exception e) {
			logger.error("Exception during deserializing "
					+ klass.getSimpleName());
			return null;
		}
		return object;
	}
}
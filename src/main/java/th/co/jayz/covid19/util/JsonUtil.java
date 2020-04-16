package th.co.jayz.covid19.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {
	
	public static String getStrJsonByObj(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writer().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LogUtil.warn(ExceptionUtil.getStackTrace(e));
		}
		return json;
	}
	
	public static String getStrJsonPrettyByObj(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LogUtil.warn(ExceptionUtil.getStackTrace(e));
		}
		return json;
	}
	
	public static Map<String,Object> getJsonToMap(String json){
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<>();
		try {
			map = mapper.readValue(json,new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			LogUtil.warn(ExceptionUtil.getStackTrace(e));
		}
		return map;
	}

}

package com.training.bookmanagement.util.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CommonUtil {

	private static final ObjectMapper jsonMapper;
	static {
		jsonMapper = Jackson2ObjectMapperBuilder.json().build();
		jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * get json mapper
	 */
	public static ObjectMapper getMapper() {
		return jsonMapper;
	}

	/**
	 * get json node from json string
	 */
	public static JsonNode fromJson(String jsonString) {
		try {
			return jsonMapper.readTree(jsonString);
		} catch (Exception e) {
			System.err.println("Unable to parse json " + jsonString);
			return null;
		}
	}

	/**
	 * converting json to class object.
	 */
	public static <T> T fromJson(String jsonString, Class<T> className) {
		try {
			return jsonMapper.readValue(jsonString, className);
		} catch (IOException e) {
			System.err.println("Unable to parse json " + jsonString);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * converting by type to object
	 */
	@SuppressWarnings("rawtypes")
	public static Object fromJson(String jsonString, Class<? extends Collection> collectionClass,
                                  Class<?> elementType) {
		try {
			CollectionType colType = jsonMapper.getTypeFactory().constructCollectionType(collectionClass, elementType);
			return jsonMapper.readValue(jsonString, colType);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * converting by type to object
	 */
	@SuppressWarnings("rawtypes")
	public static Object fromJson(String jsonString, Class<? extends Map> mapClass, Class<?> keyClass,
                                  Class<?> valueClass) {
		try {
			MapType mapType = jsonMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
			return jsonMapper.readValue(jsonString, mapType);
		} catch (IOException e) {
			System.err.println("Unable to parse json " + jsonString);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * map json string to given class
	 */
	public static <T> T fromJson(JsonNode jsonNode, Class<T> className) {
		try {
			return jsonMapper.treeToValue(jsonNode, className);
		} catch (IOException e) {
			System.err.println("Unable to parse json ");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * map json string to given class
	 */
	@SuppressWarnings("unchecked")
	public static String[] fromJsonNodeToStringArray(JsonNode jsonNode) {
		return (String[]) jsonMapper.convertValue(jsonNode, ArrayList.class).stream().map(String::valueOf)
				.toArray(String[]::new);

	}

	/**
	 * converting object to json
	 */
	public static String toJson(Object inputObject) {
		try {
			return jsonMapper.writeValueAsString(inputObject);
		} catch (JsonProcessingException e) {
			System.err.println("Unable to convert to json ");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * convert given object to object node.
	 */
	public static ObjectNode toObjectNode(Object inputObject) {
		return jsonMapper.convertValue(inputObject, ObjectNode.class);
	}

	/**
	 * convert given json string to object node
	 */
	public static ObjectNode toObjectNode(String inputJsonStr) {
		try {
			return (ObjectNode) jsonMapper.readTree(inputJsonStr);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to test whether the given list is empty or not.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List inputList) {
		return inputList == null || inputList.isEmpty();
	}

	/**
	 * Method to test whether the given list is empty or not.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(List inputList) {
		return !isEmpty(inputList);
	}

	/**
	 * method to test whether the given map is empty or not
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map inputMap) {
		return inputMap == null || inputMap.isEmpty();
	}

	/**
	 * method to test whether the given set is empty or not
	 */

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Set inputSet) {
		return inputSet == null || inputSet.isEmpty();
	}

	/**
	 * check whether the given string is empty
	 */
	public static boolean isEmpty(String val) {
		return ((null == val) || (val.length() == 0));
	}

	/**
	 * get map with single key value
	 */
	public static Map<String, Object> defaultMap(String key, Object value) {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	/**
	 * @param className
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] stringArrayToType(String[] arr, Class<T> className) {
		if (arr == null) {
			return null;
		}
		if (className == Integer.class) {
			return (T[]) Arrays.stream(arr).parallel().map(Integer::valueOf).toArray(Integer[]::new);
		} else if (className == Long.class) {
			return (T[]) Arrays.stream(arr).parallel().map(Long::valueOf).toArray(Long[]::new);
		} else if (className == String.class) {
			return (T[]) arr;
		}
		return null;
	}

	/**
	 */
	public static <T> String[] typeArrayToStringArray(T[] arr) {
		if (arr == null) {
			return null;
		}

		return Arrays.stream(arr).parallel().map(String::valueOf).toArray(String[]::new);
	}

	/**
	 * is given bytes are equal
	 */
	public static boolean isEqual(byte[] sourceBytes, byte[] targetBytes) {
		if (sourceBytes.length != targetBytes.length)
			return false;
		byte ret = 0;
		for (int i = 0; i < targetBytes.length; i++)
			ret |= sourceBytes[i] ^ targetBytes[i];
		return ret == 0;
	}

	/**
	 * get date
	 */
	public static Date getDateInUTC() {
		// no need of setting the timezone because timezone for application is
		// set on application startup
		return Calendar.getInstance().getTime();
	}

	/**
	 * converting json node to java Type.
	 */
	public static <T> T fromJsonConvert(JsonNode jsonNode, Class<T> className) {
		return jsonMapper.convertValue(jsonNode, className);
	}

	/**
	 * getting string from bigdecimal object.
	 */
	public static String getString(Object value) {
		String ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = ((BigDecimal) value).toString();
			} else if (value instanceof String) {
				ret = (String) value;
			} else if (value instanceof BigInteger) {
				ret = ((BigInteger) value).toString();
			} else if (value instanceof Integer) {
				ret = ((Integer) value).toString();
			} else if (value instanceof Long) {
				ret = ((Long) value).toString();
			}
		}
		return ret;
	}

	/**
	 * converting json to class object.
	 */
	public static <S, T> List<T> entityToModel(List<S> entity, Class<T> className) {
		java.util.ListIterator<S> iterator = entity.listIterator();
		@SuppressWarnings({ "rawtypes", "unchecked" })
        List<T> modelList = new ArrayList();
		while (iterator.hasNext()) {
			S entitySingle = iterator.next();
			T model = fromJson(toJson(entitySingle), className);
			modelList.add(model);
		}
		return modelList;
	}

	/**
	 * get list with the added message
	 */
	public static <T> List<T> deafultList(T message) {
		List<T> msg = new LinkedList<>();
		msg.add(message);
		return msg;
	}

	/**
	 * Get set with data
	 */
	public static <T> Set<T> deafultSet(T data) {
		Set<T> defSet = new HashSet<>();
		defSet.add(data);
		return defSet;
	}

	/**
	 * convert Object to Array Node
	 */
	public static ArrayNode toArrayNode(Object o) {
		return jsonMapper.valueToTree(o);
	}

	/**
	 * format date by given format
	 */
	public static String getFormattedDate(Date inputDate, String format) {
		return getFormattedDate(inputDate, format, null);
	}

	/**
	 * format date by given format
	 */
	public static String getFormattedDate(Date inputDate, String format, String zoneCode) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(null == zoneCode ? "IST" : zoneCode));
		return sdf.format(inputDate);
	}

	/**
	 * get printable price
	 */
	public static String getPrintablePrice(float inPrice, String priceUnit) {
		String result;
		if (inPrice % 1 == 0) {
			result = String.format("%,.0f", inPrice);
		} else {
			result = String.format("%,.2f", inPrice);
		}
		return null == priceUnit ? result : priceUnit + " " + result;
	}

	/**
	 * get date differences in days
	 */
	public static long getDifferenceDays(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	/**
	 * Get key as string from object node.
	 */
	public static String getKeyFromObjectNode(ObjectNode node, String key) {
		if (null == node || null == node.get(key)) {
			return null;
		} else {
			return node.get(key).asText();
		}
	}

	/**
	 * get ammount by currency
	 */
	public static Integer getAmountByCurrency(String currency, Float price) {
		return (int) (price * 100);
	}

	public static void syslog(String s) {
		System.out.println("\n\n" + s + "\n\n");
	}

	public static long getCurrTimeInUTCMilli() {
		// default timezone is set to UTC hence this will give UTC time in
		// millisecond
		return Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * private constructor to not allow creating object.
	 */
	private CommonUtil() {
	}
}

package com.sz.youban.common.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.util.TokenBuffer;

import com.feilong.core.Validator;




public class JsonUtil {

    private static JsonFactory jsonFactory;
    private static ObjectMapper objectMapper;

    static {
        jsonFactory = new JsonFactory();
        objectMapper = new ObjectMapper(jsonFactory);
        jsonFactory.setCodec(objectMapper);
    }


    public static <T> T parseObj(String json, Class<T> clazz) {
        if (!isJsonObjectString(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }


    public static <T> T[] parseArray(String json, Class<T[]> clazz) {
        if (!isJsonObjectString(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public static JsonNode string2JsonNode(String jsonString) {
        if (!isJsonObjectString(jsonString)) {
            return null;
        }
        JsonNode jsonNode = null;
        JsonParser parser = null;
        try {
            parser = jsonFactory.createJsonParser(jsonString);
            jsonNode = objectMapper.readTree(parser);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
        return jsonNode;
    }


    public static ArrayNode createArrayNode() {
        return objectMapper.createArrayNode();
    }


    public static ArrayNode toArrayNode(String json) {
        if (!isJsonObjectString(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, ArrayNode.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    /**
     * 判断是否json对象字符串
     */
    public static boolean isJsonObjectString(String str) {
        return !Validator.isNullOrEmpty(str) && str.matches("^\\{.*\\}$");
    }

    /**
     * 判断是否json数组字符串
     */
    public static boolean isJsonArrayString(String str) {
        return !Validator.isNullOrEmpty(str) && str.matches("^\\[.*\\]$");
    }

    /**
     * json字符串转化为Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMap(String json) {
        if (!isJsonObjectString(json)) {
            return null;
        }
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    
    public static <T> T parseMapToObj(Object obj, Class<T> clazz) {
        if (Validator.isNullOrEmpty(obj)) {
            return null;
        }
        try {
            return objectMapper.convertValue(obj, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public static Map<String, String> parseStringMap(String jsonStr) {
        if (!isJsonObjectString(jsonStr)) {
            return null;
        }
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> parseObjToMap(Object obj) {
        if (Validator.isNullOrEmpty(obj)) {
            return null;
        }
        Map<String, Object> map = parseMap(parseObject2Str(obj));
        return map;
    }

    /**
     * 对象转为json字符串
     */
    public static String parseObject2Str(Object obj) {
        if (Validator.isNullOrEmpty(obj)) {
            return null;
        }
        String json = "";
        try {
            json = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
        	e.printStackTrace();
            return "";
        }
        return json;
    }

    /**
     * json字符串转为List
     */
    public static List<Object> parseList(String jsonStr) {
        if (!isJsonArrayString(jsonStr)) {
            return null;
        }
        ArrayNode array = toArrayNode(jsonStr);
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.size(); i++) {
            JsonNode value = array.get(i);
            if (value == null) {
                list.add(null);
                continue;
            }
            String _value = parseObject2Str(value);
            if (isJsonArrayString(_value)) {
                list.add(parseList(_value));
            } else if (isJsonObjectString(_value)) {
                list.add(parseMap(_value));
            } else {
                list.add(value.getTextValue());
            }
        }
        return list;
    }

    /**
     * 对象转化
     */
    public static <T> T convert(Object bean, Class<T> descClass) {
        if (Validator.isNullOrEmpty(bean)) {
            return null;
        }
        try {
            Map<String, Object> map = parseMap(parseObject2Str(bean));
            List<String> fieldNames = com.sz.youban.common.utils.bean.BeanUtil.getAllFieldNames(descClass);
            Map<String, Object> targetMap = new HashMap<String, Object>();
            for (String key : map.keySet()) {
                for (String temp : fieldNames) {
                    if (key.equalsIgnoreCase(temp)) {
                        Object value = targetMap.get(temp);
                        if (value == null) {
                            targetMap.put(temp, map.get(key));
                        }
                    }
                }
            }
            return (T) objectMapper.readValue(bean2JsonNode(targetMap), descClass);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[] convertToArray(Object bean) {
        if (Validator.isNullOrEmpty(bean)) {
            return null;
        }
        Map<String, Object> map = parseMap(parseObject2Str(bean));
        return map.values().toArray();
    }

    /**
     * 对象转化为JsonNode
     */
    public static JsonNode bean2JsonNode(Object bean) {
        if (Validator.isNullOrEmpty(bean)) {
            return null;
        }
        try {
            TokenBuffer buffer = new TokenBuffer(objectMapper);
            objectMapper.writeValue(buffer, bean);
            JsonNode node = objectMapper.readTree(buffer.asParser());
            return node;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("对象转换错误");
        }
    }

}

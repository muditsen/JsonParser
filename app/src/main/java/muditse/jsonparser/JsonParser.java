package muditse.jsonparser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by mudit sen on 3/19/17.
 * to parse json object
 */

public class JsonParser {

    public static void parse(Object toObject, JSONObject fromJsonObject) {
        Field[] fields = toObject.getClass().getFields();
        for (Field field : fields) {
            JsonKey key = field.getAnnotation(JsonKey.class);
            if (key != null) {
                try {

                    if (fromJsonObject.get(key.value()) instanceof JSONObject) {
                        Object obj = field.getType().newInstance();
                        parse(obj, fromJsonObject.getJSONObject(key.value()));
                        field.set(toObject, obj);
                    } else {
                        field.set(toObject, fromJsonObject.get(key.value()));
                    }


                } catch (IllegalAccessException | JSONException | InstantiationException e) {
                    e.printStackTrace();
                }
                Log.e("muditLog", "key is " + key.value());
            }
        }
    }


    public static JSONObject getJsonObjectFrom(Object object) {
        JSONObject jsonObject = new JSONObject();
        if (object == null) {
            return jsonObject;
        }
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            JsonKey key = field.getAnnotation(JsonKey.class);
            if (key != null) {
                try {
                    if (isPrimitiveType(field.getType().getName())) {
                        jsonObject.put(key.value(), field.get(object));
                    } else if (isStringType(field.getType().getName())) {
                        if (field.get(object) == null) {
                            jsonObject.put(key.value(), "");
                        } else {
                            jsonObject.put(key.value(), field.get(object));
                        }
                    } else {
                        jsonObject.put(key.value(), getJsonObjectFrom(field.get(object)));
                    }

                } catch (IllegalAccessException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    private static boolean isStringType(String type) {
        return type.equalsIgnoreCase("java.lang.String");
    }

    private static boolean isPrimitiveType(String type) {
        if (type.equalsIgnoreCase("int")) {
            return true;
        } else if (type.equalsIgnoreCase("float")) {
            return true;
        } else if (type.equalsIgnoreCase("double")) {
            return true;
        } else if (type.equalsIgnoreCase("short")) {
            return true;
        } else if (type.equalsIgnoreCase("boolean")) {
            return true;
        } else if (type.equalsIgnoreCase("byte")) {
            return true;
        } else if (type.equalsIgnoreCase("long")) {
            return true;
        } else if (type.equalsIgnoreCase("char")) {
            return true;
        }
        return false;
    }


}

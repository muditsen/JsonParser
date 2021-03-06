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

    public static Object parse(Class clazz, JSONObject fromJsonObject) {
        try {
            Object o = clazz.newInstance();
            parse(o, fromJsonObject);
            return o;
        } catch (InstantiationException e) {
            Log.e("Error", "Unable to instantiate the field " + e.toString());
        } catch (IllegalAccessException e) {
            Log.e("Error", "Unable to access the field " + e.toString());
        }
        return null;
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
        } else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("java.lang.Float")) {
            return true;
        } else if (type.equalsIgnoreCase("double") || type.equalsIgnoreCase("java.lang.Double")) {
            return true;
        } else if (type.equalsIgnoreCase("short") || type.equalsIgnoreCase("java.lang.Short")) {
            return true;
        } else if (type.equalsIgnoreCase("boolean") || type.equalsIgnoreCase("java.lang.Boolean")) {
            return true;
        } else if (type.equalsIgnoreCase("byte") || type.equalsIgnoreCase("java.lang.Byte")) {
            return true;
        } else if (type.equalsIgnoreCase("long") || type.equalsIgnoreCase("java.lang.Long")) {
            return true;
        } else if (type.equalsIgnoreCase("char") || type.equalsIgnoreCase("java.lang.Char")) {
            return true;
        }
        return false;
    }


}

package com.research.dbsync.serializer.json;


import com.research.dbsync.core.Trace;
import com.research.dbsync.serializer.DataFormat;
import com.research.dbsync.serializer.IGetter;
import com.research.dbsync.serializer.ISetter;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.custom.CustomGetterHandler;
import com.research.dbsync.serializer.custom.CustomSetterHandler;
import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by iFreedom87 on 4/22/17.
 */

public class JSONSetter implements ISetter<JSONObject>, IGetter<JSONObject> {
    private static JSONSetter _INSTANCE;
    private CustomGetterHandler<JSONObject> customGetterHandler;
    private CustomSetterHandler<JSONObject> customSetterHandler;

    private JSONSetter() {
        customGetterHandler = new CustomGetterHandler<>();
        customSetterHandler = new CustomSetterHandler<>();
    }

    public static JSONSetter getInstance() {
        if(_INSTANCE == null)
            _INSTANCE = new JSONSetter();
        return _INSTANCE;
    }

    @Override
    public boolean getBoolean(JSONObject source, String key) {
        try{
            return source.getBoolean(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return false;
    }

    @Override
    public byte getByte(JSONObject source, String key) {
        try{
            return (byte) source.getInt(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public short getShort(JSONObject source, String key) {
        try{
            return (short) source.getInt(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public int getInt(JSONObject source, String key) {
        try{
            return source.getInt(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public long getLong(JSONObject source, String key) {
        try{
            return source.getLong(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public float getFloat(JSONObject source, String key) {
        try{
            return (float) source.getDouble(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public double getDouble(JSONObject source, String key) {
        try{
            return source.getDouble(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public char getChar(JSONObject source, String key) {
        try{
            return source.getString(key).charAt(0);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public String getString(JSONObject source, String key) {
        try{
            return source.getString(key);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public <V extends SerializableModel> V getObject(JSONObject source, String key, Class<V> serializableClass) {
        try{
            JSONObject jsonObject = source.getJSONObject(key);
            V serializableModel = serializableClass.newInstance();
            JSONEncoder.getInstance().decode(jsonObject, serializableModel);
            return serializableModel;
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Object Error: Object: " + source + " key value:" + key, exp);
        }

        return null;
    }

    @Override
    public Object[] getArray(JSONObject source, String key, int dataFormat) {
        try {
            JSONArray jsonArray = source.getJSONArray(key);
            int arrayLength = jsonArray.length();
            Class primitiveClass = DataFormat.getPrimitiveClass(dataFormat);

            @SuppressWarnings("unchecked")
            Object[] array = (Object[]) Array.newInstance(primitiveClass, arrayLength);

            if(primitiveClass == Integer.class) {
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast(jsonArray.getInt(i));
                }
            }else if(primitiveClass == String.class) {
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast(jsonArray.getString(i));
                }
            }else if(primitiveClass == Double.class){
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast(jsonArray.getDouble(i));
                }
            }else if(primitiveClass == Boolean.class){
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast(jsonArray.getBoolean(i));
                }
            }else if(primitiveClass == Character.class){
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast(jsonArray.getString(i).charAt(0));
                }
            }else if(primitiveClass == Short.class){
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast((short) jsonArray.getInt(i));
                }
            }else if(primitiveClass == Long.class) {
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast(jsonArray.getLong(i));
                }
            }else if(primitiveClass == Float.class){
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast((float) jsonArray.getDouble(i));
                }
            }else if(primitiveClass == Byte.class){
                for(int i = 0; i < arrayLength; i++) {
                    array[i] = primitiveClass.cast((byte) jsonArray.getInt(i));
                }
            }

            return array;

        }catch (Exception exp){
            Trace.e("JSONSetter", "Get Array Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public <V extends SerializableModel> List<V> getList(JSONObject source, String key, Class<V> serializableClass) {
        try {
            JSONObject jsonObject;
            V model;
            JSONArray jsonArray = source.getJSONArray(key);
            List<V> list = new ArrayList<>();
            for (int i=0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                model = serializableClass.newInstance();
                JSONEncoder.getInstance().decode(jsonObject, model);
                list.add(model);
            }
            return list;
        }catch (Exception exp){
            Trace.e("JSONSetter", "Get List Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public void setBoolean(JSONObject target, String key, boolean value) {
        try{
            target.put(key, value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setByte(JSONObject target, String key, byte value) {
        try{
            target.put(key, (int) value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setShort(JSONObject target, String key, short value) {
        try{
            target.put(key, (int) value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setInt(JSONObject target, String key, int value) {
        try{
            target.put(key, value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setLong(JSONObject target, String key, long value) {
        try{
            target.put(key, value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setFloat(JSONObject target, String key, float value) {
        try{
            target.put(key, (double) value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setDouble(JSONObject target, String key, double value) {
        try{
            target.put(key, value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setChar(JSONObject target, String key, char value) {
        try{
            target.put(key, Character.toString(value));
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setString(JSONObject target, String key, String value) {
        try{
            target.put(key, value);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public <V extends SerializableModel> void setObject(JSONObject target, String key, V value) {
        try {
            JSONObject jsonObject = JSONEncoder.getInstance().encode(value);
            jsonObject.put(key, jsonObject);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Object Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public <V> void setArray(JSONObject target, String key, V[] array) {
        try{
            JSONArray jsonArray = new JSONArray(Arrays.asList(array));
            target.put(key, jsonArray);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set Array Error: Object: " + target + " key value:" + key, exp);
        }

    }

    @Override
    public <V extends SerializableModel> void setList(JSONObject target, String key, List<V> list) {
        try{
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < list.size(); i++){
                jsonArray.put(JSONEncoder.getInstance().encode(list.get(i)));
            }
            target.put(key, jsonArray);
        }catch (Exception exp){
            Trace.e("JSONSetter", "Set List Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void addCustom(String customKey, ICustomGetter customGettable) {
        customGetterHandler.addCustom(customKey, customGettable);
    }

    @Override
    public ICustomGetter getCustomGetter(String customKey) {
        return customGetterHandler.getCustomGetter(customKey);
    }

    @Override
    public <V> V getCustom(JSONObject target, String targetKey, String customKey, Class<V> customClass) {
        return customGetterHandler.getCustom(target, targetKey, customKey, customClass);
    }

    @Override
    public boolean containGetterCustomKey(String customKey) {
        return customGetterHandler.containGetterCustomKey(customKey);
    }

    @Override
    public void addCustom(String customKey, ICustomSetter customSettable) {
        customSetterHandler.addCustom(customKey, customSettable);
    }

    @Override
    public <V> void setCustom(JSONObject target, String targetKey, String customKey, V value) {
        customSetterHandler.setCustom(target, targetKey, customKey, value);
    }

    @Override
    public ICustomSetter getCustomSetter(String customKey) {
        return customSetterHandler.getCustomSetter(customKey);
    }

    @Override
    public boolean containSetterCustomKey(String customKey) {
        return customSetterHandler.containSetterCustomKey(customKey);
    }
}

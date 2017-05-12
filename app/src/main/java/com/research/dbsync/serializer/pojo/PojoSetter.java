package com.research.dbsync.serializer.pojo;

import com.research.dbsync.core.Trace;
import com.research.dbsync.serializer.IGetter;
import com.research.dbsync.serializer.ISetter;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.custom.CustomGetterHandler;
import com.research.dbsync.serializer.custom.CustomSetterHandler;
import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;
import com.research.dbsync.util.ArrayUtils;
import com.research.dbsync.util.ReflectionUtils;
import com.research.dbsync.util.Validator;

import java.util.List;

import javadz.beanutils.BeanUtils;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public class PojoSetter implements ISetter<Object>, IGetter<Object> {
    private static PojoSetter _INSTANCE;

    private CustomGetterHandler<Object> customGetterHandler;
    private CustomSetterHandler<Object> customSetterHandler;

    private PojoSetter(){
        customGetterHandler = new CustomGetterHandler<>();
        customSetterHandler = new CustomSetterHandler<>();
    }

    @Override
    public boolean getBoolean(Object source, String key) {
        try{
            return Boolean.parseBoolean(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return false;
    }

    @Override
    public byte getByte(Object source, String key) {
        try{
            return Byte.parseByte(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public short getShort(Object source, String key) {
        try{
            return Short.parseShort(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public int getInt(Object source, String key) {
        try{
            return Integer.parseInt(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public long getLong(Object source, String key) {
        try{
            return Long.parseLong(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public float getFloat(Object source, String key) {
        try{
            return Float.parseFloat(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public double getDouble(Object source, String key) {
        try{
            return Double.parseDouble(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public char getChar(Object source, String key) {
        try{
            return BeanUtils.getProperty(source, key).charAt(0);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return 0;
    }

    @Override
    public String getString(Object source, String key) {
        try{
            return String.valueOf(BeanUtils.getProperty(source, key));
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Value Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public <V extends SerializableModel> V getObject(Object source, String key, Class<V> serializableClass) {
        try{
            return (V) ReflectionUtils.getPropertyValue(source, key);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Object Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public Object[] getArray(Object source, String key, int dataFormat) {
        try{
            Object propertyValue = ReflectionUtils.getPropertyValue(source, key);
            return ArrayUtils.convertToObjectArray(propertyValue);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get Array Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public <V extends SerializableModel> List<V> getList(Object source, String key, Class<V> vClass) {
        try{
            Object propertyValue = ReflectionUtils.getPropertyValue(source, key);
            return (List<V>) propertyValue;
        }catch (Exception exp){
            Trace.e("PojoSetter", "Get List Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public void setBoolean(Object target, String key, boolean value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setByte(Object target, String key, byte value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setShort(Object target, String key, short value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setInt(Object target, String key, int value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setLong(Object target, String key, long value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setFloat(Object target, String key, float value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setDouble(Object target, String key, double value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setChar(Object target, String key, char value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public void setString(Object target, String key, String value) {
        if(Validator.containString(value)){
            try{
                BeanUtils.setProperty(target, key, value);
            }catch (Exception exp){
                Trace.e("PojoSetter", "Set Value Error: Object: " + target + " key value:" + key, exp);
            }
        }
    }

    @Override
    public <V extends SerializableModel> void setObject(Object target, String key, V value) {
        try{
            BeanUtils.setProperty(target, key, value);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Object Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public <V> void setArray(Object target, String key, V[] array) {
        try{
            BeanUtils.setProperty(target, key, array);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set Array Error: Object: " + target + " key value:" + key, exp);
        }
    }

    @Override
    public <V extends SerializableModel> void setList(Object target, String key, List<V> list) {
        try{
            BeanUtils.setProperty(target, key, list);
        }catch (Exception exp){
            Trace.e("PojoSetter", "Set List Error: Object: " + target + " key value:" + key, exp);
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
    public <V> V getCustom(Object target, String targetKey, String customKey, Class<V> customClass) {
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
    public ICustomSetter getCustomSetter(String customKey) {
        return customSetterHandler.getCustomSetter(customKey);
    }

    @Override
    public <V> void setCustom(Object target, String targetKey, String customKey, V value) {
        customSetterHandler.setCustom(target, targetKey, customKey, value);
    }

    @Override
    public boolean containSetterCustomKey(String customKey) {
        return customSetterHandler.containSetterCustomKey(customKey);
    }

    public static PojoSetter getInstance(){
        if(_INSTANCE == null)
            _INSTANCE = new PojoSetter();
        return _INSTANCE;
    }
}

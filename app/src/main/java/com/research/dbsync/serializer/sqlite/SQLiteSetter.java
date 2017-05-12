package com.research.dbsync.serializer.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.research.dbsync.core.Trace;
import com.research.dbsync.database.GSysDB;
import com.research.dbsync.serializer.DataFormat;
import com.research.dbsync.serializer.IGetter;
import com.research.dbsync.serializer.ISetter;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.custom.CustomGetterHandler;
import com.research.dbsync.serializer.custom.CustomSetterHandler;
import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;
import com.research.dbsync.serializer.json.JSONEncoder;
import com.research.dbsync.serializer.json.JSONSetter;
import com.research.dbsync.util.Validator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public class SQLiteSetter implements ISetter<ContentValues>, IGetter<Cursor> {
    private static SQLiteSetter _INSTANCE;

    private CustomGetterHandler<Cursor> customGetterHandler;
    private CustomSetterHandler<ContentValues> customSetterHandler;

    private SQLiteSetter(){
        customGetterHandler = new CustomGetterHandler<>();
        customSetterHandler = new CustomSetterHandler<>();
    }

    @Override
    public boolean getBoolean(Cursor source, String key) {
        return (source.getInt(source.getColumnIndex(key)) == 0) ? false : true;
    }

    @Override
    public byte getByte(Cursor source, String key) {
        return (byte) source.getShort(source.getColumnIndex(key));
    }

    @Override
    public short getShort(Cursor source, String key) {
        return source.getShort(source.getColumnIndex(key));
    }

    @Override
    public int getInt(Cursor source, String key) {
        return source.getInt(source.getColumnIndex(key));
    }

    @Override
    public long getLong(Cursor source, String key) {
        return source.getLong(source.getColumnIndex(key));
    }

    @Override
    public float getFloat(Cursor source, String key) {
        return source.getFloat(source.getColumnIndex(key));
    }

    @Override
    public double getDouble(Cursor source, String key) {
        return source.getDouble(source.getColumnIndex(key));
    }

    @Override
    public char getChar(Cursor source, String key) {
        return source.getString(source.getColumnIndex(key)).charAt(0);
    }

    @Override
    public String getString(Cursor source, String key) {
        return source.getString(source.getColumnIndex(key));
    }

    @Override
    public <V extends SerializableModel> V getObject(Cursor source, String key, Class<V> serializableClass) {
        // Refer to Other Table And Get
        try{
            if(GSysDB.getInstance().table_contain(serializableClass)){
                Trace.d("SQLiteSetter", "GET: Enter Table Base ID Reference: " + source.getLong(source.getColumnIndex(key)));
                long id = source.getLong(source.getColumnIndex(key));
                // Based on Int Look For Table
                return GSysDB.getInstance().row_get(serializableClass, id);
            }else{
                Trace.d("SQLiteSetter", "GET: Enter JSON Object Reference: " + serializableClass);
                V serializableModel = serializableClass.newInstance();
                String jsonString = source.getString(source.getColumnIndex(key));
                JSONEncoder.getInstance().decode(new JSONObject(jsonString), serializableModel);
                return serializableModel;
            }
        }catch (Exception exp){
            Trace.e("SQLiteSetter", "Set List Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public Object[] getArray(Cursor source, String key, int dataFormat) {
        try{
            String jsonString = source.getString(source.getColumnIndex(key));
            JSONObject jsonObject = new JSONObject(jsonString);
            return JSONSetter.getInstance().getArray(jsonObject, key, dataFormat);
        }catch (Exception exp){
            Trace.e("SQLiteSetter", "Set Array Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public <V extends SerializableModel> List<V> getList(Cursor source, String key, Class<V> serializableClass) {
        try{
            if(GSysDB.getInstance().table_contain(serializableClass)){
                Object[] ids = getArray(source, key, DataFormat.LONG);
                // Based on Int Look For Table
                return GSysDB.getInstance().rows_get(serializableClass, (Long[]) ids);
            }else{
                String jsonListString = source.getString(source.getColumnIndex(key));
                return JSONSetter.getInstance().getList(new JSONObject(jsonListString), key, serializableClass);
            }
        }catch (Exception exp){
            Trace.e("SQLiteSetter", "Set List Error: Object: " + source + " key value:" + key, exp);
        }
        return null;
    }

    @Override
    public void setBoolean(ContentValues target, String key, boolean value) {
        // There is No Boolean Value in SQL hence convert to 1 or 0
        target.put(key, (value) ? 1 : 0);
    }

    @Override
    public void setByte(ContentValues target, String key, byte value) {
        target.put(key, value);
    }

    @Override
    public void setShort(ContentValues target, String key, short value) {
        target.put(key, value);
    }

    @Override
    public void setInt(ContentValues target, String key, int value) {
        target.put(key, value);
    }

    @Override
    public void setLong(ContentValues target, String key, long value) {
        target.put(key, value);
    }

    @Override
    public void setFloat(ContentValues target, String key, float value) {
        target.put(key, value);
    }

    @Override
    public void setDouble(ContentValues target, String key, double value) {
        target.put(key, value);
    }

    @Override
    public void setChar(ContentValues target, String key, char value) {
        target.put(key, Character.toString(value));
    }

    @Override
    public void setString(ContentValues target, String key, String value) {
        if(Validator.containString(value))
            target.put(key, value);
        // Else Don't Put
    }

    @Override
    public <V extends SerializableModel> void setObject(ContentValues target, String key, V value) {
        try{
            if(GSysDB.getInstance().table_contain(value.getClass())){
                Trace.d("SQLiteSetter", "SET: Enter Table Base ID Reference: " + value.getId());
                target.put(key, value.getId()); //The id would reference to the corresponding table
            }else{
                Trace.d("SQLiteSetter", "SET: Enter JSON Object Reference: " + value.getClass());
                // Store and Encode Into JSON String
                target.put(
                        key,
                        JSONEncoder.getInstance().encode(value).toString()
                );
            }
        }catch (Exception exp){
            Trace.e("SQLiteEncoder", "Set Object Error", exp);
        }
    }

    @Override
    public <V> void setArray(ContentValues target, String key, V[] array) {
        try{
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray(Arrays.asList(array));
            jsonObject.put(key, jsonArray);
            target.put(key, jsonObject.toString());
        }catch (Exception exp){
            Trace.e("SQLiteEncoder", "Set Array Error", exp);
        }
    }

    @Override
    public <V extends SerializableModel> void setList(ContentValues target, String key, List<V> list) {
        try{
            if(list.size() == 0)
                return;

            JSONObject jsonObject = new JSONObject();
            if(GSysDB.getInstance().table_contain(
                list.get(0).getSerializationInfo().getObjectClass(key)
            )){
                List<Long> ids = new ArrayList<>();
                for(int i = 0; i < list.size(); i++){
                    ids.add(list.get(i).getId());
                }
                JSONSetter.getInstance().setArray(jsonObject, key, ids.toArray(new Long[ids.size()]));
            }else{
                JSONSetter.getInstance().setList(jsonObject, key, list);
                target.put(key, jsonObject.toString());
            }
        }catch (Exception exp){

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
    public <V> V getCustom(Cursor target, String targetKey, String customKey, Class<V> customClass) {
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
    public <V> void setCustom(ContentValues target, String targetKey, String customKey, V value) {
        customSetterHandler.setCustom(target, targetKey, customKey, value);
    }

    @Override
    public boolean containSetterCustomKey(String customKey) {
        return customSetterHandler.containSetterCustomKey(customKey);
    }

    public static SQLiteSetter getInstance(){
        if(_INSTANCE == null)
            _INSTANCE = new SQLiteSetter();
        return _INSTANCE;
    }
}

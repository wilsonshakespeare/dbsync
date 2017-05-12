package com.research.dbsync.serializer;

import com.research.dbsync.serializer.custom.ICustomGetter;

import java.util.List;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public interface IGetter<T> {
    boolean getBoolean(T source, String key);
    byte getByte(T source, String key);
    short getShort(T source, String key);
    int getInt(T source, String key);
    long getLong(T source, String key);
    float getFloat(T source, String key);
    double getDouble(T source, String key);
    char getChar(T source, String key);
    String getString(T source, String key);
    <V extends SerializableModel> V getObject(T source, String key, Class<V> serializableClass);
    Object[] getArray(T source, String key, int dataFormat); // note: arrays are primitive, Object[] for standard passing
    <V extends SerializableModel> List<V> getList(T source, String key, Class<V> serializableClass);

    // Custom Handling
    void addCustom(String customKey, ICustomGetter customGettable); // Based on key to retrieve the setter strategy
    ICustomGetter getCustomGetter(String customKey);
    <V> V getCustom(T target, String targetKey, String customKey, Class<V> customClass);
    boolean containGetterCustomKey(String customKey);

}

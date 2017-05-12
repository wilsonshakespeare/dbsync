package com.research.dbsync.serializer;

import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;

import java.util.List;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public interface ISetter<T> {
    void setBoolean(T target, String key, boolean value);
    void setByte(T target, String key, byte value);
    void setShort(T target, String key, short value);
    void setInt(T target, String key, int value);
    void setLong(T target, String key, long value);
    void setFloat(T target, String key, float value);
    void setDouble(T target, String key, double value);
    void setChar(T target, String key, char value);
    void setString(T target, String key, String value);
    <V extends SerializableModel> void setObject(T target, String key, V value);
    <V> void setArray(T target, String key, V[] array); // note: arrays are primitive
    <V extends SerializableModel> void setList(T target, String key, List<V> list);

    // Custom Handling
    void addCustom(String customKey, ICustomSetter customSettable); // Based on key to retrieve the setter strategy
    ICustomSetter getCustomSetter(String customKey);
    <V> void setCustom(T target, String targetKey, String customKey, V value);
    boolean containSetterCustomKey(String customKey);

    // Set and Get Array and Object
}

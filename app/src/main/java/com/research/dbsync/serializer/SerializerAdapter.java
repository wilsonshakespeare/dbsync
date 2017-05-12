package com.research.dbsync.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 4/20/17.
 */

public class SerializerAdapter {
    private static SerializerAdapter _INSTANCE;
    private Map<Class<? extends SerializableModel>, SerializationInfo> _serializeInfoMap;

    private SerializerAdapter(){
        _serializeInfoMap = new HashMap<>();
    }

    public boolean containInfo(Class<? extends SerializableModel> classType){
        return _serializeInfoMap.containsKey(classType);
    }

    public void serializationInfo_set(Class<? extends SerializableModel> classType, SerializationInfo info){
        _serializeInfoMap.put(classType, info);
    }

    public SerializationInfo serializationInfo_get(Class<? extends SerializableModel> classType){
        return _serializeInfoMap.get(classType);
    }

    public static SerializerAdapter getInstance(){
        if(_INSTANCE == null) {
            _INSTANCE = new SerializerAdapter();
        }
        return _INSTANCE;
    }
}

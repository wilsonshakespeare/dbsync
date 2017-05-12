package com.research.dbsync.serializer;

import com.research.dbsync.serializer.custom.CustomInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iFreedom87 on 4/20/17.
 */

public class SerializationInfo {
    //TODO: Serialization Info For Map
    //TODO: A More Optimised Method than using Maps for retrieval - Process Count Consideration for Hashing

    private List<String> _keys = null;
    private List<String> _localKeys = null;

    private Map<String, Integer> _dataFormat = null;
    private Map<String, Class<? extends SerializableModel>> _objectFormat = null;
    private Map<String, Integer> _arrayFormat = null;
    private Map<String, Class<? extends SerializableModel>> _listFormat = null;

    // Custom Handling
    private Map<String, String> _customKeyMap = null;
    private Map<String, Class> _customClassMap = null;

    public SerializationInfo(){
        _dataFormat = new HashMap<>();
        _objectFormat = new HashMap<>();
        _arrayFormat = new HashMap<>();
        _listFormat = new HashMap<>();
        _keys = new ArrayList<>();
        _localKeys = new ArrayList<>();

        // Custom Handling
        _customKeyMap = new HashMap<>();
        _customClassMap = new HashMap<>();

    }

    public String[] getKeys(){
        String[] keys = new String[_keys.size()];
        keys = _keys.toArray(keys);
        return keys;
    }

    public String[] getLocalKeys(){
        String[] keys = new String[_localKeys.size()];
        keys = _localKeys.toArray(keys);
        return keys;
    }

    public void addFormat(String key, String localKey, @DataFormat.Formats int dataFormat){
        if(_keys.contains(key) || _localKeys.contains(localKey))
            return;

        _dataFormat.put(key, dataFormat);
        _keys.add(key);
        _localKeys.add(localKey);
    }

    public int getFormat(String key){
        return _dataFormat.get(key);
    }

    public boolean containFormat(String key){
        return _dataFormat.containsKey(key);
    }

    public void addObject(String key, String localKey, Class<? extends SerializableModel> classType){
        if(_keys.contains(key) || _localKeys.contains(localKey))
            return;

        _objectFormat.put(key, classType);
        _keys.add(key);
        _localKeys.add(localKey);
    }

    public Class<? extends SerializableModel> getObjectClass(String key){
        return _objectFormat.get(key);
    }

    public boolean containObject(String key){
        return _objectFormat.containsKey(key);
    }

    public void addArray(String key, String localKey, @DataFormat.Formats int dataFormat){
        if(_keys.contains(key) || _localKeys.contains(localKey))
            return;

        _arrayFormat.put(key, dataFormat);
        _keys.add(key);
        _localKeys.add(localKey);
    }

    public int getArrayFormat(String key){
        return _arrayFormat.get(key);
    }

    public boolean containArray(String key){
        return _arrayFormat.containsKey(key);
    }

    public void addList(String key, String localKey, Class<? extends SerializableModel> classType){
        if(_keys.contains(key) || _localKeys.contains(localKey))
            return;

        _listFormat.put(key, classType);
        _keys.add(key);
        _localKeys.add(localKey);
    }

    public Class<? extends SerializableModel> getListClass(String key){
        return _objectFormat.get(key);
    }

    public boolean containList(String key){
        return _listFormat.containsKey(key);
    }

    public void addCustom(String key, String localKey, String customReferenceKey, CustomInitializer initializer){
        if(_keys.contains(key) || _localKeys.contains(localKey))
            return;

        initializer.setup(customReferenceKey);

        _customKeyMap.put(key, customReferenceKey);
        _customClassMap.put(key, initializer.getCustomClass());
        _keys.add(key);
        _localKeys.add(localKey);
    }

    public String getCustomKey(String key){
        return _customKeyMap.get(key);
    }

    public Class<?> getCustomClass(String key){
        return _customClassMap.get(key);
    }

    public boolean containCustom(String key){
        return _customClassMap.containsKey(key);
    }



}

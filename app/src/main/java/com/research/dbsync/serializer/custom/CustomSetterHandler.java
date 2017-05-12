package com.research.dbsync.serializer.custom;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 5/10/17.
 */

public class CustomSetterHandler<T> {
    private Map<String, ICustomSetter> customSetterMap;

    public CustomSetterHandler() {
        this.customSetterMap = new HashMap<>();
    }

    public void addCustom(String customKey, ICustomSetter customSettable){
        this.customSetterMap.put(customKey, customSettable);
    }

    public ICustomSetter getCustomSetter(String customKey){
        return this.customSetterMap.get(customKey);
    }

    public <V> void setCustom(T target, String targetKey, String customKey, V value){
        this.customSetterMap.get(customKey).setCustomObject(target, targetKey, value);
    }

    public boolean containSetterCustomKey(String customKey){
        return customSetterMap.containsKey(customKey);
    }
}

package com.research.dbsync.serializer.custom;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 5/10/17.
 */

public class CustomGetterHandler<T> {
    private Map<String, ICustomGetter> customGetterMap;

    public CustomGetterHandler() {
        this.customGetterMap = new HashMap<>();
    }


    public void addCustom(String customKey, ICustomGetter customGettable){
        this.customGetterMap.put(customKey, customGettable);
    }

    public ICustomGetter getCustomGetter(String customKey){
        return this.customGetterMap.get(customKey);
    }

    public <V> V getCustom(T target, String targetKey, String customKey, Class<V> customClass){
        return (V) this.customGetterMap.get(customKey).getCustomObject(target, targetKey);
    }

    public boolean containGetterCustomKey(String customKey){
        return customGetterMap.containsKey(customKey);
    }
}

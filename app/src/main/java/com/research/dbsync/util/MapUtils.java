package com.research.dbsync.util;

import android.os.Build;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.greenrobot.common.ListMap;

/**
 * Created by iFreedom87 on 6/27/16.
 */
public final class MapUtils {
    /*
    public static <K,V> Map<K,V> map_create(List<K> kList, List<V> vList){
        //return
    }
    */
    public static <K, V> boolean removeElement(ListMap<K, V> list, K key, V value, boolean removeKeyOnEmptyList){
        boolean removal =  list.removeElement(key, value);
        if(removeKeyOnEmptyList){
            if(list.get(key).size() == 0){
                list.remove(key);
            }
        }
        return removal;
    }

    public static <K,V> Map<K,V> wildHashMap_init(K key, V value){
        Map<K,V> map = new HashMap<K,V>();
        map.put(key, value);
        return map;
    }

    public static <K, V> void map_delete(Map<K, V> map){
        map_delete(map, false, false);
    }

    public static <K, V> void map_delete(Map<K, V> map, boolean keyDisposableCheck, boolean valueDisposableCheck){
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        Map.Entry entry = null;
        while(it.hasNext()) {
            entry = (Map.Entry)it.next();
            if(keyDisposableCheck) MemoryUtils.dispose(entry.getKey());
            if(valueDisposableCheck) MemoryUtils.dispose(entry.getValue());
            it.remove();
        }
    }

    public static <K, V> Map<K,V> arrayMap_create(){
        // ArrayMap Not Supported
        if(Build.VERSION.SDK_INT < 19){
            return new HashMap<>();
        }else{
            return new ArrayMap<>();
        }
    }
}

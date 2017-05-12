package com.research.dbsync.util;

import com.research.dbsync.core.IDisposable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.greenrobot.common.ListMap;


/**
 * Created by iFreedom87 on 6/29/16.
 */

public final class MemoryUtils {

    public static <K, V> void listMap_delete(ListMap<K, V> map){
        listMap_delete(map, false, false);
    }

    public static <K, V> void listMap_delete(ListMap<K, V> map, boolean keyDisposableCheck, boolean valueDisposableCheck){
        Iterator<ListMap.Entry<K, List<V>>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            if(keyDisposableCheck) dispose(entry.getKey());
            list_delete((List<V>) entry.getValue(), valueDisposableCheck);
            it.remove();
        }
    }

    public static <K, V> void listMap_remove(ListMap<K, V> listMap, K key, boolean disposalCheck){
        List<V> list = listMap.remove(key);
        list_delete(list, disposalCheck);
    }

    public static <E> void list_delete(List<E> list, boolean disposalCheck){
        while (list.size() != 0){
            listItem_delete(list, 0, disposalCheck);
        }
    }

    public static <E> void listItem_delete(List<E> list, int at, boolean disposalCheck){
        if(disposalCheck) dispose(list.get(at));
        list.remove(at);

    }

    public static void dispose(Object object){
        if(object instanceof IDisposable)
            ((IDisposable) object).dispose();
    }

    /*// For Search Remove
    Map.Entry<String, String> entry = it.next();
    if(entry.getKey().equals("test")) {
        it.remove();
    }
    /*/
}

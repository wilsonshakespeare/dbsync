package com.research.dbsync.util;

import com.research.dbsync.core.IDisposable;
import com.research.dbsync.core.Trace;
import com.research.dbsync.model.ModelBase;

import java.util.ArrayList;
import java.util.List;

import javadz.beanutils.BeanUtils;

/**
 * Created by iFreedom87 on 8/4/16.
 */

public final class ListUtils {

    public static <E, C> List<C> castTo(List<E> originalList, Class<C> castClass){
        @SuppressWarnings("unchecked")
        List<C> returnList = (List) originalList;
        return returnList;
    }

    public static <E extends T, T> void list_clone(List<E> source, List<T> target){
        target.clear();
        for(int i = 0; i < source.size(); i++){
            target.add(source.get(i));
        }
    }

    public static <T> List<T> retrieve_property_list(List list, String property, Class<T> propertyType){
        List<T> returnList = new ArrayList<>();
        try {
            for(int i = 0; i < list.size(); i++){
                returnList.add((T) ReflectionUtils.getPropertyValue(list.get(i), property));
            }
        }catch (Exception exp){
            Trace.e("ListUtils", "property error retrieval: " + property + " of list: " + list, exp);
        }
        return returnList;
    }

    /*/
    public static <T extends ModelBase> List<T> retrieveConditionedList(List<T> source, String condition, Object value){
        List<T> list = new ArrayList<>();
        for(int i = 0 ; i < source.size(); i++){
            if(source.get(i).getConditionHandler().isCondition(condition, value)){
                list.add(source.get(i));
            }
        }
        return list;
    }

    public static <T extends ModelBase> List<T> retrieveConditionedList(List<T> source, String condition, String proprerty, Object value){
        List<T> list = new ArrayList<>();
        for(int i = 0 ; i < source.size(); i++){
            if(source.get(i).getConditionHandler().isCondition(condition, proprerty, value)){
                list.add(source.get(i));
            }
        }
        return list;
    }

    public static <T extends ModelBase> List<T> retrieveConditionedList(List<T> source, int condition, Object value){
        List<T> list = new ArrayList<>();
        for(int i = 0 ; i < source.size(); i++){
            if(source.get(i).getConditionHandler().isCondition(condition, value)){
                list.add(source.get(i));
            }
        }
        return list;
    }

    public static <T extends ModelBase> List<T> retrieveConditionedList(List<T> source, int condition, String proprerty, Object value){
        List<T> list = new ArrayList<>();
        for(int i = 0 ; i < source.size(); i++){
            if(source.get(i).getConditionHandler().isCondition(condition, proprerty, value)){
                list.add(source.get(i));
            }
        }
        return list;
    }
    /*/

    public static <T extends IDisposable> void clearAndDispose(List<T> disposableList){
        for(int i = 0; i < disposableList.size(); i++){
            disposableList.get(i).dispose();
        }
        disposableList.clear();
    }

}

package com.research.dbsync.model;

import java.util.Map;

/**
 * @author iFreedom87
 * @since 2016-06-23
 */
public interface IModelOwner {
    <T extends ModelBase> T model_get(Class<T> c);
    <T extends ModelBase> void model_add(Class<T> c, Map<String, Object> properties);
    <T extends ModelBase> void model_add(T model);
    <T extends ModelBase> void model_remove(Class<T> c);
    Object data_get(String key);
    void data_set(String key, Object data);

}

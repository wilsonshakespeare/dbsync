package com.research.dbsync.model;

/**
 * Created by iFreedom87 on 5/6/17.
 */

public interface IAppliable<T> {
    // Appliables Are For Models to Apply Own Preset Properties on an Object
    void apply(T target);
}

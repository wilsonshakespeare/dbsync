package com.research.dbsync.serializer.custom;

/**
 * Created by iFreedom87 on 5/10/17.
 */

public interface ICustomSetter<S, T> {
    void setCustomObject(S source, String sourceKey, T data);
}

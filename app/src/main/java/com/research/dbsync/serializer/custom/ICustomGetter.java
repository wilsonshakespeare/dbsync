package com.research.dbsync.serializer.custom;

/**
 * Created by iFreedom87 on 5/10/17.
 */

public interface ICustomGetter<S, T> {
    T getCustomObject(S source, String sourceKey);
}

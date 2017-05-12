package com.research.dbsync.serializer;

/**
 * Created by iFreedom87 on 4/20/17.
 */

public interface ISerializableModel {
    SerializationInfo getSerializationInfo();
    Object getObjectSource();
    void setObjectSource(Object objectSource);
    IGetter getGettableProxy();
    ISetter getSettableProxy();
}

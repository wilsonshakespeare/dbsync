package com.research.dbsync.serializer;

import java.util.Map;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public interface IEncoder<T> {
    // Encoding Match Local to Foreign Key
    T encode(SerializableModel serializableModel);
    // Encode With Specific Keys Only
}

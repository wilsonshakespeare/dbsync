package com.research.dbsync.serializer;

import java.util.Map;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public interface IDecoder<T> {
    // Decoding Match Foreign to Local
    void decode(T source, SerializableModel target);
}

package com.research.dbsync.handler.list.process;

import com.research.dbsync.model.ModelBase;
import com.research.dbsync.serializer.SerializableModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 5/6/17.
 */
public class ListProcessFactory {
    private static ListProcessFactory ourInstance = new ListProcessFactory();

    public static ListProcessFactory getInstance() {
        return ourInstance;
    }

    private Map<Class<? extends SerializableModel>, DBListProcess> dbListProcessMap;

    private ListProcessFactory() {
        dbListProcessMap = new HashMap<>();
    }

    public <T extends SerializableModel> DBListProcess<T> getDBListProcess(Class<T> serializableModelClass){
        if(dbListProcessMap.containsKey(serializableModelClass)){
            return dbListProcessMap.get(serializableModelClass);
        }else{
            DBListProcess<T> dbListProcess = new DBListProcess<>(serializableModelClass);
            dbListProcessMap.put(serializableModelClass, dbListProcess);
            return dbListProcess;
        }
    }
}

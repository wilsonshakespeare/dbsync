package com.research.dbsync.handler.list;

import com.research.dbsync.handler.list.process.DBListProcess;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.serializer.SerializableModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 5/6/17.
 */
public class ListHandlerFactory {
    private static ListHandlerFactory ourInstance = new ListHandlerFactory();

    public static ListHandlerFactory getInstance() {
        return ourInstance;
    }

    private Map<Class<? extends ModelBase>, ListHandler> listHandlerMap;

    private ListHandlerFactory() {
        listHandlerMap = new HashMap<>();
    }

    public boolean containHandler(Class<? extends ModelBase> modelClass){
        return listHandlerMap.containsKey(modelClass);
    }

    public <T extends ModelBase> ListHandler<T> getListHandler(Class<T> modelClass){
        if(listHandlerMap.containsKey(modelClass)){
            return listHandlerMap.get(modelClass);
        }else{
            ListHandler<T> listHandler;
            if(SerializableModel.class.isAssignableFrom(modelClass)){
                // Note: assignable checked
                listHandler = new SerializedModelListHandler(modelClass);
            }else{
                listHandler = new ListHandler<>(modelClass);
            }
            listHandlerMap.put(modelClass, listHandler);
            return listHandler;
        }
    }
}

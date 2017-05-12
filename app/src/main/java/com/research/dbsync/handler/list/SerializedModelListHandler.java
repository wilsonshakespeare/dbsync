package com.research.dbsync.handler.list;

import android.util.Log;

import com.research.dbsync.core.Trace;
import com.research.dbsync.handler.list.process.ListProcessModel;
import com.research.dbsync.handler.list.process.ListSourceModel;
import com.research.dbsync.serializer.SerializableModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iFreedom87 on 5/9/17.
 */

public class SerializedModelListHandler<T extends SerializableModel> extends ListHandler<T>{
    private Map<Long, T> modelHashMap;

    public SerializedModelListHandler(Class<T> modelClass) {
        super(modelClass);
        modelHashMap = new HashMap<>();
    }

    @Override
    public void item_add_success(ListProcessModel<T> processModel) {
        T model = processModel.getModel();
        modelHashMap.put(model.getId(), model);
        super.item_add_success(processModel);
    }

    @Override
    public void item_delete_success(ListProcessModel<T> processModel) {
        SerializableModel model = processModel.getModel();
        modelHashMap.remove(model.getId());
        super.item_delete_success(processModel);
    }

    @Override
    public void items_loaded(ListSourceModel<T> processedSource) {
        super.items_loaded(processedSource);
        List<T> models = this.getItems();
        for(int i = 0; i < models.size(); i++){
            modelHashMap.put(models.get(i).getId(), models.get(i));
        }
    }

    public int getItemIndex(long id){
        return getItemIndex(getModelByID(id));
    }

    public T getModelByID(long id){
        return modelHashMap.get(id);
    }
}

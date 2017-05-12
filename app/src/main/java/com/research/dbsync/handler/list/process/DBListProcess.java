package com.research.dbsync.handler.list.process;

import com.research.dbsync.core.Trace;
import com.research.dbsync.database.GSysDB;
import com.research.dbsync.project.model.UserModel;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.util.ListUtils;

import java.util.List;

/**
 * Created by iFreedom87 on 4/25/17.
 */

public class DBListProcess<T extends SerializableModel> implements IListSourceable<T>{
    // Note: DBListProcess only handles SerializableModel
    private Class<T> modelClass;

    public DBListProcess(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public void item_add(ListProcessModel<T> processModel) {
        GSysDB.getInstance().insert(processModel.getModel());
        item_add_success(processModel);
    }

    @Override
    public void item_add_success(ListProcessModel<T> processModel) {
        processModel.complete_process();
        processModel.getHandler().item_add_success(processModel);
    }

    @Override
    public void item_update(ListProcessModel<T> processModel) {
        GSysDB.getInstance().update(processModel.getModel());
        item_update_sucess(processModel);
    }

    @Override
    public void item_update_sucess(ListProcessModel<T> processModel) {
        processModel.complete_process();
        processModel.getHandler().item_update_sucess(processModel);
    }

    @Override
    public void item_delete(ListProcessModel<T> processModel) {
        GSysDB.getInstance().delete(processModel.getModel().getId(), processModel.getModel().getClass());
        item_delete_success(processModel);
    }

    @Override
    public void item_delete_success(ListProcessModel<T> processModel) {
        processModel.complete_process();
        processModel.getHandler().item_delete_success(processModel);
    }

    @Override
    public void items_load(ListSourceModel<T> listProcessModel) {
        List<T> list = GSysDB.getInstance().rows_get(modelClass);
        ListUtils.list_clone(list, listProcessModel.getModels());
        items_loaded(listProcessModel);
    }

    @Override
    public void items_loaded(ListSourceModel<T> listProcessModel) {
        listProcessModel.getHandler().items_loaded(listProcessModel);
    }

    @Override
    public Class<T> getModelClass() {
        return this.modelClass;
    }

    @Override
    public List<T> getItems() {
        return null;
    }
}

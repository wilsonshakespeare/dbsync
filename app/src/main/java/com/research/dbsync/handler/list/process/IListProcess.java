package com.research.dbsync.handler.list.process;

import com.research.dbsync.model.ModelBase;

import java.util.List;

/**
 * Created by iFreedom87 on 4/25/17.
 */

public interface IListProcess<T extends ModelBase> {
    //Note: Some Processes Are AsyncTasked or waiting response example HTTP Response
    //Hence the add and success model on ListHandler is Necessary, and ListProcessModel as Visitor object is required
    //Should be Singleton?
    void item_add(ListProcessModel<T> processModel);
    void item_add_success(ListProcessModel<T> processModel);
    void item_update(ListProcessModel<T> processModel);
    void item_update_sucess(ListProcessModel<T> processModel);
    void item_delete(ListProcessModel<T> processModel);
    void item_delete_success(ListProcessModel<T> processModel);
    Class<T> getModelClass();
    List<T> getItems();
}

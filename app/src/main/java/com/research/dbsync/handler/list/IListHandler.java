package com.research.dbsync.handler.list;

import com.research.dbsync.view.component.adapter.IDataSetNotifiable;
import com.research.dbsync.handler.list.process.ListProcessModel;
import com.research.dbsync.handler.list.process.ListSourceModel;
import com.research.dbsync.model.ModelBase;

import java.util.List;

/**
 * Created by iFreedom87 on 4/25/17.
 */

public interface IListHandler<T extends ModelBase> {
    //TODO: Adding List of Items

    // List Adding and Removing Processes
    void item_add(T item);
    void item_add(int index, T item);
    void item_add_success(ListProcessModel<T> processModel);
    void item_update(T item);
    void item_update_sucess(ListProcessModel<T> processModel);
    void item_delete(T item);
    void item_delete(int index);
    void item_delete_success(ListProcessModel<T> processModel);

    // List Sourcing Processes
    void items_load();
    void items_loaded(ListSourceModel<T> processedSource);

    // Adapter Updates View
    ListHandler<T> setDataSetNotifiable(IDataSetNotifiable notifiable);

    // Getters
    Class<T> getModelClass();
    List<T> getItems();

    // List Adapter Standard
    int getItemIndex(T model);
    T getItem(int i);
    long getItemId(int i);
    int getCount();
}

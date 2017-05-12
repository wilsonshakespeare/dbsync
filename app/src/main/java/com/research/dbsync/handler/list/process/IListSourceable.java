package com.research.dbsync.handler.list.process;

import com.research.dbsync.handler.list.IListHandler;
import com.research.dbsync.model.ModelBase;

/**
 * Created by iFreedom87 on 4/29/17.
 */

public interface IListSourceable<T extends ModelBase> extends IListProcess<T> {
    void items_load(ListSourceModel<T> listProcessModel);
    void items_loaded(ListSourceModel<T> listProcessModel);
}

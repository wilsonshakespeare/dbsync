package com.research.dbsync.handler.list;

import com.research.dbsync.model.ModelBase;

/**
 * Created by iFreedom87 on 5/9/17.
 */

public interface IListHandlable<T extends ModelBase> {
    ListHandler<T> getListHandler();
}

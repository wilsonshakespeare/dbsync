package com.research.dbsync.handler.list.process;

import com.research.dbsync.core.Disposable;
import com.research.dbsync.handler.list.IListHandler;
import com.research.dbsync.model.ModelBase;

import java.util.List;

/**
 * Created by iFreedom87 on 4/29/17.
 */

public class ListSourceModel<T extends ModelBase> extends Disposable {
    private IListHandler handler;
    private List<T> models;

    public ListSourceModel(List<T> models, IListHandler<T> handler){
        this.models = models;
        this.handler = handler;
    }

    public IListHandler getHandler() {
        return handler;
    }

    public List<T> getModels(){
        return this.models;
    }

    @Override
    public void dispose() {
        if(isDisposed())
            return;

        super.dispose();
        this.models = null;
        this.handler = null;
    }
}

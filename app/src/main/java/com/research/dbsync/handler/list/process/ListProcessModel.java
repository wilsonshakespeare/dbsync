package com.research.dbsync.handler.list.process;

import com.research.dbsync.core.Disposable;
import com.research.dbsync.handler.list.IListHandler;
import com.research.dbsync.model.ModelBase;

import java.util.List;

/**
 * Created by iFreedom87 on 4/25/17.
 */

public class ListProcessModel<T extends ModelBase> extends Disposable {
    private List<T> models;
    private T model;
    private int indexAt;
    private int completeCount;
    private IListHandler handler;

    public ListProcessModel(T model, IListHandler<T> handler){
        this.model = model;
        this.handler = handler;
        completeCount = 0;
        indexAt = -1;
    }

    public ListProcessModel(List<T> models, IListHandler<T> handler){
        this.models = models;
        this.handler = handler;
        completeCount = 0;
        indexAt = -1;
    }

    public int getIndexAt() {
        return indexAt;
    }

    public void setIndexAt(int indexAt) {
        this.indexAt = indexAt;
    }

    public void complete_process(){
        completeCount++;
    }

    public IListHandler getHandler() {
        return handler;
    }

    public int getCompleteCount() {
        return completeCount;
    }

    public T getModel(){
        return model;
    }

    public List<T> getModels() {
        return models;
    }

    @Override
    public void dispose() {
        if(isDisposed())
            return;

        super.dispose();
        this.model = null;
        this.models = null;
        this.handler = null;
    }
}

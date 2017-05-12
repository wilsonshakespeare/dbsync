package com.research.dbsync.view.component.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;

import com.research.dbsync.core.IDisposable;
import com.research.dbsync.handler.list.IListHandler;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.view.component.adapter.IDataSetNotifiable;
import com.research.dbsync.view.component.adapter.IViewItemListenersBinder;
import com.research.dbsync.view.component.inflater.ModelItemInflater;

/**
 * Created by iFreedom87 on 4/29/17.
 */

public abstract class GSysAdapter<V extends GSysViewHolder, M extends ModelBase> extends RecyclerView.Adapter<V> implements IDataSetNotifiable, IDisposable {
    protected IListHandler<M> listHandler = null;
    protected ModelItemInflater itemInflater = null;
    protected IViewItemListenersBinder listenersBinder = null; // In Event Handling Framework, the listener is being added upon build

    public GSysAdapter(IListHandler<M> listHandler, ModelItemInflater itemInflater) {
        this.listHandler = listHandler;
        this.listHandler.setDataSetNotifiable(this);
        this.itemInflater = itemInflater;
    }

    public void setListenerBinder(IViewItemListenersBinder listenersBinder){
        this.listenersBinder = listenersBinder;
    }

    public void add(M model) {
        listHandler.item_add(model);
    }

    public void add(int i, M model) {
        listHandler.item_add(i, model);
    }

    public void remove(int i) {
        listHandler.item_delete(i);
    }

    public void remove(M model) {
        listHandler.item_delete(model);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        if(listenersBinder == null)
            return;
        listenersBinder.listeners_setup(holder.itemView, this.listHandler.getItem(position));
    }

    @Override
    public int getItemCount() {
        return listHandler.getCount();
    }

    public M getItem(int i) {
        return listHandler.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return listHandler.getItemId(i);
    }

    private boolean _isDisposed = false;
    @Override
    public void dispose() {
        if(isDisposed())
            return;
        _isDisposed = true;

        this.listHandler.setDataSetNotifiable(null);
        this.listHandler = null;
        this.itemInflater = null;
        this.listenersBinder = null;
    }

    @Override
    public boolean isDisposed() {
        return _isDisposed;
    }
}

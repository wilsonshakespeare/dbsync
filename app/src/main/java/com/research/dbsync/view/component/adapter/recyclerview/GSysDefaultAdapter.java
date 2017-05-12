package com.research.dbsync.view.component.adapter.recyclerview;

import android.view.ViewGroup;

import com.research.dbsync.handler.list.IListHandler;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.view.component.inflater.ModelItemInflater;

/**
 * Created by iFreedom87 on 5/6/17.
 */

public class GSysDefaultAdapter<M extends ModelBase> extends GSysAdapter<GSysViewHolder, M> {

    public GSysDefaultAdapter(IListHandler<M> listHandler, ModelItemInflater itemInflater) {
        super(listHandler, itemInflater);
    }

    @Override
    public GSysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GSysViewHolder(itemInflater.inflate(parent));
    }

    @Override
    public void onBindViewHolder(GSysViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        itemInflater.bind(holder.itemView, listHandler.getItem(position));
    }
}

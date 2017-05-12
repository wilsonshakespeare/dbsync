package com.research.dbsync.view.component.adapter;

import android.view.View;

import com.research.dbsync.model.ModelBase;

/**
 * Created by iFreedom87 on 5/3/17.
 */

public interface IViewItemListenersBinder {
    void listeners_setup(View view, ModelBase modelBase); // Note: Sometimes Requires Model to Make Listener Decisions
}

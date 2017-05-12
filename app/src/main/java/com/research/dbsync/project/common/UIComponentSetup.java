package com.research.dbsync.project.common;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by iFreedom87 on 5/6/17.
 */

public class UIComponentSetup {
    public static RecyclerView getCommonRecyclerView(Activity activity, @Nullable View rootView, RecyclerView.Adapter adapter, int idRes){
        RecyclerView recyclerView = null;
        if(rootView != null){
            recyclerView = (RecyclerView) rootView.findViewById(idRes);
        }else{
            recyclerView = (RecyclerView) activity.findViewById(idRes);
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}

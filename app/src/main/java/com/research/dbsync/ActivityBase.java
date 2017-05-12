package com.research.dbsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.research.dbsync.core.Trace;
import com.research.dbsync.model.ModelBase;
//TODO: Interface to bridge normal adapter and recycler view adapter
import com.research.dbsync.project.screen.user.UserFormActivity;
import com.research.dbsync.project.screen.user.UserListActivity;
import com.research.dbsync.util.MapUtils;
import com.research.dbsync.view.component.adapter.recyclerview.GSysAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 4/24/17.
 */

public class ActivityBase extends AppCompatActivity {
    private static boolean _isInit = false;
    private Map<Class<? extends ModelBase>, GSysAdapter>  gSysAdapterMap = null;
    private Toolbar activityToolbar;
    public ActionBar activityActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gSysAdapterMap = new HashMap<>();

        if(!_isInit){
            MainHandler.getInstance().activity_first_on_create(this);
            _isInit = true;
        }
    }

    public void setActivityToolbar(Toolbar activityToolbar){
        this.activityToolbar = activityToolbar;

        setSupportActionBar(activityToolbar);
        activityActionBar = getSupportActionBar();
        activityActionBar.setTitle("");
        activityActionBar.setIcon(null);

    }

    public void adapter_add(Class<? extends ModelBase> modelBaseClass, GSysAdapter adapter){
        gSysAdapterMap.put(modelBaseClass, adapter);
    }

    public GSysAdapter adapter_get(Class<? extends ModelBase> modelBaseClass){
        return gSysAdapterMap.get(modelBaseClass);
    }

    public Activity getActivity(){
        // reason of this is for reference from inner class
        return this;
    }

    protected void onDestroy(){
        super.onDestroy();
        MapUtils.map_delete(gSysAdapterMap, false, true);
        gSysAdapterMap = null;
    }
}

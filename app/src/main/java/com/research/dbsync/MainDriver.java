package com.research.dbsync;

import android.app.Application;
import android.util.Log;

import com.research.dbsync.database.GSysDB;

/**
 * Created by iFreedom87 on 4/18/17.
 */

public class MainDriver extends Application {
    private static MainDriver _INSTANCE;
    GSysDB database;

    public MainDriver() {
        super();
        _INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Architecture", "MainDriver: onCreate");
        database = GSysDB.getInstance(this, "dbsync_db", 9); // Instantiate DataBase
        MainHandler.getInstance().driver_on_create();
    }

    public static MainDriver getInstance(){
        return _INSTANCE;
    }
}

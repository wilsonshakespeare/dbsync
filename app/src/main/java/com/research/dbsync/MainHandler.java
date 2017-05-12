package com.research.dbsync;

import android.content.Context;

import com.research.dbsync.database.GSysDB;
import com.research.dbsync.database.SQLiteTableModel;
import com.research.dbsync.handler.list.ListHandlerFactory;
import com.research.dbsync.project.model.TaskModel;
import com.research.dbsync.project.model.UserModel;

/**
 * Created by iFreedom87 on 5/6/17.
 */
public class MainHandler {
    private static MainHandler ourInstance = new MainHandler();

    public static MainHandler getInstance() {
        return ourInstance;
    }

    private MainHandler() {
    }

    public void driver_on_create(){
        database_tables_init();
    }

    public void activity_first_on_create(Context context){
        //Note: Use to Initiate Content Providers, Example, Contacts
    }

    public void database_tables_init(){
        GSysDB.getInstance().table_add(new SQLiteTableModel("tasks", TaskModel.class));
        GSysDB.getInstance().table_add(new SQLiteTableModel("users", UserModel.class));
        GSysDB.getInstance().init();

        TaskModel.LIST_HANDLER.items_load();
        UserModel.LIST_HANDLER.items_load();
    }
}

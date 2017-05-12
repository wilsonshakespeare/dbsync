package com.research.dbsync.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.research.dbsync.core.Trace;
import com.research.dbsync.project.model.TaskModel;
import com.research.dbsync.project.model.UserModel;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.SerializationInfo;
import com.research.dbsync.serializer.SerializerAdapter;
import com.research.dbsync.serializer.sqlite.SQLiteEncoder;
import com.research.dbsync.util.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iFreedom87 on 4/18/17.
 */

public class GSysDB extends SQLiteOpenHelper {
    private static GSysDB _INSTANCE = null;
    private List<SQLiteTableModel> tableModels;
    private Map<String, SQLiteTableModel> tableModelMap;
    private Map<Class<? extends SerializableModel>, SQLiteTableModel> rowModelMap;

    private Context context;

    private GSysDB(Context context, String databaseName, int version){
        super(context, databaseName, null, version);
        this.context = context;
        this.tableModelMap = new HashMap<>();
        this.rowModelMap = new HashMap<>();
        this.tableModels = new ArrayList<>();
    }

    public void table_add(SQLiteTableModel tableModel){
        tableModels.add(tableModel);
        tableModelMap.put(tableModel.getTableName(), tableModel);
        rowModelMap.put(tableModel.getRowModelType(), tableModel);
    }

    public void init(){
        for(int i = 0; i < tableModels.size(); i++){
            tableModels.get(i).init();
        }
    }

    public boolean table_contain(Class<? extends SerializableModel> classType){
        return rowModelMap.containsKey(classType);
    }

    public SQLiteTableModel table_get(Class<? extends SerializableModel> classType){
        return rowModelMap.get(classType);
    }

    public boolean table_contain(String tableName){
        return tableModelMap.containsKey(tableName);
    }

    public SQLiteTableModel table_get(String tableName){
        return tableModelMap.get(tableName);
    }

    //TODO: insertOnConflict
    public long insert(SerializableModel model)
    {
        return insert(model, null);
    }

    public long insert(SerializableModel model, String nullColumnHack){
        SQLiteDatabase dbb = getWritableDatabase();
        ContentValues contentValues = SQLiteEncoder.getInstance().encode(model);
        long id = dbb.insert(rowModelMap.get(model.getClass()).getTableName(), nullColumnHack , contentValues);
        return id;
    }

    public boolean delete(long id, Class<? extends SerializableModel> serializableClass){
        int count = 0;
        if(table_contain(serializableClass)){
            SQLiteDatabase db = getWritableDatabase();
            String[] whereArgs ={ String.valueOf(id) };
            count = db.delete(table_get(serializableClass).getTableName(), "id = ?", whereArgs);
        }

        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    public <T extends SerializableModel> void update(T model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = SQLiteEncoder.getInstance().encode(model);
        if(model instanceof TaskModel)
            Log.d("DBTest", contentValues.get("assigned_user") +  " : id : " + model.getId());
        db.update(rowModelMap.get(model.getClass()).getTableName(), contentValues, "id = ?", new String[]{String.valueOf(model.getId())});
    }

    //TODO: List Update Query
    public <T extends SerializableModel> void update(List<T> models){
        /* Query For Mass Update For Faster Processing
        for(int i = 0; i < models.size(); i++){
            update(models.get(i));
        }
        */
    }

    //TODO: Where Clause Generator, Only apply for primitive data
    // Based On Primary ID Retrieve the Row
    public <T extends SerializableModel> T row_get(Class<T> serializableModelClass, long id){
        List<T> list = rows_get(serializableModelClass, new Long[]{id});
        if(list.size() == 0){
            return null;
        }else{
            return list.get(0);
        }
    }

    public <T extends SerializableModel> List<T> rows_get(Class<T> serializableModelClass, Long ids[]){
        SQLiteDatabase db = getWritableDatabase();
        String tableName = rowModelMap.get(serializableModelClass).getTableName();
        SerializationInfo info = SerializerAdapter.getInstance().serializationInfo_get(serializableModelClass);
        String[] columns = info.getKeys();

        String whereClause = "id = ?";
        for(int i = 1; i < ids.length; i++){
            whereClause += " OR id = ?";
        }

        String[] whereArgs = ArrayUtils.toStringArray(ids);
        String orderBy = "id";

        Cursor cursor = db.query(tableName, columns, whereClause, whereArgs, null, null, orderBy);
        List<T> list = SQLiteEncoder.getInstance().decode(cursor, serializableModelClass);

        return list;
    }

    public <T extends SerializableModel> List<T> rows_get(Class<T> serializableModelClass)
    {
        SQLiteDatabase db = getWritableDatabase();

        String tableName = rowModelMap.get(serializableModelClass).getTableName();
        SerializationInfo info = SerializerAdapter.getInstance().serializationInfo_get(serializableModelClass);
        String[] columns = info.getKeys();

        // Generate Where Clause
        Cursor cursor = db.query(tableName, columns, null, null, null, null, null);
        List<T> list = SQLiteEncoder.getInstance().decode(cursor, serializableModelClass);

        Log.d("TestSQL: Decode", "Size: " + list.size());
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < tableModels.size(); i++){
            try {
                Trace.d("GSysDB", "query: " + tableModels.get(i).queryString_createTable());
                db.execSQL(tableModels.get(i).queryString_createTable());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = 0; i < tableModels.size(); i++){
            try {
                //TODO: Drop Table If Exist -> Upgrade Converter Proxy Design
                db.execSQL("DROP TABLE IF EXISTS '" + tableModels.get(i).getTableName() +"'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        onCreate(db);
    }

    public static GSysDB getInstance(Context context, String databaseName, int version){
        if(_INSTANCE == null){
            _INSTANCE = new GSysDB(context, databaseName, version);
        }
        return _INSTANCE;
    }

    public static GSysDB getInstance(){
        return _INSTANCE;
    }

    /*  For Reference
        String[] tableColumns = new String[] {
            "column1",
            "(SELECT max(column1) FROM table2) AS max"
        };
        String whereClause = "column1 = ? OR column1 = ?";
        String[] whereArgs = new String[] {
            "value1",
            "value2"
        };
    */
}

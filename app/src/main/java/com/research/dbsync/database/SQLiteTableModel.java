package com.research.dbsync.database;

import com.research.dbsync.core.Trace;
import com.research.dbsync.serializer.ISetter;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.SerializationInfo;
import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;
import com.research.dbsync.serializer.sqlite.ISQLiteCustomSetter;
import com.research.dbsync.serializer.sqlite.SQLiteSetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iFreedom87 on 4/19/17.
 */

public class SQLiteTableModel {
    //TODO: Template required for primary key
    // Current Design All Table will Require ID Auto Increment: Fixed Primary Key
    private String tableName;
    private Class<? extends SerializableModel> rowModelType;
    private Map<String, SQLiteColumn> columnMap;
    private List<SQLiteColumn> columns; // So the column array is in order
    private List<String> columnNames;

    // Primary Field
    private SQLiteColumn primaryField;
    private boolean isAutoIncrement = true;

    public SQLiteTableModel(String tableName, Class<? extends SerializableModel> rowModelType) {
        this.tableName = tableName;
        this.rowModelType = rowModelType;
        this.columnMap = new HashMap<>();
        this.columns = new ArrayList<>();
        this.columnNames = new ArrayList<>();
        this.primaryField = new SQLiteColumn("id", SQLiteColumn.INTEGER); // Default
    }

    public void init(){
        try{
            columns_setup(rowModelType.newInstance().getSerializationInfo());
        }catch (Exception exp){
            Trace.e("SQLTable: Fields", "Setup Failure", exp);
        }
    }

    public void columns_setup(SerializationInfo serializationInfo){
        String[] keys = serializationInfo.getKeys();

        for(int i = 0; i < keys.length; i++){
            if(primaryField.getName() == keys[i]){
                continue;
            }

            if(serializationInfo.containFormat(keys[i])){
                column_add(
                        new SQLiteColumn(
                                keys[i], SQLiteColumn.type_match(serializationInfo.getFormat(keys[i]))
                        )
                );
            }else if(serializationInfo.containObject(keys[i])){
                boolean useJson = true;
                if(GSysDB.getInstance() != null){
                    if(GSysDB.getInstance().table_contain(serializationInfo.getObjectClass(keys[i]))){
                        //Important Note: This is the case object is in Relation Diagram to another table, store
                        column_add(new SQLiteColumn(keys[i], SQLiteColumn.INTEGER));
                        useJson = false;
                    }
                }
                if(useJson){
                    column_add(new SQLiteColumn(keys[i], SQLiteColumn.TEXT)); // Store The ID For The Table
                }
            }else if(serializationInfo.containArray(keys[i])){
                // USE JSON to Convert to Array
                column_add(new SQLiteColumn(keys[i], SQLiteColumn.TEXT));
            }else if(serializationInfo.containList(keys[i])){
                // USE JSON to Convert to Array: Note Can Be Array of IDs, It can also be in JSONObject Array Format
                column_add(new SQLiteColumn(keys[i], SQLiteColumn.TEXT)); // Store The ID For The Table
            }else if(serializationInfo.containCustom(keys[i])){
                String customKey = serializationInfo.getCustomKey(keys[i]);
                if(SQLiteSetter.getInstance().containGetterCustomKey(customKey)){
                    ICustomGetter setter = SQLiteSetter.getInstance().getCustomGetter(customKey);
                    if(setter instanceof ISQLiteCustomSetter){
                        String type = ((ISQLiteCustomSetter) setter).getSQLColumnType();
                        column_add(new SQLiteColumn(keys[i], type));
                    }
                }

            }
            // Custom One:
            //
        }
    }

    public String queryString_createTable(){
        if(columns.size() == 0)
            return "";

        String query = "CREATE TABLE " + tableName + " (" + primaryField.getName() + " " + primaryField.getDataString() + " PRIMARY KEY " + ((isAutoIncrement) ? "AUTOINCREMENT " : "") + primaryField.getConstraintString();
        for(int i = 0; i < columns.size(); i++){
            query += ", " + columns.get(i).getCreateTableFieldString();
        }

        query += ")";
        return query;
    }

    /*/
    Not Ready For Customisable Primary Key Because Now SerializableModel uses ID as Primary,
    Requires Model Ownership to Expand Such Capability: But No Developer Ready for Such Foreign Design Concept Yet
    /*/

    /*/
    public void setPrimaryKey(SQLiteColumn field, boolean isAutoIncrement){
        this.primaryField.dispose();
        this.primaryField = field;
        if(isAutoIncrement && field.getDataString() == SQLiteColumn.INTEGER){
            this.isAutoIncrement = true;
        }else{
            this.isAutoIncrement = false;
        }
    }
    /*/

    public void column_add(SQLiteColumn column){
        columns.add(column);
        columnNames.add(column.getName());
        columnMap.put(column.getName(), column);
        //fields.put(fieldName, dataType);
    }

    public SQLiteColumn column_get(String columnName){
        return columnMap.get(columnName);
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public Class<? extends SerializableModel> getRowModelType() {
        return rowModelType;
    }

    // validate update, insert and delete field
    //public

}

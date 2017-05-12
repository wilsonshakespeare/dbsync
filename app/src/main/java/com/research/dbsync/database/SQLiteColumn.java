package com.research.dbsync.database;

import com.research.dbsync.core.Disposable;
import com.research.dbsync.serializer.DataFormat;

/**
 * Created by iFreedom87 on 4/19/17.
 */

public class SQLiteColumn extends Disposable{
    // Important Note: Don't Support None Type
    // TODO: Use StringDef

    public static final String INTEGER = "INTEGER";
    public static final String REAL = "REAL";
    public static final String TEXT = "TEXT";
    public static final String BLOB = "BLOB";

    private String name;
    private String dataString;

    // CONSTRAINTS
    private boolean isNotNull;
    private boolean isUnique;

    // TODO: Will Have Constraint Model CHECK, FOREIGN KEY, DEFAULT
    private String additionalConstrain = "";

    public SQLiteColumn(String name, String dataString){
        this.name = name;
        this.dataString = dataString;
    }

    public String getName() {
        return name;
    }

    public SQLiteColumn setName(String name) {
        this.name = name;
        return this;
    }

    public String getDataString() {
        return dataString;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public SQLiteColumn setNotNull(boolean notNull) {
        isNotNull = notNull;
        return this;
    }

    public String getAdditionalContraint(){
        return this.additionalConstrain;
    }

    public SQLiteColumn setAdditionalConstraint(String additionalConstrain){
        this.additionalConstrain = additionalConstrain;
        return this;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public SQLiteColumn setUnique(boolean unique) {
        isUnique = unique;
        return this;
    }

    public String getCreateTableFieldString(){
        return name + " " + dataString + getConstraintString() + additionalConstrain;
    }

    public String getConstraintString(){
        return ((isNotNull) ? " NOT NULL": "") + ((isUnique) ? " UNIQUE": "");
    }

    public static String type_match(int format){
        if(format == (format & (DataFormat.INT | DataFormat.LONG | DataFormat.SHORT | DataFormat.BYTE | DataFormat.BOOLEAN))){
            return INTEGER;
        }else if(format == DataFormat.STRING | format == DataFormat.CHAR){
            return TEXT;
        }else if(format == (format & (DataFormat.FLOAT | DataFormat.DOUBLE))){
            return REAL;
        }
        return null;
    }

    @Override
    public void dispose() {
        if(isDisposed())
            return;

        super.dispose();
        name = null;
        dataString = null;
    }
}

package com.research.dbsync.serializer.custom.date;

import android.content.ContentValues;
import android.database.Cursor;

import com.research.dbsync.core.Trace;
import com.research.dbsync.database.SQLiteColumn;
import com.research.dbsync.serializer.custom.CustomInitializer;
import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;
import com.research.dbsync.serializer.json.JSONSetter;
import com.research.dbsync.serializer.pojo.PojoSetter;
import com.research.dbsync.serializer.sqlite.ISQLiteCustomSetter;
import com.research.dbsync.serializer.sqlite.SQLiteSetter;
import com.research.dbsync.util.ReflectionUtils;

import org.json.JSONObject;

import java.util.Date;

import javadz.beanutils.BeanUtils;

/**
 * Created by iFreedom87 on 5/6/17.
 */

public class DateSetter extends CustomInitializer{
    public static final int FORMAT_TIMESTAMP = 1;
    private int format;

    public DateSetter(int format) {
        super(Date.class);
        this.format = format;
    }

    @Override
    protected void init_format() {
        JSONDateSetter jsonDateSetter = new JSONDateSetter();
        addCustomGetter(JSONSetter.getInstance(), jsonDateSetter);
        addCustomSetter(JSONSetter.getInstance(), jsonDateSetter);

        PojoDateSetter pojoDateSetter = new PojoDateSetter();
        addCustomGetter(PojoSetter.getInstance(), pojoDateSetter);
        addCustomSetter(PojoSetter.getInstance(), pojoDateSetter);

        SQLiteDateSetter sqLiteDateSetter = new SQLiteDateSetter();
        addCustomGetter(SQLiteSetter.getInstance(), sqLiteDateSetter);
        addCustomSetter(SQLiteSetter.getInstance(), sqLiteDateSetter);
    }

    // Base on Format will determine the storage or encoded string, for time being only timestamp is supported
    private class JSONDateSetter implements ICustomGetter<JSONObject, Date>, ICustomSetter<JSONObject, Date>{
        @Override
        public Date getCustomObject(JSONObject source, String sourceKey) {
            try{
                long time = source.getLong(sourceKey);
                return new Date(time);
            }catch (Exception exp){
                Trace.e("JSONDateSetter", "Get Custom Value Error: Object: " + source + " key value:" + sourceKey, exp);
            }

            return null;
        }

        @Override
        public void setCustomObject(JSONObject source, String sourceKey, Date data) {
            try{
                long time = data.getTime();
                source.put(sourceKey, time);
            }catch (Exception exp){
                Trace.e("JSONDateSetter", "Set Custom Value Error: Object: " + source + " key value:" + sourceKey, exp);
            }
        }
    }

    private class SQLiteDateSetter implements ISQLiteCustomSetter<Date>{
        @Override
        public Date getCustomObject(Cursor source, String sourceKey) {
            long timestamp = source.getLong(source.getColumnIndex(sourceKey));
            return new Date(timestamp);
        }

        @Override
        public void setCustomObject(ContentValues source, String sourceKey, Date data) {
            source.put(sourceKey, data.getTime());
        }

        @Override
        public String getSQLColumnType() {
            return SQLiteColumn.INTEGER;
        }
    }

    private class PojoDateSetter implements ICustomGetter<Object, Date>, ICustomSetter<Object, Date>{
        @Override
        public Date getCustomObject(Object source, String sourceKey) {
            try{
                return (Date) ReflectionUtils.getPropertyValue(source, sourceKey);
            }catch (Exception exp){
                Trace.e("PojoDateSetter", "Get Custom Value Error: Object: " + source + " key value:" + sourceKey, exp);
            }
            return null;
        }

        @Override
        public void setCustomObject(Object source, String sourceKey, Date data) {
            try{
                BeanUtils.setProperty(source, sourceKey, data);
            }catch (Exception exp){
                Trace.e("PojoDateSetter", "Set Value Error: Object: " + source + " key value:" + sourceKey, exp);
            }
        }
    }

    public int getDateFormat(){
        return format;
    }
}

package com.research.dbsync.serializer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by iFreedom87 on 11/12/16.
 */

//TODO: Enum Change to IntDef because of inefficiency of Enum Handling
public class DataFormat {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BOOLEAN, STRING, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE})
    public @interface Formats {}
    // For Bitwise Operation Purpose
    public static final int BOOLEAN = 1;
    public static final int STRING = 2;
    public static final int BYTE = 4;
    public static final int SHORT = 8;
    public static final int INT = 16;
    public static final int LONG = 32;
    public static final int FLOAT = 64;
    public static final int DOUBLE = 128;
    public static final int CHAR = 256;


    public static void value_set(int dataType, Object source, String sourceKey , IGetter gettable, Object target, String targetKey, ISetter settable){
        if(dataType == BOOLEAN){
            settable.setBoolean(target, targetKey, gettable.getBoolean(source, sourceKey));
        }else if(dataType == STRING){
            settable.setString(target, targetKey, gettable.getString(source, sourceKey));
        }else if(dataType == BYTE){
            settable.setByte(target, targetKey, gettable.getByte(source, sourceKey));
        }else if(dataType == SHORT){
            settable.setShort(target, targetKey, gettable.getShort(source, sourceKey));
        }else if(dataType == INT){
            settable.setInt(target, targetKey, gettable.getInt(source, sourceKey));
        }else if(dataType == LONG){
            settable.setLong(target, targetKey, gettable.getLong(source, sourceKey));
        }else if(dataType == FLOAT){
            settable.setFloat(target, targetKey, gettable.getFloat(source, sourceKey));
        }else if(dataType == DOUBLE){
            settable.setDouble(target, targetKey, gettable.getDouble(source, sourceKey));
        }else if(dataType == CHAR){
            settable.setDouble(target, targetKey, gettable.getDouble(source, sourceKey));
        }
    }

    public static Object value_get(int dataType, Object source, String sourceKey, IGetter gettable){
        if(dataType == BOOLEAN){
            return gettable.getBoolean(source, sourceKey);
        }else if(dataType == STRING){
            return gettable.getString(source, sourceKey);
        }else if(dataType == BYTE){
            return gettable.getByte(source, sourceKey);
        }else if(dataType == SHORT){
            return gettable.getShort(source, sourceKey);
        }else if(dataType == INT){
            return gettable.getInt(source, sourceKey);
        }else if(dataType == LONG){
            return gettable.getLong(source, sourceKey);
        }else if(dataType == FLOAT){
            return gettable.getFloat(source, sourceKey);
        }else if(dataType == DOUBLE){
            return gettable.getDouble(source, sourceKey);
        }else if(dataType == CHAR){
            return gettable.getDouble(source, sourceKey);
        }
        return null;
    }

    public static Class getPrimitiveClass(int dataFormat){
        if(dataFormat == BOOLEAN){
            return Integer.class;
        }else if(dataFormat == STRING){
            return String.class;
        }else if(dataFormat == BYTE){
            return Byte.class;
        }else if(dataFormat == SHORT){
            return Short.class;
        }else if(dataFormat == INT){
            return Integer.class;
        }else if(dataFormat == LONG){
            return Long.class;
        }else if(dataFormat == FLOAT){
            return Float.class;
        }else if(dataFormat == DOUBLE){
            return Double.class;
        }else if(dataFormat == CHAR){
            return Character.class;
        }
        return null;
    }

    //public static void
}

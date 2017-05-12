package com.research.dbsync.condition;

import com.research.dbsync.core.Trace;
import com.research.dbsync.serializer.DataFormat;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.SerializationInfo;
import com.research.dbsync.util.ReflectionUtils;

import java.util.List;

/**
 * Created by iFreedom87 on 4/28/17.
 */

public class Condition {
    // Just in Case Require Bitwise Operation
    public static final int NONE = 1; // Meaning No Specific Filter
    public static final int EQUALS = 2;
    public static final int NOT_EQUALS = 4;
    public static final int NUMERIC_LESS = 8;
    public static final int NUMERIC_LESS_EQUALS = 16;
    public static final int NUMERIC_MORE = 32;
    public static final int NUMERIC_MORE_EQUALS = 64;
    public static final int STRING_CONTAIN = 64;

    private String key;
    private int condition;

    public Condition(){
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
    /*
    public boolean isCondition(SerializableModel model, Object value){
        if(this.key == null || this.condition == 0)
            return true;

        if(this.condition == NONE)
            return true;

        SerializationInfo info = model.getSerializationInfo();
        Object source = model.getObjectSource();

        if(info.containFormat(targetKey)){
            int format = info.getFormat(targetKey);
            DataFormat.value_set(
                    format,
                    source, sourceKey, serializableSource.getGettableProxy(),
                    target, targetKey, settableProxy
            );
        }else if(info.containObject(targetKey)){
            try{
                SerializableModel model = serializableSource.getGettableProxy().getObject(source, sourceKey, info.getObjectClass(targetKey));
                settableProxy.setObject(target, targetKey, model);
            }catch (Exception exp){
                Trace.e(this.getClass().toString(), "Set Object Error", exp);
            }
        }else if(info.containArray(targetKey)){
            try{
                Object[] array = serializableSource.getGettableProxy().getArray(source, sourceKey, info.getArrayFormat(targetKey));
                settableProxy.setArray(target, targetKey, array);
            }catch (Exception exp){
                Trace.e(this.getClass().toString(), "Set Array Error", exp);
            }
        }else if(info.containList(targetKey)) {
            try {
                List list = serializableSource.getGettableProxy().getList(source, sourceKey, info.getListClass(targetKey));
                settableProxy.setList(target, targetKey, list);
            } catch (Exception exp) {
                Trace.e(this.getClass().toString(), "Set List Error", exp);
            }
        }

        return true;
    }
    */
}

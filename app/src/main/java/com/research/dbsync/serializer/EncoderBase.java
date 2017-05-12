package com.research.dbsync.serializer;

import com.research.dbsync.core.Trace;

import java.util.List;

/**
 * Created by iFreedom87 on 4/24/17.
 */

public abstract class EncoderBase {
    private ISetter settableProxy;
    private IGetter gettableProxy;

    public EncoderBase(ISetter settableProxy, IGetter gettableProxy) {
        this.settableProxy = settableProxy;
        this.gettableProxy = gettableProxy;
    }

    public void encode(Object target, String targetKey, SerializableModel serializableSource, String sourceKey){
        SerializationInfo info = serializableSource.getSerializationInfo();
        Object source = serializableSource.getObjectSource();

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
        }else if(info.containCustom(targetKey)){
            String customKey = info.getCustomKey(targetKey);
            Object object = serializableSource.getGettableProxy().getCustom(source, sourceKey, customKey, info.getCustomClass(targetKey));
            settableProxy.setCustom(target, targetKey, customKey, object);
        }

    }

    public void decode(SerializableModel serializableTarget, String targetKey, Object source, String sourceKey){
        SerializationInfo info = serializableTarget.getSerializationInfo();
        Object targetObject = serializableTarget.getObjectSource();

        if(info.containFormat(sourceKey)){
            DataFormat.value_set(
                    info.getFormat(sourceKey),
                    source, sourceKey, gettableProxy,
                    targetObject, targetKey, serializableTarget.getSettableProxy()
            );
        }else if(info.containObject(sourceKey)){
            SerializableModel model = gettableProxy.getObject(source, sourceKey, info.getObjectClass(sourceKey));
            serializableTarget.getSettableProxy().setObject(targetObject, targetKey, model);
        }else if(info.containArray(sourceKey)){
            Object[] array = gettableProxy.getArray(source, sourceKey, info.getArrayFormat(sourceKey));
            serializableTarget.getSettableProxy().setArray(targetObject, targetKey, array);
        }else if(info.containList(sourceKey)){
            List list = gettableProxy.getList(source, sourceKey, info.getListClass(sourceKey));
            serializableTarget.getSettableProxy().setList(targetObject, targetKey, list);
        }else if(info.containCustom(sourceKey)){
            String customKey = info.getCustomKey(sourceKey);
            Object object = gettableProxy.getCustom(source, sourceKey, customKey, info.getCustomClass(targetKey));
            serializableTarget.getSettableProxy().setCustom(targetObject, targetKey, customKey, object);
        }
    }

}

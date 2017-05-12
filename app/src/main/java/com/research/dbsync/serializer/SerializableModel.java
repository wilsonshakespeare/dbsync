package com.research.dbsync.serializer;

import com.research.dbsync.core.ILongID;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.serializer.custom.CustomInitializer;
import com.research.dbsync.serializer.pojo.PojoSetter;

/**
 * Created by iFreedom87 on 4/20/17.
 */

public abstract class SerializableModel extends ModelBase implements ISerializableModel, ILongID {
    private Object objectSource;
    protected long id;

    public SerializableModel(){
        super();
        objectSource = this;
        if(SerializerAdapter.getInstance().containInfo(this.getClass())){
            return;
        }
        init();

    }

    private SerializationInfo serializationInfo = null;
    private void init(){
        serializationInfo = new SerializationInfo();
        SerializerAdapter.getInstance().serializationInfo_set(this.getClass(), serializationInfo);
        addFormat("id", "id", DataFormat.LONG);
        format_declare();
    }

    abstract protected void format_declare(); // Hence the format declaration only run once

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public void addList(String key, String localKey, Class<? extends SerializableModel> classType) {
        if(serializationInfo == null)
            return;
        serializationInfo.addList(key, localKey, classType);
    }

    public void addArray(String key, String localKey, @DataFormat.Formats int dataFormat) {
        if(serializationInfo == null)
            return;
        serializationInfo.addArray(key, localKey, dataFormat);
    }

    public void addObject(String key, String localKey, Class<? extends SerializableModel> classType) {
        if(serializationInfo == null)
            return;
        serializationInfo.addObject(key, localKey, classType);
    }

    public void addFormat(String key, String localKey, @DataFormat.Formats int dataFormat) {
        if(serializationInfo == null)
            return;
        serializationInfo.addFormat(key, localKey, dataFormat);
    }

    public void addCustom(String key, String localKey, String customReferenceKey, CustomInitializer initializer) {
        if(serializationInfo == null)
            return;
        serializationInfo.addCustom(key, localKey, customReferenceKey, initializer);
    }

    @Override
    public SerializationInfo getSerializationInfo(){
        return SerializerAdapter.getInstance().serializationInfo_get(this.getClass());
    }

    @Override
    public void setObjectSource(Object objectSource) {
        this.objectSource = objectSource;
    }

    @Override
    public Object getObjectSource() {
        //Note: Unless Is a Wrapper Object
        return objectSource;
    }

    @Override
    public IGetter getGettableProxy() {
        //Note: Unless Is a Wrapper Object
        return PojoSetter.getInstance();
    }

    @Override
    public ISetter getSettableProxy() {
        return PojoSetter.getInstance();
    }

    @Override
    public void dispose() {
        if(isDisposed())
            return;
        super.dispose();
        this.objectSource = null;
    }
}

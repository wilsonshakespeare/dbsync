package com.research.dbsync.serializer.custom;

import com.research.dbsync.serializer.IGetter;
import com.research.dbsync.serializer.ISetter;
import com.research.dbsync.serializer.json.JSONSetter;
import com.research.dbsync.serializer.pojo.PojoSetter;
import com.research.dbsync.serializer.sqlite.SQLiteSetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iFreedom87 on 5/10/17.
 */

public abstract class CustomInitializer {
    private Class<?> customClass;
    private Map<ISetter, ICustomSetter> settableMap;
    private Map<IGetter, ICustomGetter> gettableMap;

    public CustomInitializer(Class<?> customClass) {
        this.customClass = customClass;
        this.settableMap = new HashMap<>();
        this.gettableMap = new HashMap<>();

        init_format();
    }

    protected abstract void init_format();

    public void setup(String customKey){
        if(settableMap.containsKey(JSONSetter.getInstance()))
            JSONSetter.getInstance().addCustom(customKey, settableMap.get(JSONSetter.getInstance()));
        if(settableMap.containsKey(PojoSetter.getInstance()))
            PojoSetter.getInstance().addCustom(customKey, settableMap.get(PojoSetter.getInstance()));
        if(settableMap.containsKey(SQLiteSetter.getInstance()))
            SQLiteSetter.getInstance().addCustom(customKey, settableMap.get(SQLiteSetter.getInstance()));

        if(gettableMap.containsKey(JSONSetter.getInstance()))
            JSONSetter.getInstance().addCustom(customKey, gettableMap.get(JSONSetter.getInstance()));
        if(gettableMap.containsKey(PojoSetter.getInstance()))
            PojoSetter.getInstance().addCustom(customKey, gettableMap.get(PojoSetter.getInstance()));
        if(gettableMap.containsKey(SQLiteSetter.getInstance()))
            SQLiteSetter.getInstance().addCustom(customKey, gettableMap.get(SQLiteSetter.getInstance()));
    }

    protected void addCustomSetter(ISetter setter, ICustomSetter customSetter){
        this.settableMap.put(setter, customSetter);
    }

    protected void addCustomGetter(IGetter getter, ICustomGetter customGetter){
        this.gettableMap.put(getter, customGetter);
    }

    public Class<?> getCustomClass(){
        return customClass;
    }
}

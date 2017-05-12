package com.research.dbsync.model;

import com.research.dbsync.core.Disposable;
import com.research.dbsync.util.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iFreedom87
 * @since 2016-06-23
 */
public class ModelOwner extends Disposable implements IModelOwner {
    private Map<Class, ModelBase> _models;
    private Map<String, Object> _dataBind;

    public ModelOwner(){
        _models = new HashMap<Class, ModelBase>();
        _dataBind = new HashMap<>();
    }

    // Direct and Straightfoward Adding
    @Override
    public Object data_get(String key) {
        return _dataBind.get(key);
    }

    @Override
    public void data_set(String key, Object data) {
        _dataBind.put(key, data);
    }

    @Override
    public <T extends ModelBase> T model_get(Class<T> c) {
        return (T) _models.get(c);
    }

    @Override
    public <T extends ModelBase> void model_add(Class<T> c, Map<String, Object> properties) {
        try{
            if(_models.containsKey(c)){
                // Dispose Old and Override
                _models.get(c).dispose();
            }
            T model = c.newInstance();
            model.properties_set(properties);
            _models.put(c, model);
        }catch(Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public <T extends ModelBase> void model_add(T model) {
        Class tClass = model.getClass();
        if(_models.containsKey(tClass)){
            // Dispose Old and Override
            _models.get(tClass).dispose(); // To Replace New Model
        }
        _models.put(tClass, model);
    }

    @Override
    public <T extends ModelBase> void model_remove(Class<T> c) {
        if(_models.containsKey(c)) {
            _models.get(c).dispose();
            _models.remove(c);
        }
    }

    @Override
    public void dispose() {
        if(isDisposed())
            return;
        super.dispose();

        MapUtils.map_delete(_models, false, true);
        MapUtils.map_delete(_dataBind, false, true);

        _models = null;
        _dataBind = null;
    }

    //TODO: ICloneable if Necessary

}

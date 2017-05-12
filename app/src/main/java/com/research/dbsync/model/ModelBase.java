package com.research.dbsync.model;

import com.research.dbsync.condition.IConditional;
import com.research.dbsync.condition.StandardCondition;
import com.research.dbsync.core.Disposable;
import com.research.dbsync.core.ISettable;

import java.util.HashMap;
import java.util.Map;

import javadz.beanutils.BeanUtils;


/**
 * @author iFreedom87
 * @since 2016-06-23
 */
public abstract class ModelBase extends Disposable implements ISettable {
    private Map<String, Object> updateMap = null;

    public ModelBase(){
        super();
    }

    @Override
    public void properties_set(Map<String, Object> properties) {
        try {
            BeanUtils.populate(this, properties);

            if(updateMap != null) updateMap.clear();
            updateMap = new HashMap<>(properties);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public Map<String, Object> getUpdateMap() {
        return updateMap;
    }

    @Override
    public void dispose() {
        if(isDisposed())
            return;
        super.dispose();

        if(updateMap != null) updateMap.clear();
    }

    // Reference Only
        /* Note: Don't Need BeanUtils is able to handle it
        Iterator it = properties.entrySet().iterator();
        int[] charAt = new int[]{0};
        Log.d("Cibai", String.valueOf(it));
        while (it.hasNext()) {
            try {
                Map.Entry pair = (Map.Entry) it.next();
                Method m = this.getClass().getMethod(
                        "set" + StringUtils.toUpperCase(pair.getKey().toString(), charAt),
                        new Class[]{pair.getValue().getClass()}
                );
                Log.d("MyMethod", String.valueOf(m));
                if (m != null) {
                    Class type = pair.getValue().getClass();
                    m.invoke(this, type.cast(pair.getValue()));
                }
                it.remove(); // avoids a ConcurrentModificationException
            } catch (Exception exp) {
                // Report Exception
                // How To Seperate Logging Between Android and Java... WTF
                // exp.printStackTrace();
                Log.e("Wireframe", "Exception", exp);
            }
        }
        */
}

package com.research.dbsync.view.component.inflater;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.research.dbsync.core.Trace;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.util.ReflectionUtils;
import com.research.dbsync.view.component.updater.IViewUpdater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iFreedom87 on 4/30/17.
 */

public class ModelItemInflater {
    @LayoutRes private int inflationLayout;

    private List<Integer> components = null;
    private List<String> properties = null;
    private List<IViewUpdater> updaters = null;

    public ModelItemInflater(@LayoutRes int inflationLayout) {
        this.inflationLayout = inflationLayout;
        this.components = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.updaters = new ArrayList<>();
    }

    public void components_add(int component, @NonNull String modelProperty, IViewUpdater updater){
        this.components.add(component);
        this.properties.add(modelProperty);
        this.updaters.add(updater);
    }

    public void components_add(int component, IViewUpdater updater){
        // For Components Require More than One Properties to Make Display Decision
        this.components.add(component);
        this.properties.add(null);
        this.updaters.add(updater);
    }

    public View inflate(ViewGroup viewGroup){
        return LayoutInflater.from(viewGroup.getContext())
                .inflate(inflationLayout, viewGroup, false);
    }

    public void bind(View view, ModelBase model){
        for(int i = 0; i < updaters.size(); i++){
            try{
                if(properties.get(i) == null){
                    updaters.get(i).view_update(view.findViewById(components.get(i)), model);
                }else{
                    updaters.get(i).view_update(
                            view.findViewById(components.get(i)),
                            ReflectionUtils.getPropertyValue(model, properties.get(i))
                    );
                }
            }catch (Exception exp){
                Trace.e("ModelListInflator", "Failed to update at: " + i + " of property: " + properties.get(i), exp);
            }
        }
    }


}

package com.research.dbsync.handler.list;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.research.dbsync.core.ILongID;
import com.research.dbsync.view.component.adapter.IDataSetNotifiable;
import com.research.dbsync.core.Trace;
import com.research.dbsync.handler.list.process.IListProcess;
import com.research.dbsync.handler.list.process.IListSourceable;
import com.research.dbsync.handler.list.process.ListProcessModel;
import com.research.dbsync.handler.list.process.ListSourceModel;
import com.research.dbsync.model.ModelBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iFreedom87 on 4/25/17.
 */

public class ListHandler<T extends ModelBase> implements IListHandler<T> {
    //TODO: Filterable Strategy, Once applied it will need to change the notify Index change According to the Display List

    private Class<T> modelClass;
    private List<T> items;
    private List<IListProcess> processes;
    protected IListSourceable<T> listSourceable;

    private IDataSetNotifiable notifiable;

    public ListHandler(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.items = new ArrayList<>();
        this.processes = new ArrayList<>();
    }

    public ListHandler<T> process_add(IListProcess listProcess){
        if(processes.contains(listProcess))
            return this;

        if(listProcess.getModelClass() == modelClass){
            processes.add(listProcess);
        }else{
            Trace.w(this.getClass().toString(), "process_add: model class mismatch, process not added: " + listProcess);
        }
        return this;
    }

    public ListHandler<T> setListSourceable(IListSourceable<T> listSourceable){
        this.listSourceable = listSourceable;
        return this;
    }

    @Override
    public ListHandler<T> setDataSetNotifiable(@Nullable IDataSetNotifiable notifiable) {
        this.notifiable = notifiable;
        return this;
    }

    @Override
    public void item_add(T item) {
        ListProcessModel<T> processModel = new ListProcessModel<T>(item, this);
        if(processes.size() == 0){
            item_add_success(processModel);
        }else{
            for(int i = 0; i < processes.size(); i++){
                processes.get(i).item_add(processModel);
            }
        }
    }

    public void item_add(int index, T item) {
        ListProcessModel<T> processModel = new ListProcessModel<T>(item, this);
        processModel.setIndexAt(index);
        if(processes.size() == 0){
            item_add_success(processModel);
        }else{
            for(int i = 0; i < processes.size(); i++){
                processes.get(i).item_add(processModel);
            }
        }
    }

    @Override
    public void item_add_success(ListProcessModel<T> processModel) {
        if(processModel.getCompleteCount() == processes.size()){
            if(processModel.getIndexAt() == -1)
                items.add(processModel.getModel());
            else
                items.add(processModel.getIndexAt(), processModel.getModel());

            processModel.dispose();
            if (this.notifiable instanceof RecyclerView.Adapter) {
                //TODO: Change to Display List
                ((RecyclerView.Adapter) this.notifiable).notifyItemInserted(items.size() - 1);
            }else{
                if(this.notifiable != null)
                    this.notifiable.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void item_update(T item) {
        ListProcessModel<T> processModel = new ListProcessModel<T>(item, this);
        if(processes.size() == 0){
            item_update_sucess(processModel);
        }else{
            for(int i = 0; i < processes.size(); i++){
                processes.get(i).item_update(processModel);
            }
        }
    }

    @Override
    public void item_update_sucess(ListProcessModel<T> processModel) {
        if(processModel.getCompleteCount() == processes.size()) {
            if (this.notifiable == null)
                return;

            if (this.notifiable instanceof RecyclerView.Adapter) {
                //TODO: Change to Display List
                ((RecyclerView.Adapter) this.notifiable).notifyItemChanged(items.indexOf(processModel.getModel()));
            }else{
                this.notifiable.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void item_delete(T item) {
        ListProcessModel<T> processModel = new ListProcessModel<T>(item, this);
        if(processes.size() == 0){
            item_delete_success(processModel);
        }else{
            for(int i = 0; i < processes.size(); i++){
                processes.get(i).item_delete(processModel);
            }
        }
    }

    @Override
    public void item_delete(int index) {
        ListProcessModel<T> processModel = new ListProcessModel<T>(items.get(index), this);
        if(processes.size() == 0){
            item_delete_success(processModel);
        }else{
            for(int i = 0; i < processes.size(); i++){
                processes.get(i).item_delete(processModel);
            }
        }
    }

    @Override
    public void item_delete_success(ListProcessModel<T> processModel) {
        if(processModel.getCompleteCount() == processes.size()){
            int removeIndex = items.indexOf(processModel.getModel());
            items.remove(removeIndex);
            processModel.dispose();
            if (this.notifiable instanceof RecyclerView.Adapter) {
                //TODO: Change to Display List
                ((RecyclerView.Adapter) this.notifiable).notifyItemRemoved(removeIndex);
                ((RecyclerView.Adapter) this.notifiable).notifyItemRangeChanged(removeIndex, getCount());
            }else{
                if(this.notifiable != null)
                    this.notifiable.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void items_load() {
        if(this.listSourceable == null)
            return;
        this.listSourceable.items_load(new ListSourceModel<>(this.items, this));
    }

    @Override
    public void items_loaded(ListSourceModel<T> processedSource) {
        if(this.listSourceable == null)
            return;

        // Already being sourced and processed
        if(this.notifiable == null)
            return;

        this.notifiable.notifyDataSetChanged();
    }

    @Override
    public Class<T> getModelClass() {
        return this.modelClass;
    }

    @Override
    public List<T> getItems() {
        return this.items;
    }

    @Override
    public int getItemIndex(T model) {
        return this.items.indexOf(model);
    }

    @Override
    public T getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        if(items.get(i) instanceof ILongID)
            return ((ILongID) items.get(i)).getId();
        return i;
    }

    @Override
    public int getCount() {
        return items.size();
    }

}

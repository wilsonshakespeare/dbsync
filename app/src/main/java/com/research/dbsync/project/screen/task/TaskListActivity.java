package com.research.dbsync.project.screen.task;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.research.dbsync.ActivityBase;
import com.research.dbsync.R;
import com.research.dbsync.handler.list.ListHandler;
import com.research.dbsync.handler.list.process.DBListProcess;
import com.research.dbsync.handler.list.process.ListProcessFactory;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.project.ProjectActivityBase;
import com.research.dbsync.project.common.UIComponentSetup;
import com.research.dbsync.project.model.TaskModel;
import com.research.dbsync.project.model.UserModel;
import com.research.dbsync.project.screen.user.UserFormActivity;
import com.research.dbsync.serializer.DataFormat;
import com.research.dbsync.view.component.adapter.IViewItemListenersBinder;
import com.research.dbsync.view.component.adapter.recyclerview.GSysDefaultAdapter;
import com.research.dbsync.view.component.inflater.ModelItemInflater;

/**
 * Created by iFreedom87 on 4/24/17.
 */

public class TaskListActivity extends ProjectActivityBase implements IViewItemListenersBinder{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setActivityToolbar((Toolbar) findViewById(R.id.activity_toolbar));

        GSysDefaultAdapter<TaskModel> adapter = new GSysDefaultAdapter<>(TaskModel.LIST_HANDLER, TaskModel.getInflater());
        UIComponentSetup.getCommonRecyclerView(this, null, adapter, R.id.recycler_view);

        adapter_add(TaskModel.class, adapter); // Adapter will then automatically disposed upon on destroy

        adapter.setListenerBinder(this);
    }

    @Override
    public void listeners_setup(View view, final ModelBase modelBase) {
        final TaskModel taskModel = (TaskModel) modelBase;
        // Note: Such Redundency, That is why event model behaviour is introduced.
        // The id will be attached to the view and hence the event function only one is required
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence selections[] = new CharSequence[] {"Edit", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(selections, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent = new Intent(getActivity(), TaskFormActivity.class);
                            intent.putExtra(
                                    "edit_index",
                                    TaskModel.LIST_HANDLER.getItemIndex(taskModel)
                            );
                            startActivity(intent);
                            finish();
                        }else if(which == 1){
                            TaskModel.LIST_HANDLER.item_delete(taskModel);
                        }
                    }
                });

                builder.show();
                return false;
            }
        });
    }


}

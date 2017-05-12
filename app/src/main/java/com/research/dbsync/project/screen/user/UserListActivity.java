package com.research.dbsync.project.screen.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.research.dbsync.ActivityBase;
import com.research.dbsync.R;
import com.research.dbsync.handler.list.ListHandlerFactory;
import com.research.dbsync.model.ModelBase;
import com.research.dbsync.project.ProjectActivityBase;
import com.research.dbsync.project.common.UIComponentSetup;
import com.research.dbsync.project.model.TaskModel;
import com.research.dbsync.project.model.UserModel;
import com.research.dbsync.view.component.adapter.IViewItemListenersBinder;
import com.research.dbsync.view.component.adapter.recyclerview.GSysDefaultAdapter;

/**
 * Created by iFreedom87 on 5/8/17.
 */

public class UserListActivity extends ProjectActivityBase implements IViewItemListenersBinder {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setActivityToolbar((Toolbar) findViewById(R.id.activity_toolbar));

        GSysDefaultAdapter<UserModel> adapter = new GSysDefaultAdapter<>(UserModel.LIST_HANDLER, UserModel.getInflater());
        UIComponentSetup.getCommonRecyclerView(this, null, adapter, R.id.recycler_view);

        adapter.setListenerBinder(this);

        adapter_add(UserModel.class, adapter); // Adapter will then automatically disposed upon on destroy
    }

    @Override
    public void listeners_setup(View view, final ModelBase modelBase) {
        final UserModel userModel = (UserModel) modelBase;
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
                            Intent intent = new Intent(getActivity(), UserFormActivity.class);
                            intent.putExtra(
                                    "edit_index",
                                    UserModel.LIST_HANDLER.getItemIndex(userModel)
                            );
                            startActivity(intent);
                            finish();
                        }else if(which == 1){
                            UserModel.LIST_HANDLER.item_delete(userModel);
                        }
                    }
                });

                builder.show();
                return false;
            }
        });
    }

}

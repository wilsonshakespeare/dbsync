package com.research.dbsync.project.screen.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.research.dbsync.R;
import com.research.dbsync.core.Trace;
import com.research.dbsync.project.ProjectActivityBase;
import com.research.dbsync.project.model.UserModel;

/**
 * Created by iFreedom87 on 5/7/17.
 */

public class UserFormActivity extends ProjectActivityBase {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final int editIndex = intent.getIntExtra("edit_index", -1);

        setContentView(R.layout.layout_form);
        setActivityToolbar((Toolbar) findViewById(R.id.activity_toolbar));

        FormSectionController section = new FormSectionController(this, "Add Person");
        UserModel.form_section_setup(section, this);
        ViewGroup containerView = (ViewGroup) findViewById(R.id.form_elements_container);

        final FormController formController = new FormController(this);
        formController.addSection(section);
        formController.recreateViews(containerView);

        Button button = (Button) this.findViewById(R.id.form_submit);
        if(editIndex != -1){
            button.setText("Edit");
            UserModel.LIST_HANDLER.getItem(editIndex).form_input_populate(section);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formController.resetValidationErrors();
                if(formController.isValidInput()){
                    UserModel userModel;

                    if(editIndex == -1){
                        userModel = new UserModel();
                        userModel.form_value_populate(formController.getModel());
                        UserModel.LIST_HANDLER.item_add(userModel);
                    }else{
                        userModel = UserModel.LIST_HANDLER.getItem(editIndex);
                        userModel.form_value_populate(formController.getModel());
                        UserModel.LIST_HANDLER.item_update(userModel);
                    }

                    Intent intent = new Intent(getActivity(), UserListActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    formController.showValidationErrors();
                }
            }
        });
    }
}

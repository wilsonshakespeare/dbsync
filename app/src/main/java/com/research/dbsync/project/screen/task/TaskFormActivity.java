package com.research.dbsync.project.screen.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.research.dbsync.R;
import com.research.dbsync.project.ProjectActivityBase;
import com.research.dbsync.project.model.TaskModel;
import com.research.dbsync.project.model.UserModel;
import com.research.dbsync.project.screen.user.UserListActivity;

/**
 * Created by iFreedom87 on 5/7/17.
 */

public class TaskFormActivity extends ProjectActivityBase {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final int editIndex = intent.getIntExtra("edit_index", -1);

        setContentView(R.layout.layout_form);
        setActivityToolbar((Toolbar) findViewById(R.id.activity_toolbar));

        final FormSectionController section = new FormSectionController(this, "Add Tasks");
        TaskModel.form_section_setup(section, this);
        ViewGroup containerView = (ViewGroup) findViewById(R.id.form_elements_container);

        final FormController formController = new FormController(this);
        formController.addSection(section);
        formController.recreateViews(containerView);

        Button button = (Button) this.findViewById(R.id.form_submit);
        if(editIndex != -1){
            button.setText("Edit");
            TaskModel.LIST_HANDLER.getItem(editIndex).form_input_populate(section);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formController.resetValidationErrors();
                if(formController.isValidInput()){
                    int selectedPosition = ((SelectionController) section.getElement("assignedUser")).getSpinner().getSelectedItemPosition();
                    TaskModel taskModel;

                    if(editIndex == -1){
                        taskModel = new TaskModel();
                        taskModel.form_value_populate(formController.getModel(), selectedPosition);
                        taskModel.getListHandler().item_add(taskModel);
                    }else{
                        taskModel = TaskModel.LIST_HANDLER.getItem(editIndex);
                        taskModel.form_value_populate(formController.getModel(), selectedPosition);
                        taskModel.getListHandler().item_update(taskModel);
                    }

                    Intent intent = new Intent(getActivity(), TaskListActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    formController.showValidationErrors();
                }
            }
        });
    }
}

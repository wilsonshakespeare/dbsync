package com.research.dbsync.project.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.dkharrat.nexusdialog.FormModel;
import com.github.dkharrat.nexusdialog.controllers.DatePickerController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.research.dbsync.R;
import com.research.dbsync.core.Trace;
import com.research.dbsync.handler.list.IListHandlable;
import com.research.dbsync.handler.list.ListHandler;
import com.research.dbsync.handler.list.ListHandlerFactory;
import com.research.dbsync.handler.list.SerializedModelListHandler;
import com.research.dbsync.handler.list.process.ListProcessFactory;
import com.research.dbsync.serializer.DataFormat;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.custom.date.DateSetter;
import com.research.dbsync.util.ListUtils;
import com.research.dbsync.view.component.inflater.ModelItemInflater;
import com.research.dbsync.view.component.updater.IViewUpdater;
import com.research.dbsync.view.component.updater.TextUpdater;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javadz.beanutils.BeanUtils;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public class TaskModel extends SerializableModel implements IListHandlable<TaskModel>{
    public String title;
    public String description;
    public Date createdDate;
    public Date dueDate;
    public UserModel assignedUser;
    public List<TaskModel> subTasks; // Will Fix for Single Table List Reference

    private static ModelItemInflater _INFLATER = null;

    public static final SerializedModelListHandler<TaskModel> LIST_HANDLER =
            (SerializedModelListHandler<TaskModel>) ListHandlerFactory.getInstance().getListHandler(TaskModel.class).
                    process_add(ListProcessFactory.getInstance().getDBListProcess(TaskModel.class)).
                    setListSourceable(ListProcessFactory.getInstance().getDBListProcess(TaskModel.class));

    @Override
    protected void format_declare() {
        addFormat("title", "title", DataFormat.STRING);
        addFormat("description", "description", DataFormat.STRING);
        addObject("assigned_user", "assignedUser", UserModel.class);
        addCustom("created_date", "createdDate", "date_timestamp", new DateSetter(DateSetter.FORMAT_TIMESTAMP));
        addCustom("due_date", "dueDate", "date_timestamp", new DateSetter(DateSetter.FORMAT_TIMESTAMP));
        // addList("sub_tasks", "subTasks", TaskModel.class); // Other Table List is Fine: Will Fix on Friday
    }

    // For Easy Property Reference, There is Only One Inflater Reference Required
    public static ModelItemInflater getInflater() {
        if(_INFLATER == null){
            _INFLATER = new ModelItemInflater(R.layout.activity_task_item_panel);
            _INFLATER.components_add(R.id.task_item_name, "title", TextUpdater.getInstance());
            _INFLATER.components_add(R.id.task_item_desc, "description", TextUpdater.getInstance());
            _INFLATER.components_add(R.id.task_item_user, "assignedUser", updater);
            _INFLATER.components_add(R.id.task_item_date, "createdDate", TextUpdater.getInstance());
            _INFLATER.components_add(R.id.task_item_duedate, "dueDate", TextUpdater.getInstance());
        }
        return _INFLATER;
    }

    //TODO: Form Builder Still Require to be improved
    public static void form_section_setup(FormSectionController section, Context context){
        section.addElement(new EditTextController(context, "title", "Task Name", "", true));
        section.addElement(new EditTextController(context, "description", "Description", "", true));
        section.addElement(new SelectionController(
                context, "assignedUser", "Assign", true, "Select",
                ListUtils.retrieve_property_list(UserModel.LIST_HANDLER.getItems(), "name", String.class),
                true)
        );
        section.addElement(new DatePickerController(context, "dueDate", "Due", true, new SimpleDateFormat("dd-MMM-yyyy"))); // It is Date Object
    }

    public void form_input_populate(FormSectionController section){
        ((EditTextController) section.getElement("title")).getEditText().setText((this.getTitle() == null) ? "" : this.getTitle());
        ((EditTextController) section.getElement("description")).getEditText().setText((this.getDescription() == null) ? "" : this.getDescription());
        ((SelectionController) section.getElement("assignedUser")).getSpinner().setSelection((this.getAssignedUser() == null) ? 0 : UserModel.LIST_HANDLER.getItemIndex(this.getAssignedUser().getId()) + 1);
        //((SelectionController) section.getElement("assignedUser")).getSpinner().setSelection((this.getAssignedUser() == null) ? 0 : UserModel.LIST_HANDLER.getItemIndex(this.getAssignedUser().getId()) + 1);
    }

    // TODO: Own Form Builder, Based On Controller Type Retrieve Info and Intepreter for Each Element
    // 1. This is the demo for time being Sync of DB and List and show how auto population work
    // 2. Automated Form Building will be next thing to do
    // 3. Context shall not be part of controller
    public void form_value_populate(FormModel formModel, int userSelectionPosition){
        String[] localKeys = getSerializationInfo().getLocalKeys();

        for(int i = 0; i < localKeys.length; i++){
            if(localKeys[i] == "id" || localKeys[i] == "createdDate")
                continue;
            try{
                if(localKeys[i] == "assignedUser" && userSelectionPosition != 0){
                    this.setAssignedUser(UserModel.LIST_HANDLER.getItem(userSelectionPosition - 1));
                    // Will Do Sub Tasks
                }else{
                    BeanUtils.setProperty(this, localKeys[i], formModel.getValue(localKeys[i]));
                }
            }catch (Exception exp){
                Trace.e("FormPopulate", "population error for property: " + localKeys[i],  exp);
            }
        }

        if(this.createdDate == null)
            this.createdDate = new Date();
    }

    private static IViewUpdater<UserModel> updater = new IViewUpdater<UserModel>() {
        @Override
        public void view_update(View view, UserModel value) {
            if(view instanceof TextView){
                if(value != null)
                    ((TextView) view).setText(value.getName());
            }

        }
    };

    public SerializedModelListHandler<TaskModel> getListHandler(){
        return LIST_HANDLER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<TaskModel> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<TaskModel> subTasks) {
        this.subTasks = subTasks;
    }

    public UserModel getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(UserModel assignedUser) {
        this.assignedUser = assignedUser;
    }


}

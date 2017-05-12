package com.research.dbsync.project.model;

import android.content.Context;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormModel;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.research.dbsync.R;
import com.research.dbsync.core.Trace;
import com.research.dbsync.handler.list.IListHandlable;
import com.research.dbsync.handler.list.ListHandler;
import com.research.dbsync.handler.list.ListHandlerFactory;
import com.research.dbsync.handler.list.SerializedModelListHandler;
import com.research.dbsync.handler.list.process.ListProcessFactory;
import com.research.dbsync.serializer.DataFormat;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.view.component.inflater.ModelItemInflater;
import com.research.dbsync.view.component.updater.TextUpdater;

import javadz.beanutils.BeanUtils;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public class UserModel extends SerializableModel implements IListHandlable<UserModel>{
    public String name;
    public String position;
    public String contactNumber;

    private static ModelItemInflater _INFLATER = null;

    public static final SerializedModelListHandler<UserModel> LIST_HANDLER =
            (SerializedModelListHandler<UserModel>)
                    ListHandlerFactory.getInstance().getListHandler(UserModel.class).
                        process_add(ListProcessFactory.getInstance().getDBListProcess(UserModel.class)).
                        setListSourceable(ListProcessFactory.getInstance().getDBListProcess(UserModel.class));

    @Override
    protected void format_declare() {
        addFormat("name", "name", DataFormat.STRING);
        addFormat("position", "position", DataFormat.STRING);
        addFormat("contact_number", "contactNumber", DataFormat.STRING);
    }

    public static ModelItemInflater getInflater() {
        if(_INFLATER == null){
            _INFLATER = new ModelItemInflater(R.layout.activity_user_item_panel);
            _INFLATER.components_add(R.id.user_text_name, "name", TextUpdater.getInstance());
            _INFLATER.components_add(R.id.user_text_position, "position", TextUpdater.getInstance());
            _INFLATER.components_add(R.id.user_text_contact, "contactNumber", TextUpdater.getInstance());
        }
        return _INFLATER;
    }

    public SerializedModelListHandler<UserModel> getListHandler(){
        return LIST_HANDLER;
    }

    //TODO: Own Form Builder: There are design flaws in this nexus dialog,
    //1. the context shall not be included in the add Elements, as such model is pure info and shall not have Context
    //2. the style should be an object or layout inflation model
    public static void form_section_setup(FormSectionController section, Context context){
        // TODO: Concept - This part is called only once like format declare
        // addFormFormat(EditTextController(EditTextLayout(R.layout.editText, R.id.label, R.id.editText), "name", "Name")) // For Custom UI don't use context, All others follow Nexus Dialog
        // Note: will base on UserModel class create instance and set based on settable
        // Form.build(UserModel.class) // Will build the form, External Call
        // Form.build(userModel) // Will build the form and auto populate
        section.addElement(new EditTextController(context, "name", "Name", "", true));
        section.addElement(new EditTextController(context, "position", "Position", "", true));
        section.addElement(new EditTextController(context, "contactNumber", "Contact", "", true));
    }

    //TODO: Implement Variation Of Property Input, Example as in Inflater using Updater as population Strategy
    // 1. Only can be done with own form builder as requires preset elements to make command
    public void form_value_populate(FormModel formModel){
        // Auto Populate
        String[] localKeys = getSerializationInfo().getLocalKeys();
        for(int i = 0; i < localKeys.length; i++){
            if(localKeys[i] == "id")
                continue;
            try{
                BeanUtils.setProperty(this, localKeys[i], formModel.getValue(localKeys[i]));
            }catch (Exception exp){
                Trace.e("FormPopulate", "population error for property: " + localKeys[i],  exp);
            }
        }
    }

    public void form_input_populate(FormSectionController section){
        ((EditTextController) section.getElement("name")).getEditText().setText((this.getName() == null) ? "" : this.getName());
        ((EditTextController) section.getElement("position")).getEditText().setText((this.getPosition() == null) ? "" : this.getPosition());
        ((EditTextController) section.getElement("contactNumber")).getEditText().setText((this.getContactNumber() == null) ? "" : this.getContactNumber());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

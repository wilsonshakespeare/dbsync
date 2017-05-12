# README #

This README would normally document whatever steps are necessary to get your application up and running.

## What is this repository for? ##

* Quick summary
* Version 1.0.0
Important: This is a private repository and has yet to have intention of open source

This is the demo for dbsync library system, the demo application will continue to improve with more feature added:

Task List Management:
Click on the top right menu to select as following:
- Add User
- Display User List
- Add Task
- Display Task List

User: add, edit, and delete users, these are the users that the tasks are being assigned to.
- Name
- Position
- Contact

Task: add, edit, and delete tasks:
- Title
- Description
- Assigned User
- Created Date
- Due Date

Will have demo for subtask list.

Note: List Encoding is being developed but we would like to demo same database table sub-listing technology.

Note: Form Building in this example shall be ommitted, we are using Nexus Dialog To Accomodate the building of the form at the moment but the design of nexus Dialog is still poor.

We will Soon Build a Form Builder with Better Code Design

## How do I get set up? ##

### For Initialising the Database: ###
IMAGINE NO MORE ANNOYING SQL QUERY!!!

#### Step One: Database and table setup ####
Recommended to initialize at Driver class (AKA Application Class)

```java
// Instantiate DataBase, Last Value is Database version every table column change will require version update
GSysDB database = GSysDB.getInstance(this, "dbsync_db", 9); 
```

#### Step Two: To Initilize Table in Database you will need the following only: ####

```java
GSysDB.getInstance().table_add(new SQLiteTableModel("tasks", TaskModel.class));
GSysDB.getInstance().table_add(new SQLiteTableModel("users", UserModel.class));
GSysDB.getInstance().init();
```

##### Important Note: Please add all table before calling init (Due to Relational Dependency Initialisation) #####

##### Important Note: Class of the Model (As Above) will require to be SerializableModel sub class #####

#### Step Three: Setup the Model Format Class ####

##### Inside TaskModel.class #####

```java
public String title;
public String description;
public Date createdDate;
public Date dueDate;
public UserModel assignedUser;
public List<TaskModel> subTasks; // Will Fix for Single Table List Reference

@Override
protected void format_declare() {
	// First Parameter is the common key: in this case key in database, Second Parameter is the local key: which is the model variable name
    addFormat("title", "title", DataFormat.STRING);
    addFormat("description", "description", DataFormat.STRING);
    addObject("assigned_user", "assignedUser", UserModel.class);
    addCustom("created_date", "createdDate", "date_timestamp", new DateSetter(DateSetter.FORMAT_TIMESTAMP));
    addCustom("due_date", "dueDate", "date_timestamp", new DateSetter(DateSetter.FORMAT_TIMESTAMP));
    // addList("sub_tasks", "subTasks", TaskModel.class); // Other Form of Table List is Fine: Will Add on Next Version
}
```

##### Important: Use Android Studio to auto-populate setter and getter for it to work. #####

##### Important: Add Custom is when there is a custom class type (Not SerializableModel Object) to handle #####

Conclusion with that the tables are automatically created

### For Initialising Listing - To Synchronise with Database ###

#### Step 1: Retrieving Central List Handler TaskModel have its own task model list (Dynamically) ####

```java
ListHandlerFactory.getInstance().getListHandler(TaskModel.class) 
// Hence the parameter can depend on dynamic class variable assign for retrieval and instantiation
```

As for code handling it could be too long hence reference are adviced to made through Model Class

Following will be the instantiation:

##### Inside TaskModel.class #####

```java
public static final SerializedModelListHandler<TaskModel> LIST_HANDLER = 
	(SerializedModelListHandler<TaskModel>) ListHandlerFactory.getInstance().getListHandler(TaskModel.class)
```

Hence in future can reference through TaskModel.LIST_HANDLER to retrieve list

#### Step 2: Attach Database Processes ####

```java
process_add(ListProcessFactory.getInstance().getDBListProcess(TaskModel.class));
```
In this case the List Processes will be synchronised with database

##### Example: #####

When TaskModel.LIST_HANDLER.item_add is being called for adding a new TaskModel object

It will automatically insert into database as well, so is item_update and item_delete

Now the List is Synchronized with the database

How about when Application Initializes and you will need to load the list from database?

#### Step 3: Loading from database ####

As Simple as:
TaskModel.LIST_HANDLER.items_load();

Hence all data are automatically loaded but wait, how do you know the source is from database? You can store the data in any form like file in JSON format. No Worries:

```java
setListSourceable(ListProcessFactory.getInstance().getDBListProcess(TaskModel.class));
```
This is why you need to set ListSourceable. In this case DBListProcess Strategy is a Source for the List.

### For Initialising Recycler View - To Synchronise with Listing ###

Creating View Holder for every adapter and create adapter everywhere just to support custom view holder and model. DUH.... 

#### Step 1: Instantiate the Adapter (Use the default): ####

Using the DefaultAdapter. For the moment only recycler view is supported. Why use ListView while recycler view are more optimised?

```java
GSysDefaultAdapter<TaskModel> adapter = new GSysDefaultAdapter<>(TaskModel.LIST_HANDLER, TaskModel.getInflater());
adapter_add(TaskModel.class, adapter); // Adapter will then automatically disposed upon on destroy
```

So 2 things is provided to the Adapter, the ListHandler and the ModelItemInflater

If your activity class extended the base activity class then do adapter_add to dispose automatically, else please dispose the adapter OnDestroy to effectively release the memory.

#### Step 2: Define the Pairs in ModelItemInflater ####

```java
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
```

1. See: You just need to declare the layout for the item panel
2. And then pair the item id with the variable name: in String format 
3. How do you like to update it. If it is a text view and the toString works for the object (Example Date: use default TextUpdater.getInstance())

Don't need to use onBindView and then declare every single thing in your adapter

#### Step 3: do you realise there is an updater object there? Just in case you need a custom update method ####
Just implements IViewUpdater with the parameter you need.

```java
private static IViewUpdater<UserModel> updater = new IViewUpdater<UserModel>() {
    @Override
    public void view_update(View view, UserModel value) {
        if(view instanceof TextView){
            if(value != null)
                ((TextView) view).setText(value.getName());
        }

    }
};
```

Need more than one parameter to make display decision?

Worry Less:

```java
_INFLATER.components_add(R.id.your_custom_view, yourCustomUpdater);
```
This will send you the whole model ^ ^...

#### Step 4: Eh I bind my view with listener at onBindViewHolder? Now How? ####

Well.. I do have a event handler and behaviour framework but not in here that is too valuable :p 

```java
public class TaskListActivity extends ProjectActivityBase implements IViewItemListenersBinder{
	
	public void onCreate(Bundle bundle){	
		// Setup the Listner Binding to the Adapter
		adapter.setListenerBinder(this);

	}

	public void listeners_setup(View view, final ModelBase modelBase) {
		// Your Codes to bind the view listener. This allows you to detach OnBindViewHolder Tyranny to force you to use Adapter to bind listeners
		// Now you can detach listener setup to anywhere as you could reuse the same setup code structure
	}
}
```


## Contribution guidelines ##

Advance: Creating Custom Parameter Handler

### Who do I talk to? ###

* Repo owner or admin: business.sern@gmail.com
* Other community or team contact
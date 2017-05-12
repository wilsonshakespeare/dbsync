package com.research.dbsync.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.research.dbsync.ActivityBase;
import com.research.dbsync.R;
import com.research.dbsync.project.screen.task.TaskFormActivity;
import com.research.dbsync.project.screen.task.TaskListActivity;
import com.research.dbsync.project.screen.user.UserFormActivity;
import com.research.dbsync.project.screen.user.UserListActivity;

/**
 * Created by iFreedom87 on 5/8/17.
 */

public class ProjectActivityBase extends ActivityBase {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_menu, menu);

        menu.add(Menu.NONE, 1, Menu.NONE, "Add User");
        menu.add(Menu.NONE, 2, Menu.NONE, "User List");
        menu.add(Menu.NONE, 3, Menu.NONE, "Add Task");
        menu.add(Menu.NONE, 4, Menu.NONE, "Task List");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case 1:
                if(this instanceof UserFormActivity)
                    return true;
                intent = new Intent(this, UserFormActivity.class);
                startActivity(intent);
                finish();
                return true;
            case 2:
                if(this instanceof UserListActivity)
                    return true;
                intent = new Intent(this, UserListActivity.class);
                startActivity(intent);
                finish();
                return true;
            case 3:
                if(this instanceof TaskFormActivity)
                    return true;
                intent = new Intent(this, TaskFormActivity.class);
                startActivity(intent);
                finish();
                return true;
            case 4:
                if(this instanceof TaskListActivity)
                    return true;
                intent = new Intent(this, TaskListActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                break;
        }

        return true;
    }



}

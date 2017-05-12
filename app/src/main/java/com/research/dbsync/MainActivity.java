package com.research.dbsync;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.research.dbsync.project.screen.user.UserListActivity;
import com.research.dbsync.util.Message;

public class MainActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Architecture", "MainActivity:onCreate");

        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
        finish();

    }
}

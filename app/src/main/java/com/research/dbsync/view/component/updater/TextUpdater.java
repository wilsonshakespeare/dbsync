package com.research.dbsync.view.component.updater;

import android.view.View;
import android.widget.TextView;

/**
 * Created by iFreedom87 on 4/30/17.
 */

public class TextUpdater implements IViewUpdater<Object> {
    private static TextUpdater _INSTANCE = null;
    private TextUpdater() {
    }

    public static TextUpdater getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new TextUpdater();
        }
        return _INSTANCE;
    }

    @Override
    public void view_update(View view, Object value) {
        if(view instanceof TextView)
            ((TextView) view).setText(String.valueOf(value));
    }
}

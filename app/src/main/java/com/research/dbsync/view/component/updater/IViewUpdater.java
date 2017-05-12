package com.research.dbsync.view.component.updater;

import android.view.View;

/**
 * Created by iFreedom87 on 4/30/17.
 */

public interface IViewUpdater<T> {
    void view_update(View view, T value);
}

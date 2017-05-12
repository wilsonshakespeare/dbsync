package com.research.dbsync.serializer.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.research.dbsync.serializer.custom.ICustomGetter;
import com.research.dbsync.serializer.custom.ICustomSetter;

import java.util.Date;

/**
 * Created by iFreedom87 on 5/11/17.
 */

public interface ISQLiteCustomSetter<T> extends ICustomGetter<Cursor, T>, ICustomSetter<ContentValues, T> {
    String getSQLColumnType();
}

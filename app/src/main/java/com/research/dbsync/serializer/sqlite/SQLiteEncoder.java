package com.research.dbsync.serializer.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.research.dbsync.core.Trace;
import com.research.dbsync.serializer.EncoderBase;
import com.research.dbsync.serializer.IDecoder;
import com.research.dbsync.serializer.IEncoder;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.SerializationInfo;
import com.research.dbsync.serializer.SerializerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public class SQLiteEncoder extends EncoderBase implements IEncoder<ContentValues>, IDecoder<Cursor> {
    //Note: Reason of Singleton Proxy Design is because it can be attached to object
    private static SQLiteEncoder _INSTANCE;

    private SQLiteEncoder(){
        super(SQLiteSetter.getInstance(), SQLiteSetter.getInstance()); // Same Proxy
    }

    @Override
    public ContentValues encode(SerializableModel serializableModel) {
        // Convert to Content Value
        SerializationInfo info = serializableModel.getSerializationInfo();
        String[] targetKeys = info.getKeys();
        String[] sourceKeys = info.getLocalKeys();
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < targetKeys.length; i++){
            // id Should never be encoded for SQL, it is by default auto-increment at the moment for insert,
            // if it is update it would not make any difference either, because id is primary key
            if(targetKeys[i] == "id")
                continue;

            encode(contentValues, targetKeys[i], serializableModel, sourceKeys[i]);
        }
        return contentValues;
    }

    // full encoding -> later
    public <T extends SerializableModel> List<T> decode(Cursor source, Class<T> serializableClass){
        //Reduces need of get keys converstion of standard decode class
        List<T> list = new ArrayList<>();
        T target;
        SerializationInfo info = SerializerAdapter.getInstance().serializationInfo_get(serializableClass);
        String[] sourceKeys = info.getKeys();
        String[] targetKeys = info.getLocalKeys();

        while (source.moveToNext())
        {
            try {
                target = serializableClass.newInstance();
                for(int i = 0; i < sourceKeys.length; i++){
                    decode(target, targetKeys[i], source, sourceKeys[i]);
                }
                list.add(target);
            }catch (Exception exp){
                Trace.e("SQLiteEncoder", "Error: Decoding List for Class: " + serializableClass, exp);
            }
        }

        return list;
    }

    public void decode(Cursor source, SerializableModel target) {
        SerializationInfo info = target.getSerializationInfo();
        String[] sourceKeys = info.getKeys();
        String[] targetKeys = info.getLocalKeys();
        for(int i = 0; i < sourceKeys.length; i++){
            decode(target, targetKeys[i], source, sourceKeys[i]);
        }
    }

    public static SQLiteEncoder getInstance(){
        if(_INSTANCE == null)
            _INSTANCE = new SQLiteEncoder();
        return _INSTANCE;
    }
}

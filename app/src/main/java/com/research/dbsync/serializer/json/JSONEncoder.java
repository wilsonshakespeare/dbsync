package com.research.dbsync.serializer.json;

import com.research.dbsync.serializer.EncoderBase;
import com.research.dbsync.serializer.IDecoder;
import com.research.dbsync.serializer.IEncoder;
import com.research.dbsync.serializer.SerializableModel;
import com.research.dbsync.serializer.SerializationInfo;

import org.json.JSONObject;

/**
 * Created by iFreedom87 on 4/23/17.
 */

public class JSONEncoder extends EncoderBase implements IEncoder<JSONObject>, IDecoder<JSONObject> {
    private static JSONEncoder _INSTANCE = null;

    private JSONEncoder() {
        super(JSONSetter.getInstance(), JSONSetter.getInstance());
    }

    @Override
    public JSONObject encode(SerializableModel serializableModel) {
        JSONObject jsonObject = new JSONObject();
        SerializationInfo info = serializableModel.getSerializationInfo();
        String[] targetKeys = info.getKeys();
        String[] sourceKeys = info.getLocalKeys();
        for(int i = 0; i < targetKeys.length; i++){
            encode(jsonObject, targetKeys[i], serializableModel,  sourceKeys[i]);
        }
        return jsonObject;
    }

    @Override
    public void decode(JSONObject source, SerializableModel target) {
        SerializationInfo info = target.getSerializationInfo();
        String[] sourceKeys = info.getKeys();
        String[] targetKeys = info.getLocalKeys();
        for(int i = 0; i < sourceKeys.length; i++){
            decode(target, targetKeys[i], source, sourceKeys[i]);
        }
    }

    public static JSONEncoder getInstance() {
        if(_INSTANCE == null){
            _INSTANCE = new JSONEncoder();
        }
        return _INSTANCE;
    }
}

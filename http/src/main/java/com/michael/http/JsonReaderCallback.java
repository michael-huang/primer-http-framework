package com.michael.http;

import android.util.Log;

import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public abstract class JsonReaderCallback<T extends IEntity> extends BaseCallback<T> {

    private static final String TAG = JsonReaderCallback.class.getSimpleName();

    @Override
    protected T bindData(String path) throws AppException {
        try {
            Log.d(TAG, "JsonReaderCallback path: " + path);
            JSONObject json = new JSONObject(path);
            Object data = json.opt("data");
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            T t = ((Class<T>) type).newInstance();
//            StringReader in = new StringReader(data.toString());
            FileReader in = new FileReader(path);
            JsonReader reader = new JsonReader(in);
            String node;
            reader.beginObject();
            while (reader.hasNext()) {
                node = reader.nextName();
                if ("data".equalsIgnoreCase(node)) {
                    t.readFromJson(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return t;

        } catch (Exception e) {
               throw new AppException(AppException.ErrorType.JSON, e.getMessage());
        }
    }
}

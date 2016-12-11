package com.michael.http;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public abstract class JsonCallback<T> extends BaseCallback<T> {

    @Override
    protected T bindData(String result) throws AppException {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject data = json.optJSONObject("data");
            Gson gson = new Gson();
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return gson.fromJson(data.toString(), type);
        } catch (JSONException e) {
               throw new AppException(e.getMessage());
        }
    }
}

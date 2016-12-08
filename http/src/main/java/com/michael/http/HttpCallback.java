package com.michael.http;

import java.net.HttpURLConnection;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public interface HttpCallback<T> {
    void onSuccess(T result);

    void onFailure(Exception e);

    T parse(HttpURLConnection connection) throws Exception;
}

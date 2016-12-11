package com.michael.http;

import java.net.HttpURLConnection;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public interface HttpCallback<T> {
    void onSuccess(T result);

    void onFailure(AppException e);

    T parse(HttpURLConnection connection, OnProgressUpdateListener listener) throws AppException;

    T parse(HttpURLConnection connection) throws AppException;

    void onProgressUpdate(int curLen, int totalLen);

    void cancel();
}

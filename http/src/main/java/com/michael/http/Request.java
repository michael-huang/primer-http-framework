package com.michael.http;

import android.os.Build;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by huangyanzhen on 2016/12/7.
 */

public class Request {
    public boolean isEnableProgressUpdate = false;
    public OnGlobalExceptionListener onGlobalExceptionListener;
    public String tag;
    private RequestTask task;

    public void enableProgressUpdate(boolean isEnable) {
        this.isEnableProgressUpdate = isEnable;
    }

    public void setGlobalExceptionListener(OnGlobalExceptionListener onGlobalExceptionListener) {
        this.onGlobalExceptionListener = onGlobalExceptionListener;
    }

    public void checkIfCancelled() throws AppException {
        if (isCancelled) {
            throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancelled");
        }
    }

    public void cancel(boolean isForece) {
        isCancelled = true;
        callback.cancel();
        if (isForece && task != null) {
            task.cancel(isForece);
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void execute(Executor executor) {
        task = new RequestTask(this);
        if (Build.VERSION.SDK_INT > 11) {
            task.executeOnExecutor(executor);
        } else {
            task.execute();
        }
    }

    public enum RequestMethod {GET, POST, PUT, DELETE}

    public String url;
    public String content;
    public Map<String, String> headers;
    public RequestMethod method;
    public HttpCallback callback;
    public int maxRetryCount = 3;
    public volatile boolean isCancelled;

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public Request(String url) {
        this.url = url;
        this.method = RequestMethod.GET;
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }
}

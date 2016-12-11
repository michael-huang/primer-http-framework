package com.michael.http;

import java.util.Map;

/**
 * Created by huangyanzhen on 2016/12/7.
 */

public class Request {
    public boolean isEnableProgressUpdate = false;
    public OnGlobalExceptionListener onGlobalExceptionListener;

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

    public void cancel() {
        isCancelled = true;
        callback.cancel();
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

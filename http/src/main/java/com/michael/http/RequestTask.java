package com.michael.http;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public class RequestTask extends AsyncTask<Void, Integer, Object> {

    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... voids) {
        return request(0);
    }

    public Object request(int retry) {
        try {
            HttpURLConnection connection = HttpUrlConnectionUtil.execute(request);
            if (request.isEnableProgressUpdate) {
                return request.callback.parse(connection, new OnProgressUpdateListener() {
                    @Override
                    public void onProgressUpdate(int curLen, int totalLen) {
                        publishProgress(curLen, totalLen);
                    }
                });
            } else {
                return request.callback.parse(connection);
            }
        } catch (AppException e) {
            if (e.errorType == AppException.ErrorType.TIMEOUT) {
                if (retry < request.maxRetryCount) {
                    retry++;
                    return request(retry);
                }
            }
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof AppException) {
            if (request.onGlobalExceptionListener != null) {
                if (request.onGlobalExceptionListener.handleException((AppException) o)) {
                    request.callback.onFailure((AppException) o);
                }
            }
            request.callback.onFailure((AppException) o);
        } else {
            request.callback.onSuccess(o);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        request.callback.onProgressUpdate(values[0], values[1]);
    }
}

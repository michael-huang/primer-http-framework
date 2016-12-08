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
        try {
            HttpURLConnection connection = HttpUrlConnectionUtil.execute(request);
            return request.callback.parse(connection);
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof Exception) {
            request.callback.onFailure((Exception) o);
        } else {
            request.callback.onSuccess(o);
        }
    }
}

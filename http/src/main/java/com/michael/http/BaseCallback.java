package com.michael.http;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public abstract class BaseCallback<T> implements HttpCallback<T> {
    private String path;

    @Override
    public T parse(HttpURLConnection connection) throws Exception {
        return parse(connection, null);
    }

    @Override
    public T parse(HttpURLConnection connection, OnProgressUpdateListener listener) throws Exception {
        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            if (path == null) {
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[2048];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                is.close();
                out.flush();
                out.close();

                String result = new String(out.toByteArray());
                return bindData(result);
            } else {
                InputStream is = connection.getInputStream();
                FileOutputStream out = new FileOutputStream(path);

                int totalLen = connection.getContentLength();
                int curLen = 0;

                byte[] buffer = new byte[2048];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    curLen += len;
                    if (listener != null) {
                        listener.onProgressUpdate(curLen, totalLen);
                    }
                }
                is.close();
                out.flush();
                out.close();

                return bindData(path);
            }
        }
        return null;
    }

    @Override
    public void onProgressUpdate(int curLen, int totalLen) {

    }

    protected abstract T bindData(String result) throws Exception;


    public HttpCallback setCachePath(String path) {
        this.path = path;
        return this;
    }
}

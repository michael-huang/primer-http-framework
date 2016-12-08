package com.michael.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public abstract class BaseCallback<T> implements HttpCallback<T> {
    @Override
    public T parse(HttpURLConnection connection) throws Exception {
        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read()) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();

            String result = new String(out.toByteArray());
            return bindDtata(result);
        }
        return null;
    }

    protected abstract T bindDtata(String result) throws Exception;
}

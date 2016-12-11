package com.michael.http;

import android.webkit.URLUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by huangyanzhen on 2016/12/7.
 */

public class HttpUrlConnectionUtil {
    public static final String TAG = HttpUrlConnectionUtil.class.getSimpleName();

    public static HttpURLConnection execute(Request request) throws AppException {
        if (!URLUtil.isNetworkUrl(request.url)) {
            throw new AppException("the url: " + request.url + "is invalid");
        }
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request);
        }
        return null;
    }

    private static HttpURLConnection get(Request request) throws AppException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(2000);
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }

        addHeader(connection, request.headers);

        return connection;
    }

    private static HttpURLConnection post(Request request) throws AppException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(20000);
            connection.setDoOutput(true); // You need to set it to true if you want to send (output) a request body

            addHeader(connection, request.headers);

            OutputStream os = connection.getOutputStream();
            os.write(request.content.getBytes());
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }

        return connection;
    }

    private static void addHeader(HttpURLConnection connection, Map<String, String> headers) {
        if(headers == null || headers.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}

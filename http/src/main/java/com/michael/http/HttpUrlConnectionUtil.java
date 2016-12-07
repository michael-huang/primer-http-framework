package com.michael.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by huangyanzhen on 2016/12/7.
 */

public class HttpUrlConnectionUtil {
    public static final String TAG = HttpUrlConnectionUtil.class.getSimpleName();

    public static String execute(Request request) throws IOException {
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

    private static String get(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
        connection.setRequestMethod(request.method.name());
        connection.setReadTimeout(20000);
        connection.setConnectTimeout(2000);

        addHeader(connection, request.headers);

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) { // HttpStatus.SC_OK was deprecated in API level 22
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
            return new String(out.toByteArray());
        }
        return null;
    }

    private static String post(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
        connection.setRequestMethod(request.method.name());
        connection.setReadTimeout(20000);
        connection.setConnectTimeout(20000);
        connection.setDoOutput(true); // You need to set it to true if you want to send (output) a request body

        addHeader(connection, request.headers);

        OutputStream os = connection.getOutputStream();
        os.write(request.content.getBytes());

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
        }
        return null;
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

package com.michael.http;

import android.os.Environment;
import android.test.InstrumentationTestCase;
import android.util.Log;

import java.io.File;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public class TestHttp extends InstrumentationTestCase {

    private static final String TAG = TestHttp.class.getSimpleName();

//    public void testHttpGet() throws Throwable {
//        String url = "http://jsonplaceholder.typicode.com/posts";
//        Request request = new Request(url);
//        String result = HttpUrlConnectionUtil.execute(request);
//        Log.d(TAG, "testHttpGet returns: " + result);
//    }
//
//    public void testHttpPost() throws Throwable {
//        String url = "http://jsonplaceholder.typicode.com/userlogin";
//        String content = "username=michael&password=123456";
//        Request request = new Request(url, Request.RequestMethod.POST);
//        request.content = content;
//        String result = HttpUrlConnectionUtil.execute(request);
//        Log.d(TAG, "testHttpPost returns: " + result);
//    }

    public void testHttpPostOnSubThread() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/userlogin";
        String content = "username=michael&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = content;
        request.setCallback(new JsonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "testHttpPostOnSubThread returns: " + result);
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute(); // Actually unit test doesn't support thread execution, I just test out the syntax here
    }

    public void testHttpPostOnSubThreadforGeneric() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/userlogin";
        String content = "username=michael&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = content;
        request.setCallback(new JsonCallback<User>() {
            @Override
            public void onSuccess(User result) {
                Log.d(TAG, "testHttpPostOnSubThreadforGeneric returns: " + result.toString());
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute(); // Actually unit test doesn't support thread execution, I just test out the syntax here
    }

    public void testHttpPostOnSubThreadforDownload() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/userlogin";
        String content = "username=michael&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = content;
        String path = Environment.getExternalStorageDirectory() + File.separator + "test.txt";
        request.setCallback(new FileCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "path = " + result);
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }
        }.setCachePath(path));
        RequestTask task = new RequestTask(request);
        task.execute(); // Actually unit test doesn't support thread execution, I just test out the syntax here
    }

    public void testHttpPostOnSubThreadforDownloadProgress() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/uploads/test.jpg";
        Request request = new Request(url, Request.RequestMethod.GET);
        String path = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        request.setCallback(new FileCallback() {
            @Override
            public void onProgressUpdate(int curLen, int totalLen) {
                Log.d(TAG, "download: " + curLen + "/" + totalLen);
            }

            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "path = " + result);
            }

            @Override
            public void onFailure(AppException e) {
                if (e.statusCode == 403) {
                    if ("password incorrect".equals(e.responseMessage)) {
                        // TODO
                    }
                }
                e.printStackTrace();
            }
        }.setCachePath(path));
        request.enableProgressUpdate(true);
        RequestTask task = new RequestTask(request);
        task.execute(); // Actually unit test doesn't support thread execution, I just test out the syntax here
    }
}

package com.michael.http;

import android.test.InstrumentationTestCase;
import android.util.Log;

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
        request.setCallback(new HttpCallbackImpl<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "testHttpPostOnSubThread returns: " + result);
            }

            @Override
            public void onFailure(Exception e) {
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
        request.setCallback(new HttpCallbackImpl<User>() {
            @Override
            public void onSuccess(User result) {
                Log.d(TAG, "testHttpPostOnSubThreadforGeneric returns: " + result.toString());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute(); // Actually unit test doesn't support thread execution, I just test out the syntax here
    }
}

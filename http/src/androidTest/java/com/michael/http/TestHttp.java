package com.michael.http;

import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public class TestHttp extends InstrumentationTestCase {

    private static final String TAG = TestHttp.class.getSimpleName();

    public void testHttpGet() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/posts";
        Request request = new Request(url);
        String result = HttpUrlConnectionUtil.execute(request);
        Log.d(TAG, "testHttpGet returns: " + result);
    }

    public void testHttpPost() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/userlogin";
        String content = "username=michael&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = content;
        String result = HttpUrlConnectionUtil.execute(request);
        Log.d(TAG, "testHttpPost returns: " + result);
    }
}

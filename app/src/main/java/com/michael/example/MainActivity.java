package com.michael.example;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.michael.http.AppException;
import com.michael.http.BaseActivity;
import com.michael.http.FileCallback;
import com.michael.http.JsonCallback;
import com.michael.http.Request;
import com.michael.http.RequestManager;
import com.michael.http.RequestTask;
import com.michael.http.User;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRun = (Button) findViewById(R.id.btn_run);
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_run:
                testHttpPostOnSubThreadforDownloadProgressCancelTest();
                break;
        }
    }

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
        task.execute();
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
        task.execute();
    }

    public void testHttpPostOnSubThreadforGenericLoadMore() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/?service=user.getAll";
        url += "&timestamp=" + System.currentTimeMillis() + "&count=30";
        Request request = new Request(url, Request.RequestMethod.GET);
        request.setCallback(new JsonCallback<ArrayList<Module>>() {

            @Override
            public ArrayList<Module> preRequest() {
                // TODO fetch data from db
                return null;
            }

            @Override
            public ArrayList<Module> postRequest(ArrayList<Module> modules) {
                // TODO insert into db
                return modules;
            }

            @Override
            public void onSuccess(ArrayList<Module> result) {
                Log.d(TAG, "result size: " + result.size());
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    public void testHttpPostOnSubThreadforGenericPostRequest() throws Throwable {
        String url = "http://jsonplaceholder.typicode.com/userlogin";
        String content = "username=michael&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.content = content;
        request.setCallback(new JsonCallback<User>() {

            @Override
            public User postRequest(User user) {
                // TODO insert into db
                // TODO edit user
                Log.d(TAG, "postRequest returns: " + user.toString());
                user.email = "jackson@xxx.com";
                return null;
            }

            @Override
            public void onSuccess(User result) {
                Log.d(TAG, "onSuccess returns: " + result.toString());
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute();
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
        task.execute();
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
                    } else if ("token invalid".equals(e.getMessage())) {
                        // TODO re-login
                    }
                }
                e.printStackTrace();
            }
        }.setCachePath(path));
        request.setGlobalExceptionListener(this);
        request.enableProgressUpdate(true);
        RequestTask task = new RequestTask(request);
        task.execute();
        task.cancel(true);
        request.cancel();
    }

    public void testHttpPostOnSubThreadforDownloadProgressCancelTest() {
        String url = "http://jsonplaceholder.typicode.com/uploads/test.jpg";
        final Request request = new Request(url, Request.RequestMethod.GET);
//        final RequestTask task = new RequestTask(request);
        final String path = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        request.setCallback(new FileCallback() {
            @Override
            public void onProgressUpdate(int curLen, int totalLen) {
                Log.d(TAG, "download: " + curLen + "/" + totalLen);
                if (curLen * 1001 / totalLen > 50) { // cancel the task while the progress is over 50%
//                    task.cancel(true);
//                    request.cancel();
                }
            }

            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "path = " + result);
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }
        }.setCachePath(path));
        request.setGlobalExceptionListener(this);
        request.enableProgressUpdate(true);
        request.setTag(toString());
        RequestManager.getInstance().performRequest(request);
//        task.cancel(true);
//        request.cancel();
    }
}

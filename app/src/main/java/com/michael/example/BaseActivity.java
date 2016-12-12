package com.michael.example;

import android.support.v7.app.AppCompatActivity;

import com.michael.http.AppException;
import com.michael.http.OnGlobalExceptionListener;
import com.michael.http.RequestManager;

/**
 * Created by huangyanzhen on 2016/12/11.
 */

public class BaseActivity extends AppCompatActivity implements OnGlobalExceptionListener {
    @Override
    public boolean handleException(AppException e) {
        if (e.statusCode == 403) {
            if ("token invalid".equals(e.responseMessage)); {
                // TODO re-login
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.getInstance().cancelRequest(toString(), true);
    }
}

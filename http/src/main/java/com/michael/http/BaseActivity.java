package com.michael.http;

import android.support.v7.app.AppCompatActivity;

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
}

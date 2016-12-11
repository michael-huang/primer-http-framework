package com.michael.http;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public abstract class StringCallback extends BaseCallback<String> {

    @Override
    protected String bindData(String result) throws AppException {
        return result;
    }
}

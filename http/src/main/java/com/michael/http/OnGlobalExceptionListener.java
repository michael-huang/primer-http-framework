package com.michael.http;

/**
 * Created by huangyanzhen on 2016/12/11.
 */

public interface OnGlobalExceptionListener {
    boolean handleException(AppException e);
}

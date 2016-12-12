package com.michael.http;

import com.google.gson.stream.JsonReader;

/**
 * Created by huangyanzhen on 2016/12/12.
 */

public interface IEntity {
    void readFromJson(JsonReader reader) throws AppException;
}

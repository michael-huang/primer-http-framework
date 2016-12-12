package com.michael.example;

import com.google.gson.stream.JsonReader;
import com.michael.http.AppException;
import com.michael.http.IEntity;

import java.io.IOException;

import static android.R.attr.name;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public class User implements IEntity{
//    {"ret":200, "data":{"id":"1", "username":"michael", "email":"michael@xxx.com"}}
    public String id;
    public String username;
    public String email;

    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }

    @Override
    public void readFromJson(JsonReader reader) throws AppException {
        try {
            reader.beginObject();
            String node;
            while (reader.hasNext()) {
                node = reader.nextName();
                if ("username".equalsIgnoreCase(node)) {
                    username = reader.nextString();
                } else if ("email".equalsIgnoreCase(node)) {
                    email = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.JSON, e.getMessage());
        }
    }
}

package com.michael.http;

import static android.R.attr.name;

/**
 * Created by huangyanzhen on 2016/12/8.
 */

public class User  {
//    {"ret":200, "data":{"id":"1", "username":"michael", "email":"michael@xxx.com"}}
    public String id;
    public String username;
    public String email;

    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }
}

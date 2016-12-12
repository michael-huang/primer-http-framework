package com.michael.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangyanzhen on 2016/12/12.
 */

public class RequestManager {
    private static RequestManager mInstance;
    private HashMap<String, ArrayList<Request>> mCachedRequest;

    private RequestManager() {
        mCachedRequest = new HashMap<>();
    }

    public static synchronized RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    public void performRequest(Request request) {
        RequestTask task = new RequestTask(request);
        task.execute();
        if (!mCachedRequest.containsKey(request.tag)) {
            ArrayList<Request> requests = new ArrayList<>();
            mCachedRequest.put(request.tag, requests);
        }
        mCachedRequest.get(request.tag).add(request);
    }

    public void cancelRequest(String tag) {
        if (tag == null || "".equals(tag.trim())) {
            return;
        }
        // find mCachedRequest by tag and cancel them
        if (mCachedRequest.containsKey(tag)) {
            ArrayList<Request> requests = mCachedRequest.remove(tag);
            for (Request request : requests) {
                if (!request.isCancelled && tag.equals(request.tag)) {
                    request.cancel();
                }
            }
        }
    }

    public void cancelAll() {
        for (Map.Entry<String, ArrayList<Request>> entry : mCachedRequest.entrySet()) {
            ArrayList<Request> requests = entry.getValue();
            for (Request request : requests) {
                if (!request.isCancelled) {
                    request.cancel();
                }
            }
        }
    }
}

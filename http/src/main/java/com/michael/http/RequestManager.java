package com.michael.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangyanzhen on 2016/12/12.
 */

public class RequestManager {
    private static RequestManager mInstance;
    private final ExecutorService mExecutors;
    private HashMap<String, ArrayList<Request>> mCachedRequest;

    private RequestManager() {
        mCachedRequest = new HashMap<>();
        mExecutors = Executors.newFixedThreadPool(5);
    }

    public static synchronized RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    public void performRequest(Request request) {
        request.execute(mExecutors);
        if (!mCachedRequest.containsKey(request.tag)) {
            ArrayList<Request> requests = new ArrayList<>();
            mCachedRequest.put(request.tag, requests);
        }
        mCachedRequest.get(request.tag).add(request);
    }

    public void cancelRequest(String tag) {
        cancelRequest(tag, false);
    }

    public void cancelRequest(String tag, boolean isForce) {
        if (tag == null || "".equals(tag.trim())) {
            return;
        }
        // find mCachedRequest by tag and cancel them
        if (mCachedRequest.containsKey(tag)) {
            ArrayList<Request> requests = mCachedRequest.remove(tag);
            for (Request request : requests) {
                if (!request.isCancelled && tag.equals(request.tag)) {
                    request.cancel(isForce);
                }
            }
        }
    }

    public void cancelAll() {
        for (Map.Entry<String, ArrayList<Request>> entry : mCachedRequest.entrySet()) {
            ArrayList<Request> requests = entry.getValue();
            for (Request request : requests) {
                if (!request.isCancelled) {
                    request.cancel(true);
                }
            }
        }
    }
}

package com.isbndb.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.isbndb.helper.KeyManager;
import com.isbndb.helper.SearchHistory;

public class AppController extends Application {


    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private static KeyManager keyManager;
    private static SearchHistory searchHistory;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        keyManager = new KeyManager(getApplicationContext());
        searchHistory = new SearchHistory(getApplicationContext());

    }

    public static KeyManager getKeyManagerInstant() {
        return keyManager;
    }

    public static SearchHistory getSearchHistoryInstant() {
        return searchHistory;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
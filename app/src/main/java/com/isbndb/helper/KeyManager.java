package com.isbndb.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.isbndb.app.AppController;
import com.isbndb.utility.ApiDetails;

/**
 * Created by Sgsudhir on 2/22/2016.
 */
public class KeyManager {
    private Context context;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final boolean DEFAULT_KEY = false;
    public static final boolean USER_KEY = true;

    public KeyManager(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences("token_manager",Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public String getKey() {
        if (getKeyType() == USER_KEY)
            return mSharedPreferences.getString("key", null);
        return ApiDetails.DEFAULT_API_TOKEN;
    }

    public String getDefaultKey() {
        return ApiDetails.DEFAULT_API_TOKEN;
    }

    public boolean getKeyType() {
        return mSharedPreferences.getBoolean("key_type", DEFAULT_KEY);
    }

    public boolean getIsValidKey() {
        return  mSharedPreferences.getBoolean("key_value", false);
    }

    public void setKey(String key) {
        mEditor.remove("key");
        mEditor.putString("key", key);
        mEditor.commit();
    }

    public void setIsValidKey(boolean valid) {
        mEditor.remove("key_value");
        mEditor.putBoolean("key_value", valid);
        mEditor.commit();
    }

    public void setKeyType(boolean type) {
        mEditor.remove("key_type");
        mEditor.putBoolean("key_type",type);
        mEditor.commit();

    }

    public void setKey(String key, boolean valid, boolean type) {
        mEditor.remove("key");
        mEditor.remove("key_value");
        mEditor.remove("key_type");
        mEditor.putString("key", key);
        mEditor.putBoolean("key_value", valid);
        mEditor.putBoolean("key_type", type);
        mEditor.commit();
    }
}

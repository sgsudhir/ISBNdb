package com.isbndb.helper;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

/**
 * Created by Sgsudhir on 2/16/2016.
 */
public class SearchHistory {
    private Context context;
    private int index;
    private String data;
    public static final String USER_HISTORY_DATA = "UserHistory";
    SharedPreferences mSharedPreference;
    SharedPreferences.Editor mEditor;

    public SearchHistory(Context context) {
        this.context=context;
        mSharedPreference = context.getSharedPreferences(USER_HISTORY_DATA,Context.MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
        this.index = mSharedPreference.getInt("index",-1);
        if (this.index == -1)
            reInitializeHistory();
    }

    public boolean addToHistory(String query) {
        this.index = mSharedPreference.getInt("index",-1);
        if (this.index == -1)
            return false;
        mEditor.putString((this.index + 1) + "",query);
        mEditor.remove("index");
        mEditor.commit();
        mEditor.putInt("index",this.index + 1);
        mEditor.commit();
        return true;
    }

    public String[] getHistory() {
        this.index = getHistoryLength();
        if (this.index == -1)
            return null;
        String[] history = new String[index];
        for (int i=1; i<= index; i++)
            history[i-1] = mSharedPreference.getString(i + "", "isbn");
        return history;
    }
    
    public int getHistoryLength(){
        return mSharedPreference.getInt("index", -1);
    }

    public void reInitializeHistory(){
        mEditor.clear();
        mEditor.commit();
        mEditor.putInt("index", 0);
        mEditor.commit();
    }

}

package com.emmanuelcorrales.filemanager;

import android.content.Context;
import android.content.SharedPreferences;

class SettingsManager {

    private static final String PREFS_NAME = "com.emmanuelcorrales.filemanager";
    private static final String KEY_IS_LISTVIEW = "key_is_listview";

    private SharedPreferences mSharedPreferences;

    SettingsManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
    }

    void setListView(boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_LISTVIEW, value);
        editor.apply();
    }

    boolean isListView() {
        return mSharedPreferences.getBoolean(KEY_IS_LISTVIEW, true);
    }
}

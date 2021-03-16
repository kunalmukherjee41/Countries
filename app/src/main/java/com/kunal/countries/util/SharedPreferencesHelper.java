package com.kunal.countries.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {
    private static final String PREF_TIME = "Pref Time";
    private static SharedPreferencesHelper mInstance;
    private SharedPreferences preferences;

    private SharedPreferencesHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesHelper(context);
        }
        return mInstance;
    }

    public void saveUpdateTime(long time) {
        preferences.edit().putLong(PREF_TIME, time).apply();
    }

    public long getUpdateTime() {
        return preferences.getLong(PREF_TIME, 0);
    }

}


package com.vishnu.vitalsense;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";

    // Constructor
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Function to login
    public void login(int userId) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // Function to check login status
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Function to get logged in user ID
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // Default value -1 if not found
    }

    // Function to logout
    public void logout() {
        editor.clear();
        editor.apply();
    }
}

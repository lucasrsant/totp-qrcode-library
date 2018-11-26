package br.edu.fei.auth_library;

import android.content.Context;
import android.content.SharedPreferences;

/*package*/ class StorageManager {

    private static SharedPreferences sharedPreferences;

    /*package*/ static void initialize(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    /*package*/ static void persistString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    /*package*/ static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    /*package*/ static boolean containsKey(String key) {
        return sharedPreferences.contains(key);
    }
}

package com.example.hydrus.system;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import android.content.Context;
import android.content.SharedPreferences;

import functional.Maybe;

public class LocalStorage {

    private static final String _PREFERENCES = "_PREFERENCES";
    private static final int _INT_RETRIEVAL_FAILURE_VALUE = -1;
    private static final String _STRING_RETRIEVAL_FAILURE_VALUE = "NONE";

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void storeStringWithKey(String stringToStore, String key) {
        SharedPreferences.Editor editor = _getSharedPreferencesEditorForLocalStorage();
        editor.putString(key, stringToStore);
        editor.apply();
    }

    public static void storeIntWithKey(Integer integerToStore, String key) {
        SharedPreferences.Editor editor = _getSharedPreferencesEditorForLocalStorage();
        editor.putInt(key, integerToStore.intValue());
        editor.apply();
    }

    public static Maybe<String> getStringWithKey(String key) {
        SharedPreferences preferences = _getSharedPreferencesForLocalStorage();
        if (preferences.contains(key)) {
            return _getStringWithKeyInPreferences(key, preferences);
        } else {
            return Maybe.asNothing();
        }
    }

    public static Maybe<Integer> getIntWithKey(String key) {
        SharedPreferences preferences = _getSharedPreferencesForLocalStorage();
        if (preferences.contains(key)) {
            return _getIntegerWithKeyInPreference(key, preferences);
        } else {
            return Maybe.asNothing();
        }
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static Maybe<Integer> _getIntegerWithKeyInPreference(String key, SharedPreferences preferences) {
        int storageRetrivalResultForKey = preferences.getInt(key, _INT_RETRIEVAL_FAILURE_VALUE);
        if(storageRetrivalResultForKey != _INT_RETRIEVAL_FAILURE_VALUE) {
            return Maybe.asObject(Integer.valueOf(storageRetrivalResultForKey));
        } else {
            return Maybe.asNothing();
        }
    }

    private static Maybe<String> _getStringWithKeyInPreferences(String key, SharedPreferences preferences) {
        String retrievedString = preferences.getString(key, _STRING_RETRIEVAL_FAILURE_VALUE);
        if(!(retrievedString.equals(_STRING_RETRIEVAL_FAILURE_VALUE))) {
            return Maybe.asObject(retrievedString);
        } else {
            return Maybe.asNothing();
        }
    }

    private static SharedPreferences.Editor _getSharedPreferencesEditorForLocalStorage() {
        Context context = GlobalContext.getGlobalContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }

    private static SharedPreferences _getSharedPreferencesForLocalStorage() {
        Context context = GlobalContext.getGlobalContext();
        return context.getSharedPreferences(_PREFERENCES, Context.MODE_PRIVATE);
    }
}

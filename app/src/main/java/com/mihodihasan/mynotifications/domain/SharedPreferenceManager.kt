package com.mihodihasan.mynotifications.domain


import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(private val preferences: SharedPreferences) {
    fun save(key: String, value: String): Boolean {
        preferences.edit().putString(key, value).apply()
        return true
    }

    fun save(key: String, value: Int): Boolean {
        preferences.edit().putInt(key, value).apply()
        return true
    }

    fun save(key: String, value: Long): Boolean {
        preferences.edit().putLong(key, value).apply()
        return true
    }

    fun save(key: String, value: Boolean): Boolean {
        preferences.edit().putBoolean(key, value).apply()
        return true
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, -1)
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, -1)
    }

    fun clearSharedPreference() {
        preferences.edit().clear().apply()
    }

    fun remove(key: String) : Boolean {
        preferences.edit().remove(key).apply()
        return true
    }

}

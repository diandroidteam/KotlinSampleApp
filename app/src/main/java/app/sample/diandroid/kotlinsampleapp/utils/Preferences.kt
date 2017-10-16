package app.sample.diandroid.kotlinsampleapp.utils

import android.content.Context
import android.content.SharedPreferences

import java.util.ArrayList
import java.util.HashSet

/**
 * Common class for Shared preferences
 */

class Preferences(private val mContext: Context) {


    private val sharedPreferencesKey = "DIAndroidAppPreferences"
    private var sharedPreferences: SharedPreferences? = null

    fun saveString(key: String, value: String) {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().putString(key, value).apply()
    }

    fun readString(key: String): String? {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString(key, null)
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().putBoolean(key, value).apply()
    }

    fun readBoolean(key: String): Boolean {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean(key, false)
    }

    fun saveLong(key: String, value: Long) {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().putLong(key, value).apply()
    }

    fun readLong(key: String): Long {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        return sharedPreferences!!.getLong(key, 0)
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().putInt(key, value).apply()
    }

    fun readInt(key: String): Int {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt(key, 0)
    }

    fun clearAllPreferences() {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().clear().apply()
    }

    fun saveStringSet(key: String, list: ArrayList<String>) {

        val tasksSet = HashSet(list)
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().putStringSet(key, tasksSet).apply()
    }

    fun readStringSet(key: String): ArrayList<String> {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val list = sharedPreferences!!.getStringSet(key, HashSet())
        return ArrayList(list!!)
    }

    fun clear(key: String) {
        sharedPreferences = mContext.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        sharedPreferences!!.edit().remove(key).apply()
    }

}




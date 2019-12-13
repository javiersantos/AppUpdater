package com.github.javiersantos.appupdater

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

internal class LibraryPreferences(context: Context?) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor
    var appUpdaterShow: Boolean
        get() = sharedPreferences.getBoolean(KeyAppUpdaterShow, true)
        set(res) {
            editor.putBoolean(KeyAppUpdaterShow, res)
            editor.commit()
        }

    var successfulChecks: Int?
        get() = sharedPreferences.getInt(KeySuccessfulChecks, 0)
        set(checks) {
            editor.putInt(KeySuccessfulChecks, checks!!)
            editor.commit()
        }

    companion object {
        const val KeyAppUpdaterShow = "prefAppUpdaterShow"
        const val KeySuccessfulChecks = "prefSuccessfulChecks"
    }

    init {
        editor = sharedPreferences.edit()
    }
}
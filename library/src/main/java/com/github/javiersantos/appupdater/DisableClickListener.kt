package com.github.javiersantos.appupdater

import android.content.Context
import android.content.DialogInterface

/**
 * Click listener for the "Do Not Show Again" button of the update dialog. <br></br>
 * Extend this class to add custom actions to the button on top of the default functionality.
 */
class DisableClickListener(context: Context?) : DialogInterface.OnClickListener {
    private val libraryPreferences: LibraryPreferences = LibraryPreferences(context)
    override fun onClick(dialog: DialogInterface, which: Int) {
        libraryPreferences.appUpdaterShow = false
    }
}
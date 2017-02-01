package com.github.javiersantos.appupdater;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Click listener for the "Do Not Show Again" button of the update dialog. <br/>
 * Extend this class to add custom actions to the button on top of the default functionality.
 */
public class DisableClickListener implements DialogInterface.OnClickListener {

    private final LibraryPreferences libraryPreferences;

    public DisableClickListener(final Context context) {
        libraryPreferences = new LibraryPreferences(context);
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        libraryPreferences.setAppUpdaterShow(false);
    }
}

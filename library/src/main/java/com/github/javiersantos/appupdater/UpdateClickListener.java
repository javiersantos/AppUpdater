package com.github.javiersantos.appupdater;

import android.content.Context;
import android.content.DialogInterface;

import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.net.URL;

/**
 * Click listener for the "Update" button of the update dialog. <br/>
 * Extend this class to add custom actions to the button on top of the default functionality.
 */
public class UpdateClickListener implements DialogInterface.OnClickListener {

    private final Context context;
    private final UpdateFrom updateFrom;
    private final URL apk;

    public UpdateClickListener(final Context context, final UpdateFrom updateFrom, final URL apk) {
        this.context = context;
        this.updateFrom = updateFrom;
        this.apk = apk;
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        UtilsLibrary.goToUpdate(context, updateFrom, apk);
    }
}

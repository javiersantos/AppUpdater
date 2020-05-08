package com.github.javiersantos.appupdater.Displays;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.github.javiersantos.appupdater.UtilsDisplay;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.interfaces.IUpdateDisplay;

import java.net.URL;

public class SnackbarDisplay implements IUpdateDisplay {
    private Snackbar snackbar;
    private Context context;
    String descriptionText;
    String descriptionNoUpdateText;
    UpdateFrom updateFrom;
    private Duration duration;
    URL updateUrl;

    public SnackbarDisplay(Context context) {
        this.context = context;
        this.duration = Duration.NORMAL;
    }

    @Override
    public void onShow() {
        snackbar = UtilsDisplay.showUpdateAvailableSnackbar(context, descriptionText, getDurationEnumToBoolean(duration), updateFrom, updateUrl);
        snackbar.show();
    }

    @Override
    public void onUpdated() {
        snackbar = UtilsDisplay.showUpdateNotAvailableSnackbar(context, descriptionNoUpdateText, getDurationEnumToBoolean(duration));
        snackbar.show();
    }

    static Boolean getDurationEnumToBoolean(Duration duration) {
        Boolean res = false;

        switch (duration) {
            case INDEFINITE:
                res = true;
                break;
        }

        return res;
    }

    @Override
    public IUpdateDisplay setDescriptionUpdate(String text) {
        this.descriptionText = text;
        return this;
    }

    @Override
    public IUpdateDisplay setDescriptionNoUpdate(String text) {
        this.descriptionNoUpdateText = text;
        return this;
    }

    @Override
    public IUpdateDisplay setUpdateUrl(URL url) {
        this.updateUrl = url;
        return this;
    }

    @Override
    public IUpdateDisplay setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
        return this;
    }

    @Override
    public void dismiss() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }
}

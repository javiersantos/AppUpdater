package com.github.javiersantos.appupdater.Displays;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.github.javiersantos.appupdater.R;
import com.github.javiersantos.appupdater.UtilsDisplay;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.objects.Update;

public class SnackbarDisplay extends BaseDisplay {
    private Snackbar snackbar;
    private Duration duration;

    public SnackbarDisplay(Context context) {
        super(context);
        this.duration = Duration.NORMAL;
    }

    @Override
    public void onShow(Update update) {
        setDescriptionUpdate(String.format(context.getResources().getString(R.string.appupdater_update_available_description_snackbar), update.getLatestVersion()));
        snackbar = UtilsDisplay.showUpdateAvailableSnackbar(context, getDescriptionUpdate(), getDurationEnumToBoolean(duration), updateFrom, getUpdateUrl(update));
        snackbar.show();
    }

    @Override
    public void onUpdated(Update update) {
        snackbar = UtilsDisplay.showUpdateNotAvailableSnackbar(context, getDescriptionNoUpdate(), getDurationEnumToBoolean(duration));
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

    public SnackbarDisplay setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public void dismiss() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }
}

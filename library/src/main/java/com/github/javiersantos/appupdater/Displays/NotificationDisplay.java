package com.github.javiersantos.appupdater.Displays;

import android.content.Context;

import com.github.javiersantos.appupdater.R;
import com.github.javiersantos.appupdater.UtilsDisplay;
import com.github.javiersantos.appupdater.objects.Update;

public class NotificationDisplay extends BaseDisplay {
    private String titleUpdate;
    private String titleNoUpdate;
    private int iconResId;

    public NotificationDisplay(Context context) {
        super(context);
        this.iconResId = R.drawable.ic_stat_name;
    }

    @Override
    public void onShow(Update update) {
        super.onShow(update);
        UtilsDisplay.showUpdateAvailableNotification(context, titleUpdate, descriptionText, updateFrom, getUpdateUrl(update), iconResId);
    }

    @Override
    public void onUpdated(Update update) {
        UtilsDisplay.showUpdateNotAvailableNotification(context, titleNoUpdate, descriptionNoUpdateText, iconResId);
    }

    @Override
    public void dismiss() {
        return;
    }
}

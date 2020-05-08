package com.github.javiersantos.appupdater.Displays;

import android.content.Context;

import com.github.javiersantos.appupdater.R;
import com.github.javiersantos.appupdater.UtilsLibrary;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.interfaces.IUpdateDisplay;
import com.github.javiersantos.appupdater.objects.Update;

import java.net.URL;

public abstract class BaseDisplay implements IUpdateDisplay {
    public Context context;
    String descriptionText,descriptionNoUpdateText;
    UpdateFrom updateFrom;
    URL updateUrl;

    public BaseDisplay(Context context) {
        this.context = context;
        descriptionNoUpdateText = String.format(context.getResources().getString(R.string.appupdater_update_not_available_description), UtilsLibrary.getAppName(context));
    }

    @Override
    public void onShow(Update update) {
        if (descriptionText == null){
            descriptionText = String.format(context.getResources().getString(R.string.appupdater_update_available_description_notification), update.getLatestVersion(), UtilsLibrary.getAppName(context));
        }
    }

    @Override
    public IUpdateDisplay setDescriptionNoUpdate(String text) {
        this.descriptionNoUpdateText = text;
        return this;
    }

    @Override
    public IUpdateDisplay setDescriptionUpdate(String text) {
        this.descriptionText = text;
        return this;
    }

    @Override
    public String getDescriptionUpdate() {
        return descriptionText;
    }

    @Override
    public String getDescriptionNoUpdate() {
        return descriptionNoUpdateText;
    }

    @Override
    public URL getUpdateUrl(Update update) {
        return update.getUrlToDownload();
    }

    @Override
    public IUpdateDisplay setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
        return this;
    }

}

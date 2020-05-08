package com.github.javiersantos.appupdater.interfaces;

import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import java.net.URL;

public interface IUpdateDisplay {
    void onShow(Update update);
    void onUpdated(Update update);
    IUpdateDisplay setDescriptionUpdate(String text);
    IUpdateDisplay setDescriptionNoUpdate(String text);
    IUpdateDisplay setUpdateFrom(UpdateFrom updateFrom);
    String getDescriptionUpdate();
    String getDescriptionNoUpdate();
    URL getUpdateUrl(Update update);
    void dismiss();
}

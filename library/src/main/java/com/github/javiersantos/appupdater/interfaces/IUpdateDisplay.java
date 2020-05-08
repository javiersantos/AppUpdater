package com.github.javiersantos.appupdater.interfaces;

import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.net.URL;

public interface IUpdateDisplay {
    void onShow();
    void onUpdated();
    IUpdateDisplay setDescriptionUpdate(String text);
    IUpdateDisplay setDescriptionNoUpdate(String text);
    IUpdateDisplay setUpdateUrl(URL url);
    IUpdateDisplay setUpdateFrom(UpdateFrom updateFrom);
    void dismiss();
}

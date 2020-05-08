package com.github.javiersantos.appupdater.Displays;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.github.javiersantos.appupdater.DisableClickListener;
import com.github.javiersantos.appupdater.R;
import com.github.javiersantos.appupdater.UpdateClickListener;
import com.github.javiersantos.appupdater.UtilsDisplay;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.interfaces.IUpdateDisplay;

import java.net.URL;

public class DialogDisplay implements IUpdateDisplay {
    private Context context;
    private AlertDialog alertDialog;
    private Boolean isDialogCancelable;
    private DialogInterface.OnClickListener btnUpdateClickListener, btnDismissClickListener, btnDisableClickListener;
    String titleUpdate;
    String titleNoUpdate;
    String descriptionText,descriptionNoUpdateText;
    String btnDismiss;
    String btnDisable;
    String btnUpdate;
    UpdateFrom updateFrom;
    URL updateUrl;

    public DialogDisplay(Context context) {
        this.context = context;
        this.titleUpdate = context.getResources().getString(R.string.appupdater_update_available);
        this.titleNoUpdate = context.getResources().getString(R.string.appupdater_update_not_available);
        this.btnDismiss = context.getResources().getString(R.string.appupdater_btn_dismiss);
        this.btnDisable = context.getResources().getString(R.string.appupdater_btn_disable);
        this.btnUpdate = context.getResources().getString(R.string.appupdater_btn_update);
        isDialogCancelable = true;
    }

    @Override
    public void onShow() {
        final DialogInterface.OnClickListener updateClickListener = btnUpdateClickListener == null ? new UpdateClickListener(context, updateFrom, updateUrl) : btnUpdateClickListener;
        final DialogInterface.OnClickListener disableClickListener = btnDisableClickListener == null ? new DisableClickListener(context) : btnDisableClickListener;

        alertDialog = UtilsDisplay.showUpdateAvailableDialog(context, titleUpdate, descriptionText, btnDismiss, btnUpdate, btnDisable, updateClickListener, btnDismissClickListener, disableClickListener);
        alertDialog.setCancelable(isDialogCancelable);
        alertDialog.show();
    }

    @Override
    public void onUpdated() {
        alertDialog = UtilsDisplay.showUpdateNotAvailableDialog(context, titleNoUpdate, descriptionText);
        alertDialog.setCancelable(isDialogCancelable);
        alertDialog.show();
    }

    public Boolean getDialogCancelable() {
        return isDialogCancelable;
    }

    public IUpdateDisplay setDialogCancelable(Boolean dialogCancelable) {
        isDialogCancelable = dialogCancelable;
        return this;
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
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}

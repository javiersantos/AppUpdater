package com.github.javiersantos.appupdater.Displays;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.github.javiersantos.appupdater.DisableClickListener;
import com.github.javiersantos.appupdater.R;
import com.github.javiersantos.appupdater.UpdateClickListener;
import com.github.javiersantos.appupdater.UtilsDisplay;
import com.github.javiersantos.appupdater.UtilsLibrary;
import com.github.javiersantos.appupdater.interfaces.IUpdateDisplay;
import com.github.javiersantos.appupdater.objects.Update;

public class DialogDisplay extends BaseDisplay {
    private AlertDialog alertDialog;
    private Boolean isDialogCancelable;
    private DialogInterface.OnClickListener btnUpdateClickListener, btnDismissClickListener, btnDisableClickListener;
    String titleUpdate;
    String titleNoUpdate;
    String btnDismiss;
    String btnDisable;
    String btnUpdate;
    String descriptionUpdate;


    public DialogDisplay(Context context) {
        super(context);
        this.titleUpdate = context.getResources().getString(R.string.appupdater_update_available);
        this.titleNoUpdate = context.getResources().getString(R.string.appupdater_update_not_available);
        this.btnDismiss = context.getResources().getString(R.string.appupdater_btn_dismiss);
        this.btnDisable = context.getResources().getString(R.string.appupdater_btn_disable);
        this.btnUpdate = context.getResources().getString(R.string.appupdater_btn_update);
        isDialogCancelable = true;
    }

    @Override
    public void onShow(Update update) {
        super.onShow(update);
        final DialogInterface.OnClickListener updateClickListener = btnUpdateClickListener == null ? new UpdateClickListener(context, updateFrom, updateUrl) : btnUpdateClickListener;
        final DialogInterface.OnClickListener disableClickListener = btnDisableClickListener == null ? new DisableClickListener(context) : btnDisableClickListener;

        alertDialog = UtilsDisplay.showUpdateAvailableDialog(context, titleUpdate, getDescriptionUpdate(update), btnDismiss, btnUpdate, btnDisable, updateClickListener, btnDismissClickListener, disableClickListener);
        alertDialog.setCancelable(isDialogCancelable);
        alertDialog.show();
    }

    @Override
    public void onUpdated(Update update) {
        alertDialog = UtilsDisplay.showUpdateNotAvailableDialog(context, titleNoUpdate, getDescriptionUpdate(update));
        alertDialog.setCancelable(isDialogCancelable);
        alertDialog.show();
    }

    public String getDescriptionUpdate(Update update) {
        if (update.getReleaseNotes() != null && !TextUtils.isEmpty(update.getReleaseNotes())) {
            if (TextUtils.isEmpty(descriptionUpdate))
                return update.getReleaseNotes();
            else
                return String.format(context.getResources().getString(R.string.appupdater_update_available_description_dialog_before_release_notes), update.getLatestVersion(), update.getReleaseNotes());
        } else {
            return String.format(context.getResources().getString(R.string.appupdater_update_available_description_dialog), update.getLatestVersion(), UtilsLibrary.getAppName(context));
        }
    }

    public Boolean getDialogCancelable() {
        return isDialogCancelable;
    }

    public IUpdateDisplay setDialogCancelable(Boolean dialogCancelable) {
        isDialogCancelable = dialogCancelable;
        return this;
    }

    @Override
    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}

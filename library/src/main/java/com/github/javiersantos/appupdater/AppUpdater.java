package com.github.javiersantos.appupdater;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.interfaces.IAppUpdater;
import com.github.javiersantos.appupdater.objects.GitHub;
import com.github.javiersantos.appupdater.objects.Update;

public class AppUpdater implements IAppUpdater {
    private Context context;
    private LibraryPreferences libraryPreferences;
    private Display display;
    private UpdateFrom updateFrom;
    private Duration duration;
    private GitHub gitHub;
    private String xmlOrJsonUrl;
    private Integer showEvery;
    private Boolean showAppUpdated;
    private String titleUpdate, descriptionUpdate, btnDismiss, btnUpdate, btnDisable; // Update available
    private String titleNoUpdate, descriptionNoUpdate; // Update not available
    private int iconResId;
    private UtilsAsync.LatestAppVersion latestAppVersion;
    private DialogInterface.OnClickListener btnUpdateClickListener, btnDismissClickListener, btnDisableClickListener;

    private AlertDialog alertDialog;
    private Snackbar snackbar;
    private Boolean isDialogCancelable;

    public AppUpdater(Context context) {
        this.context = context;
        this.libraryPreferences = new LibraryPreferences(context);
        this.display = Display.DIALOG;
        this.updateFrom = UpdateFrom.GOOGLE_PLAY;
        this.duration = Duration.NORMAL;
        this.showEvery = 1;
        this.showAppUpdated = false;
        this.iconResId = R.drawable.ic_stat_name;

        // Dialog
        this.titleUpdate = context.getResources().getString(R.string.appupdater_update_available);
        this.titleNoUpdate = context.getResources().getString(R.string.appupdater_update_not_available);
        this.btnUpdate = context.getResources().getString(R.string.appupdater_btn_update);
        this.btnDismiss = context.getResources().getString(R.string.appupdater_btn_dismiss);
        this.btnDisable = context.getResources().getString(R.string.appupdater_btn_disable);
        this.isDialogCancelable = true;
    }

    @Override
    public AppUpdater setDisplay(Display display) {
        this.display = display;
        return this;
    }

    @Override
    public AppUpdater setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
        return this;
    }

    @Override
    public AppUpdater setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public AppUpdater setGitHubUserAndRepo(@NonNull String user, @NonNull String repo) {
        this.gitHub = new GitHub(user, repo);
        return this;
    }

    @Override
    public AppUpdater setUpdateXML(@NonNull String xmlUrl) {
        this.xmlOrJsonUrl = xmlUrl;
        return this;
    }

    @Override
    public AppUpdater setUpdateJSON(@NonNull String jsonUrl) {
        this.xmlOrJsonUrl = jsonUrl;
        return this;
    }


    @Override
    public AppUpdater showEvery(Integer times) {
        this.showEvery = times;
        return this;
    }

    @Override
    public AppUpdater showAppUpdated(Boolean res) {
        this.showAppUpdated = res;
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogTitleWhenUpdateAvailable(@NonNull String title) {
        setTitleOnUpdateAvailable(title);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogTitleWhenUpdateAvailable(@StringRes int textResource) {
        setTitleOnUpdateAvailable(textResource);
        return this;
    }

    @Override
    public AppUpdater setTitleOnUpdateAvailable(@NonNull String title) {
        this.titleUpdate = title;
        return this;
    }

    @Override
    public AppUpdater setTitleOnUpdateAvailable(@StringRes int textResource) {
        this.titleUpdate = context.getString(textResource);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogDescriptionWhenUpdateAvailable(@NonNull String description) {
        setContentOnUpdateAvailable(description);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogDescriptionWhenUpdateAvailable(@StringRes int textResource) {
        setContentOnUpdateAvailable(textResource);
        return this;
    }

    @Override
    public AppUpdater setContentOnUpdateAvailable(@NonNull String description) {
        this.descriptionUpdate = description;
        return this;
    }

    @Override
    public AppUpdater setContentOnUpdateAvailable(@StringRes int textResource) {
        this.descriptionUpdate = context.getString(textResource);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogTitleWhenUpdateNotAvailable(@NonNull String title) {
        setTitleOnUpdateNotAvailable(title);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogTitleWhenUpdateNotAvailable(@StringRes int textResource) {
        setTitleOnUpdateNotAvailable(textResource);
        return this;
    }

    @Override
    public AppUpdater setTitleOnUpdateNotAvailable(@NonNull String title) {
        this.titleNoUpdate = title;
        return this;
    }

    @Override
    public AppUpdater setTitleOnUpdateNotAvailable(@StringRes int textResource) {
        this.titleNoUpdate = context.getString(textResource);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@NonNull String description) {
        setContentOnUpdateNotAvailable(description);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@StringRes int textResource) {
        setContentOnUpdateNotAvailable(textResource);
        return this;
    }

    @Override
    public AppUpdater setContentOnUpdateNotAvailable(@NonNull String description) {
        this.descriptionNoUpdate = description;
        return this;
    }

    @Override
    public AppUpdater setContentOnUpdateNotAvailable(@StringRes int textResource) {
        this.descriptionNoUpdate = context.getString(textResource);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogButtonUpdate(@NonNull String text) {
        setButtonUpdate(text);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogButtonUpdate(@StringRes int textResource) {
        setButtonUpdate(textResource);
        return this;
    }

    @Override
    public AppUpdater setButtonUpdate(@NonNull String text) {
        this.btnUpdate = text;
        return this;
    }

    @Override
    public AppUpdater setButtonUpdate(@StringRes int textResource) {
        this.btnUpdate = context.getString(textResource);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogButtonDismiss(@NonNull String text) {
        setButtonDismiss(text);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogButtonDismiss(@StringRes int textResource) {
        setButtonDismiss(textResource);
        return this;
    }

    @Override
    public AppUpdater setButtonDismiss(@NonNull String text) {
        this.btnDismiss = text;
        return this;
    }

    @Override
    public AppUpdater setButtonDismiss(@StringRes int textResource) {
        this.btnDismiss = context.getString(textResource);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogButtonDoNotShowAgain(@NonNull String text) {
        setButtonDoNotShowAgain(text);
        return this;
    }

    @Override
    @Deprecated
    public AppUpdater setDialogButtonDoNotShowAgain(@StringRes int textResource) {
        setButtonDoNotShowAgain(textResource);
        return this;
    }

    @Override
    public AppUpdater setButtonDoNotShowAgain(@NonNull String text) {
        this.btnDisable = text;
        return this;
    }

    @Override
    public AppUpdater setButtonDoNotShowAgain(@StringRes int textResource) {
        this.btnDisable = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setButtonUpdateClickListener(final DialogInterface.OnClickListener clickListener) {
        btnUpdateClickListener = clickListener;
        return this;
    }

    @Override
    public AppUpdater setButtonDismissClickListener(final DialogInterface.OnClickListener clickListener) {
        btnDismissClickListener = clickListener;
        return this;
    }

    @Override
    public AppUpdater setButtonDoNotShowAgainClickListener(final DialogInterface.OnClickListener clickListener) {
        btnDisableClickListener = clickListener;
        return this;
    }

    @Override
    public AppUpdater setIcon(@DrawableRes int iconRes) {
        this.iconResId = iconRes;
        return this;
    }

    @Override
    public AppUpdater setCancelable(Boolean isDialogCancelable) {
        this.isDialogCancelable = isDialogCancelable;
        return this;
    }

    @Override
    public AppUpdater init() {
        start();
        return this;
    }

    @Override
    public void start() {
        latestAppVersion = new UtilsAsync.LatestAppVersion(context, false, updateFrom, gitHub, xmlOrJsonUrl, new LibraryListener() {
            @Override
            public void onSuccess(Update update) {
                if (context instanceof Activity && ((Activity) context).isFinishing()) {
                    return;
                }

                Update installedUpdate = new Update(UtilsLibrary.getAppInstalledVersion(context), UtilsLibrary.getAppInstalledVersionCode(context));
                if (UtilsLibrary.isUpdateAvailable(installedUpdate, update)) {
                    Integer successfulChecks = libraryPreferences.getSuccessfulChecks();
                    if (UtilsLibrary.isAbleToShow(successfulChecks, showEvery)) {
                        switch (display) {
                            case DIALOG:
                                final DialogInterface.OnClickListener updateClickListener = btnUpdateClickListener == null ? new UpdateClickListener(context, updateFrom, update.getUrlToDownload()) : btnUpdateClickListener;
                                final DialogInterface.OnClickListener disableClickListener = btnDisableClickListener == null ? new DisableClickListener(context) : btnDisableClickListener;

                                alertDialog = UtilsDisplay.showUpdateAvailableDialog(context, titleUpdate, getDescriptionUpdate(context, update, Display.DIALOG), btnDismiss, btnUpdate, btnDisable, updateClickListener, btnDismissClickListener, disableClickListener);
                                alertDialog.setCancelable(isDialogCancelable);
                                alertDialog.show();
                                break;
                            case SNACKBAR:
                                snackbar = UtilsDisplay.showUpdateAvailableSnackbar(context, getDescriptionUpdate(context, update, Display.SNACKBAR), UtilsLibrary.getDurationEnumToBoolean(duration), updateFrom, update.getUrlToDownload());
                                snackbar.show();
                                break;
                            case NOTIFICATION:
                                UtilsDisplay.showUpdateAvailableNotification(context, titleUpdate, getDescriptionUpdate(context, update, Display.NOTIFICATION), updateFrom, update.getUrlToDownload(), iconResId);
                                break;
                        }
                    }
                    libraryPreferences.setSuccessfulChecks(successfulChecks + 1);
                } else if (showAppUpdated) {
                    switch (display) {
                        case DIALOG:
                            alertDialog = UtilsDisplay.showUpdateNotAvailableDialog(context, titleNoUpdate, getDescriptionNoUpdate(context));
                            alertDialog.setCancelable(isDialogCancelable);
                            alertDialog.show();
                            break;
                        case SNACKBAR:
                            snackbar = UtilsDisplay.showUpdateNotAvailableSnackbar(context, getDescriptionNoUpdate(context), UtilsLibrary.getDurationEnumToBoolean(duration));
                            snackbar.show();
                            break;
                        case NOTIFICATION:
                            UtilsDisplay.showUpdateNotAvailableNotification(context, titleNoUpdate, getDescriptionNoUpdate(context), iconResId);
                            break;
                    }
                }
            }

            @Override
            public void onFailed(AppUpdaterError error) {
                if (error == AppUpdaterError.UPDATE_VARIES_BY_DEVICE) {
                    Log.e("AppUpdater", "UpdateFrom.GOOGLE_PLAY isn't valid: update varies by device.");
                } else if (error == AppUpdaterError.GITHUB_USER_REPO_INVALID) {
                    throw new IllegalArgumentException("GitHub user or repo is empty!");
                } else if (error == AppUpdaterError.XML_URL_MALFORMED) {
                    throw new IllegalArgumentException("XML file is not valid!");
                } else if (error == AppUpdaterError.JSON_URL_MALFORMED) {
                    throw new IllegalArgumentException("JSON file is not valid!");
                }
            }
        });

        latestAppVersion.execute();
    }

    @Override
    public void stop() {
        if (latestAppVersion != null && !latestAppVersion.isCancelled()) {
            latestAppVersion.cancel(true);
        }
    }

    @Override
    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private String getDescriptionUpdate(Context context, Update update, Display display) {
        if (descriptionUpdate == null || TextUtils.isEmpty(descriptionUpdate)) {
            switch (display) {
                case DIALOG:
                    if (update.getReleaseNotes() != null && !TextUtils.isEmpty(update.getReleaseNotes())) {
                        if (TextUtils.isEmpty(descriptionUpdate))
                            return update.getReleaseNotes();
                        else
                            return String.format(context.getResources().getString(R.string.appupdater_update_available_description_dialog_before_release_notes), update.getLatestVersion(), update.getReleaseNotes());
                    } else {
                        return String.format(context.getResources().getString(R.string.appupdater_update_available_description_dialog), update.getLatestVersion(), UtilsLibrary.getAppName(context));
                    }

                case SNACKBAR:
                    return String.format(context.getResources().getString(R.string.appupdater_update_available_description_snackbar), update.getLatestVersion());

                case NOTIFICATION:
                    return String.format(context.getResources().getString(R.string.appupdater_update_available_description_notification), update.getLatestVersion(), UtilsLibrary.getAppName(context));

            }
        }

        return descriptionUpdate;
    }

    private String getDescriptionNoUpdate(Context context) {
        if (descriptionNoUpdate == null) {
            return String.format(context.getResources().getString(R.string.appupdater_update_not_available_description), UtilsLibrary.getAppName(context));
        } else {
            return descriptionNoUpdate;
        }
    }

}

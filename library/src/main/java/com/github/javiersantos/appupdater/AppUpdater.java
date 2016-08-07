package com.github.javiersantos.appupdater;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;
import com.github.javiersantos.appupdater.objects.Update;

public class AppUpdater implements IAppUpdater {
    private Context context;
    private LibraryPreferences libraryPreferences;
    private Display display;
    private UpdateFrom updateFrom;
    private Duration duration;
    private GitHub gitHub;
    private String xmlUrl;
    private Integer showEvery;
    private Boolean showAppUpdated;
    private String titleUpdate, descriptionUpdate, btnDismiss, btnUpdate, btnDisable; // Update available
    private String titleNoUpdate, descriptionNoUpdate; // Update not available
    private int iconResId;
    private UtilsAsync.LatestAppVersion latestAppVersion;

    private AlertDialog alertDialog;
    private Snackbar snackbar;

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
        this.xmlUrl = xmlUrl;
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
    public AppUpdater setDialogTitleWhenUpdateAvailable(@NonNull String title) {
        this.titleUpdate = title;
        return this;
    }

    @Override
    public AppUpdater setDialogTitleWhenUpdateAvailable(@StringRes int textResource) {
        this.titleUpdate = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setDialogDescriptionWhenUpdateAvailable(@NonNull String description) {
        this.descriptionUpdate = description;
        return this;
    }

    @Override
    public AppUpdater setDialogDescriptionWhenUpdateAvailable(@StringRes int textResource) {
        this.descriptionUpdate = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setDialogTitleWhenUpdateNotAvailable(@NonNull String title) {
        this.titleNoUpdate = title;
        return this;
    }

    @Override
    public AppUpdater setDialogTitleWhenUpdateNotAvailable(@StringRes int textResource) {
        this.titleNoUpdate = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@NonNull String description) {
        this.descriptionNoUpdate = description;
        return this;
    }

    @Override
    public AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@StringRes int textResource) {
        this.descriptionNoUpdate = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setDialogButtonUpdate(@NonNull String text) {
        this.btnUpdate = text;
        return this;
    }

    @Override
    public AppUpdater setDialogButtonUpdate(@StringRes int textResource) {
        this.btnUpdate = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setDialogButtonDismiss(@NonNull String text) {
        this.btnDismiss = text;
        return this;
    }

    @Override
    public AppUpdater setDialogButtonDismiss(@StringRes int textResource) {
        this.btnDismiss = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setDialogButtonDoNotShowAgain(@NonNull String text) {
        this.btnDisable = text;
        return this;
    }

    @Override
    public AppUpdater setDialogButtonDoNotShowAgain(@StringRes int textResource) {
        this.btnDisable = context.getString(textResource);
        return this;
    }

    @Override
    public AppUpdater setIcon(@DrawableRes int iconRes) {
        this.iconResId = iconRes;
        return this;
    }

    @Override
    public AppUpdater init() {
        start();
        return this;
    }

    @Override
    public void start() {
        latestAppVersion = new UtilsAsync.LatestAppVersion(context, false, updateFrom, gitHub, xmlUrl, new LibraryListener() {
            @Override
            public void onSuccess(Update update) {
                if (UtilsLibrary.isUpdateAvailable(UtilsLibrary.getAppInstalledVersion(context), update.getLatestVersion())) {
                    Integer successfulChecks = libraryPreferences.getSuccessfulChecks();
                    if (UtilsLibrary.isAbleToShow(successfulChecks, showEvery)) {
                        switch (display) {
                            case DIALOG:
                                alertDialog = UtilsDisplay.showUpdateAvailableDialog(context, titleUpdate, getDescriptionUpdate(context, update, Display.DIALOG), btnDismiss, btnUpdate, btnDisable, updateFrom, update.getUrlToDownload());
                                alertDialog.show();
                                break;
                            case SNACKBAR:
                                snackbar = UtilsDisplay.showUpdateAvailableSnackbar(context, getDescriptionUpdate(context, update, Display.SNACKBAR), UtilsLibrary.getDurationEnumToBoolean(duration), updateFrom, update.getUrlToDownload());
                                snackbar.show();
                                break;
                            case NOTIFICATION:
                                UtilsDisplay.showUpdateAvailableNotification(context, context.getResources().getString(R.string.appupdater_update_available), getDescriptionUpdate(context, update, Display.NOTIFICATION), updateFrom, update.getUrlToDownload(), iconResId);
                                break;
                        }
                    }
                    libraryPreferences.setSuccessfulChecks(successfulChecks + 1);
                } else if (showAppUpdated) {
                    switch (display) {
                        case DIALOG:
                            alertDialog = UtilsDisplay.showUpdateNotAvailableDialog(context, titleNoUpdate, getDescriptionNoUpdate(context));
                            alertDialog.show();
                            break;
                        case SNACKBAR:
                            snackbar = UtilsDisplay.showUpdateNotAvailableSnackbar(context, getDescriptionNoUpdate(context), UtilsLibrary.getDurationEnumToBoolean(duration));
                            snackbar.show();
                            break;
                        case NOTIFICATION:
                            UtilsDisplay.showUpdateNotAvailableNotification(context, context.getResources().getString(R.string.appupdater_update_not_available), getDescriptionNoUpdate(context), iconResId);
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
        if (descriptionUpdate == null) {
            switch (display) {
                case DIALOG:
                    if (update.getReleaseNotes() != null && !TextUtils.isEmpty(update.getReleaseNotes())) {
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

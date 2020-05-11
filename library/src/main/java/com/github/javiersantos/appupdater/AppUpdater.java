package com.github.javiersantos.appupdater;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import com.github.javiersantos.appupdater.Displays.DialogDisplay;
import com.github.javiersantos.appupdater.Displays.NotificationDisplay;
import com.github.javiersantos.appupdater.Displays.SnackbarDisplay;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.interfaces.IAppUpdater;
import com.github.javiersantos.appupdater.interfaces.IUpdateDisplay;
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

    private Boolean isDialogCancelable;
    private IUpdateDisplay displayUpdater;

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
        displayUpdater = new DialogDisplay(context);
    }

    public AppUpdater setDisplayUpdater(IUpdateDisplay displayUpdater) {
        this.displayUpdater = displayUpdater;
        return this;
    }

    public IUpdateDisplay getDisplayUpdater(){
        return displayUpdater;
    }

    @Override
    public AppUpdater setDisplay(Display display) {
        this.display = display;
        switch (display){
            case SNACKBAR:
                displayUpdater = new SnackbarDisplay(context).setUpdateFrom(updateFrom);
                break;
            case NOTIFICATION:
                displayUpdater = new NotificationDisplay(context).setUpdateFrom(updateFrom);
                break;
        }
        return this;
    }

    @Override
    public AppUpdater setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
        displayUpdater.setUpdateFrom(updateFrom);
        return this;
    }

    @Override
    public AppUpdater setDuration(Duration duration) {
        SnackbarDisplay disp = (SnackbarDisplay) displayUpdater;
        disp.setDuration(duration);
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
                        displayUpdater.setUpdateFrom(updateFrom);
                        displayUpdater.onShow(update);
                    }
                    libraryPreferences.setSuccessfulChecks(successfulChecks + 1);
                } else if (showAppUpdated) {
                    displayUpdater.onUpdated(update);
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
        displayUpdater.dismiss();
    }

}

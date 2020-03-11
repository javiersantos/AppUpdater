package com.github.javiersantos.appupdater.interfaces;

import android.content.DialogInterface;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.DisableClickListener;
import com.github.javiersantos.appupdater.UpdateClickListener;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

public interface IAppUpdater {
    /**
     * Set the type of message used to notify the user when a new update has been found. Default: DIALOG.
     *
     * @param display how the update will be shown
     * @return this
     * @see com.github.javiersantos.appupdater.enums.Display
     */
    AppUpdater setDisplay(Display display);

    /**
     * Set the source where the latest update can be found. Default: GOOGLE_PLAY.
     *
     * @param updateFrom source where the latest update is uploaded. If GITHUB is selected, .setGitHubAndRepo method is required.
     * @return this
     * @see com.github.javiersantos.appupdater.enums.UpdateFrom
     * @see <a href="https://github.com/javiersantos/AppUpdater/wiki">Additional documentation</a>
     */
    AppUpdater setUpdateFrom(UpdateFrom updateFrom);

    /**
     * Set the duration of the Snackbar Default: NORMAL.
     *
     * @param duration duration of the Snackbar
     * @return this
     * @see com.github.javiersantos.appupdater.enums.Duration
     */
    AppUpdater setDuration(Duration duration);

    /**
     * Set the user and repo where the releases are uploaded. You must upload your updates as a release in order to work properly tagging them as vX.X.X or X.X.X.
     *
     * @param user GitHub user
     * @param repo GitHub repository
     * @return this
     */
    AppUpdater setGitHubUserAndRepo(@NonNull String user, @NonNull String repo);

    /**
     * Set the url to the xml file with the latest version info.
     *
     * @param xmlUrl file
     * @return this
     */
    AppUpdater setUpdateXML(@NonNull String xmlUrl);

    /**
     * Set the url to the json file with the latest version info.
     *
     * @param jsonUrl file
     * @return this
     */

    AppUpdater setUpdateJSON(@NonNull String jsonUrl);

    /**
     * Set the times the app ascertains that a new update is available and display a dialog, Snackbar or notification. It makes the updates less invasive. Default: 1.
     *
     * @param times every X times
     * @return this
     */
    AppUpdater showEvery(Integer times);

    /**
     * Set if the dialog, Snackbar or notification is displayed although there aren't updates. Default: false.
     *
     * @param res true to show, false otherwise
     * @return this
     */
    AppUpdater showAppUpdated(Boolean res);

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param title for the dialog
     * @return this
     * @deprecated use {@link #setTitleOnUpdateAvailable(String)} instead
     */
    AppUpdater setDialogTitleWhenUpdateAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setTitleOnUpdateAvailable(int)} instead
     */
    AppUpdater setDialogTitleWhenUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param title for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param description for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateAvailable(String)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateAvailable(int)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param description for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateAvailable(@StringRes int textResource);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param title for the dialog
     * @return this
     * @deprecated use {@link #setTitleOnUpdateNotAvailable(String)} instead
     */
    AppUpdater setDialogTitleWhenUpdateNotAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setTitleOnUpdateNotAvailable(int)} instead
     */
    AppUpdater setDialogTitleWhenUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param title for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateNotAvailable(@NonNull String title);

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setTitleOnUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param description for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateNotAvailable(String)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     * @deprecated use {@link #setContentOnUpdateNotAvailable(int)} instead
     */
    AppUpdater setDialogDescriptionWhenUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param description for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateNotAvailable(@NonNull String description);

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    AppUpdater setContentOnUpdateNotAvailable(@StringRes int textResource);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param text for the update button
     * @return this
     * @deprecated use {@link #setButtonUpdate(String)} instead
     */
    AppUpdater setDialogButtonUpdate(@NonNull String text);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the update button
     * @return this
     * @deprecated use {@link #setButtonUpdate(int)} instead
     */
    AppUpdater setDialogButtonUpdate(@StringRes int textResource);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param text for the update button
     * @return this
     */
    AppUpdater setButtonUpdate(@NonNull String text);

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the update button
     * @return this
     */
    AppUpdater setButtonUpdate(@StringRes int textResource);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param text for the dismiss button
     * @return this
     * @deprecated use {@link #setButtonDismiss(String)} instead
     */
    AppUpdater setDialogButtonDismiss(@NonNull String text);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the dismiss button
     * @return this
     * @deprecated  use {@link #setButtonDismiss(int)} instead
     */
    AppUpdater setDialogButtonDismiss(@StringRes int textResource);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param text for the dismiss button
     * @return this
     */
    AppUpdater setButtonDismiss(@NonNull String text);

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the dismiss button
     * @return this
     */
    AppUpdater setButtonDismiss(@StringRes int textResource);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param text for the disable button
     * @return this
     * @deprecated use {@link #setButtonDoNotShowAgain(String)} instead
     */
    AppUpdater setDialogButtonDoNotShowAgain(@NonNull String text);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the disable button
     * @return this
     * @deprecated use {@link #setButtonDoNotShowAgain(int)} instead
     */
    AppUpdater setDialogButtonDoNotShowAgain(@StringRes int textResource);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param text for the disable button
     * @return this
     */
    AppUpdater setButtonDoNotShowAgain(@NonNull String text);

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the disable button
     * @return this
     */
    AppUpdater setButtonDoNotShowAgain(@StringRes int textResource);

    /**
     * Sets a custom click listener for the "Update" button when a new update is available.
     * In order to maintain the default functionality, extend {@link UpdateClickListener}
     *
     * @param clickListener for update button
     * @return this
     */
    AppUpdater setButtonUpdateClickListener(DialogInterface.OnClickListener clickListener);

    /**
     * Sets a custom click listener for the "Dismiss" button when a new update is available.
     *
     * @param clickListener for dismiss button
     * @return this
     */
    AppUpdater setButtonDismissClickListener(DialogInterface.OnClickListener clickListener);

    /**
     * Sets a custom click listener for the "Don't show again" button when a new update is available. <br/>
     * In order to maintain the default functionality, extend {@link DisableClickListener}
     *
     * @param clickListener for disable button
     * @return this
     */
    AppUpdater setButtonDoNotShowAgainClickListener(DialogInterface.OnClickListener clickListener);

    /**
     * Sets the resource identifier for the small notification icon
     *
     * @param iconRes The id of the drawable item
     * @return this
     */
    AppUpdater setIcon(@DrawableRes int iconRes);

    /**
     * Make update dialog non-cancelable, and
     * force user to make update
     *  @param isCancelable true to force user to make update, false otherwise
     *  @return this
     */
    AppUpdater setCancelable(Boolean isCancelable);

    /**
     * Execute AppUpdater in background.
     *
     * @return this
     * @deprecated use {@link #start()} instead
     */
    AppUpdater init();

    /**
     * Execute AppUpdater in background.
     */
    void start();

    /**
     * Stops the execution of AppUpdater.
     */
    void stop();

    /**
     * Dismisses the alert dialog or the snackbar.
     */
    void dismiss();

    interface LibraryListener {
        void onSuccess(Update update);

        void onFailed(AppUpdaterError error);
    }
}

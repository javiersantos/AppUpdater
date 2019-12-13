package com.github.javiersantos.appupdater.interfaces

import android.content.DialogInterface
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.Duration
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update

interface IAppUpdater {
    /**
     * Set the type of message used to notify the user when a new update has been found. Default: DIALOG.
     *
     * @param display how the update will be shown
     * @return this
     * @see com.github.javiersantos.appupdater.enums.Display
     */
    fun setDisplay(display: Display): AppUpdater

    /**
     * Set the source where the latest update can be found. Default: GOOGLE_PLAY.
     *
     * @param updateFrom source where the latest update is uploaded. If GITHUB is selected, .setGitHubAndRepo method is required.
     * @return this
     * @see com.github.javiersantos.appupdater.enums.UpdateFrom
     *
     * @see [Additional documentation](https://github.com/javiersantos/AppUpdater/wiki)
     */
    fun setUpdateFrom(updateFrom: UpdateFrom): AppUpdater

    /**
     * Set the duration of the Snackbar Default: NORMAL.
     *
     * @param duration duration of the Snackbar
     * @return this
     * @see com.github.javiersantos.appupdater.enums.Duration
     */
    fun setDuration(duration: Duration): AppUpdater

    /**
     * Set the user and repo where the releases are uploaded. You must upload your updates as a release in order to work properly tagging them as vX.X.X or X.X.X.
     *
     * @param user GitHub user
     * @param repo GitHub repository
     * @return this
     */
    fun setGitHubUserAndRepo(user: String, repo: String): AppUpdater

    /**
     * Set the url to the xml file with the latest version info.
     *
     * @param xmlUrl file
     * @return this
     */
    fun setUpdateXML(xmlUrl: String): AppUpdater

    /**
     * Set the url to the json file with the latest version info.
     *
     * @param jsonUrl file
     * @return this
     */
    fun setUpdateJSON(jsonUrl: String): AppUpdater

    /**
     * Set the times the app ascertains that a new update is available and display a dialog, Snackbar or notification. It makes the updates less invasive. Default: 1.
     *
     * @param times every X times
     * @return this
     */
    fun showEvery(times: Int): AppUpdater

    /**
     * Set if the dialog, Snackbar or notification is displayed although there aren't updates. Default: false.
     *
     * @param res true to show, false otherwise
     * @return this
     */
    fun showAppUpdated(res: Boolean): AppUpdater

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param title for the dialog
     * @return this
     */
    @Deprecated("use {@link #setTitleOnUpdateAvailable(String)} instead")
    fun setDialogTitleWhenUpdateAvailable(title: String): AppUpdater

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    @Deprecated("use {@link #setTitleOnUpdateAvailable(int)} instead")
    fun setDialogTitleWhenUpdateAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param title for the dialog
     * @return this
     */
    fun setTitleOnUpdateAvailable(title: String): AppUpdater

    /**
     * Set a custom title for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    fun setTitleOnUpdateAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param description for the dialog
     * @return this
     */
    @Deprecated("use {@link #setContentOnUpdateAvailable(String)} instead")
    fun setDialogDescriptionWhenUpdateAvailable(description: String): AppUpdater

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    @Deprecated("use {@link #setContentOnUpdateAvailable(int)} instead")
    fun setDialogDescriptionWhenUpdateAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param description for the dialog
     * @return this
     */
    fun setContentOnUpdateAvailable(description: String): AppUpdater

    /**
     * Set a custom description for the dialog when an update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    fun setContentOnUpdateAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param title for the dialog
     * @return this
     */
    @Deprecated("use {@link #setTitleOnUpdateNotAvailable(String)} instead")
    fun setDialogTitleWhenUpdateNotAvailable(title: String): AppUpdater

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    @Deprecated("use {@link #setTitleOnUpdateNotAvailable(int)} instead")
    fun setDialogTitleWhenUpdateNotAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param title for the dialog
     * @return this
     */
    fun setTitleOnUpdateNotAvailable(title: String): AppUpdater

    /**
     * Set a custom title for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    fun setTitleOnUpdateNotAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param description for the dialog
     * @return this
     */
    @Deprecated("use {@link #setContentOnUpdateNotAvailable(String)} instead")
    fun setDialogDescriptionWhenUpdateNotAvailable(description: String): AppUpdater

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    @Deprecated("use {@link #setContentOnUpdateNotAvailable(int)} instead")
    fun setDialogDescriptionWhenUpdateNotAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param description for the dialog
     * @return this
     */
    fun setContentOnUpdateNotAvailable(description: String): AppUpdater

    /**
     * Set a custom description for the dialog when no update is available.
     *
     * @param textResource resource from the strings xml file for the dialog
     * @return this
     */
    fun setContentOnUpdateNotAvailable(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param text for the update button
     * @return this
     */
    @Deprecated("use {@link #setButtonUpdate(String)} instead")
    fun setDialogButtonUpdate(text: String): AppUpdater

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the update button
     * @return this
     */
    @Deprecated("use {@link #setButtonUpdate(int)} instead")
    fun setDialogButtonUpdate(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param text for the update button
     * @return this
     */
    fun setButtonUpdate(text: String): AppUpdater

    /**
     * Set a custom "Update" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the update button
     * @return this
     */
    fun setButtonUpdate(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param text for the dismiss button
     * @return this
     */
    @Deprecated("use {@link #setButtonDismiss(String)} instead")
    fun setDialogButtonDismiss(text: String): AppUpdater

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the dismiss button
     * @return this
     */
    @Deprecated("use {@link #setButtonDismiss(int)} instead")
    fun setDialogButtonDismiss(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param text for the dismiss button
     * @return this
     */
    fun setButtonDismiss(text: String): AppUpdater

    /**
     * Set a custom "Dismiss" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the dismiss button
     * @return this
     */
    fun setButtonDismiss(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param text for the disable button
     * @return this
     */
    @Deprecated("use {@link #setButtonDoNotShowAgain(String)} instead")
    fun setDialogButtonDoNotShowAgain(text: String): AppUpdater

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the disable button
     * @return this
     */
    @Deprecated("use {@link #setButtonDoNotShowAgain(int)} instead")
    fun setDialogButtonDoNotShowAgain(@StringRes textResource: Int): AppUpdater

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param text for the disable button
     * @return this
     */
    fun setButtonDoNotShowAgain(text: String): AppUpdater

    /**
     * Set a custom "Don't show again" button text when a new update is available.
     *
     * @param textResource resource from the strings xml file for the disable button
     * @return this
     */
    fun setButtonDoNotShowAgain(@StringRes textResource: Int): AppUpdater

    /**
     * Sets a custom click listener for the "Update" button when a new update is available.
     * In order to maintain the default functionality, extend [UpdateClickListener]
     *
     * @param clickListener for update button
     * @return this
     */
    fun setButtonUpdateClickListener(clickListener: DialogInterface.OnClickListener): AppUpdater

    /**
     * Sets a custom click listener for the "Dismiss" button when a new update is available.
     *
     * @param clickListener for dismiss button
     * @return this
     */
    fun setButtonDismissClickListener(clickListener: DialogInterface.OnClickListener): AppUpdater

    /**
     * Sets a custom click listener for the "Don't show again" button when a new update is available. <br></br>
     * In order to maintain the default functionality, extend [DisableClickListener]
     *
     * @param clickListener for disable button
     * @return this
     */
    fun setButtonDoNotShowAgainClickListener(clickListener: DialogInterface.OnClickListener): AppUpdater

    /**
     * Sets the resource identifier for the small notification icon
     *
     * @param iconRes The id of the drawable item
     * @return this
     */
    fun setIcon(@DrawableRes iconRes: Int): AppUpdater

    /**
     * Make update dialog non-cancelable, and
     * force user to make update
     * @param isCancelable true to force user to make update, false otherwise
     * @return this
     */
    fun setCancelable(isCancelable: Boolean): AppUpdater

    /**
     * Execute AppUpdater in background.
     *
     * @return this
     */
    @Deprecated("use {@link #start()} instead")
    fun init(): AppUpdater

    /**
     * Execute AppUpdater in background.
     */
    fun start()

    /**
     * Stops the execution of AppUpdater.
     */
    fun stop()

    /**
     * Dismisses the alert dialog or the snackbar.
     */
    fun dismiss()

    interface LibraryListener {
        fun onSuccess(update: Update)
        fun onFailed(error: AppUpdaterError)
    }
}
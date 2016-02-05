package com.github.javiersantos.appupdater;

import android.content.Context;

import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;

public class AppUpdater {
    private Context context;
    private Display display;
    private UpdateFrom updateFrom;
    private Duration duration;
    private GitHub gitHub;
    private Integer showEvery;
    private Boolean showAppUpdated;

    public AppUpdater(Context context) {
        this.context  = context;
        this.display = Display.DIALOG;
        this.updateFrom = UpdateFrom.GOOGLE_PLAY;
        this.duration = Duration.NORMAL;
        this.showEvery = 1;
        this.showAppUpdated = false;
    }

    /**
     * Set the type of message used to notify the user when a new update has been found. Default: DIALOG.
     *
     * @param display how the update will be shown
     * @return this
     * @see com.github.javiersantos.appupdater.enums.Display
     */
    public AppUpdater setDisplay(Display display) {
        this.display = display;
        return this;
    }

    /**
     * Set the source where the latest update can be found. Default: GOOGLE_PLAY.
     *
     * @param updateFrom source where the latest update is uploaded. If GITHUB is selected, .setGitHubAndRepo method is required.
     * @return this
     * @see com.github.javiersantos.appupdater.enums.UpdateFrom
     */
    public AppUpdater setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
        return this;
    }

    /**
     * Set the duration of the Snackbar Default: NORMAL.
     *
     * @param duration duration of the Snackbar
     * @return this
     * @see com.github.javiersantos.appupdater.enums.Duration
     */
    public AppUpdater setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Set the user and repo where the releases are uploaded. You must upload your updates as a release in order to work properly taggin them as vX.X.X or X.X.X.
     *
     * @param user GitHub user
     * @param repo GitHub repository
     * @return this
     */
    public AppUpdater setGitHubUserAndRepo(String user, String repo) {
        this.gitHub = new GitHub(user, repo);
        return this;
    }

    /**
     * Set the times the app ascertains that a new update is available and display a dialog, Snackbar or notification. It makes the updates less invasive. Default: 1.
     *
     * @param times every X times
     * @return this
     */
    public AppUpdater showEvery(Integer times) {
        this.showEvery = times;
        return this;
    }

    /**
     * Set if the dialog, Snackbar or notification is displayed although there aren't updates. Default: false.
     *
     * @param res true to show, false otherwise
     * @return this
     */
    public AppUpdater showAppUpdated(Boolean res) {
        this.showAppUpdated = res;
        return this;
    }

    /**
     * Execute AppUpdater in background.
     *
     * @return this
     */
    public AppUpdater init() {
        UtilsAsync.LatestAppVersion latestAppVersion = new UtilsAsync.LatestAppVersion(context, showEvery, showAppUpdated, updateFrom, display, duration, gitHub);
        latestAppVersion.execute();
        return this;
    }

}

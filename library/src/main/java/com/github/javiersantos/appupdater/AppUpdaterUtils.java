package com.github.javiersantos.appupdater;

import android.content.Context;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;

public class AppUpdaterUtils {
    private Context context;
    private AppUpdaterListener appUpdaterListener;
    private UpdateFrom updateFrom;
    private GitHub gitHub;

    public interface AppUpdaterListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't retrieve the latest version
         *
         * @param latestVersion available in the provided source
         * @param isUpdateAvailable compare installed version with the latest one
         */
        void onSuccess(String latestVersion, Boolean isUpdateAvailable);
        void onFailed(AppUpdaterError error);
    }

    public AppUpdaterUtils(Context context) {
        this.context = context;
        this.updateFrom = UpdateFrom.GOOGLE_PLAY;
    }

    /**
     * Set the source where the latest update can be found. Default: GOOGLE_PLAY.
     *
     * @param updateFrom source where the latest update is uploaded. If GITHUB is selected, .setGitHubAndRepo method is required.
     * @return this
     * @see com.github.javiersantos.appupdater.enums.UpdateFrom
     */
    public AppUpdaterUtils setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
        return this;
    }

    /**
     * Set the user and repo where the releases are uploaded. You must upload your updates as a release in order to work properly tagging them as vX.X.X or X.X.X.
     *
     * @param user GitHub user
     * @param repo GitHub repository
     * @return this
     */
    public AppUpdaterUtils setGitHubUserAndRepo(String user, String repo) {
        this.gitHub = new GitHub(user, repo);
        return this;
    }

    /**
     * Method to set the AppUpdaterListener for the AppUpdaterUtils actions
     *
     * @param appUpdaterListener the listener to be notified
     * @return this
     * @see com.github.javiersantos.appupdater.AppUpdaterUtils.AppUpdaterListener
     */
    public AppUpdaterUtils withListener(AppUpdaterListener appUpdaterListener) {
        this.appUpdaterListener = appUpdaterListener;
        return this;
    }

    /**
     * Execute AppUpdaterUtils in background.
     */
    public void start() {
        UtilsAsync.LatestAppVersion latestAppVersion = new UtilsAsync.LatestAppVersion(context, true, updateFrom, gitHub, new AppUpdater.LibraryListener() {
            @Override
            public void onSuccess(String version) {
                appUpdaterListener.onSuccess(version, UtilsLibrary.isUpdateAvailable(UtilsLibrary.getAppInstalledVersion(context), version));
            }

            @Override
            public void onFailed(AppUpdaterError error) {
                appUpdaterListener.onFailed(error);
            }
        });

        latestAppVersion.execute();
    }

}

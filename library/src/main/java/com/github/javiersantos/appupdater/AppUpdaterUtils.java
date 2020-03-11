package com.github.javiersantos.appupdater;

import android.content.Context;
import androidx.annotation.NonNull;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;
import com.github.javiersantos.appupdater.objects.Update;

public class AppUpdaterUtils {
    private Context context;
    private UpdateListener updateListener;
    private AppUpdaterListener appUpdaterListener;
    private UpdateFrom updateFrom;
    private GitHub gitHub;
    private String xmlOrJSONUrl;
    private UtilsAsync.LatestAppVersion latestAppVersion;

    public interface UpdateListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't retrieve the latest version
         *
         * @param update            object with the latest update information: version and url to download
         * @see com.github.javiersantos.appupdater.objects.Update
         * @param isUpdateAvailable compare installed version with the latest one
         */
        void onSuccess(Update update, Boolean isUpdateAvailable);

        void onFailed(AppUpdaterError error);
    }

    @Deprecated
    public interface AppUpdaterListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't retrieve the latest version
         *
         * @param latestVersion     available in the provided source
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
     * @see <a href="https://github.com/javiersantos/AppUpdater/wiki">Additional documentation</a>
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
     * Set the url to the xml with the latest version info.
     *
     * @param xmlUrl file
     * @return this
     */
    public AppUpdaterUtils setUpdateXML(@NonNull String xmlUrl) {
        this.xmlOrJSONUrl = xmlUrl;
        return this;
    }

    /**
     * Set the url to the xml with the latest version info.
     *
     * @param jsonUrl file
     * @return this
     */
    public AppUpdaterUtils setUpdateJSON(@NonNull String jsonUrl) {
        this.xmlOrJSONUrl = jsonUrl;
        return this;
    }


    /**
     * Method to set the AppUpdaterListener for the AppUpdaterUtils actions
     *
     * @param appUpdaterListener the listener to be notified
     * @return this
     * @see com.github.javiersantos.appupdater.AppUpdaterUtils.AppUpdaterListener
     * @deprecated
     */
    public AppUpdaterUtils withListener(AppUpdaterListener appUpdaterListener) {
        this.appUpdaterListener = appUpdaterListener;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param updateListener the listener to be notified
     * @return this
     * @see com.github.javiersantos.appupdater.AppUpdaterUtils.UpdateListener
     */
    public AppUpdaterUtils withListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
        return this;
    }

    /**
     * Execute AppUpdaterUtils in background.
     */
    public void start() {
        latestAppVersion = new UtilsAsync.LatestAppVersion(context, true, updateFrom, gitHub, xmlOrJSONUrl, new AppUpdater.LibraryListener() {
            @Override
            public void onSuccess(Update update) {
                Update installedUpdate = new Update(UtilsLibrary.getAppInstalledVersion(context), UtilsLibrary.getAppInstalledVersionCode(context));

                if (updateListener != null) {
                    updateListener.onSuccess(update, UtilsLibrary.isUpdateAvailable(installedUpdate, update));
                } else if (appUpdaterListener != null) {
                    appUpdaterListener.onSuccess(update.getLatestVersion(), UtilsLibrary.isUpdateAvailable(installedUpdate, update));
                } else {
                    throw new RuntimeException("You must provide a listener for the AppUpdaterUtils");
                }
            }

            @Override
            public void onFailed(AppUpdaterError error) {
                if (updateListener != null) {
                    updateListener.onFailed(error);
                } else if (appUpdaterListener != null) {
                    appUpdaterListener.onFailed(error);
                } else {
                    throw new RuntimeException("You must provide a listener for the AppUpdaterUtils");
                }
            }
        });

        latestAppVersion.execute();
    }

    /**
     * Stops the execution of AppUpdater.
     */
    public void stop() {
        if (latestAppVersion != null && !latestAppVersion.isCancelled()) {
            latestAppVersion.cancel(true);
        }
    }
}

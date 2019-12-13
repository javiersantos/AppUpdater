package com.github.javiersantos.appupdater

import android.content.Context
import com.github.javiersantos.appupdater.UtilsAsync.LatestAppVersion
import com.github.javiersantos.appupdater.UtilsLibrary.getAppInstalledVersion
import com.github.javiersantos.appupdater.UtilsLibrary.getAppInstalledVersionCode
import com.github.javiersantos.appupdater.UtilsLibrary.isUpdateAvailable
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.interfaces.IAppUpdater.LibraryListener
import com.github.javiersantos.appupdater.objects.GitHub
import com.github.javiersantos.appupdater.objects.Update

class AppUpdaterUtils(private val context: Context) {
    private var updateListener: UpdateListener? = null
    private var appUpdaterListener: AppUpdaterListener? = null
    private var updateFrom: UpdateFrom
    private var gitHub: GitHub? = null
    private var xmlOrJSONUrl: String? = null
    private var latestAppVersion: LatestAppVersion? = null

    interface UpdateListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't retrieve the latest version
         *
         * @param update            object with the latest update information: version and url to download
         * @see com.github.javiersantos.appupdater.objects.Update
         *
         * @param isUpdateAvailable compare installed version with the latest one
         */
        fun onSuccess(update: Update?, isUpdateAvailable: Boolean)

        fun onFailed(error: AppUpdaterError?)
    }

    @Deprecated("")
    interface AppUpdaterListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't retrieve the latest version
         *
         * @param latestVersion     available in the provided source
         * @param isUpdateAvailable compare installed version with the latest one
         */
        fun onSuccess(latestVersion: String?, isUpdateAvailable: Boolean?)

        fun onFailed(error: AppUpdaterError?)
    }

    /**
     * Set the source where the latest update can be found. Default: GOOGLE_PLAY.
     *
     * @param updateFrom source where the latest update is uploaded. If GITHUB is selected, .setGitHubAndRepo method is required.
     * @return this
     * @see com.github.javiersantos.appupdater.enums.UpdateFrom
     *
     * @see [Additional documentation](https://github.com/javiersantos/AppUpdater/wiki)
     */
    fun setUpdateFrom(updateFrom: UpdateFrom): AppUpdaterUtils {
        this.updateFrom = updateFrom
        return this
    }

    /**
     * Set the user and repo where the releases are uploaded. You must upload your updates as a release in order to work properly tagging them as vX.X.X or X.X.X.
     *
     * @param user GitHub user
     * @param repo GitHub repository
     * @return this
     */
    fun setGitHubUserAndRepo(user: String, repo: String): AppUpdaterUtils {
        gitHub = GitHub(user, repo)
        return this
    }

    /**
     * Set the url to the xml with the latest version info.
     *
     * @param xmlUrl file
     * @return this
     */
    fun setUpdateXML(xmlUrl: String): AppUpdaterUtils {
        xmlOrJSONUrl = xmlUrl
        return this
    }

    /**
     * Set the url to the xml with the latest version info.
     *
     * @param jsonUrl file
     * @return this
     */
    fun setUpdateJSON(jsonUrl: String): AppUpdaterUtils {
        xmlOrJSONUrl = jsonUrl
        return this
    }

    /**
     * Method to set the AppUpdaterListener for the AppUpdaterUtils actions
     *
     * @param appUpdaterListener the listener to be notified
     * @return this
     * @see com.github.javiersantos.appupdater.AppUpdaterUtils.AppUpdaterListener
     *
     */
    @Deprecated("")
    fun withListener(appUpdaterListener: AppUpdaterListener?): AppUpdaterUtils {
        this.appUpdaterListener = appUpdaterListener
        return this
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param updateListener the listener to be notified
     * @return this
     * @see com.github.javiersantos.appupdater.AppUpdaterUtils.UpdateListener
     */
    fun withListener(updateListener: UpdateListener?): AppUpdaterUtils {
        this.updateListener = updateListener
        return this
    }

    /**
     * Execute AppUpdaterUtils in background.
     */
    fun start() {
        latestAppVersion = LatestAppVersion(context, true, updateFrom, gitHub, xmlOrJSONUrl, object : LibraryListener {
            override fun onSuccess(update: Update) {
                val installedUpdate = Update(getAppInstalledVersion(context), getAppInstalledVersionCode(context))
                when {
                    updateListener != null -> {
                        updateListener!!.onSuccess(update, isUpdateAvailable(installedUpdate, update))
                    }
                    appUpdaterListener != null -> {
                        appUpdaterListener!!.onSuccess(update.latestVersion, isUpdateAvailable(installedUpdate, update))
                    }
                    else -> {
                        throw RuntimeException("You must provide a listener for the AppUpdaterUtils")
                    }
                }
            }

            override fun onFailed(error: AppUpdaterError) {
                when {
                    updateListener != null -> {
                        updateListener!!.onFailed(error)
                    }
                    appUpdaterListener != null -> {
                        appUpdaterListener!!.onFailed(error)
                    }
                    else -> {
                        throw RuntimeException("You must provide a listener for the AppUpdaterUtils")
                    }
                }
            }
        })
        latestAppVersion!!.execute()
    }

    /**
     * Stops the execution of AppUpdater.
     */
    fun stop() {
        if (latestAppVersion != null && !latestAppVersion!!.isCancelled) {
            latestAppVersion!!.cancel(true)
        }
    }

    init {
        updateFrom = UpdateFrom.GOOGLE_PLAY
    }
}
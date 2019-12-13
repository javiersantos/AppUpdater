package com.github.javiersantos.appupdater

import android.content.Context
import android.os.AsyncTask
import com.github.javiersantos.appupdater.UtilsLibrary.getLatestAppVersion
import com.github.javiersantos.appupdater.UtilsLibrary.getLatestAppVersionStore
import com.github.javiersantos.appupdater.UtilsLibrary.isNetworkAvailable
import com.github.javiersantos.appupdater.UtilsLibrary.isStringAVersion
import com.github.javiersantos.appupdater.UtilsLibrary.isStringAnUrl
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.interfaces.IAppUpdater.LibraryListener
import com.github.javiersantos.appupdater.objects.GitHub
import com.github.javiersantos.appupdater.objects.Update
import java.lang.ref.WeakReference

internal class UtilsAsync {
    internal class LatestAppVersion(context: Context,
                                    private val fromUtils: Boolean,
                                    private val updateFrom: UpdateFrom,
                                    private val gitHub: GitHub?,
                                    private val xmlOrJsonUrl: String?,
                                    private val listener: LibraryListener?) : AsyncTask<Void?, Void?, Update?>() {
        private val contextRef: WeakReference<Context> = WeakReference(context)
        private val libraryPreferences: LibraryPreferences = LibraryPreferences(context)
        override fun onPreExecute() {
            super.onPreExecute()
            val context = contextRef.get()
            if (context == null || listener == null) {
                cancel(true)
            } else if (isNetworkAvailable(context)) {
                if (!fromUtils && !libraryPreferences.appUpdaterShow) {
                    cancel(true)
                } else {
                    if (updateFrom == UpdateFrom.GITHUB && !GitHub.isGitHubValid(gitHub)) {
                        listener.onFailed(AppUpdaterError.GITHUB_USER_REPO_INVALID)
                        cancel(true)
                    } else if (updateFrom == UpdateFrom.XML && (xmlOrJsonUrl == null || !isStringAnUrl(xmlOrJsonUrl))) {
                        listener.onFailed(AppUpdaterError.XML_URL_MALFORMED)
                        cancel(true)
                    } else if (updateFrom == UpdateFrom.JSON && (xmlOrJsonUrl == null || !isStringAnUrl(xmlOrJsonUrl))) {
                        listener.onFailed(AppUpdaterError.JSON_URL_MALFORMED)
                        cancel(true)
                    }
                }
            } else {
                listener.onFailed(AppUpdaterError.NETWORK_NOT_AVAILABLE)
                cancel(true)
            }
        }

        override fun doInBackground(vararg voids: Void?): Update? {
            return try {
                if (updateFrom == UpdateFrom.XML || updateFrom == UpdateFrom.JSON) {
                    val update = getLatestAppVersion(updateFrom, xmlOrJsonUrl)
                    if (update != null) {
                        update
                    } else {
                        val error = if (updateFrom == UpdateFrom.XML) AppUpdaterError.XML_ERROR else AppUpdaterError.JSON_ERROR
                        listener?.onFailed(error)
                        cancel(true)
                        null
                    }
                } else {
                    val context = contextRef.get()
                    if (context != null) {
                        getLatestAppVersionStore(context, updateFrom, gitHub)
                    } else {
                        cancel(true)
                        null
                    }
                }
            } catch (ex: Exception) {
                cancel(true)
                null
            }
        }

        override fun onPostExecute(update: Update?) {
            super.onPostExecute(update)
            if (listener != null) {
                if (isStringAVersion(update?.latestVersion ?: "")) {
                    listener.onSuccess(update!!)
                } else {
                    listener.onFailed(AppUpdaterError.UPDATE_VARIES_BY_DEVICE)
                }
            }
        }

    }
}
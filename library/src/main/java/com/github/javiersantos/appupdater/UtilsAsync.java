package com.github.javiersantos.appupdater;

import android.content.Context;
import android.os.AsyncTask;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;
import com.github.javiersantos.appupdater.objects.Update;

import java.lang.ref.WeakReference;

class UtilsAsync {

    static class LatestAppVersion extends AsyncTask<Void, Void, Update> {
        private WeakReference<Context> contextRef;
        private LibraryPreferences libraryPreferences;
        private Boolean fromUtils;
        private UpdateFrom updateFrom;
        private GitHub gitHub;
        private String xmlOrJsonUrl;
        private AppUpdater.LibraryListener listener;

        public LatestAppVersion(Context context, Boolean fromUtils, UpdateFrom updateFrom, GitHub gitHub, String xmlOrJsonUrl, AppUpdater.LibraryListener listener) {
            this.contextRef = new WeakReference<>(context);
            this.libraryPreferences = new LibraryPreferences(context);
            this.fromUtils = fromUtils;
            this.updateFrom = updateFrom;
            this.gitHub = gitHub;
            this.xmlOrJsonUrl = xmlOrJsonUrl;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Context context = contextRef.get();
            if (context == null || listener == null) {
                cancel(true);
            } else if (UtilsLibrary.isNetworkAvailable(context)) {
                if (!fromUtils && !libraryPreferences.getAppUpdaterShow()) {
                    cancel(true);
                } else {
                    if (updateFrom == UpdateFrom.GITHUB && !GitHub.isGitHubValid(gitHub)) {
                        listener.onFailed(AppUpdaterError.GITHUB_USER_REPO_INVALID);
                        cancel(true);
                    } else if (updateFrom == UpdateFrom.XML && (xmlOrJsonUrl == null || !UtilsLibrary.isStringAnUrl(xmlOrJsonUrl))) {
                        listener.onFailed(AppUpdaterError.XML_URL_MALFORMED);

                        cancel(true);
                    } else if (updateFrom == UpdateFrom.JSON && (xmlOrJsonUrl == null || !UtilsLibrary.isStringAnUrl(xmlOrJsonUrl))) {
                        listener.onFailed(AppUpdaterError.JSON_URL_MALFORMED);

                        cancel(true);
                    }
                }
            } else {
                listener.onFailed(AppUpdaterError.NETWORK_NOT_AVAILABLE);
                cancel(true);
            }
        }

        @Override
        protected Update doInBackground(Void... voids) {
            if (updateFrom == UpdateFrom.XML || updateFrom == UpdateFrom.JSON) {
                Update update = UtilsLibrary.getLatestAppVersion(updateFrom, xmlOrJsonUrl);
                if (update != null) {
                    return update;
                } else {
                    AppUpdaterError error = updateFrom == UpdateFrom.XML ? AppUpdaterError.XML_ERROR
                            : AppUpdaterError.JSON_ERROR;

                    if (listener != null) {
                        listener.onFailed(error);
                    }
                    cancel(true);
                    return null;
                }
            } else {
                Context context = contextRef.get();
                if (context != null) {
                    return UtilsLibrary.getLatestAppVersionHttp(context, updateFrom, gitHub);
                } else {
                    cancel(true);
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(Update update) {
            super.onPostExecute(update);

            if (listener != null) {
                if (UtilsLibrary.isStringAVersion(update.getLatestVersion())) {
                    listener.onSuccess(update);
                } else {
                    listener.onFailed(AppUpdaterError.UPDATE_VARIES_BY_DEVICE);
                }
            }
        }
    }

}

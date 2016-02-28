package com.github.javiersantos.appupdater;

import android.content.Context;
import android.os.AsyncTask;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;

class UtilsAsync {

    static class LatestAppVersion extends AsyncTask<Void, Void, String> {
        private Context context;
        private LibraryPreferences libraryPreferences;
        private Boolean fromUtils;
        private UpdateFrom updateFrom;
        private GitHub gitHub;
        private AppUpdater.LibraryListener listener;

        public LatestAppVersion(Context context, Boolean fromUtils, UpdateFrom updateFrom, GitHub gitHub, AppUpdater.LibraryListener libraryListener) {
            this.context = context;
            this.libraryPreferences = new LibraryPreferences(context);
            this.fromUtils = fromUtils;
            this.updateFrom = updateFrom;
            this.gitHub = gitHub;
            this.listener = libraryListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (UtilsLibrary.isNetworkAvailable(context)) {
                if (!fromUtils && !libraryPreferences.getAppUpdaterShow()) { cancel(true); }
                else {
                    if (updateFrom == UpdateFrom.GITHUB) {
                        if (!GitHub.isGitHubValid(gitHub)) { cancel(true); }
                    }
                }
            } else { cancel(true); }
        }

        @Override
        protected String doInBackground(Void... voids) {
            return UtilsLibrary.getLatestAppVersion(context, updateFrom, gitHub);
        }

        @Override
        protected void onPostExecute(String version) {
            super.onPostExecute(version);
            if (UtilsLibrary.isStringAVersion(version)) {
                listener.onSuccess(version);
            } else {
                listener.onFailed(AppUpdaterError.UPDATE_VARIES_BY_DEVICE);
            }
        }
    }

}

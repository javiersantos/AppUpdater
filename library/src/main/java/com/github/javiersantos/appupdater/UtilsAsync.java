package com.github.javiersantos.appupdater;

import android.content.Context;
import android.os.AsyncTask;

import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.GitHub;

class UtilsAsync {

    static class LatestAppVersion extends AsyncTask<Void, Void, String> {
        private Context context;
        private LibraryPreferences libraryPreferences;
        private Integer showEvery;
        private Boolean showAppUpdated;
        private UpdateFrom updateFrom;
        private Display display;
        private Duration duration;
        private GitHub gitHub;

        public LatestAppVersion(Context context, Integer showEvery, Boolean showAppUpdated, UpdateFrom updateFrom, Display display, Duration duration, GitHub gitHub) {
            this.context = context;
            this.libraryPreferences = new LibraryPreferences(context);
            this.showEvery = showEvery;
            this.showAppUpdated = showAppUpdated;
            this.updateFrom = updateFrom;
            this.display = display;
            this.duration = duration;
            this.gitHub = gitHub;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (UtilsLibrary.isNetworkAvailable(context) && libraryPreferences.getAppUpdaterShow()) {
                switch (updateFrom) {
                    case GITHUB:
                        if (gitHub == null) {
                            throw new NullPointerException("GitHub user and repo are not set!!");
                        }
                        break;
                }
            } else {
                cancel(true);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            return UtilsLibrary.getLatestAppVersion(context, updateFrom, gitHub);
        }

        @Override
        protected void onPostExecute(String version) {
            super.onPostExecute(version);

            if (UtilsLibrary.isUpdateAvailable(UtilsLibrary.getAppInstalledVersion(context), version)) {
                Integer successfulChecks = libraryPreferences.getSuccessfulChecks();
                libraryPreferences.setSuccessfulChecks(successfulChecks++);
                if (UtilsLibrary.isAbleToShow(context, showEvery)) {
                    switch (display) {
                        case DIALOG:
                            UtilsDisplay.showUpdateAvailableDialog(context, context.getResources().getString(R.string.appupdater_update_available), String.format(context.getResources().getString(R.string.appupdater_update_available_description_dialog), version, UtilsLibrary.getAppName(context)), updateFrom, gitHub);
                            break;
                        case SNACKBAR:
                            UtilsDisplay.showUpdateAvailableSnackbar(context, String.format(context.getResources().getString(R.string.appupdater_update_available_description_snackbar), UtilsLibrary.getAppInstalledVersion(context)), UtilsLibrary.getDurationEnumToBoolean(duration), updateFrom, gitHub);
                            break;
                        case NOTIFICATION:
                            UtilsDisplay.showUpdateAvailableNotification(context, context.getResources().getString(R.string.appupdater_update_available), String.format(context.getResources().getString(R.string.appupdater_update_available_description_notification), version, UtilsLibrary.getAppName(context)), updateFrom, gitHub);
                            break;
                    }
                }
            } else if (showAppUpdated) {
                switch (display) {
                    case DIALOG:
                        UtilsDisplay.showUpdateNotAvailableDialog(context, context.getResources().getString(R.string.appupdater_update_not_available), String.format(context.getResources().getString(R.string.appupdater_update_not_available_description), UtilsLibrary.getAppName(context)));
                        break;
                    case SNACKBAR:
                        UtilsDisplay.showUpdateNotAvailableSnackbar(context, context.getResources().getString(R.string.appupdater_update_not_available_description), UtilsLibrary.getDurationEnumToBoolean(duration));
                        break;
                    case NOTIFICATION:
                        UtilsDisplay.showUpdateNotAvailableNotification(context, context.getResources().getString(R.string.appupdater_update_not_available), String.format(context.getResources().getString(R.string.appupdater_update_not_available_description), UtilsLibrary.getAppName(context)));
                        break;
                }
            }
        }
    }

}

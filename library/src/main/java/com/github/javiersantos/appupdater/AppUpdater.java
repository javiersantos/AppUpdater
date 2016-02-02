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
        this.showEvery = 5;
        this.showAppUpdated = false;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setUpdateFrom(UpdateFrom updateFrom) {
        this.updateFrom = updateFrom;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setGitHubUserAndRepo(String user, String repo) {
        this.gitHub = new GitHub(user, repo);
    }

    public void showEvery(Integer times) {
        this.showEvery = times;
    }

    public void showAppUpdated(Boolean res) {
        this.showAppUpdated = res;
    }

    public void init() {
        UtilsAsync.LatestAppVersion latestAppVersion = new UtilsAsync.LatestAppVersion(context, showEvery, showAppUpdated, updateFrom, display, duration, gitHub);
        latestAppVersion.execute();
    }

}

package com.github.javiersantos.appupdater.objects;

import java.net.URL;

public class Update {
    private String version;
    private String releaseNotes;
    private URL apk;

    public Update() {}

    public Update(String latestVersion, String releaseNotes, URL apk) {
        this.version = latestVersion;
        this.apk = apk;
        this.releaseNotes = releaseNotes;
    }

    public String getLatestVersion() {
        return version;
    }

    public void setLatestVersion(String latestVersion) {
        this.version = latestVersion;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public URL getUrlToDownload() {
        return apk;
    }

    public void setUrlToDownload(URL apk) {
        this.apk = apk;
    }
}

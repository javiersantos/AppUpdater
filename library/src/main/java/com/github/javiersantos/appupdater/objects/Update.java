package com.github.javiersantos.appupdater.objects;

import java.net.URL;

public class Update {
    private static Update instance;

    private String version;
    private Integer versionCode;
    private String releaseNotes;
    private URL apk;
    private String changelog;
    private Boolean useWebviewChangelog = false;

    private Update() {}

    public static void init() {
        synchronized(Update.class) {
                instance = new Update();
        }
    }

    public static Update getInstance() {
        return instance;
    }

    public Update(String latestVersion, Integer latestVersionCode) {
        this.version = latestVersion;
        this.versionCode = latestVersionCode;
    }

    public Update(String latestVersion, URL apk) {
        this.version = latestVersion;
        this.apk = apk;
    }

    public Update(String latestVersion, String releaseNotes, URL apk) {
        this.version = latestVersion;
        this.apk = apk;
        this.releaseNotes = releaseNotes;
    }

    public Update(String latestVersion, Integer latestVersionCode, String releaseNotes, URL apk) {
        this(latestVersion, releaseNotes, apk);
        this.versionCode = latestVersionCode;
    }

    public String getLatestVersion() {
        return version;
    }

    public void setLatestVersion(String latestVersion) {
        this.version = latestVersion;
    }

    public Integer getLatestVersionCode() {
        return versionCode;
    }

    public void setLatestVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public String getChangelogUrl() {
        return changelog;
    }

    public void setwebview(Boolean useWebview){
        useWebviewChangelog = useWebview;
    }

    public boolean useWebview(){
        return useWebviewChangelog;
    }

    public void setChangelogUrl(String changelog) {
        this.changelog = changelog;
    }

    public URL getUrlToDownload() {
        return apk;
    }

    public void setUrlToDownload(URL apk) {
        this.apk = apk;
    }
}

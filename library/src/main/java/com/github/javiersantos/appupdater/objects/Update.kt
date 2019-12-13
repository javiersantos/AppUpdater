package com.github.javiersantos.appupdater.objects

import java.net.URL

class Update {
    var latestVersion: String? = null
    var latestVersionCode: Int? = null
    var releaseNotes: String? = null
    var urlToDownload: URL? = null

    constructor()
    constructor(latestVersion: String?, latestVersionCode: Int?) {
        this.latestVersion = latestVersion
        this.latestVersionCode = latestVersionCode
    }

    constructor(latestVersion: String?, apk: URL?) {
        this.latestVersion = latestVersion
        urlToDownload = apk
    }

    constructor(latestVersion: String?, releaseNotes: String?, apk: URL?) {
        this.latestVersion = latestVersion
        urlToDownload = apk
        this.releaseNotes = releaseNotes
    }

    constructor(latestVersion: String?, latestVersionCode: Int?, releaseNotes: String?, apk: URL?) : this(latestVersion, releaseNotes, apk) {
        this.latestVersionCode = latestVersionCode
    }

}
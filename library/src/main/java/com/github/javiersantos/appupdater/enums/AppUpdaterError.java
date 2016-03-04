package com.github.javiersantos.appupdater.enums;

public enum AppUpdaterError {
    /**
     * Google Play returned "Varies by device"
     */
    UPDATE_VARIES_BY_DEVICE,

    /**
     * GitHub user or repo is empty
     */
    GITHUB_USER_REPO_INVALID,

    /**
     * No Internet connection available
     */
    NETWORK_NOT_AVAILABLE,

    /**
     * URL for the XML file is not valid
     */
    XML_URL_MALFORMED
}

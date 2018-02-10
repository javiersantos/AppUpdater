package com.github.javiersantos.appupdater.objects;

import android.support.annotation.NonNull;
import android.util.Log;

public class Version implements Comparable<Version> {
    private String version;

    public final String get() {
        return this.version;
    }

    public Version(String version) {
        final String TAG = "AppUpdater";
        if (version == null)
            Log.e(TAG, "Version can not be null");
        version = version.replaceAll("[^0-9?!\\.]", "");
        if (!version.matches("[0-9]+(\\.[0-9]+)*"))
            Log.e(TAG, "Invalid version format");
        this.version = version;
    }

    @Override
    public int compareTo(@NonNull Version that) {
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ?
                    Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ?
                    Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart)
                return -1;
            if (thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        return this.getClass() == that.getClass() && this.compareTo((Version) that) == 0;
    }

}

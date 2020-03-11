package com.github.javiersantos.appupdater.objects;

import androidx.annotation.NonNull;

public class Version implements Comparable<Version> {
    private String version;

    public final String get() {
        return this.version;
    }

    public Version(@NonNull final String version) throws Exception
    {
        String trimmedVersion = version.replaceAll("[^0-9?!\\.]", "");
        // replace all empty version number-parts with zeros
        trimmedVersion = trimmedVersion.replaceAll("\\.(\\.|$)", "\\.0$1");
        if (!trimmedVersion.matches("[0-9]+(\\.[0-9]+)*"))
            throw new Exception("Invalid version format. Original: `" + version + "` trimmed: `" + trimmedVersion + "`");
        this.version = trimmedVersion;
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

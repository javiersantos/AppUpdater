package com.github.javiersantos.appupdater.objects

import kotlin.math.max

class Version(version: String) : Comparable<Version> {
    private val version: String
    fun get(): String {
        return version
    }

    override fun compareTo(other: Version): Int {
        val thisParts = this.get().split("\\.".toRegex()).toTypedArray()
        val thatParts = other.get().split("\\.".toRegex()).toTypedArray()
        val length = max(thisParts.size, thatParts.size)
        for (i in 0 until length) {
            val thisPart = if (i < thisParts.size) thisParts[i].toInt() else 0
            val thatPart = if (i < thatParts.size) thatParts[i].toInt() else 0
            if (thisPart < thatPart) return -1
            if (thisPart > thatPart) return 1
        }
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other == null) false else this.javaClass == other.javaClass && this.compareTo((other as Version?)!!) == 0
    }

    override fun hashCode(): Int {
        return version.hashCode()
    }

    init {
        var trimmedVersion = version.replace("[^0-9?!.]".toRegex(), "")
        // replace all empty version number-parts with zeros
        trimmedVersion = trimmedVersion.replace("\\.(\\.|$)".toRegex(), "\\.0$1")
        if (!trimmedVersion.matches("[0-9]+(\\.[0-9]+)*".toRegex())) throw Exception("Invalid version format. Original: `$version` trimmed: `$trimmedVersion`")
        this.version = trimmedVersion
    }
}
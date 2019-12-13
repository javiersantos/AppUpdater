package com.github.javiersantos.appupdater

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.javiersantos.appupdater.objects.Update
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibraryTest {
    @Test
    fun version_UpdateAvailable_1() {
        val installedUpdate = Update()
        installedUpdate.latestVersion = "1.0.0"
        val latestUpdate = Update()
        latestUpdate.latestVersion = "1.0.5"
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun version_UpdateAvailable_2() {
        val installedUpdate = Update()
        installedUpdate.latestVersion = "1.0.0"
        val latestUpdate = Update()
        latestUpdate.latestVersion = "1.0.0.33"
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun version_UpdateAvailable_3() {
        val installedUpdate = Update()
        installedUpdate.latestVersion = "1.0.0"
        val latestUpdate = Update()
        latestUpdate.latestVersion = "2.0.0.5"
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun version_UpdateAvailable_4() {
        val installedUpdate = Update()
        installedUpdate.latestVersion = "2.1.3.alpha.42.RC"
        val latestUpdate = Update()
        latestUpdate.latestVersion = "2.1.3.alpha.45.RC"
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun versionCode_UpdateAvailable_1() {
        val installedUpdate = Update("1.0.0", 1)
        val latestUpdate = Update("1.0.0", 2)
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun versionCode_UpdateAvailable_2() {
        val installedUpdate = Update("1.0.0", 1)
        val latestUpdate = Update("1.0.1", 2)
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun versionCode_UpdateAvailable_3() {
        val installedUpdate = Update("1.0.0", 1)
        val latestUpdate = Update("0.9.0", 2)
        Assert.assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun version_UpdateNotAvailable_1() {
        val installedUpdate = Update()
        installedUpdate.latestVersion = "1.0.9"
        val latestUpdate = Update()
        latestUpdate.latestVersion = "1.0.5"
        Assert.assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun version_UpdateNotAvailable_2() {
        val installedUpdate = Update()
        installedUpdate.latestVersion = "1.0.9"
        val latestUpdate = Update()
        latestUpdate.latestVersion = "1.0.9.0"
        Assert.assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun versionCode_UpdateNotAvailable_1() {
        val installedUpdate = Update("1.0.0", 1)
        val latestUpdate = Update("1.0.0", 1)
        Assert.assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun versionCode_UpdateNotAvailable_2() {
        val installedUpdate = Update("1.0.0", 2)
        val latestUpdate = Update("1.0.1", 1)
        Assert.assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }

    @Test
    fun versionCode_UpdateNotAvailable_3() {
        val installedUpdate = Update("1.0.0", 1)
        val latestUpdate = Update("1.2.0", 1)
        Assert.assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate))
    }
}
package com.github.javiersantos.appupdater;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.javiersantos.appupdater.objects.Update;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LibraryTest {

    @Test
    public void Version_UpdateAvailable_1() {
        Update installedUpdate = new Update();
        installedUpdate.setLatestVersion("1.0.0");

        Update latestUpdate = new Update();
        latestUpdate.setLatestVersion("1.0.5");

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void Version_UpdateAvailable_2() {
        Update installedUpdate = new Update();
        installedUpdate.setLatestVersion("1.0.0");

        Update latestUpdate = new Update();
        latestUpdate.setLatestVersion("1.0.0.33");

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void Version_UpdateAvailable_3() {
        Update installedUpdate = new Update();
        installedUpdate.setLatestVersion("1.0.0");

        Update latestUpdate = new Update();
        latestUpdate.setLatestVersion("2.0.0.5");

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void Version_UpdateAvailable_4() {
        Update installedUpdate = new Update();
        installedUpdate.setLatestVersion("2.1.3.alpha.42.RC");

        Update latestUpdate = new Update();
        latestUpdate.setLatestVersion("2.1.3.alpha.45.RC");

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void VersionCode_UpdateAvailable_1() {
        Update installedUpdate = new Update("1.0.0", 1);
        Update latestUpdate = new Update("1.0.0", 2);

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void VersionCode_UpdateAvailable_2() {
        Update installedUpdate = new Update("1.0.0", 1);
        Update latestUpdate = new Update("1.0.1", 2);

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void VersionCode_UpdateAvailable_3() {
        Update installedUpdate = new Update("1.0.0", 1);
        Update latestUpdate = new Update("0.9.0", 2);

        assertTrue(UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void Version_UpdateNotAvailable_1() {
        Update installedUpdate = new Update();
        installedUpdate.setLatestVersion("1.0.9");

        Update latestUpdate = new Update();
        latestUpdate.setLatestVersion("1.0.5");

        assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void Version_UpdateNotAvailable_2() {
        Update installedUpdate = new Update();
        installedUpdate.setLatestVersion("1.0.9");

        Update latestUpdate = new Update();
        latestUpdate.setLatestVersion("1.0.9.0");

        assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void VersionCode_UpdateNotAvailable_1() {
        Update installedUpdate = new Update("1.0.0", 1);
        Update latestUpdate = new Update("1.0.0", 1);

        assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void VersionCode_UpdateNotAvailable_2() {
        Update installedUpdate = new Update("1.0.0", 2);
        Update latestUpdate = new Update("1.0.1", 1);

        assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

    @Test
    public void VersionCode_UpdateNotAvailable_3() {
        Update installedUpdate = new Update("1.0.0", 1);
        Update latestUpdate = new Update("1.2.0", 1);

        assertTrue(!UtilsLibrary.isUpdateAvailable(installedUpdate, latestUpdate));
    }

}

package com.github.javiersantos.appupdater;

import android.support.test.runner.AndroidJUnit4;

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

}

package com.github.javiersantos.appupdater;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.UiThreadTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class NoUpdateAvailableTest {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Test
    public void UpdateAvailable_Basic_JSON() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AppUpdaterUtils(InstrumentationRegistry.getTargetContext())
                        .setUpdateFrom(UpdateFrom.JSON)
                        .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-basic.json")
                        .withListener(new AppUpdaterUtils.UpdateListener() {
                            @Override
                            public void onSuccess(Update update, Boolean isUpdateAvailable) {
                                assertFalse(isUpdateAvailable);
                                signal.countDown();
                            }

                            @Override
                            public void onFailed(AppUpdaterError error) {
                                assertTrue("Failed", false);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    @Test
    public void UpdateAvailable_VersionCode_JSON() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AppUpdaterUtils(InstrumentationRegistry.getTargetContext())
                        .setUpdateFrom(UpdateFrom.JSON)
                        .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-versionCode.json")
                        .withListener(new AppUpdaterUtils.UpdateListener() {
                            @Override
                            public void onSuccess(Update update, Boolean isUpdateAvailable) {
                                assertFalse(isUpdateAvailable);
                                signal.countDown();
                            }

                            @Override
                            public void onFailed(AppUpdaterError error) {
                                assertTrue("Failed", false);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    @Test
    public void UpdateAvailable_Basic_XML() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AppUpdaterUtils(InstrumentationRegistry.getTargetContext())
                        .setUpdateFrom(UpdateFrom.XML)
                        .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-basic.xml")
                        .withListener(new AppUpdaterUtils.UpdateListener() {
                            @Override
                            public void onSuccess(Update update, Boolean isUpdateAvailable) {
                                assertFalse(isUpdateAvailable);
                                signal.countDown();
                            }

                            @Override
                            public void onFailed(AppUpdaterError error) {
                                assertTrue("Failed", false);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    @Test
    public void UpdateAvailable_VersionCode_XML() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AppUpdaterUtils(InstrumentationRegistry.getTargetContext())
                        .setUpdateFrom(UpdateFrom.XML)
                        .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-versionCode.xml")
                        .withListener(new AppUpdaterUtils.UpdateListener() {
                            @Override
                            public void onSuccess(Update update, Boolean isUpdateAvailable) {
                                assertFalse(isUpdateAvailable);
                                signal.countDown();
                            }

                            @Override
                            public void onFailed(AppUpdaterError error) {
                                assertTrue("Failed", false);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

}

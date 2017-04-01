package com.github.javiersantos.appupdater;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

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

@RunWith(AndroidJUnit4.class)
public class NoUpdateAvailableTest {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Test
    public void updateAvailable_Basic_JSON() throws Throwable {
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
                                assertNotNull(error);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    @Test
    public void updateAvailable_VersionCode_JSON() throws Throwable {
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
                                assertNotNull(error);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    @Test
    public void updateAvailable_Basic_XML() throws Throwable {
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
                                assertNotNull(error);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

    @Test
    public void updateAvailable_VersionCode_XML() throws Throwable {
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
                                assertNotNull(error);
                                signal.countDown();
                            }
                        })
                        .start();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

}

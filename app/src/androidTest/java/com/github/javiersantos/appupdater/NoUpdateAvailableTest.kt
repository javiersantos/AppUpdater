package com.github.javiersantos.appupdater

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.UiThreadTestRule
import com.github.javiersantos.appupdater.AppUpdaterUtils.UpdateListener
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class NoUpdateAvailableTest {
    @Rule
    @JvmField
    var uiThreadTestRule = UiThreadTestRule()

    @Test
    @Throws(Throwable::class)
    fun updateAvailable_Basic_JSON() {
        val signal = CountDownLatch(1)
        uiThreadTestRule.runOnUiThread {
            AppUpdaterUtils(InstrumentationRegistry.getInstrumentation().targetContext)
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-basic.json")
                    .withListener(object : UpdateListener {
                        override fun onSuccess(update: Update?, isUpdateAvailable: Boolean) {
                            Assert.assertFalse(isUpdateAvailable)
                            signal.countDown()
                        }

                        override fun onFailed(error: AppUpdaterError?) {
                            Assert.assertTrue("Failed", false)
                            signal.countDown()
                        }
                    })
                    .start()
        }
        signal.await(30, TimeUnit.SECONDS)
    }

    @Test
    @Throws(Throwable::class)
    fun updateAvailable_VersionCode_JSON() {
        val signal = CountDownLatch(1)
        uiThreadTestRule.runOnUiThread {
            AppUpdaterUtils(InstrumentationRegistry.getInstrumentation().targetContext)
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-versionCode.json")
                    .withListener(object : UpdateListener {
                        override fun onSuccess(update: Update?, isUpdateAvailable: Boolean) {
                            Assert.assertFalse(isUpdateAvailable)
                            signal.countDown()
                        }

                        override fun onFailed(error: AppUpdaterError?) {
                            Assert.assertTrue("Failed", false)
                            signal.countDown()
                        }
                    })
                    .start()
        }
        signal.await(30, TimeUnit.SECONDS)
    }

    @Test
    @Throws(Throwable::class)
    fun updateAvailable_Basic_XML() {
        val signal = CountDownLatch(1)
        uiThreadTestRule.runOnUiThread {
            AppUpdaterUtils(InstrumentationRegistry.getInstrumentation().targetContext)
                    .setUpdateFrom(UpdateFrom.XML)
                    .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-basic.xml")
                    .withListener(object : UpdateListener {
                        override fun onSuccess(update: Update?, isUpdateAvailable: Boolean) {
                            Assert.assertFalse(isUpdateAvailable)
                            signal.countDown()
                        }

                        override fun onFailed(error: AppUpdaterError?) {
                            Assert.assertTrue("Failed", false)
                            signal.countDown()
                        }
                    })
                    .start()
        }
        signal.await(30, TimeUnit.SECONDS)
    }

    @Test
    @Throws(Throwable::class)
    fun updateAvailable_VersionCode_XML() {
        val signal = CountDownLatch(1)
        uiThreadTestRule.runOnUiThread {
            AppUpdaterUtils(InstrumentationRegistry.getInstrumentation().targetContext)
                    .setUpdateFrom(UpdateFrom.XML)
                    .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/src/androidTest/java/com/github/javiersantos/appupdater/files/no-update-available-versionCode.xml")
                    .withListener(object : UpdateListener {
                        override fun onSuccess(update: Update?, isUpdateAvailable: Boolean) {
                            Assert.assertFalse(isUpdateAvailable)
                            signal.countDown()
                        }

                        override fun onFailed(error: AppUpdaterError?) {
                            Assert.assertTrue("Failed", false)
                            signal.countDown()
                        }
                    })
                    .start()
        }
        signal.await(30, TimeUnit.SECONDS)
    }
}
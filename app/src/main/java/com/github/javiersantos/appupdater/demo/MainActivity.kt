package com.github.javiersantos.appupdater.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.demo.databinding.ActivityMainBinding
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.UpdateFrom

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/AppUpdater"))) }
        binding.included.dialogUpdateChangelog.setOnClickListener {
            AppUpdater(this) //.setUpdateFrom(UpdateFrom.GITHUB)
                    //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.json")
                    .setDisplay(Display.DIALOG)
                    .showAppUpdated(true)
                    .start()
        }
        binding.included.dialogUpdate.setOnClickListener {
            AppUpdater(this) //.setUpdateFrom(UpdateFrom.GITHUB)
                    //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                    .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.json")
                    .setDisplay(Display.DIALOG)
                    .showAppUpdated(true)
                    .start()
        }
        binding.included.snackbarUpdate.setOnClickListener {
            AppUpdater(this) //.setUpdateFrom(UpdateFrom.GITHUB)
                    //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                    .setUpdateFrom(UpdateFrom.XML)
                    .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
                    .setDisplay(Display.SNACKBAR)
                    .showAppUpdated(true)
                    .start()
        }
        binding.included.notificationUpdate.setOnClickListener {
            AppUpdater(this) //.setUpdateFrom(UpdateFrom.GITHUB)
                    //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                    .setUpdateFrom(UpdateFrom.XML)
                    .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
                    .setDisplay(Display.NOTIFICATION)
                    .showAppUpdated(true)
                    .start()
        }
        binding.included.dialogNoUpdate.setOnClickListener {
            AppUpdater(this)
                    .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                    .setDisplay(Display.DIALOG)
                    .showAppUpdated(true)
                    .start()
        }
        binding.included.snackbarNoUpdate.setOnClickListener {
            AppUpdater(this)
                    .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                    .setDisplay(Display.SNACKBAR)
                    .showAppUpdated(true)
                    .start()
        }
        binding.included.notificationNoUpdate.setOnClickListener {
            AppUpdater(this)
                    .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                    .setDisplay(Display.NOTIFICATION)
                    .showAppUpdated(true)
                    .start()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
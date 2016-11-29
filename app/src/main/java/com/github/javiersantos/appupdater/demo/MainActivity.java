package com.github.javiersantos.appupdater.demo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.demo.databinding.ActivityMainBinding;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.context = this;

        setSupportActionBar(binding.toolbar);

        binding.fab.setImageDrawable(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github).color(Color.WHITE).sizeDp(24));
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/AppUpdater")));
            }
        });



        binding.included.dialogUpdateChangelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        //.setUpdateFrom(UpdateFrom.GITHUB)
                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                        .setUpdateFrom(UpdateFrom.JSON)
                        .setUpdateJSON("https://raw.githubusercontent.com/kgritesh/AppUpdater/feature-json-updater/app/update-changelog.json")
                        .setDisplay(Display.DIALOG)
                        .showAppUpdated(true)
                        .start();
            }
        });

        binding.included.dialogUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        //.setUpdateFrom(UpdateFrom.GITHUB)
                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                        .setUpdateFrom(UpdateFrom.XML)
                        .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
                        .setDisplay(Display.DIALOG)
                        .showAppUpdated(true)
                        .start();
            }
        });

        binding.included.snackbarUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        //.setUpdateFrom(UpdateFrom.GITHUB)
                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                        .setUpdateFrom(UpdateFrom.XML)
                        .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
                        .setDisplay(Display.SNACKBAR)
                        .showAppUpdated(true)
                        .start();
            }
        });

        binding.included.notificationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        //.setUpdateFrom(UpdateFrom.GITHUB)
                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                        .setUpdateFrom(UpdateFrom.JSON)
                        .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
                        .setDisplay(Display.NOTIFICATION)
                        .showAppUpdated(true)
                        .start();
            }
        });

        binding.included.dialogNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                        .setDisplay(Display.DIALOG)
                        .showAppUpdated(true)
                        .start();
            }
        });

        binding.included.snackbarNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                        .setDisplay(Display.SNACKBAR)
                        .showAppUpdated(true)
                        .start();
            }
        });

        binding.included.notificationNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppUpdater(context)
                        .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                        .setDisplay(Display.NOTIFICATION)
                        .showAppUpdated(true)
                        .start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_about).setIcon(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info).color(Color.WHITE).actionBar());
        menu.findItem(R.id.action_settings).setIcon(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_settings).color(Color.WHITE).actionBar());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

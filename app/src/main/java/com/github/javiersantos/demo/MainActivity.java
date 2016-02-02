package com.github.javiersantos.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.demo.R;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        CardView dialog = (CardView) findViewById(R.id.dialog);
        CardView snackbar = (CardView) findViewById(R.id.snackbar);
        CardView notification = (CardView) findViewById(R.id.notification);

        fab.setImageDrawable(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github).color(Color.WHITE).sizeDp(24));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/AppUpdater")));
            }
        });

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                // appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.DIALOG);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                // appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.SNACKBAR);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                // appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.NOTIFICATION);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });
    }
}

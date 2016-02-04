package com.github.javiersantos.appupdater.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.javiersantos.appupdater.AppUpdater;
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
        CardView dialogUpdate = (CardView) findViewById(R.id.dialog_update);
        CardView snackbarUpdate = (CardView) findViewById(R.id.snackbar_update);
        CardView notificationUpdate = (CardView) findViewById(R.id.notification_update);

        CardView dialogNoUpdate = (CardView) findViewById(R.id.dialog_no_update);
        CardView snackbarNoUpdate = (CardView) findViewById(R.id.snackbar_no_update);
        CardView notificationNoUpdate = (CardView) findViewById(R.id.notification_no_update);

        fab.setImageDrawable(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github).color(Color.WHITE).sizeDp(24));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/AppUpdater")));
            }
        });

        dialogUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                // appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.FDROID);
                appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.DIALOG);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        snackbarUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                // appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.FDROID);
                appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.SNACKBAR);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        notificationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                // appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.FDROID);
                appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.NOTIFICATION);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        dialogNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.FDROID);
                // appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                // appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.DIALOG);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        snackbarNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.FDROID);
                // appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                // appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.SNACKBAR);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });

        notificationNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater appUpdater = new AppUpdater(context);
                appUpdater.setUpdateFrom(UpdateFrom.GOOGLE_PLAY);
                // appUpdater.setUpdateFrom(UpdateFrom.AMAZON);
                // appUpdater.setUpdateFrom(UpdateFrom.FDROID);
                // appUpdater.setUpdateFrom(UpdateFrom.GITHUB);
                // appUpdater.setGitHubUserAndRepo("javiersantos", "AppUpdater");
                appUpdater.setDisplay(Display.NOTIFICATION);
                appUpdater.showAppUpdated(true);
                appUpdater.init();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_about).setIcon(new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info).color(Color.WHITE).actionBar());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

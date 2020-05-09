package com.github.javiersantos.appupdater.demo;

import android.content.Context;
import android.widget.Toast;

import com.github.javiersantos.appupdater.Displays.BaseDisplay;
import com.github.javiersantos.appupdater.objects.Update;

public class CustomDisplay extends BaseDisplay {

    public CustomDisplay(Context context) {
        super(context);
    }

    @Override
    public void onShow(Update update) {
        super.onShow(update);
        Toast.makeText(context.getApplicationContext(),getDescriptionUpdate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdated(Update update) {
        Toast.makeText(context.getApplicationContext(),getDescriptionNoUpdate(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismiss() {
        return;
    }
}

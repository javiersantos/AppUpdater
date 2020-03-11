package com.github.javiersantos.appupdater;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.media.RingtoneManager;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.net.URL;

class UtilsDisplay {

    static AlertDialog showUpdateAvailableDialog(final Context context, String title, String content, String btnNegative, String btnPositive, String btnNeutral, final DialogInterface.OnClickListener updateClickListener, final DialogInterface.OnClickListener dismissClickListener, final DialogInterface.OnClickListener disableClickListener) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(btnPositive, updateClickListener)
                .setNegativeButton(btnNegative, dismissClickListener)
                .setNeutralButton(btnNeutral, disableClickListener).create();
    }

    static AlertDialog showUpdateNotAvailableDialog(final Context context, String title, String content) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(context.getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                })
                .create();
    }

    static Snackbar showUpdateAvailableSnackbar(final Context context, String content, Boolean indefinite, final UpdateFrom updateFrom, final URL apk) {
        Activity activity = (Activity) context;
        int snackbarTime = indefinite ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG;

        /*if (indefinite) {
            snackbarTime = Snackbar.LENGTH_INDEFINITE;
        } else {
            snackbarTime = Snackbar.LENGTH_LONG;
        }*/

        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), content, snackbarTime);
        snackbar.setAction(context.getResources().getString(R.string.appupdater_btn_update), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsLibrary.goToUpdate(context, updateFrom, apk);
            }
        });
        return snackbar;
    }

    static Snackbar showUpdateNotAvailableSnackbar(final Context context, String content, Boolean indefinite) {
        Activity activity = (Activity) context;
        int snackbarTime = indefinite ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG;

        /*if (indefinite) {
            snackbarTime = Snackbar.LENGTH_INDEFINITE;
        } else {
            snackbarTime = Snackbar.LENGTH_LONG;
        }*/


        return Snackbar.make(activity.findViewById(android.R.id.content), content, snackbarTime);
    }

    static void showUpdateAvailableNotification(Context context, String title, String content, UpdateFrom updateFrom, URL apk, int smallIconResourceId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        initNotificationChannel(context, notificationManager);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, context.getPackageManager().getLaunchIntentForPackage(UtilsLibrary.getAppPackageName(context)), PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntentUpdate = PendingIntent.getActivity(context, 0, UtilsLibrary.intentToUpdate(context, updateFrom, apk), PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = getBaseNotification(context, contentIntent, title, content, smallIconResourceId)
                .addAction(R.drawable.ic_system_update_white_24dp, context.getResources().getString(R.string.appupdater_btn_update), pendingIntentUpdate);

        notificationManager.notify(0, builder.build());
    }

    static void showUpdateNotAvailableNotification(Context context, String title, String content, int smallIconResourceId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        initNotificationChannel(context, notificationManager);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, context.getPackageManager().getLaunchIntentForPackage(UtilsLibrary.getAppPackageName(context)), PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = getBaseNotification(context, contentIntent, title, content, smallIconResourceId)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }

    private static NotificationCompat.Builder getBaseNotification(Context context, PendingIntent contentIntent, String title, String content, int smallIconResourceId) {
        return new NotificationCompat.Builder(context, context.getString(R.string.appupdater_channel))
                .setContentIntent(contentIntent)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setSmallIcon(smallIconResourceId)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);

    }

    private static void initNotificationChannel(Context context, NotificationManager notificationManager) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    context.getString(R.string.appupdater_channel),
                    context.getString(R.string.appupdater_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

}

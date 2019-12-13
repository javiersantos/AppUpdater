package com.github.javiersantos.appupdater

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import com.github.javiersantos.appupdater.UtilsLibrary.getAppPackageName
import com.github.javiersantos.appupdater.UtilsLibrary.goToUpdate
import com.github.javiersantos.appupdater.UtilsLibrary.intentToUpdate
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.google.android.material.snackbar.Snackbar
import java.net.URL

internal object UtilsDisplay {
    @JvmStatic
    fun showUpdateAvailableDialog(context: Context?, title: String?, content: String?, btnNegative: String?, btnPositive: String?, btnNeutral: String?, updateClickListener: DialogInterface.OnClickListener?, dismissClickListener: DialogInterface.OnClickListener?, disableClickListener: DialogInterface.OnClickListener?): AlertDialog {
        return AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(btnPositive, updateClickListener)
                .setNegativeButton(btnNegative, dismissClickListener)
                .setNeutralButton(btnNeutral, disableClickListener).create()
    }

    @JvmStatic
    fun showUpdateNotAvailableDialog(context: Context, title: String?, content: String?): AlertDialog {
        return AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(context.resources.getString(android.R.string.ok)) { _, _ -> }
                .create()
    }

    @JvmStatic
    fun showUpdateAvailableSnackbar(context: Context, content: String?, indefinite: Boolean, updateFrom: UpdateFrom?, apk: URL?): Snackbar {
        val activity = context as Activity
        val snackbarTime = if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        /*if (indefinite) {
            snackbarTime = Snackbar.LENGTH_INDEFINITE;
        } else {
            snackbarTime = Snackbar.LENGTH_LONG;
        }*/
        val snackbar = Snackbar.make(activity.findViewById(android.R.id.content), content!!, snackbarTime)
        snackbar.setAction(context.getResources().getString(R.string.appupdater_btn_update)) { goToUpdate(context, updateFrom!!, apk!!) }
        return snackbar
    }

    @JvmStatic
    fun showUpdateNotAvailableSnackbar(context: Context, content: String?, indefinite: Boolean): Snackbar {
        val activity = context as Activity
        val snackbarTime = if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        /*if (indefinite) {
            snackbarTime = Snackbar.LENGTH_INDEFINITE;
        } else {
            snackbarTime = Snackbar.LENGTH_LONG;
        }*/return Snackbar.make(activity.findViewById(android.R.id.content), content!!, snackbarTime)
    }

    @JvmStatic
    fun showUpdateAvailableNotification(context: Context, title: String, content: String, updateFrom: UpdateFrom?, apk: URL?, smallIconResourceId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        initNotificationChannel(context, notificationManager)
        val contentIntent = PendingIntent.getActivity(context, 0, context.packageManager.getLaunchIntentForPackage(getAppPackageName(context)), PendingIntent.FLAG_CANCEL_CURRENT)
        val pendingIntentUpdate = PendingIntent.getActivity(context, 0, intentToUpdate(context, updateFrom!!, apk!!), PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = getBaseNotification(context, contentIntent, title, content, smallIconResourceId)
                .addAction(R.drawable.ic_system_update_white_24dp, context.resources.getString(R.string.appupdater_btn_update), pendingIntentUpdate)
        notificationManager.notify(0, builder.build())
    }

    @JvmStatic
    fun showUpdateNotAvailableNotification(context: Context, title: String, content: String, smallIconResourceId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        initNotificationChannel(context, notificationManager)
        val contentIntent = PendingIntent.getActivity(context, 0, context.packageManager.getLaunchIntentForPackage(getAppPackageName(context)), PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = getBaseNotification(context, contentIntent, title, content, smallIconResourceId)
                .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }

    private fun getBaseNotification(context: Context, contentIntent: PendingIntent, title: String, content: String, smallIconResourceId: Int): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, context.getString(R.string.appupdater_channel))
                .setContentIntent(contentIntent)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                .setSmallIcon(smallIconResourceId)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
    }

    private fun initNotificationChannel(context: Context, notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                    context.getString(R.string.appupdater_channel),
                    context.getString(R.string.appupdater_channel_name),
                    NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}
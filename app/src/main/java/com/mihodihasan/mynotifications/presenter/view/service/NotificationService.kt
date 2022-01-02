package com.mihodihasan.mynotifications.presenter.view.service

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.mihodihasan.mynotifications.data.db.NotificationDao
import com.mihodihasan.mynotifications.data.model.Notification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : NotificationListenerService() {

    @Inject
    lateinit var dao: NotificationDao

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val pack = sbn?.packageName

        var text = ""
        var title: String? = null
        val extras = sbn?.notification?.extras
        text = extras?.getCharSequence("android.text").toString()
        title = extras?.getString("android.title")

        Log.i("NotificationAccessData", getAppNameFromPackageName(pack ?: "Unknown"))
        Log.i("NotificationAccessData", title.toString())
        Log.i("NotificationAccessData", text)
        Log.i("NotificationAccessData", "Notification was removed $sbn")
        GlobalScope.launch(Dispatchers.IO) {
            dao.addNotification(
                Notification(
                    title,
                    text,
                    System.currentTimeMillis(),
                    getAppNameFromPackageName(pack ?: "Unknown")
                )
            )
        }


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        Log.i("NotificationAccessData", "Notification was removed ${sbn.toString()}")
    }

    private fun getAppNameFromPackageName(packageName: String): String {
        var appName: String = packageName
        val ai: ApplicationInfo? = try {
            applicationContext.packageManager.getApplicationInfo(packageName, 0)

        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        (if (ai != null) appName =
            applicationContext?.packageManager?.getApplicationLabel(ai).toString())
        return appName
    }
}
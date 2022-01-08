package com.mihodihasan.mynotifications.presenter.view.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
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
        val text: String
        val title: String
        val extras = sbn?.notification?.extras
        text = extras?.getCharSequence("android.text").toString()
        title = extras?.getString("android.title").toString()
        GlobalScope.launch(Dispatchers.IO) {
            dao.addNotification(
                Notification(
                    title,
                    text,
                    System.currentTimeMillis(),
                    pack ?: "Unknown"
                )
            )
        }


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }

}
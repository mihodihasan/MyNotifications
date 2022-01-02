package com.mihodihasan.mynotifications.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mihodihasan.mynotifications.data.model.Notification

@Database(
    entities = [Notification::class],
    exportSchema = true,
    version = 1
)
abstract class PermanentDatabase :RoomDatabase() {
    abstract fun getNotificationDao(): NotificationDao
}

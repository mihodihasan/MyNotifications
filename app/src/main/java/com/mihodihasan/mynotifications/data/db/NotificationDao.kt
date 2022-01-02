package com.mihodihasan.mynotifications.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mihodihasan.mynotifications.data.model.Notification

@Dao
interface NotificationDao {

    @Query("select * from notification")
    fun getAllNotificationsLiveData(): LiveData<List<Notification>>

    @Insert
    fun addNotification(vararg notification: Notification)

    @Query("DELETE FROM notification")
    fun clearTable()

}


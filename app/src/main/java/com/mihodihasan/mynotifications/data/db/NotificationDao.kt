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

    @Query("select * from notification group by appPackage order by id desc")
    fun getAllPackagesLiveData(): LiveData<List<Notification>>

    @Query("select * from notification where appPackage = :packageName group by title order by id desc")
    fun getAllTitlesLiveData(packageName:String): LiveData<List<Notification>>

    @Query("select * from notification where appPackage = :packageName and title = :title order by id desc")
    fun getAllMessagesLiveData(packageName:String, title:String): LiveData<List<Notification>>

    @Insert
    fun addNotification(vararg notification: Notification)

    @Query("DELETE FROM notification")
    fun clearTable()

}


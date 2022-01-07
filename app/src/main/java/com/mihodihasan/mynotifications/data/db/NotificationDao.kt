package com.mihodihasan.mynotifications.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mihodihasan.mynotifications.data.model.Notification

@Dao
interface NotificationDao {

    /*@Query("select * from notification")
    fun getAllNotificationsLiveData(): LiveData<List<Notification>>*/

    @Query("select * from notification group by appPackage order by id desc limit 100 offset :pageNo*100")
    suspend fun getAllPackagesLiveData(pageNo:Int): List<Notification>

    @Query("select * from notification where appPackage = :packageName group by title order by id desc limit 100 offset :pageNo*100")
    suspend fun getAllTitlesLiveData(packageName:String, pageNo:Int): List<Notification>

    @Query("select * from notification where appPackage = :packageName and title = :title order by id desc limit 100 offset :pageNo*100")
    suspend fun getAllMessagesLiveData(packageName:String, title:String, pageNo:Int): List<Notification>

    @Insert
    suspend fun addNotification(vararg notification: Notification)

}


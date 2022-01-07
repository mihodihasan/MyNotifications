package com.mihodihasan.mynotifications.domain.repository

import androidx.lifecycle.LiveData
import com.mihodihasan.mynotifications.data.model.Notification

interface Repository {

    suspend fun getStoredPackagesData(pageNo:Int): List<Notification>

    suspend fun getStoredTitlesData(packageName:String, pageNo:Int): List<Notification>

    suspend fun getStoredMessagesData(packageName:String, title:String, pageNo:Int): List<Notification>

}
package com.mihodihasan.mynotifications.data

import androidx.lifecycle.LiveData
import com.mihodihasan.mynotifications.data.db.NotificationDao
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.domain.SharedPreferenceManager
import com.mihodihasan.mynotifications.domain.repository.Repository

class RepoImpl(private val sharedPreferenceManager: SharedPreferenceManager, private val dao: NotificationDao) : Repository {

    override suspend fun getStoredPackagesData(pageNo: Int): List<Notification> = dao.getAllPackagesLiveData(pageNo)

    override suspend fun getStoredTitlesData(
        packageName: String,
        pageNo: Int
    ): List<Notification> = dao.getAllTitlesLiveData(packageName, pageNo)

    override suspend fun getStoredMessagesData(
        packageName: String,
        title: String,
        pageNo: Int
    ): List<Notification> = dao.getAllMessagesLiveData(packageName, title, pageNo)
}
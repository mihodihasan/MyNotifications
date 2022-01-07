package com.mihodihasan.mynotifications.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mihodihasan.mynotifications.data.db.NotificationDao
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.domain.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val preferenceManager: SharedPreferenceManager,
    private val dao: NotificationDao
) : ViewModel() {

    fun getStoredPackagesData(): LiveData<List<Notification>> = dao.getAllPackagesLiveData()

    fun getStoredTitlesData(packageName:String): LiveData<List<Notification>> = dao.getAllTitlesLiveData(packageName)

    fun getStoredMessagesData(packageName:String, title:String): LiveData<List<Notification>> = dao.getAllMessagesLiveData(packageName, title)

}

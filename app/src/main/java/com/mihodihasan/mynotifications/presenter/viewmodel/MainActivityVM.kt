package com.mihodihasan.mynotifications.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mihodihasan.mynotifications.data.db.NotificationDao
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.domain.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val preferenceManager: SharedPreferenceManager,
    private val dao: NotificationDao
) : ViewModel() {

    fun getStoredData(): LiveData<List<Notification>> = dao.getAllNotificationsLiveData()


}

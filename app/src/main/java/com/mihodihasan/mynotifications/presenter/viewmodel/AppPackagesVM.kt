package com.mihodihasan.mynotifications.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mihodihasan.mynotifications.data.db.NotificationDao
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.domain.SharedPreferenceManager
import com.mihodihasan.mynotifications.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class AppPackagesVM @Inject constructor(
    private val repo: Repository) : ViewModel() {

    val packagesLiveData:MutableLiveData<List<Notification>> = MutableLiveData()
    fun getStoredPackagesData(pageNo:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            packagesLiveData.postValue(repo.getStoredPackagesData(pageNo))
        }
    }
}

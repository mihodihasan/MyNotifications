package com.mihodihasan.mynotifications.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageVm @Inject constructor(private val repo: Repository) : ViewModel() {
    val messageLiveData: MutableLiveData<List<Notification>> = MutableLiveData()
    fun getStoredMessagesData(packageName: String, title: String, pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            messageLiveData.postValue(repo.getStoredMessagesData(packageName, title, pageNo))
        }
    }
}
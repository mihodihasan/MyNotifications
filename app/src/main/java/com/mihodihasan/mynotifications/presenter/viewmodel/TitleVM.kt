package com.mihodihasan.mynotifications.presenter.viewmodel

import androidx.lifecycle.LiveData
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
class TitleVM @Inject constructor(private val repo:Repository):ViewModel(){

    val titlesLiveData:MutableLiveData<List<Notification>> = MutableLiveData<List<Notification>>()
    fun getStoredTitlesData(packageName:String, pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repo.getStoredTitlesData(packageName, pageNo)
            titlesLiveData.postValue(data)
        }
    }
}

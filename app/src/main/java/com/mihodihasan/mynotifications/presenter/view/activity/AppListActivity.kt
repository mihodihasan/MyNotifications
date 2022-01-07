package com.mihodihasan.mynotifications.presenter.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityMainBinding
import com.mihodihasan.mynotifications.presenter.view.adapter.NotificationListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.provider.Settings
import com.mihodihasan.mynotifications.domain.Constants
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener
import timber.log.Timber


@AndroidEntryPoint
class AppListActivity : AppCompatActivity(), OnListItemClickListener {

    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val notificationList = mutableListOf<Notification>()
    private val adapter:NotificationListAdapter by lazy { NotificationListAdapter(this, notificationList, this) }
    private val layoutManager by lazy {  LinearLayoutManager(this@AppListActivity)}
    private val viewModel by viewModels<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (isNotificationServiceRunning()){

        } else{
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        setUpRecycler()
        setupLiveData()


    }

    //todo test later
    private fun isNotificationServiceRunning(): Boolean {
        val enabledNotificationListeners: String = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        Timber.tag("AppFlow").d("Checking if notification service running. List of Packages with this permission $enabledNotificationListeners")
        return enabledNotificationListeners.contains(
            packageName
        )
    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun setupLiveData(){
        viewModel.getStoredPackagesData().observe(this){
            notificationList.clear()
            notificationList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onListItemClick(position: Int) {
        Timber.tag("AppFlow").d("${notificationList[position].appPackage} tapped")
        startActivity(Intent(this, TitleListActivity::class.java).apply {
            putExtra(Constants.SELECTED_PACKAGE_NAME, notificationList[position].appPackage)
        })
    }

}

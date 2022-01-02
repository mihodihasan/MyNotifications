package com.mihodihasan.mynotifications.presenter.view.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityMainBinding
import com.mihodihasan.mynotifications.presenter.view.adapter.NotificationListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.MainActivityVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val notificationList = mutableListOf<Notification>()
    private val adapter:NotificationListAdapter by lazy { NotificationListAdapter(this, notificationList) }
    private val layoutManager by lazy {  LinearLayoutManager(this@MainActivity)}
    private val viewModel by viewModels<MainActivityVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)) {

            } else {
                ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE), 100);

            }
        }

        setUpRecycler()
        setupLiveData()


    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun setupLiveData(){
        viewModel.getStoredData().observe(this){
            notificationList.clear()
            notificationList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}

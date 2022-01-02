package com.mihodihasan.mynotifications.presenter.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityMainBinding
import com.mihodihasan.mynotifications.presenter.view.adapter.NotificationListAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpRecycler()
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("LSNDEBUG", "onCreate: 1")
            delay(5000L)
            Log.d("LSNDEBUG", "onCreate: 2")
            notificationList.add(Notification("hbgv","hgft",System.currentTimeMillis(),"hbgvcf", 0))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            notificationList.add(Notification("dfgh","uygtf",System.currentTimeMillis(),"we", 1))
            Log.d("LSNDEBUG", "onCreate: 3")
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                Log.d("LSNDEBUG", "onCreate: 4")
            }
        }


    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }
}

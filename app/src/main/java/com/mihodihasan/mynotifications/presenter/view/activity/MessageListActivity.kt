package com.mihodihasan.mynotifications.presenter.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityMessageListBinding
import com.mihodihasan.mynotifications.domain.Constants
import com.mihodihasan.mynotifications.presenter.view.adapter.MessageListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MessageListActivity : AppCompatActivity()  {

    private val binding by lazy { ActivityMessageListBinding.inflate(layoutInflater) }
    private val selectedPackageName by lazy { intent.getStringExtra(Constants.SELECTED_PACKAGE_NAME) }
    private val selectedTitle by lazy { intent.getStringExtra(Constants.SELECTED_TITLE) }
    private val viewModel by viewModels<BaseViewModel>()
    private val adapter: MessageListAdapter by lazy { MessageListAdapter(this, list) }
    private val list: MutableList<Notification> by lazy { mutableListOf() }
    private val layoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.apply {
            adapter = this@MessageListActivity.adapter
            layoutManager = this@MessageListActivity.layoutManager
        }

        if (selectedPackageName != null && selectedTitle != null) {
            viewModel.getStoredMessagesData(selectedPackageName ?: return, selectedTitle ?: return)
                .observe(this) {
                    Timber.tag("AppFlow").d("${it} for package: $selectedPackageName and title: $selectedTitle")
                    list.clear()
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
                }
        } else {
            Toast.makeText(this, "Invalid Package or Title", Toast.LENGTH_SHORT).show()
        }
    }
}
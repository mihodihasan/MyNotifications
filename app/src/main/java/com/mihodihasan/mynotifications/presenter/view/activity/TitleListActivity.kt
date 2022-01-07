package com.mihodihasan.mynotifications.presenter.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityTitleListBinding
import com.mihodihasan.mynotifications.domain.Constants
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener
import com.mihodihasan.mynotifications.presenter.view.adapter.TitleListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TitleListActivity : AppCompatActivity(), OnListItemClickListener {

    private val selectedPackageName by lazy { intent.getStringExtra(Constants.SELECTED_PACKAGE_NAME) }
    private val binding by lazy { ActivityTitleListBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<BaseViewModel>()
    private val adapter: TitleListAdapter by lazy { TitleListAdapter(this, list, this) }
    private val list: MutableList<Notification> by lazy { mutableListOf() }
    private val layoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.apply {
            adapter = this@TitleListActivity.adapter
            layoutManager = this@TitleListActivity.layoutManager
        }

        if (selectedPackageName != null) {
            viewModel.getStoredTitlesData(selectedPackageName ?: return).observe(this) {
                Timber.tag("AppFlow").d("${it} for package: $selectedPackageName")
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(this, "Invalid Package", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onListItemClick(position: Int) {
        startActivity(Intent(this, MessageListActivity::class.java).apply {
            putExtra(Constants.SELECTED_PACKAGE_NAME, selectedPackageName)
            putExtra(Constants.SELECTED_TITLE, list[position].title)
        })
    }
}
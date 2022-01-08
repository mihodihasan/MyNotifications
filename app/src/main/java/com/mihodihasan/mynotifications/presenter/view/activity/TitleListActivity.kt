package com.mihodihasan.mynotifications.presenter.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityTitleListBinding
import com.mihodihasan.mynotifications.domain.Constants
import com.mihodihasan.mynotifications.domain.EndlessRecyclerViewScrollListener
import com.mihodihasan.mynotifications.domain.ExtensionFunctions.animateStartActivity
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener
import com.mihodihasan.mynotifications.presenter.view.adapter.TitleListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.TitleVM
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TitleListActivity : BaseActivity(), OnListItemClickListener {
    var loadMoreItems = true
    var endOfList = false
    private val scrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (loadMoreItems) {
                    viewModel.getStoredTitlesData(selectedPackageName ?: return, page)
                } else {
                    if (!endOfList) {
                        Toast.makeText(this@TitleListActivity, "End of list", Toast.LENGTH_SHORT)
                            .show()
                        endOfList = true
                    }

                }
            }
        }
    }
    private val selectedPackageName by lazy { intent.getStringExtra(Constants.SELECTED_PACKAGE_NAME) }
    private val binding by lazy { ActivityTitleListBinding.inflate(layoutInflater) }
    protected val toolbarBinding by lazy { binding.getToolbar() }
    private val viewModel by viewModels<TitleVM>()
    private val adapter: TitleListAdapter by lazy { TitleListAdapter(this, list, this) }
    private val list: MutableList<Notification> by lazy { mutableListOf() }
    private val layoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
        binding.recyclerView.apply {
            adapter = this@TitleListActivity.adapter
            layoutManager = this@TitleListActivity.layoutManager
            addOnScrollListener(scrollListener)
        }
        observeLiveData()
        list.clear()
        endOfList=false
        if (selectedPackageName != null) {
            viewModel.getStoredTitlesData(selectedPackageName ?: return, 0)
        } else {
            Toast.makeText(this, "Invalid Package", Toast.LENGTH_SHORT).show()
        }

    }

    private fun observeLiveData(){
        viewModel.titlesLiveData.observe(this){
            Timber.tag("AppFlow").d("${it} for package: $selectedPackageName")
//            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
            if (it.isNullOrEmpty()) loadMoreItems = false
        }
    }

    override fun onListItemClick(position: Int) {
        animateStartActivity(Intent(this, MessageListActivity::class.java).apply {
            putExtra(Constants.SELECTED_PACKAGE_NAME, selectedPackageName)
            putExtra(Constants.SELECTED_TITLE, list[position].title)
        })
    }

    private fun setToolbar() {
        toolbarBinding.updateToolbar("Titles") { onBackPressed() }
    }
}
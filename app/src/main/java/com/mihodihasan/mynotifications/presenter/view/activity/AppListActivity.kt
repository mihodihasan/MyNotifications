package com.mihodihasan.mynotifications.presenter.view.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityAppListBinding
import com.mihodihasan.mynotifications.domain.Constants
import com.mihodihasan.mynotifications.domain.EndlessRecyclerViewScrollListener
import com.mihodihasan.mynotifications.domain.ExtensionFunctions.animateStartActivity
import com.mihodihasan.mynotifications.domain.ExtensionFunctions.gone
import com.mihodihasan.mynotifications.domain.ExtensionFunctions.visible
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener
import com.mihodihasan.mynotifications.presenter.view.adapter.AppsListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.AppPackagesVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class AppListActivity : BaseActivity(), OnListItemClickListener {
    var loadMoreItems = true
    var endOfList = false
    private val scrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (loadMoreItems) {
                    viewModel.getStoredPackagesData(page)
                } else {
                    if (!endOfList) {
                        Toast.makeText(this@AppListActivity, "End of list", Toast.LENGTH_SHORT)
                            .show()
                        endOfList = true
                    }

                }
            }
        }
    }
    private val binding: ActivityAppListBinding by lazy {
        ActivityAppListBinding.inflate(
            layoutInflater
        )
    }
    protected val toolbarBinding by lazy { binding.getToolbar() }
    private val notificationList = mutableListOf<Notification>()
    private val adapter: AppsListAdapter by lazy {
        AppsListAdapter(
            this,
            notificationList,
            this
        )
    }
    private val layoutManager by lazy { LinearLayoutManager(this@AppListActivity) }
    private val viewModel by viewModels<AppPackagesVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
        if (isNotificationServiceRunning()) {

        } else {
            animateStartActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        setUpRecycler()
        setupLiveData()
        makeEmptyViewVisible()
        notificationList.clear()
        endOfList = false
        viewModel.getStoredPackagesData(0)
    }

    private fun makeEmptyViewVisible() {
        binding.includedView.root.visible()
        binding.recyclerView.gone()
    }

    //todo test later
    private fun isNotificationServiceRunning(): Boolean {
        val enabledNotificationListeners: String =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        Timber.tag("AppFlow")
            .d("Checking if notification service running. List of Packages with this permission $enabledNotificationListeners")
        return enabledNotificationListeners.contains(
            packageName
        )
    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(scrollListener)
    }

    private fun setupLiveData() {
        viewModel.packagesLiveData.observe(this) {
//            notificationList.clear()
            notificationList.addAll(it)
            adapter.notifyDataSetChanged()
            if (notificationList.size == 0) {
                makeEmptyViewVisible()
            } else {
                makeEmptyViewGone()
            }
            if (it.isNullOrEmpty()) loadMoreItems = false
        }
    }

    private fun makeEmptyViewGone() {
        binding.includedView.root.gone()
        binding.recyclerView.visible()
    }

    override fun onListItemClick(position: Int) {
        Timber.tag("AppFlow").d("${notificationList[position].appPackage} tapped")
        animateStartActivity(Intent(this, TitleListActivity::class.java).apply {
            putExtra(Constants.SELECTED_PACKAGE_NAME, notificationList[position].appPackage)
        })
    }

    private fun setToolbar() {
        toolbarBinding.updateToolbar("Applications", true) { onBackPressed() }
    }

    private var shouldExitNow: Boolean = false
    override fun onBackPressed() {


        if (shouldExitNow) {
            finishAffinity()
            super.onBackPressed()
            return
        }
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show()
        shouldExitNow = true
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500L)
            shouldExitNow = false
        }
    }

}

package com.mihodihasan.mynotifications.presenter.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.ActivityMessageListBinding
import com.mihodihasan.mynotifications.domain.Constants
import com.mihodihasan.mynotifications.domain.EndlessRecyclerViewScrollListener
import com.mihodihasan.mynotifications.domain.Utils
import com.mihodihasan.mynotifications.presenter.model.Message
import com.mihodihasan.mynotifications.presenter.model.MessageViewType
import com.mihodihasan.mynotifications.presenter.view.adapter.MessageListAdapter
import com.mihodihasan.mynotifications.presenter.viewmodel.MessageVm
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MessageListActivity : BaseActivity() {
    var loadMoreItems = true
    var endOfList = false
    private val scrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (loadMoreItems) {
                    viewModel.getStoredMessagesData(
                        selectedPackageName ?: return,
                        selectedTitle ?: return,
                        page
                    )
                } else {
                    if (!endOfList) {
                        Toast.makeText(this@MessageListActivity, "End of list", Toast.LENGTH_SHORT)
                            .show()
                        endOfList = true
                    }

                }
            }
        }
    }
    private val binding by lazy { ActivityMessageListBinding.inflate(layoutInflater) }
    protected val toolbarBinding by lazy { binding.getToolbar() }
    private val selectedPackageName by lazy { intent.getStringExtra(Constants.SELECTED_PACKAGE_NAME) }
    private val selectedTitle by lazy { intent.getStringExtra(Constants.SELECTED_TITLE) }
    private val viewModel by viewModels<MessageVm>()
    private val adapter: MessageListAdapter by lazy { MessageListAdapter(this, list) }
    private val list: MutableList<Message> by lazy { mutableListOf() }
    private val layoutManager by lazy { LinearLayoutManager(this) }

    private fun setToolbar() {
        toolbarBinding.updateToolbar("Messages") { onBackPressed() }
    }

    private fun convertDataTypes(notificationList: List<Notification>?): List<Message> {
        var lastDate: String? = null
        val returnList = mutableListOf<Message>()
        notificationList?.forEach {
            if (lastDate == null || lastDate != Utils.getFormattedDate(it.time)) {
                lastDate = Utils.getFormattedDate(it.time)
                returnList.add(Message(MessageViewType.DateType(), lastDate!!))
            }
            if (lastDate == Utils.getFormattedDate(it.time)) {
                returnList.add(Message(MessageViewType.TextType(), lastDate!!, it))
            } else {
                lastDate = Utils.getFormattedDate(it.time)
                returnList.add(Message(MessageViewType.DateType(), lastDate!!))
            }
        }
        return returnList.toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
        binding.appNameTv.text =
            Utils.getAppNameFromPackageName(application, selectedPackageName ?: "")
        binding.titleTv.text = selectedPackageName
        binding.lastTextTv.text = selectedTitle
        binding.appLogo.setImageDrawable(selectedPackageName?.let {
            Utils.getIconFromPackage(
                application,
                it
            )
        })
        binding.recyclerView.apply {
            adapter = this@MessageListActivity.adapter
            layoutManager = this@MessageListActivity.layoutManager
            addOnScrollListener(scrollListener)
        }
        observeLiveData()
        list.clear()
        endOfList = false
        if (selectedPackageName != null && selectedTitle != null) {
            viewModel.getStoredMessagesData(
                selectedPackageName ?: return,
                selectedTitle ?: return,
                0
            )
        } else {
            Toast.makeText(this, "Invalid Package or Title", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeLiveData() {
        viewModel.messageLiveData.observe(this){
            Timber.tag("AppFlow")
                .d("${it} for package: $selectedPackageName and title: $selectedTitle")
//            list.clear()
            list.addAll(convertDataTypes(it))
            adapter.notifyDataSetChanged()
            if (it.isNullOrEmpty()) loadMoreItems = false
        }
    }
}
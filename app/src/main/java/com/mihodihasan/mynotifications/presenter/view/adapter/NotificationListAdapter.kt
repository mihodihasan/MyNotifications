package com.mihodihasan.mynotifications.presenter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.R
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.RowNotificationsBinding
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener

class NotificationListAdapter(
    val context: Context,
    private val list: MutableList<Notification>,
    private val onPackageListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<NotificationListAdapter.NotificationVH>() {

    class NotificationVH(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        return NotificationVH(
            LayoutInflater.from(context).inflate(R.layout.row_notifications, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        val binding = RowNotificationsBinding.bind(holder.view)
        binding.appNameTv.text = list[position].appPackage
        holder.itemView.setOnClickListener {
            onPackageListItemClickListener.onListItemClick(position)
        }
    }

    override fun getItemCount(): Int = list.size
}
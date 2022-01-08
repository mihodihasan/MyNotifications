package com.mihodihasan.mynotifications.presenter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.MyNotificationsApp
import com.mihodihasan.mynotifications.R
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.RowNotificationsBinding
import com.mihodihasan.mynotifications.domain.Utils
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener
import java.util.*


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
        binding.appNameTv.text = Utils.getAppNameFromPackageName(
            context.applicationContext as MyNotificationsApp,
            list[position].appPackage
        )
        binding.timeTv.text = Utils.getSimpleDateFormatter().format(
            Date(list[position].time)
        )
        binding.lastTextTv.text = list[position].title
        binding.appLogo
        Utils.getIconFromPackage(
            context.applicationContext as MyNotificationsApp,
            list[position].appPackage
        ).let {
//            Picasso.get().load(it).into(binding.appLogo)
            binding.appLogo.setImageDrawable(it)
        }
        holder.itemView.setOnClickListener {
            onPackageListItemClickListener.onListItemClick(position)
        }
    }

    override fun getItemCount(): Int = list.size
}
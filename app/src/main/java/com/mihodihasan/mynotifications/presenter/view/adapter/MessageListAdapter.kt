package com.mihodihasan.mynotifications.presenter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.R
import com.mihodihasan.mynotifications.databinding.RowDatesBinding
import com.mihodihasan.mynotifications.databinding.RowMessagesBinding
import com.mihodihasan.mynotifications.domain.Utils
import com.mihodihasan.mynotifications.presenter.model.Message
import com.mihodihasan.mynotifications.presenter.model.MessageViewType
import java.util.*

class MessageListAdapter(private val context: Context, private val list: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) MessageViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_messages, parent, false
            )
        ) else DateViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_dates, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageViewHolder -> {
                val binding = RowMessagesBinding.bind(holder.itemView)
                binding.textTv.text = list[position].notification?.message
                binding.timeTv.text = Utils.getSimpleDateFormatterOnlyTime()
                    .format(Date(list[position].notification?.time ?: return))
            }
            is DateViewHolder -> {
                val binding = RowDatesBinding.bind(holder.itemView)
                binding.dateTv.text = list[position].date
            }
        }

    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return if (list[position].type is MessageViewType.DateType) 0 else 1
    }

}

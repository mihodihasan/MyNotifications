package com.mihodihasan.mynotifications.presenter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.R
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.RowMessagesBinding
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener

class MessageListAdapter(private val context: Context, private val list:List<Notification>):
    RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder = MessageViewHolder(LayoutInflater.from(context).inflate(
        R.layout.row_titles, parent, false))

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val binding = RowMessagesBinding.bind(holder.itemView)
        binding.textView.text=list[position].title
        binding.textView2.text=list[position].message
    }

    override fun getItemCount(): Int = list.size

}

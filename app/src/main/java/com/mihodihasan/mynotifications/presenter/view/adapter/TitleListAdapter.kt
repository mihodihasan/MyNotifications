package com.mihodihasan.mynotifications.presenter.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mihodihasan.mynotifications.R
import com.mihodihasan.mynotifications.data.model.Notification
import com.mihodihasan.mynotifications.databinding.RowTitlesBinding
import com.mihodihasan.mynotifications.presenter.view.OnListItemClickListener
import com.mihodihasan.mynotifications.presenter.view.adapter.TitleListAdapter.TitleViewHolder

class TitleListAdapter(private val context: Context, private val list:List<Notification>, private val itemClickListener:OnListItemClickListener):RecyclerView.Adapter<TitleViewHolder>() {

    class TitleViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder = TitleViewHolder(LayoutInflater.from(context).inflate(
        R.layout.row_titles, parent, false))


    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val binding = RowTitlesBinding.bind(holder.itemView)
        binding.textView.text = list[position].title
        binding.textView2.text = list[position].message
        holder.itemView.setOnClickListener {
            itemClickListener.onListItemClick(position)
        }
    }

    override fun getItemCount(): Int = list.size
}

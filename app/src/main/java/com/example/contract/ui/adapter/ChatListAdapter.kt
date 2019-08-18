package com.example.gallerylibrary.ui.gallery

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chat.view.*
import com.example.contract.R
import com.example.contract.util.CustomView
import javax.inject.Inject


class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private var customView: CustomView? = null
    lateinit var context: Context
    private var selectItemListener: SelectItemListener? = null
    var chatList: ArrayList<String> = ArrayList()

    fun setSelectItemListener(selectItemListener: SelectItemListener){
        this.selectItemListener = selectItemListener
    }

    fun setCustomView(customView: CustomView){
        this.customView = customView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.userName.text = chatList[position]
        holder.view.setBackgroundColor(Color.WHITE)
        holder.view.setOnClickListener {
            holder.view.setBackgroundColor(Color.LTGRAY)
            this.selectItemListener?.selected(position)
            customView?.isHighlightSelected(holder.view)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
    }

    interface SelectItemListener {
        fun selected(index: Int)
    }
}


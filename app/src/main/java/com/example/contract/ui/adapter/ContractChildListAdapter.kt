package com.example.contract.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chat.view.*
import com.example.contract.R
import com.example.contract.util.CustomView


class ContractChildListAdapter : RecyclerView.Adapter<ContractChildListAdapter.ViewHolder>() {

    private var customView: CustomView? = null
    lateinit var context: Context
    var contractChild : ArrayList<String> = ArrayList()

    fun setCustomView(customView: CustomView){
        this.customView = customView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contractChild.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.userName.text = contractChild[position]
        holder.view.setOnClickListener {
            customView?.isHighlightSelected(holder.view)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
    }
}


package com.example.gallerylibrary.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contract.R
import com.example.contract.ui.adapter.ContractChildListAdapter
import com.example.contract.util.CustomView
import kotlinx.android.synthetic.main.item_group_contract.view.*


class ContractListAdapter : RecyclerView.Adapter<ContractListAdapter.ViewHolder>() {

    private var customView: CustomView? = null
    lateinit var context: Context
    private var selectItemListener: SelectItemListener? = null
    var contractList : ArrayList<ArrayList<String>> = ArrayList()

    fun setSelectItemListener(selectItemListener: SelectItemListener){
        this.selectItemListener = selectItemListener
    }

    fun setCustomView(customView: CustomView){
        this.customView = customView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_group_contract, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contractList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.groupName.text = "Group $position"
        holder.view.contentGroup.apply {
            layoutManager = LinearLayoutManager(context)
            var contractChildListAdapter = ContractChildListAdapter()
            contractChildListAdapter.context = this.context
            contractChildListAdapter.contractChild = contractList[position]
            contractChildListAdapter.setCustomView(customView!!)
            adapter = contractChildListAdapter
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
    }

    interface SelectItemListener {
        fun selected(index: Int)
    }
}


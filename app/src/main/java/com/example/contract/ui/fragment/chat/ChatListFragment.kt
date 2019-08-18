package com.example.contract.ui.fragment.chat

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.contract.R
import com.example.contract.di.component.DaggerFragmentComponent
import com.example.contract.di.module.FragmentModule
import com.example.contract.util.CustomView
import com.example.gallerylibrary.ui.gallery.ChatListAdapter
import kotlinx.android.synthetic.main.fragment_chat_list.*
import javax.inject.Inject


class ChatListFragment : Fragment(), ChatListAdapter.SelectItemListener {

    @Inject
    lateinit var chatListAdapter: ChatListAdapter
    @Inject
    lateinit var customView: CustomView
    var chatList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView(){
        chatList.add("test0")
        chatList.add("test1")
        chatList.add("test2")
        chatList.add("test3")
        chatListAdapter.context = context!!
        chatListAdapter.chatList = chatList
        chatListAdapter.setSelectItemListener(this)
        chatListAdapter.setCustomView(customView)
        content.layoutManager = LinearLayoutManager(context)
        content.adapter = chatListAdapter
        chatListAdapter.notifyDataSetChanged()
    }

    override fun selected(index: Int) {

    }

}

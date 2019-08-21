package com.example.contract.ui.fragment.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.contract.R
import com.example.contract.di.component.DaggerFragmentComponent
import com.example.contract.di.module.FragmentModule
import com.example.contract.ui.activity.main.MainActivity
import com.example.contract.ui.base.BaseFragment
import com.example.contract.util.CustomView
import com.example.contract.ui.adapter.ChatListAdapter
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.fragment_chat_list.toolbarApp
import kotlinx.android.synthetic.main.toolbar_app.view.*
import javax.inject.Inject


class ChatListFragment : BaseFragment(), ChatListAdapter.SelectItemListener {

    @Inject
    lateinit var chatListAdapter: ChatListAdapter
    @Inject
    lateinit var customView: CustomView
    private var chatList: ArrayList<String> = ArrayList()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        //fix data
        chatList.add("test0")
        chatList.add("test1")
        chatList.add("test2")
        chatList.add("test3")

        toolbarApp.title.text = "Chat list"
        toolbarApp.leftIcon.visibility = View.GONE
        toolbarApp.leftIcon.visibility = View.GONE
        toolbarApp.rightIconFirst.visibility = View.GONE
        toolbarApp.rightIconTwo.visibility = View.GONE

        chatListAdapter.setSelectItemListener(this)
        content.apply {
            chatListAdapter.context = context!!
            chatListAdapter.chatList = chatList
            chatListAdapter.setCustomView(customView)
            content.layoutManager = LinearLayoutManager(context)
            content.adapter = chatListAdapter
        }
    }

    override fun selected(index: Int) {
        Log.d("index selected", index.toString())
        val intent = Intent(activity,MainActivity::class.java)
        intent.putExtra("userName", "User $index")
        startActivity(intent)
        this.overrideTransitionRightToLeft()
    }

}

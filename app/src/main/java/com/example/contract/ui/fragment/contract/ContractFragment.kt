package com.example.contract.ui.fragment.contract

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
import com.example.gallerylibrary.ui.gallery.ContractListAdapter
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.toolbar_app.view.*
import javax.inject.Inject


class ContractFragment : Fragment(), ContractListAdapter.SelectItemListener {

    @Inject
    lateinit var contractListAdapter: ContractListAdapter
    @Inject
    lateinit var customView: CustomView
    private var contractList: ArrayList<ArrayList<String>> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contract, container, false)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        toolbarApp.title.text = "Contract"
        toolbarApp.leftIcon.visibility = View.GONE
        toolbarApp.rightIconFirst.visibility = View.GONE
        toolbarApp.rightIconTwo.visibility = View.GONE

        //fix data
        var g1: ArrayList<String> = ArrayList()
        g1.add("Test01")
        g1.add("Test02")
        g1.add("Test03")
        var g2: ArrayList<String> = ArrayList()
        g2.add("Test11")
        g2.add("Test12")
        var g3: ArrayList<String> = ArrayList()
        g3.add("Test21")
        g3.add("Test22")
        g3.add("Test23")
        g3.add("Test24")
        var g4: ArrayList<String> = ArrayList()
        g4.add("Test40")
        contractList.add(g1)
        contractList.add(g2)
        contractList.add(g3)
        contractList.add(g4)

        toolbarApp.leftIcon.visibility = View.GONE
        toolbarApp.leftIcon.visibility = View.GONE
        toolbarApp.rightIconFirst.visibility = View.GONE
        toolbarApp.rightIconTwo.visibility = View.GONE

        contractListAdapter.context = context!!
        contractListAdapter.contractList = contractList
        contractListAdapter.setSelectItemListener(this)
        contractListAdapter.setCustomView(customView)
        content.layoutManager = LinearLayoutManager(context)
        content.adapter = contractListAdapter
        contractListAdapter.notifyDataSetChanged()
    }

    override fun selected(index: Int) {

    }
}

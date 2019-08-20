package com.example.contract.ui.fragment.contract

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.contract.R
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.toolbar_app.view.*


class ContractFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contract, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarApp.leftIcon.visibility = View.GONE
        toolbarApp.rightIconFirst.visibility = View.GONE
        toolbarApp.rightIconTwo.visibility = View.GONE
    }
}

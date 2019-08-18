package com.example.contract.ui.activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.contract.R
import com.example.contract.ui.fragment.chat.ChatListFragment
import com.example.contract.ui.fragment.contract.ContractFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.annotations.Contract


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_contract -> {
                    Log.d("tab selected", "navigation_contract")
                    var contractFragment = ContractFragment()
                    loadFragment(contractFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_chat -> {
                    Log.d("tab selected", "navigation_chat")
                    var chatListFragment = ChatListFragment()
                    loadFragment(chatListFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_setting -> {
                    Log.d("tab selected", "navigation_setting")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottomNavigationView.selectedItemId = R.id.navigation_contract
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(frameContainer.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

package com.example.contract.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.contract.R

open class BaseFragment : Fragment() {
    fun overrideTransitionRightToLeft(){
        activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    fun overrideTransitionLeftTORight(){
        activity?.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    fun overrideTransitionBottomToTop(){
        activity?.overridePendingTransition(R.anim.slide_up,R.anim.no_animation)
    }

    fun overrideTransitionTopToBottom(){
        activity?.overridePendingTransition(R.anim.no_animation,R.anim.slide_down)
    }
}
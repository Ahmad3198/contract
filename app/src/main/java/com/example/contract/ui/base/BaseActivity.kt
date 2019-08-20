package com.example.contract.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.contract.R
import com.example.contract.ui.BasePresenter

enum class AnimationClose {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    BOTTOM_TO_TOP,
    TOP_TO_BTTOM
}

open class BaseActivity : AppCompatActivity() {
    fun overrideTransitionRightToLeft(){
        this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    fun overrideTransitionLeftTORight(){
        this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
    }

    fun overrideTransitionBottomToTop(){
        this.overridePendingTransition(R.anim.slide_up,R.anim.no_animation)
    }

    fun overrideTransitionTopToBottom(){
        this.overridePendingTransition(R.anim.no_animation,R.anim.slide_down)
    }

    fun closeActivity(view: View, animation: AnimationClose){
        view.setOnClickListener {
            finish()
            when(animation) {
                AnimationClose.LEFT_TO_RIGHT -> this.overrideTransitionLeftTORight()
                AnimationClose.RIGHT_TO_LEFT -> this.overrideTransitionRightToLeft()
                AnimationClose.BOTTOM_TO_TOP -> this.overrideTransitionBottomToTop()
                AnimationClose.TOP_TO_BTTOM -> this.overrideTransitionTopToBottom()

            }
        }
    }
}
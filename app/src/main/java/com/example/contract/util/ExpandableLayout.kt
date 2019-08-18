package com.example.contract.util

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlin.math.log
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation



class ExpandableLayout(val view: View) {

    private lateinit var animationEnd: AnimationEnd

    fun attach(animationEnd: AnimationEnd) {
        this.animationEnd = animationEnd
    }

    fun show(activity: AppCompatActivity) {
//        view.measure(LinearLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT)
        /*convert px to dp : (Constants().getScreenDisplay(activity).heightPixels * 0.5 / Resources.getSystem().displayMetrics.density)*/
        val targetHeight = (Constants().getScreenDisplay(activity).heightPixels * 0.5 / Resources.getSystem().displayMetrics.density)
        view.layoutParams.height = 0
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    view.layoutParams.height = 0
                } else {
//                    view.layoutParams.height = (targetHeight * interpolatedTime).toInt()
                }
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = targetHeight.toLong()
        view.startAnimation(a)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {

            }

            override fun onAnimationStart(p0: Animation?) {
                animationEnd.onShowAnimationEnd()
            }

        })
    }

    fun hide() {
        val initialHeight = view.measuredHeight
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
//                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = initialHeight.toLong()
        view.startAnimation(a)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                animationEnd.onHideAnimationEnd()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }

    fun expandOrCollapse(activity: AppCompatActivity, isExpand: Boolean) {
        /*convert px to dp : (Constants().getScreenDisplay(activity).heightPixels * 0.5 / Resources.getSystem().displayMetrics.density)*/
        val targetHeight = (Constants().getScreenDisplay(activity).heightPixels * 0.6 / Resources.getSystem().displayMetrics.density)
        val initialHeight = view.measuredHeight
        if (isExpand) {
            view.layoutParams.height = 1
            view.visibility = View.VISIBLE
        }
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (isExpand){
                    view.layoutParams.height = (targetHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }else {
                    if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                    } else {
                        view.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                        Log.d("Height", view.layoutParams.height.toString())
                        view.requestLayout()
                    }
                }

            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        if (isExpand) {
            a.duration = targetHeight.toLong()
        } else {
            a.duration = initialHeight.toLong()
        }
        view.startAnimation(a)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {

            }

            override fun onAnimationStart(p0: Animation?) {
              if (isExpand) {animationEnd.onShowAnimationEnd()} else {animationEnd.onHideAnimationEnd()}
            }

        })
    }

    interface AnimationEnd{
        fun onShowAnimationEnd()
        fun onHideAnimationEnd()
    }
}
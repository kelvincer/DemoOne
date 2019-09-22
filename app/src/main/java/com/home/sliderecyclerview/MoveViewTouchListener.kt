package com.home.sliderecyclerview

import android.util.Log
import android.view.MotionEvent
import android.view.GestureDetector
import android.view.View


class MoveViewTouchListener(private val mView: View, val maxY: Int) : View.OnTouchListener {
    private val mGestureDetector: GestureDetector
    val TAG =MoveViewTouchListener::class.java.simpleName

    private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        private var mMotionDownX: Float = 0.toFloat()
        private var mMotionDownY: Float = 0.toFloat()

        override fun onDown(e: MotionEvent): Boolean {
            mMotionDownX = e.rawX - mView.translationX
            mMotionDownY = e.rawY - mView.translationY
            return true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                              distanceX: Float, distanceY: Float): Boolean {
            //mView.setTranslationX(e2.rawX - mMotionDownX)
            Log.d(TAG, "maxY: ${maxY}")
            Log.d(TAG, "diff: ${e2.y}")
            if ((e2.y) > maxY)
                mView.translationY = e2.rawY - mMotionDownY
            return true
        }
    }


    init {
        mGestureDetector = GestureDetector(mView.context, mGestureListener)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }
}
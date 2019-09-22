package com.home.sliderecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnTouchListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //btn.setOnTouchListener(this)

        // btn.setOnTouchListener(MoveViewTouchListener(btn, 30))
    }

    var dX: Float = 0.toFloat()
    var dY: Float = 0.toFloat()

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = v?.x ?: 0 - event.rawX
                dY = v?.y ?: 0 - event.rawY
            }// Here u can write code which is executed after the user touch on the screen

            MotionEvent.ACTION_MOVE -> {
                v?.animate()
                        ?.x(event.rawX + dX)
                        ?.y(event.rawY + dY)
                        ?.setDuration(0)
                        ?.start()

            }// Here u can write code which is executed when user move the finger on the screen
            else -> {
                return false
            }
        }
        return true
    }
}

package com.example.doctor.objects

import android.os.CountDownTimer
import android.util.Log
import java.util.*


object Status {

    var isDisconnected = false
    //Declare timer
    var cTimer: CountDownTimer? = null

    var mStatus = false

    var mDelete = false

    var mTime  = Calendar.getInstance().time



    fun setTime(newTime : Date) {
        mTime = newTime
        Log.i("settimermy", mTime.toString())
        Log.i("settimermy", mTime.time.toString())

//        return time
    }

    fun connect(){
        mStatus = true
    }

    fun disConnect(){
        mStatus = false
    }

    fun delete(){
        mDelete = true
    }

    //start timer function
    fun startTimer() {
        cTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {}
        }
        (cTimer as CountDownTimer).start()
    }

    //cancel timer
    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }


}
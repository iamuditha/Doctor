package com.example.doctor.fullScreenRecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.example.doctor.R

class FullScreenRecordActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_record)

        val myIntent: Intent = intent
        val position = myIntent.getIntExtra("position",0)
        val imageList = myIntent.getIntegerArrayListExtra("List") as ArrayList<Int>
        Log.i("postion",position.toString())

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val fullScreenViewPageAdapter =
            FullScreenViewPageAdapter(
                applicationContext,
                imageList,
                position
            )
        viewPager.adapter = fullScreenViewPageAdapter
    }
}

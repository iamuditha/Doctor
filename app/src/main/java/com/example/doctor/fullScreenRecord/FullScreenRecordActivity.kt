package com.example.doctor.fullScreenRecord

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.doctor.R
import com.example.doctor.recordList.RecordModel


class FullScreenRecordActivity(data: ArrayList<RecordModel>) : AppCompatActivity() {

    var newList = ArrayList<Bitmap>()
    var data = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_record)

        val myIntent: Intent = intent
        val position = myIntent.getIntExtra("position", 0) as Int
        var imageList = myIntent.getStringArrayListExtra("List")


        for (i in imageList){
            newList.add(newList.size, StringToBitMap(i)!!)
        }


        Log.i("postion", position.toString())

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val fullScreenViewPageAdapter =
            FullScreenViewPageAdapter(
                applicationContext,
                newList,
                position
            )
        viewPager.adapter = fullScreenViewPageAdapter
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }
}

package com.example.doctor.recordList

import android.content.Context
import android.graphics.BitmapFactory
import com.example.doctor.R

class RecordRepository() {

    fun fetchRecord(context : Context) : List<RecordModel>{
        return listOf(
//            RecordModel("https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ","2019 / 0 / 22", null),
//                    RecordModel("https://drive.google.com/uc?export=download&id=1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ","2019 / 0 / 22", null)
            RecordModel("PCR Test","2020/01/18",BitmapFactory.decodeResource(context.resources, R.drawable.record1)),
            RecordModel("Glucose Test","2019/01/28",BitmapFactory.decodeResource(context.resources, R.drawable.record2)),
            RecordModel("Dengue Test","2020/01/18",BitmapFactory.decodeResource(context.resources, R.drawable.record3))



        )
    }


}
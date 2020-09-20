package com.example.doctor.recordList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctor.R
import kotlinx.android.synthetic.main.activity_data.*

class RecordListActivity : AppCompatActivity() {

    private val arrayList = ArrayList<RecordModel>()
    private val dataAdapter =
        RecordListAdapter(arrayList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        arrayList.add(
            RecordModel(
                "Full Blood Count",
                "2020/05/10",
                R.drawable.record1
            )
        )
        arrayList.add(
            RecordModel(
                "Dengue Test",
                "2020/05/10",
                R.drawable.record2
            )
        )
        arrayList.add(
            RecordModel(
                "This is Title",
                "2020/05/10",
                R.drawable.record3
            )
        )

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = dataAdapter

    }
}

package com.example.doctor.recordList

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctor.R
import kotlinx.android.synthetic.main.activity_data.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class RecordListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

         var arrayList = ArrayList<RecordModel>()
         var newArrayList = ArrayList<RecordModel>()

         val dataAdapter = RecordListAdapter(arrayList,this)
         val dataList = RecordRepository().fetchRecord()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        newArrayList = arrayList
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = dataAdapter



        val downloader = Thread{
            Log.i("threadhjkl", "1 called")
            dataList[0].title.saveTo(Environment.getExternalStorageDirectory().toString()+"/hahahah.crypt/")
            val bitmap = Encdec().decrypt(Environment.getExternalStorageDirectory().toString() + "/hahahah.crypt/"
            )

            runOnUiThread {
                newArrayList.add(
                    RecordModel("done", "204549950", bitmap)
                )
                arrayList.clear()
                arrayList.addAll(newArrayList)
                dataAdapter.notifyDataSetChanged()
            }


        }
        downloader.start()
        downloader.join()



        val downloader1 = Thread{
            Log.i("threadhjkl", "2 called")

            dataList[0].title.saveTo(Environment.getExternalStorageDirectory().toString()+"/hahahah1.crypt/")
            val bitmap = Encdec().decrypt(Environment.getExternalStorageDirectory().toString() + "/hahahah1.crypt/"
            )
            arrayList.add(RecordModel("done sdfghjk", "204549950", bitmap))

            dataAdapter.notifyItemInserted(arrayList.size-1)


        }
        downloader1.start()
        downloader1.join()


    }

    fun String.saveTo(path: String) {
        URL(this).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
    }
}

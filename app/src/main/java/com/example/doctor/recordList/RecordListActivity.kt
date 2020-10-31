package com.example.doctor.recordList

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctor.R
import kotlinx.android.synthetic.main.activity_record_list.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RecordListActivity : AppCompatActivity() {

    private val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        var arrayList = ArrayList<RecordModel>()
        var newArrayList = ArrayList<RecordModel>()

        val dataAdapter = RecordListAdapter(arrayList, this)
        val dataList = RecordRepository().fetchRecord(this)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        autoComplete()


        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }


        from.setOnClickListener {
            DatePickerDialog(
                this@RecordListActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }



//        newArrayList = arrayList
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = dataAdapter
//
//
//
//        val downloader = Thread{
//            Log.i("threadhjkl", "1 called")
//            dataList[0].title.saveTo(Environment.getExternalStorageDirectory().toString()+"/hahahah.crypt/")
//            val bitmap = Encdec().decrypt(Environment.getExternalStorageDirectory().toString() + "/hahahah.crypt/"
//            )
//
//            runOnUiThread {
//                newArrayList.add(
//                    RecordModel("done", "204549950", bitmap)
//                )
//                arrayList.clear()
//                arrayList.addAll(newArrayList)
//                dataAdapter.notifyDataSetChanged()
//            }
//
//
//        }
//        downloader.start()
//        downloader.join()
//
//
//
//        val downloader1 = Thread{
//            Log.i("threadhjkl", "2 called")
//
//            dataList[0].title.saveTo(Environment.getExternalStorageDirectory().toString()+"/hahahah1.crypt/")
//            val bitmap = Encdec().decrypt(Environment.getExternalStorageDirectory().toString() + "/hahahah1.crypt/"
//            )
//            arrayList.add(RecordModel("done sdfghjk", "204549950", bitmap))
//
//            dataAdapter.notifyItemInserted(arrayList.size-1)
//
//
//        }
//        downloader1.start()
//        downloader1.join()
//
//
        arrayList.addAll(dataList)

    }

    private fun autoComplete() {

        val arr = arrayOf(
            "Paries,France", "PA,United States", "Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"
        )

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, arr)
        autoComplete.threshold = 1
        autoComplete.setAdapter(adapter)
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        from.setText(sdf.format(myCalendar.getTime()))
    }


    fun String.saveTo(path: String) {
        URL(this).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
    }
}

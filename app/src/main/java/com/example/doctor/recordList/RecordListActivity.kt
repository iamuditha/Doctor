package com.example.doctor.recordList

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctor.BaseActivity
import com.example.doctor.R
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RecordListActivity : BaseActivity() {

    private val fromCalender: Calendar = Calendar.getInstance()
    private val toCalendar: Calendar = Calendar.getInstance()
    private val arr = arrayOf(
        "Paries,France", "PA,United States", "Parana,Brazil",
        "Padua,Italy", "Pasadena,CA,United States"
    )


    override fun onCreate(savedInstanceState: Bundle?) {


        var arrayList = ArrayList<RecordModel>()
        var newArrayList = ArrayList<RecordModel>()

        var selectedTest = ArrayList<String>()


        val testAdapter = TestListAdapter(selectedTest, this)


        val dataAdapter = RecordListAdapter(arrayList, this)
        val dataList = RecordRepository().fetchRecord(this)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        autoComplete()
        autoComplete.setOnItemClickListener(OnItemClickListener { parent, arg1, position, arg3 ->
            val item = parent.getItemAtPosition(position)
            if (arr.contains(item)) {
                selectedTest.add(item.toString())
                Log.i("selectedText", item.toString())
                selectedTests.layoutManager = LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                selectedTests.adapter = testAdapter
            }
        })

        val fromDate =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                fromCalender.set(Calendar.YEAR, year)
                fromCalender.set(Calendar.MONTH, monthOfYear)
                fromCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(from, fromCalender)
            }

        val toDate =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                toCalendar.set(Calendar.YEAR, year)
                toCalendar.set(Calendar.MONTH, monthOfYear)
                toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(to, toCalendar)
            }


        from.setOnClickListener {
            DatePickerDialog(
                this@RecordListActivity,
                fromDate,
                fromCalender[Calendar.YEAR],
                fromCalender[Calendar.MONTH],
                fromCalender[Calendar.DAY_OF_MONTH]
            ).show()
        }
        to.setOnClickListener {
            DatePickerDialog(
                this@RecordListActivity,
                toDate,
                toCalendar[Calendar.YEAR],
                toCalendar[Calendar.MONTH],
                toCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        download.setOnClickListener {
            Log.i("selections", convertDateToString(fromCalender))
        }


//        newArrayList = arrayList
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = dataAdapter

        selectedTests.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        selectedTests.adapter = testAdapter
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun autoComplete() {


        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, arr)
        autoComplete.threshold = 1
        autoComplete.setAdapter(adapter)
    }

    private fun updateLabel(editText: EditText, calender: Calendar) {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(sdf.format(calender.time))
    }

    private fun convertDateToString(calender: Calendar): String {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(calender.time)
    }


    fun String.saveTo(path: String) {
        URL(this).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
    }

    fun makeToast(view: View,string: String){
        Log.i("selectedItem", string)
    }


}

package com.example.doctor.recordList

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.fullScreenRecord.FullScreenRecordActivity
import kotlinx.android.synthetic.main.record_item.view.*

class RecordListAdapter(private val arrayList: ArrayList<RecordModel>, private val context: Context) :
    RecyclerView.Adapter<RecordListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(
        itemView
    ) {
        fun bindItems(model: RecordModel) {
            itemView.title.text = model.title
            itemView.date.text = model.date
            itemView.recordImage.setImageBitmap(model.image)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return ViewHolder(v, context)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener {
            holder.itemView.setBackgroundColor(Color.parseColor("#b20000"))
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            val intent = Intent(
                context,
                FullScreenRecordActivity::class.java
            )
            intent.putExtra("position", position)
            Log.i("checking pos", position.toString())
            intent.putExtra("List", recordList(arrayList))
            Log.i("checking pos", recordList(arrayList)[position].toString())
            context.startActivity(intent)

        }
    }

    private fun recordList(list: ArrayList<RecordModel>):ArrayList<Bitmap>{
        var myList = ArrayList<Bitmap>()
        for(RecordModel in list){
            myList.add(RecordModel.image!!)
        }
        return  myList
    }

}

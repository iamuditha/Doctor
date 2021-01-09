package com.example.doctor.recordList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import kotlinx.android.synthetic.main.list_item.view.*

class TestListAdapter(private val arrayList: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<TestListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bindItems(model: String) {
            itemView.testName.text = model
        }

        override fun onClick(p0: View?) {
            if (p0 != null) {
                (context as RecordListActivity).makeToast(p0,adapterPosition.toString())

            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v, context)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener {
            removeAt(position)
            for (i in arrayList){
                Log.i("selectedList",i)
            }
        }




    }
    private fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, arrayList.size)
    }
}
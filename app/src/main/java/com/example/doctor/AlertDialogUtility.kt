package com.example.doctor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.alert_resource_layout.view.*

class AlertDialogUtility {

    companion object {
        fun alertDialog(context: Context, alertText: String, animNumber: Int) {
            val layoutBuilder = LayoutInflater.from(context).inflate(R.layout.alert_resource_layout, null)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(layoutBuilder)
            val alertDialog:AlertDialog = builder.show()
            layoutBuilder.tv_alert.text = alertText
            layoutBuilder.et_name.visibility = View.INVISIBLE

            if (animNumber == 1) {
                layoutBuilder.lottie_anim.setAnimation(R.raw.authentication_animation)   // json file in assets folder.
                // If json file in raw folder it will not work, file not found exception.
            } else if (animNumber == 2) {
                layoutBuilder.lottie_anim.setAnimation(R.raw.authentication_animation)
            } else {
                Log.e("no ", "anim selected")
            }

            layoutBuilder.lottie_anim.loop(true)
            layoutBuilder.lottie_anim.playAnimation()
            layoutBuilder.btn_ok.setOnClickListener {
                Toast.makeText(context, "Ok Bye "/* + layoutBuilder.et_name.text.toString()*/, Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
        }
    }
}
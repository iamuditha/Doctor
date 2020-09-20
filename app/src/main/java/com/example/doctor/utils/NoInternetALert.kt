package com.example.doctor.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.doctor.R

class NoInternetDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setIcon(R.drawable.warning)
            builder.setTitle("No Internet Connection")
            builder.setMessage("It looks like your Internet Connection is Off. Please turn it on and try again")
                .setPositiveButton("ok",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
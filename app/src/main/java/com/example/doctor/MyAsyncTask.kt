package com.example.doctor

import android.os.AsyncTask
import android.util.Log
import com.google.api.services.drive.model.File
import java.io.IOException

class MyAsyncTask(private val mDriveServiceHelper: DriveServiceHelper) : AsyncTask<Void?, Void?, List<File>?>() {

    var fileList: List<File>? = null

    override fun onPostExecute(files: List<File>?) {
        super.onPostExecute(files)
        Log.i("logininfo", "called")

        if (files!!.isEmpty()) {
            Log.i("logininfo", "No Files")
        }
        for (file in files) {
            Log.i(
                "logininfo", """Found file: File Name :${file.name} File Id :${file.id}"""
            )
        }
       returnList()
    }

     fun returnList(): List<File>? {
        return fileList
    }

    override fun doInBackground(vararg p0: Void?): List<File>? {
        try {

//            mDriveServiceHelper= new SecondActivity().mDriveServiceHelper;
            fileList = mDriveServiceHelper.listDriveImageFiles()
        } catch (e: IOException) {
            Log.i("logininfo", "IO Exception while fetching file list")
        }
        return fileList
    }


}
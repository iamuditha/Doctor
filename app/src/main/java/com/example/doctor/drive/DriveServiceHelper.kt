package com.example.doctor.drive

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import com.google.api.services.drive.model.Permission
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DriveServiceHelper(private val mDriveService: Drive) {

    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    //creating a new file in the google drive
    fun createFile(folderId: String?, filename: String?): Task<GoogleDriveFileHolder> {
        return Tasks.call<GoogleDriveFileHolder>(mExecutor,
            Callable<GoogleDriveFileHolder> {
                val googleDriveFileHolder = GoogleDriveFileHolder()
                val root: List<String> = folderId?.let { listOf(it) } ?: listOf("root")
                val metadata = File()
                    .setParents(root)
                    .setMimeType("text/plain")
                    .setName(filename)
                    .setWritersCanShare(true)
                val googleFile = mDriveService.files().create(metadata).execute()
                    ?: throw IOException("Null result when requesting file creation.")
                googleDriveFileHolder.setId(googleFile.id)
                googleDriveFileHolder
            }
        )
    }

    // create a new folder in the google drive
    fun createFolder(folderName: String?, folderId: String?): Task<GoogleDriveFileHolder> {

        val permission = Permission()
            .setRole("owner")
            .setType("anyone")
        return Tasks.call<GoogleDriveFileHolder>(mExecutor,
            Callable<GoogleDriveFileHolder> {
                val googleDriveFileHolder = GoogleDriveFileHolder()
                val root: List<String> = folderId?.let { listOf(it) } ?: listOf("root")
                val metadata = File()
                    .setParents(root)
                    .setMimeType("application/vnd.google-apps.folder")
                    .setName(folderName)

                val googleFile = mDriveService.files().create(metadata).execute()
                    ?: throw IOException("Null result when requesting file creation.")
                googleDriveFileHolder.setId(googleFile.id)
                googleDriveFileHolder
            }
        )
    }

    //download a file from the google drive
    fun downloadFile(targetFile: java.io.File?, fileId: String?): Task<Nothing?>? {
        return Tasks.call(
            mExecutor,
            Callable {
                val outputStream: OutputStream = FileOutputStream(targetFile)
                mDriveService.files()[fileId].executeMediaAndDownloadTo(outputStream)
                null
            }
        )
    }

    //delete a folder with/without files in google drive
    fun deleteFolderFile(fileId: String?): Task<Nothing?>? {
        return Tasks.call(
            mExecutor,
            Callable {
                if (fileId != null) {
                    mDriveService.files().delete(fileId).execute()
                }
                null
            }
        )
    }

    // listing the files in the google drive created by the application
    @Throws(IOException::class)
    fun listDriveImageFiles(): MutableList<File>? {
        var result: FileList
        var pageToken: String? = null
        do {
            result = mDriveService.files()
                .list()
                .setSpaces("drive")
                .setFields("nextPageToken, files(id,name,webContentLink,shared,permissions)")
                .setPageToken(pageToken)
                .execute()
            pageToken = result.nextPageToken
        } while (pageToken != null)
        return result.files
    }

    // upload a file in to google drive
    fun uploadFileToAppDataFolder(fileInputStream: ByteArray, mimeType: String?, folderId: String?, filename: String?): Task<Any>? {
        return Tasks.call(mExecutor, Callable<Any> { // Retrieve the metadata as a File object.
            val root: List<String> = folderId?.let { listOf(it) } ?: listOf("root")
            val metadata = File()
                .setParents(root)
                .setMimeType(mimeType)
                .setName(filename)
            val fileMeta = mDriveService.files().create(
                metadata,
                ByteArrayContent("json/application",fileInputStream)
            ).execute()


//            val permission = Permission().setType("anyone").setRole("reader")

//            mDriveService.Permissions().create(fileMeta.id,permission).execute()

            val link = mDriveService.files().get(fileMeta.id).setFields("webContentLink").execute()

            val googleDriveFileHolder = GoogleDriveFileHolder()
            googleDriveFileHolder.setId(fileMeta.id)
            googleDriveFileHolder.setName(fileMeta.name)
            googleDriveFileHolder.setWebContentLink(link.webContentLink)
            googleDriveFileHolder
        })
    }

    // upload a file in to google drive
    fun uploadFile(localFile: java.io.File, mimeType: String?, folderId: String?): Task<Any>? {
        return Tasks.call(mExecutor, Callable<Any> { // Retrieve the metadata as a File object.
            val root: List<String> = folderId?.let { listOf(it) } ?: listOf("root")
            val metadata = File()
                    .setParents(root)
                    .setMimeType(mimeType)
                    .setName(localFile.name)
            val fileContent = FileContent(mimeType, localFile)
            val fileMeta = mDriveService.files().create(
                    metadata,
                    fileContent
                ).execute()
            val googleDriveFileHolder = GoogleDriveFileHolder()
            googleDriveFileHolder.setId(fileMeta.id)
            googleDriveFileHolder.setName(fileMeta.name)
            googleDriveFileHolder
        })
    }


    fun createFilePdf(filePath: String?): Task<String>? {
        return Tasks.call(mExecutor, Callable {
            val fileMetaData =
                File()
            fileMetaData.name = "MyPDFFile"
            val file = java.io.File(filePath)
            val mediaContent = FileContent("image/jpeg", file)
            var myFile: File? = null
            try {
                myFile = mDriveService.files().create(fileMetaData, mediaContent).execute()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("myissue", e.message!!)
            }
            if (myFile == null) {
                throw IOException("Null result when requesting file creation")
            }
            myFile.id
        })
    }

}
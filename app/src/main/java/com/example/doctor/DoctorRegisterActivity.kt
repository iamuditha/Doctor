package com.example.doctor

//import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256

import ContractorHandlers.IAMContractorHandler
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.doctor.challangeResponse.ChallengeResponse
import com.example.doctor.qr.QRActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.gson.Gson
import crypto.did.DID
import crypto.did.DIDDocument
import crypto.did.DIDDocumentGenerator
import javaethereum.contracts.generated.IAMContract
import kotlinx.android.synthetic.main.activity_doctor_register.*
import kotlinx.android.synthetic.main.toolbar.*
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import utilities.EthFunctions
import java.io.*
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


class DoctorRegisterActivity : BaseActivity() {

    private var RC_AUTHORIZE_DRIVE = 101
    private var ACCESS_DRIVE_SCOPE = Scope(Scopes.DRIVE_FILE)
    private var SCOPE_EMAIL = Scope(Scopes.EMAIL)
    private var SCOPE_APP_DATA = Scope(Scopes.DRIVE_APPFOLDER)
    private var googleDriveService: Drive? = null
    private var mDriveServiceHelper: DriveServiceHelper? = null
    private val mExecutor: Executor = Executors.newSingleThreadExecutor()
    private var list: List<String> = ArrayList()
    private val REQUEST_IMAGE_CAPTURE: Int = 100

    val alertDialogUtility = AlertDialogUtility





    //configure the google sign in
    var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)
        checkForGooglePermissions()

        val toolbar = (R.id.toolbar_main)
        setSupportActionBar(toolbar_main)
write.setOnClickListener {
    val challengeResponse = ChallengeResponse("mydid")

    challengeResponse.challengeResponse()
}

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar_main,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setHomeButtonEnabled(true)

        alertDialogUtility.alertDialog(this, "Searching for DID Document in the Phone", 1)

        var prefs: SharedPreferences = getSharedPreferences("PROFILE_DATA", MODE_PRIVATE)
        var name: String? = prefs.getString("name", "No name defined")
        var email: String? = prefs.getString("email", "no email")
        var url: String? = prefs.getString("url", "no email")


        Log.i("sharedData", "$name $email $url")



        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //randomly generated string for the DID
        val myPublicKey =  UUID.randomUUID().toString().substring(0, 8)

        Log.i("did", filesDir.absolutePath)


        button.setOnClickListener {
//            val intent = Intent(this, QRActivity::class.java)
//            intent.putExtra("did", "this is a did")
//            startActivity(intent)

            val thread = Thread{

              try {
                  val web3j: Web3j = Web3j.build(HttpService("https://c7fc09575149.ngrok.io"))

                  val credentials = EthFunctions.createWallet("123", filesDir.absolutePath)

                  val iamContractorHandler = IAMContractorHandler.getInstance()

                  val iamContract = iamContractorHandler.getWrapperForContractor(web3j,getString(R.string.main_contractor_address),credentials)

                  //get the blockchain registered email
                  val blockchainEmail = iamContractorHandler.getDoctorEmail(iamContract,"mydid")

                  Log.i("didm", blockchainEmail)
              }catch (e : Exception){
                  Log.i("didm", "error ${e.toString()}")

              }
            }

            thread.start()

//            downloadFile()
//            getPermission()

//            getURL(googleDriveService.files()[])

//            checkForGooglePermissions()
//            createFolderInDrive()
//            uploadFileToDrive()
//            listFilesInDrive()
//            challengeResponse.challengeResponse()
//            if (true) {
//                val did = generateDID()
//                val didDocument = did?.let { it1 -> generateDIDDocument(it1, myPublicKey) }
//                writeToFile(didDocument!!, "didDocument.json", this)
//
//                val hash = hashMessage(didDocument)
//
////                val credentials = createWallet("123", filesDir.absolutePath)
////                Log.i("did", "credential done : ${credentials!!.address}")
//                var credentials = WalletUtils.loadCredentials("123","/data/data/com.example.doctor/files/UTC--2020-10-02T08-18-45.1Z--e2312d4ec9d9463f033e8d71bc36588a861ab782.json")
////                MainContractorHandler.getInstance()
////                    .getWrapperForMainContractor(
////                        web3j,
////                        getString(R.string.main_contractor_address),
////                        credentials
////                    )
////                    .registerDoctor("http://link of the did document", hash,"123456789")
//
//                val thread1 = Thread(Runnable {
//                    try {
//                        val balance = web3j.ethGetBalance("0xe2312d4ec9d9463f033e8d71bc36588a861ab782",DefaultBlockParameterName.LATEST).send().balance
//                        Log.i("did",balance.toString())
//                        val mainContractorHandler = MainContractorHandler.getInstance()
//                        val mainContract = mainContractorHandler.getWrapperForMainContractor(web3j,getString(R.string.main_contractor_address),credentials)
//                        mainContractorHandler.registerDoctor(mainContract,web3j,"asdf","asdf","123456789",credentials)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                })
//                thread1.start()
//                val thread = Thread(Runnable {
//                    try {
//                        Log.i("did", web3j.ethBlockNumber().send().blockNumber.toString())
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                })
//
//                thread.start()
//                val intent = Intent(this, QRActivity::class.java)
//                intent.putExtra("did", did)
//                startActivity(intent)
//
//
//            } else {
//                val details = readFromFile("didDocument.json", this)
//                val did = details!!.subSequence(16, 69)
//                Log.i("did", "did is $did.toString()")
//                val intent = Intent(this, QRActivity::class.java)
//                intent.putExtra("did", "123456789")
//                startActivity(intent)
//            }
        }


        //upload did to drive

        //get the hash

        //upload the link to the block chain
    }

    //convert the did document in to JSON format
    private fun convertToJSON(didDocument: DIDDocument): String {
        val gson = Gson()
        val json: String = gson.toJson(didDocument)
        Log.i("did", "Convert to a JSON /n $json")
        return json
    }

    //generate the DID as a string
    private fun generateDID(): String? {
        try {
            val did = DID.getInstance().generateDID()
            Log.i("did", "DID is Generated : DID = $did")
            return did

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            Log.i("did", "error..............")
            return e.message
        }
    }

    //generate the did document data and convert it to JSON format
    private fun generateDIDDocument(did: String, publicKey: String): String {
        return try {
            val didDocument = DIDDocumentGenerator.getInstance().generateDIDDocument(did, publicKey)
            Log.i("did", "DID Document is Generated : DID Document = $didDocument")
            convertToJSON(didDocument)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            Log.i("did", "error.............. ${e.message}")
            e.message!!
        }
    }

    //write a string to a file
    private fun writeToFile(data: String, fileName: String, context: Context) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
            Log.i("did", "Created a New file in the directory $fileName")

        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    //read the file content as a string
    private fun readFromFile(fileName: String, context: Context): String? {
        var mText = ""
        try {
            val inputStream: InputStream? = context.openFileInput(fileName)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                mText = stringBuilder.toString()

            }
        } catch (e: FileNotFoundException) {
            Log.e("did", "File $fileName not found: $e")
        } catch (e: IOException) {
            Log.e("did", "Can not read file $fileName: $e")
        }
        Log.i("did", "$fileName contains : $mText")
        return mText
    }

    //check whether the previously created DID document is in the mobile device
    private fun isDIDDocumentInThePhone(): Boolean {
        val file = File(applicationContext.filesDir, "didDocument.json")
        Log.e("did", "Is there a DID document in the mobile: ${file.exists()}")
        return file.exists()
    }

    //generate the hash of a string using SHA-256
    private fun hashMessage(msg: String): String? {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = messageDigest.digest(msg.toByteArray(StandardCharsets.UTF_8))
        val sb = java.lang.StringBuilder()
        for (i in bytes.indices) {
            sb.append((bytes[i].toInt() and 0xff) + 0x100).substring(1)
        }
        Log.i("did", "hash is : ${sb.toString()}")
        return sb.toString()
    }

    //create a wallet
    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class,
        CipherException::class,
        IOException::class
    )
    fun createWallet(walletPassword: String?, walletDirectory: String): Credentials? {
        val walletName = WalletUtils.generateBip39Wallet(walletPassword, File(walletDirectory))
        println("wallet location: $walletDirectory/$walletName")
        val credentials =
            WalletUtils.loadCredentials(walletPassword, "$walletDirectory/${walletName.filename}")
        val privateKey = credentials.ecKeyPair.privateKey.toString(16)
        val publicKey = credentials.ecKeyPair.publicKey.toString(16)
        val walletAddress = credentials.address
        println("privateKey:$privateKey")
        println("publicKey:$publicKey")
        println("Wallet Address: $walletAddress")
        return credentials
    }

    //check if the device has google permission to access the google services
    private fun checkForGooglePermissions() {
        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(applicationContext),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_APP_DATA
            )
        ) {
            Log.i("logininfo", "oooooooooooooooooooooooooooooooooooops")
            GoogleSignIn.requestPermissions(
                this@DoctorRegisterActivity,
                RC_AUTHORIZE_DRIVE,
                GoogleSignIn.getLastSignedInAccount(applicationContext),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_APP_DATA
            )
        } else {
            driveSetUp()
        }
    }

    //request the google drive permission for the device
    private fun driveSetUp() {
        val mAccount = GoogleSignIn.getLastSignedInAccount(this@DoctorRegisterActivity)
        val credential = GoogleAccountCredential.usingOAuth2(
            applicationContext, setOf(Scopes.DRIVE_APPFOLDER)
        )
        credential.selectedAccount = mAccount!!.account
        googleDriveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        )
            .setApplicationName("GoogleDriveIntegration 3")
            .build()
        val mGoogleDriveService = googleDriveService
        mDriveServiceHelper = mGoogleDriveService?.let { DriveServiceHelper(it) }
    }


    //create folder in the drive
    private fun createFolderInDrive() {
        Log.i("logininfo", "Creating a Folder...")
        mDriveServiceHelper!!.createFolder("bif DID Folder", null)
            .addOnSuccessListener { googleDriveFileHolder ->
                val gson = Gson()
                Log.i(
                    "logininfo",
                    "onSuccess c of Folder creation: " + gson.toJson(googleDriveFileHolder)
                )
            }
            .addOnFailureListener { e ->
                Log.i(
                    "logininfo",
                    "onFailure of Folder creation: " + e.message
                )
            }
    }

    private fun downloadFile(){
        mDriveServiceHelper!!.downloadFile(
            File(
                Environment.getExternalStorageDirectory().toString() + "/uditha.crypt/"
            ), "1pfaZSJDtex763-8vyTF-Z8uAl4dqAwMZ"
        )
            ?.addOnSuccessListener { googleDriveFileHolder ->
                val gson = Gson()
                Log.i(
                    "logininfo",
                    "onSuccess c of Folder creation: " + gson.toJson(googleDriveFileHolder)
                )
            }
            ?.addOnFailureListener { e ->
                Log.i(
                    "logininfo",
                    "onFailure of Folder creation: " + e.message
                )
            }
    }

    //list the files in the drive
    private fun listFilesInDrive() {

        val thread = Thread(Runnable {
            try {
                if (mDriveServiceHelper == null) {
                    Log.i("logininfo", "this is null")
                    checkForGooglePermissions()
                }
                var fileList = mDriveServiceHelper?.listDriveImageFiles()
                Log.i("logininfo", fileList!!.size.toString())
                Log.i("logininfo", fileList!![0].toString())
                Log.i("logininfo", fileList!![1].toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start()


    }

    //upload a file to the drive
    private fun uploadFileToDrive() {
        val file = File(applicationContext.filesDir, "didDocument.json")
        mDriveServiceHelper!!.uploadFile(file, "application/json", null)
            ?.addOnSuccessListener { googleDriveFileHolder ->
                val gson = Gson()
                Log.i(
                    "logininfo",
                    "on success File download" + gson.toJson(googleDriveFileHolder)
                )
            }
            ?.addOnFailureListener { e ->
                Log.i(
                    "logininfo",
                    "on failure of file download" + e.message
                )
            }
    }

    fun setList(list: List<String?>?) {
        this.list = list as List<String>
    }

    fun getURL(file: com.google.api.services.drive.model.File): String {
        val link = file.webContentLink
        return "link"
    }

    private fun getPermission() {
        //if the system is marshmallow or above get the run time permission
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
        ) {
            //permission was not enabled
            val permission = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            //show popup to request permission
            requestPermissions(permission, REQUEST_IMAGE_CAPTURE)

        } else {
            //permission already granted
//            val path = Environment.getExternalStorageDirectory().toString() +"/Eee.jpg/"

//            try {
//                val fileOutputStream = FileInputStream(path)
//                Log.i("encrypting", "image found")
//
//                val suc = Encdec().encryptFile(
//                    fileOutputStream,
//                    Environment.getExternalStorageDirectory().toString()
//                )
//                Log.i("encrypting", suc.toString())
//
//            }catch (e: Exception){
//                Log.i("encrypting", e.toString())
//            }



        }
    }
}


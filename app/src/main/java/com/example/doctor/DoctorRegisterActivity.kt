package com.example.doctor

//import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256

import ContractorHandlers.MainContractorHandler
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_doctor_register.*
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import utilities.EthFunctions
import java.io.*
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException


class DoctorRegisterActivity : AppCompatActivity() {

    private var RC_AUTHORIZE_DRIVE = 101
    private var ACCESS_DRIVE_SCOPE = Scope(Scopes.DRIVE_FILE)
    private var SCOPE_EMAIL = Scope(Scopes.EMAIL)
    private var SCOPE_APP_DATA = Scope(Scopes.DRIVE_APPFOLDER)
    private var googleDriveService: Drive? = null
    private var mDriveServiceHelper: DriveServiceHelper? = null


    private val challengeResponse = ChallengeResponse("123456789")


    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)


        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        //should be generated
        val myPublicKey = publickKey.text.toString()
        /***** should be a randomly generated random key **********/
        Log.i("did", filesDir.absolutePath)

        val web3j: Web3j = Web3j.build(HttpService("https://7548760fd8e9.jp.ngrok.io"))


        button.setOnClickListener {

//            checkForGooglePermissions()

            challengeResponse.challengeResponse()
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
                val intent = Intent(this, QRActivity::class.java)
                intent.putExtra("did", "123456789")
                startActivity(intent)
//            }
        }


        //upload did to drive

        //get the hash

        //upload the link to the block chain
    }

    private fun convertToJSON(didDocument: DIDDocument): String {
        val gson = Gson()
        val json: String = gson.toJson(didDocument)
        Log.i("did", "Convert to a JSON /n $json")
        return json
    }

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

    private fun isDIDDocumentInThePhone(): Boolean {
        val file = File(applicationContext.filesDir, "didDocument.json")
        Log.e("did", "Is there a DID document in the mobile: ${file.exists()}")
        return file.exists()
    }

    private fun hashMessage(msg: String): String? {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = messageDigest.digest(
            msg.toByteArray(StandardCharsets.UTF_8)
        )
        val sb = java.lang.StringBuilder()
        for (i in bytes.indices) {
            sb.append((bytes[i].toInt() and 0xff) + 0x100).substring(1)
        }
        Log.i("did", "hash is : ${sb.toString()}")
        return sb.toString()
    }

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

    private fun checkForGooglePermissions() {
        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(applicationContext),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_APP_DATA
            )
        ) {
            Toast.makeText(this, "ooops", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(
                this,
                "Permission to access Drive and Email has been granted",
                Toast.LENGTH_SHORT
            ).show()
            driveSetUp()
        }
    }

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

    fun createFolderInDrive() {
        Log.i("logininfo", "Creating a Folder...")
        mDriveServiceHelper!!.createFolder("DID Folder", null)
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
}


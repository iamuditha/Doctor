package com.example.doctor

//import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256

import ContractorHandlers.MainContractorHandler
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.doctor.challangeResponse.ChallengeResponse
import com.example.doctor.qr.QRActivity
import com.google.gson.Gson
import crypto.did.DID
import crypto.did.DIDDocument
import crypto.did.DIDDocumentGenerator
import kotlinx.android.synthetic.main.activity_doctor_register.*
import okhttp3.OkHttpClient
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.io.*
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException


class DoctorRegisterActivity : AppCompatActivity() {
    val challengeResponse = ChallengeResponse("asdfghjhgfdsaadfghjkhgfdsdfghgf")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)

        //should be generated
        val myPublicKey = publickKey.text.toString() /***** should be a randomly generated random key **********/
        Log.i("did", filesDir.absolutePath)

        val web3j : Web3j = Web3j.build(HttpService("https://mainnet.infura.io/v3/898d9e570ec143d6ada30bfdeab9572c"))


        button.setOnClickListener {

            challengeResponse.challengeResponse()
//            if (!isDIDDocumentInThePhone()) {
//                val did = generateDID()
//                val didDocument = did?.let { it1 -> generateDIDDocument(it1, myPublicKey) }
//                writeToFile(didDocument!!, "didDocument.json", this)
//
//                val hash = hashMessage(didDocument)
//
//                val credentials = createWallet("123", filesDir.absolutePath)
//                Log.i("did","credential done : ${credentials!!.ecKeyPair.privateKey}")
//
//                MainContractorHandler.getInstance()
//                    .getWrapperForMainContractor(web3j,getString(R.string.main_contractor_address),credentials)
//                    .registerDoctor("http://link of the did document",hash)
//
////                val thread = Thread(Runnable {
////                    try {
////                        Log.i("did",web3j.ethBlockNumber().send().blockNumber.toString())
////                    } catch (e: Exception) {
////                        e.printStackTrace()
////                    }
////                })
////
////                thread.start()
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
//                intent.putExtra("did", did)
//                startActivity(intent)
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
        val bytes = messageDigest.digest(msg.toByteArray(StandardCharsets.UTF_8)
        )
        val sb = java.lang.StringBuilder()
        for (i in bytes.indices) {
            sb.append((bytes[i].toInt() and  0xff)+ 0x100).substring(1)
        }
        Log.i("did","hash is : ${sb.toString()}")
        return sb.toString()
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class,
        CipherException::class,
        IOException::class
    )
    fun createWallet( walletPassword: String?, walletDirectory: String): Credentials? {
        val walletName = WalletUtils.generateBip39Wallet(walletPassword, File(walletDirectory))
        println("wallet location: $walletDirectory/$walletName")
        val credentials = WalletUtils.loadCredentials(walletPassword, "$walletDirectory/${walletName.filename}")
        val privateKey = credentials.ecKeyPair.privateKey.toString(16)
        val publicKey = credentials.ecKeyPair.publicKey.toString(16)
        val walletAddress = credentials.address
        println("privateKey:$privateKey")
        println("publicKey:$publicKey")
        println("Wallet Address: $walletAddress")
        return credentials
    }

}


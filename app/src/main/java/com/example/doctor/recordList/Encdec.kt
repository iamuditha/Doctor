package com.example.doctor.recordList

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.*
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class Encdec {

    fun encryptFile(inputStream: InputStream, path: String): Boolean {
        val fileOutputStream = FileOutputStream(
            Environment.getExternalStorageDirectory().toString() + "/Eee.crypt/"
        )
        var key: ByteArray = ("123456789").toByteArray(charset("UTF-8"))
        val messageDigest = MessageDigest.getInstance("SHA-1")
        key = messageDigest.digest(key)
        key = key.copyOf(16)
        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val cipherOutputStream = CipherOutputStream(fileOutputStream, cipher)
        var b: Int
        val d = ByteArray(8)
        while (inputStream.read(d).also { b = it } != -1) {
            cipherOutputStream.write(d, 0, b)
        }
        cipherOutputStream.flush()
        cipherOutputStream.close()
        inputStream.close()
        val encryptedFile = File(
            Environment.getExternalStorageDirectory().toString() + "/Eee.crypt/"
        )
        return encryptedFile.exists()
    }

    @Throws(
        IOException::class,
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class
    )
    fun decrypt(path: String?): Bitmap? {
        val fis = FileInputStream(path)
        var key: ByteArray = ("123456789").toByteArray(charset("UTF-8"))
        val sha = MessageDigest.getInstance("SHA-1")
        key = sha.digest(key)
        key = key.copyOf(16)
        val sks = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, sks)
        val cis = CipherInputStream(fis, cipher)
        val bitmap = BitmapFactory.decodeStream(cis)
        cis.close()
        return bitmap
    }
}

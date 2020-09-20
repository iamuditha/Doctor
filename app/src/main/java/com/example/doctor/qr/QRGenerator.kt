package com.example.doctor.qr

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class QRGenerator {
     fun generateQRCode(message: String, imageView: ImageView) {
        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(message, BarcodeFormat.QR_CODE, 512, 512)
            val width = 512
            val height = 512
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            imageView.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}
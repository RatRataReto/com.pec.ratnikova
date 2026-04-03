package com.pec.ratnikova.ui.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

object QRCodeUtils {
    fun generateQRCode(text: String, size: Int): Bitmap {
        val qrCodeWriter = QRCodeWriter()
        val hints = mapOf(EncodeHintType.MARGIN to 0) // Remove white margins completely
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size, hints)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bitmap
    }
}

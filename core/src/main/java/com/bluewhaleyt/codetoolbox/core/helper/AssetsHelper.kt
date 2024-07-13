package com.bluewhaleyt.codetoolbox.core.helper

import android.content.Context
import com.bluewhaleyt.codetoolbox.core.CodeToolboxInternalApi
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

@CodeToolboxInternalApi
class AssetsHelper(
    private val context: Context
) {

    fun extractAsset(assetName: String, targetFile: File) {
        if (targetFile.exists() && assetNeedsUpdate(assetName, targetFile)) {
            targetFile.delete()
        }
        try {
            context.assets.open(assetName).use { inputStream ->
                targetFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("Failed to extract asset: $assetName.")
        }
    }

    private fun assetNeedsUpdate(assetName: String, targetFile: File): Boolean {
        val assetInputStream = context.assets.open(assetName)
        FileInputStream(targetFile).use { targetFileInputStream ->
            val assetChecksum = calculateChecksum(assetInputStream)
            val targetFileChecksum = calculateChecksum(targetFileInputStream)
            return assetChecksum != targetFileChecksum
        }
    }

    private fun calculateChecksum(inputStream: InputStream): String {
        val md = MessageDigest.getInstance("SHA-256")
        val buffer = ByteArray(8192)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            md.update(buffer, 0, bytesRead)
        }
        val digest = md.digest()
        return BigInteger(1, digest).toString(16)
    }

}
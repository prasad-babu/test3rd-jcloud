package com.cloud.storage.samples

import com.google.common.io.ByteSource
import com.google.common.io.Files
import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStoreContext
import java.io.File


class AzureUpload {
    fun upload() {
        val storageAccountName = "prasadu"
        val storageAccountKey = "secret key"
        val blobName = "test_file.txt"
        val context = ContextBuilder.newBuilder("azureblob")
            .credentials(storageAccountName, storageAccountKey)
            .buildView(BlobStoreContext::class.java)
        val blobStore = context.blobStore
        val input = File("test_file.txt")
        input.delete()
        java.nio.file.Files.write(input.toPath(), "Test Data".toByteArray())
        val payload: ByteSource = Files.asByteSource(input)
        val blob = context.blobStore.blobBuilder(blobName)
            .payload(payload) // or InputStream
            .contentLength(payload.size())
            .build()
        blobStore.putBlob("bucket1", blob)
    }
}

fun main() {
    try {
        AzureUpload().upload()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
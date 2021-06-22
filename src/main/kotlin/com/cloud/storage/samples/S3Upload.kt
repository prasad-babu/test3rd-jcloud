package com.cloud.storage.samples

import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStoreContext
import org.jclouds.blobstore.options.PutOptions.Builder.multipart
import java.io.File
import java.nio.file.Files
import javax.ws.rs.core.MediaType


class S3Upload {
     fun upload() {
         val context = ContextBuilder.newBuilder("aws-s3")
             .credentials("lkdad", "assdgon0d")
             .buildView(BlobStoreContext::class.java)
         val input = File("test_file.txt")
         input.delete()
         java.nio.file.Files.write(input.toPath(), "Test Data".toByteArray())
         val blobStore = context.blobStore
         val blob = blobStore.blobBuilder("docs/" + input.name).payload(Files.newInputStream(input.toPath()))
             .contentType(MediaType.APPLICATION_OCTET_STREAM).contentDisposition(input.name).contentLength(input.length()).build()
         val eTag: String = blobStore.putBlob("23232", blob)

         println("Successfully uploaded eTag($eTag)")
         context.close()
     }
}

fun main() {

    try {
        val uploader = S3Upload().upload()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
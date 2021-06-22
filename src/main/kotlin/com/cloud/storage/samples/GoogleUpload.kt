package com.cloud.storage.samples

import com.google.common.io.ByteSource
import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStoreContext
import org.jclouds.domain.Credentials
import org.jclouds.googlecloud.GoogleCredentialsFromJson
import java.io.IOException


class GoogleUpload {

    fun upload() {
        val credentials = getCredentialFromJsonKeyFile()!!
        val context: BlobStoreContext = ContextBuilder.newBuilder("google-cloud-storage")
            .credentials(credentials.identity, credentials.credential)
            .buildView(BlobStoreContext::class.java)
        val blobStore = context.blobStore
        val blobName = "docs/test.txt"
        val payload = ByteSource.wrap("testdata".toByteArray(Charsets.UTF_8))
        val blob = blobStore.blobBuilder(blobName)
            .payload(payload)
            .contentLength(payload.size())
            .build()
        blobStore.putBlob("jcloud1", blob)

    }

    private fun getCredentialFromJsonKeyFile(): Credentials? {
        return try {
            val fileContents: String = """
                {
                  "type": "service_account",
                  "project_id": "jcloud-31ds007",
                  "private_key_id": "skdndndmfndsfds",
                  "private_key": "-----BEGIN PRIVATE KEY-----\nM\nrwevy01SViekeFovDRh5WSNkRB9ycpQ72WCzocvh9PuPmzCA0s7k/uhAkJZK7+K+\n3/EsZ3tXSb/pQQUitcZMyYm45kmvLSMkDWRXuKOEAa19pvD5Cr6bo5+tSWBx6XAG\nwRowW/cEY01/jk92InJ7IrOEH3HTDWpj92DG/XDvedJQS1jlKosfSBHaGvcwOIhl\n/phtWmDtUFcGIF7mZC4/hpttIMGOusLEoxvzDQpXoGSeyMdJgoJ+pyUb3LpSL1+r\nsUIXe97Yu+D6sU9tyKkreSIesBkET1x19kzzYkVziDxp1D/FRC3zdjyTLXNId5RQ\nEunIbOALAgMBAAECggEANngpy4AhaIJ+dMmzJkiT06EPBb88GE1Ccj/5SvE9+fF7\nYzi7d5GRu1ncYGW1nLacInK31RJa58yoRyjQ9V0NZBksRzENaqW4cLkas7xyEEXq\nkzV3bBvRdaFovZXCphbcKS7USz2WtnT6XHKOZkIpzxXl8D7FmcTDINVQm5Nvatqv\no8hIXFnX25u0drv0uXnsXJC+YCsonX2hO4F2LvpAf66HRel0oKTLk+4HUdYhAbEe\naySSFywD4cVsOvB+P+KrIealLYNnBMU8n/ZCeH7r4e7G+e44SxEWl9hhchYrZ780\njdzAN05WsQ/bsqOmObMf+Cfphn2VLv00RgMzfw9dDQKBgQDlsNMAjVLE4Dmc50YG\nAgYffI+XAaeioJMIU+471UlLmtaWG/6W8BW3tNO+Qy+TPWsDnXl6a1CevK6RXAfJ\nJ0Euepr1M9xREp2Ncs1dBTnzJ7wFOePiGIYkSoQE1MBKYvFNPVao8MkD2uiBsMS5\ndAlQtsfZElRLKAVhBTZ4iCudpQKBgQDE+jhUYSgtAfEtfVhLxySgE0doXH1vNO1/\n53HF9JoZHnKX3RMalyMINb+azAu/gMF9bxOC81uTdkY3aGMZixdIAZD3rNS/gb0J\nBEMUN1S30vU9E2Q1lPffTcDSwjAsWDEEkVH3Pa9kSwgkfts1DvvW7ZU9ok6LBc5r\nwNbb+il37wKBgQDAmZc0/UwMPiDRSrTS0Nulh2M4MnEI0zC3aPl5Po2pUfriADNp\nCYSMeJqdDeVUx8l2TJPSbS47DOgsh0kYCUyE1tYdChGSAdQPCdbeS0WeeREWKMbH\njdzdjYZKLsPXHEROikKigtVr4CHargknoP82qS3G/bGlG7f29NIKG9TG6QKBgFVR\nE8jodCBacP4FBpN75A85inpXZTUftabqtkrJkV1D5NqGNNoVkGTj8uO3cXc265fK\n3qafhZCvb+Zb3mCX8DOOCh5cy6FPLnOTVwKQnK8qPnJ/za2volUkNlqTJY5vfHu/\n/cKbAzbrkNFN6OBoZwhGy5dwj5iDoU7mramGByQdAoGAAyvVo0MWf5lpyIArKB2x\nQm62D0e77yTajoKUYP1U9y9kzVP0HLOplKsdMZduCqGn8SmUZ+hJH40o0EtVp3aY\nQeHHYQcl+x7ceiBAmCA0ReCU1sTk+t9EVv/tkR4HObwjfFhKyOpXgGLRsjagmwgd\nRXbPp8EaUhubk1SnzskRWf0=\n-----END PRIVATE KEY-----\n",
                  "client_email": "jcloud@com",
                  "client_id": "11",
                  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                  "token_uri": "https://oauth2.googleapis.com/token",
                  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/"
                }
            """.trimIndent()
            val credentialSupplier = GoogleCredentialsFromJson(fileContents)
            credentialSupplier.get()
        } catch (e: IOException) {
            System.err.println("Exception reading private key from '%s': ")
            e.printStackTrace()
            System.exit(1)
            null
        }
    }
}

fun main() {
    GoogleUpload().upload()
}
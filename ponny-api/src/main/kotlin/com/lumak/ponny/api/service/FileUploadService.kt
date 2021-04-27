package com.lumak.ponny.api.service

import com.lumak.ponny.api.client.IPFSClient
import com.lumak.ponny.api.rest.dto.response.KIP17MetaData
import com.lumak.ponny.api.toJson
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import kotlin.random.Random

@Service
class FileUploadService(
    private val ipfsClient: IPFSClient
) {
    private val ipfs = ipfsClient.ipfs
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun upload(writer: String, title: String, description: String, uploadFile: MultipartFile): String {

        val file = convertFile(uploadFile)

        val hash = fileUpload(file)
        val url = "https://ipfs.io/ipfs/$hash?filename=${file.name}"
        file.delete()
        val jsonData = KIP17MetaData(
            writer = writer,
            title = title,
            description = description,
            imageUrl = url,
            createdAt = Instant.now().epochSecond
        )

        return jsonUpload(jsonData.toJson())
    }

    fun jsonUpload(jsonString: String): String {

        val fileName = "${Random.nextInt(10, 100)}${Instant.now().epochSecond}"
        val file = File("${fileName}.json")
        file.writeText(jsonString)

        val hash = fileUpload(file)
        val url = "https://ipfs.io/ipfs/$hash?filename=${file.name}"
        logger.info("JSON FILE : ${url}\n${jsonString}")
        file.delete()
        return url
    }

    private fun fileUpload(file: File): String {
        return ipfs.add(file.path)
    }

    private fun convertFile(uploadFile: MultipartFile): File {
        val file = File(uploadFile.originalFilename)
        file.createNewFile()
        val fos = FileOutputStream(file)
        fos.write(uploadFile.bytes)
        fos.close()
        return file
    }
}
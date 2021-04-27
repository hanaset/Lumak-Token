package com.lumak.ponny.api.service

import com.lumak.ponny.api.client.IPFSClient
import com.lumak.ponny.api.rest.dto.response.FileUploadResponse
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream

@Service
class FileUploadService(
    private val ipfsClient: IPFSClient
) {
    private val ipfs = ipfsClient.ipfs

    fun upload(writer: String, title: String, description: String, uploadFile: MultipartFile): FileUploadResponse {

        val file = convertFile(uploadFile)

        val hash = fileUpload(file)
        val url = "https://ipfs.io/ipfs/$hash?filename=${file.name}"

        return FileUploadResponse(
            writer
            = writer,
            title = title,
            description = description,
            imageUrl = url
        )
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
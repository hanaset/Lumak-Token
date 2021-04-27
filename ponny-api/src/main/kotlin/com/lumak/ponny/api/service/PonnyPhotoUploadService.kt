package com.lumak.ponny.api.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse

@Service
class PonnyPhotoUploadService(
    private val kasKIP17Service: KasKIP17Service,
    private val fileUploadService: FileUploadService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun photoUpload(uploadFile: MultipartFile, name: String, title: String, description: String, to: String, symbol: String): Kip17TransactionStatusResponse {
        val res = fileUploadService.upload(writer = name, title = title, description = description, uploadFile = uploadFile)
        logger.info("$symbol Token Mint =>\n${res}")
        return kasKIP17Service.mintToken(symbol, to, res)
    }
}
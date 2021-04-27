package com.lumak.ponny.api.rest

import com.lumak.ponny.api.rest.support.RestSupport
import com.lumak.ponny.api.service.PonnyPhotoUploadService
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Api(tags = ["Ponny NFT Token API"])
@RestController
@RequestMapping("/v1/ponny")
class PonnyNFTController(
    private val ponnyPhotoUploadService: PonnyPhotoUploadService
) : RestSupport() {

    @PostMapping("/photo")
    fun photoUpload(
        @RequestParam("writer") writer: String,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("to") to: String,
        @RequestParam("symbol", defaultValue = "PNY") symbol: String
    ): ResponseEntity<*> {
        return response(ponnyPhotoUploadService.photoUpload(uploadFile = file, name = writer, title = title, description = description, to = to, symbol = symbol))
    }
}
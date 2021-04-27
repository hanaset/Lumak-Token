package com.lumak.ponny.api.rest

import com.lumak.ponny.api.rest.support.RestSupport
import com.lumak.ponny.api.service.FileUploadService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Api(tags = ["파일 업로드 API"])
@RestController
@RequestMapping("/upload")
class UploadController(
    private val fileUploadService: FileUploadService
) : RestSupport() {

    @ApiOperation(value = "사진 업로드")
    @PostMapping("/photo")
    fun photoUpload(
        @RequestParam("writer") writer: String,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<*> {
        return response(fileUploadService.upload(writer, title, description, file))
    }
}
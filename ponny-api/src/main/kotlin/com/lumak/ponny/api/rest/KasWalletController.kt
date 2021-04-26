package com.lumak.ponny.api.rest

import com.lumak.ponny.api.rest.support.RestSupport
import com.lumak.ponny.api.service.KasWalletApiService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["KAS Wallet API"])
@RestController
@RequestMapping("/kas/wallet")
class KasWalletController(
    private val kasWalletApiService: KasWalletApiService
) : RestSupport() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ApiOperation(value = "지갑 생성")
    @PostMapping("/create")
    fun createWallet(): ResponseEntity<*> {
        return response(kasWalletApiService.createWallet())
    }

    @ApiOperation(value = "지갑 조회")
    @GetMapping("/{address}")
    fun getWallet(@PathVariable("address") address: String): ResponseEntity<*> {
        return response(kasWalletApiService.getWallet(address))
    }

    @ApiOperation(value = "보유 지갑 조회")
    @GetMapping("/{publicKey}/account")
    fun getWalletByPublicKey(@PathVariable("publicKey") publicKey: String): ResponseEntity<*> {
        return response(kasWalletApiService.getWalletByPublicKey(publicKey))
    }

//    @PostMapping("/feepayer/create")
//    fun createFeePayer(): ResponseEntity<*> {
//        return response(kasWalletApiService)
//    }
}
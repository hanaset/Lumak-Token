package com.lumak.ponny.api.rest

import com.lumak.ponny.api.rest.support.RestSupport
import com.lumak.ponny.api.service.KasWalletApiService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kas/wallet")
class KasWalletController(
    private val kasWalletApiService: KasWalletApiService
) : RestSupport() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/create")
    fun createWallet(): ResponseEntity<*> {
        return response(kasWalletApiService.createWallet())
    }

    @GetMapping("/{address}")
    fun getWallet(@PathVariable("address") address: String): ResponseEntity<*> {
        return response(kasWalletApiService.getWallet(address))
    }

    @GetMapping("/{publicKey}/account")
    fun getWalletByPublicKey(@PathVariable("publicKey") publicKey: String): ResponseEntity<*> {
        return response(kasWalletApiService.getWalletByPublicKey(publicKey))
    }

    @PostMapping("/feepayer/create")
    fun createFeePayer(): ResponseEntity<*> {
        return response(kasWalletApiService)
    }


}
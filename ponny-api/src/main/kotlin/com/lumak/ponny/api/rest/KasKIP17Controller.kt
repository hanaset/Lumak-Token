package com.lumak.ponny.api.rest

import com.lumak.ponny.api.rest.dto.request.KasKIP17DeployRequest
import com.lumak.ponny.api.rest.support.RestSupport
import com.lumak.ponny.api.service.KasKIP17Service
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kas/kip17")
class KasKIP17Controller(
    private val kasKIP17Service: KasKIP17Service
) : RestSupport() {

    @PostMapping("/deploy")
    fun deployKIP17(@RequestBody request: KasKIP17DeployRequest): ResponseEntity<*> {
        return response(kasKIP17Service.deployKIP17(request.name, request.symbol, request.alias))
    }

    @GetMapping("/contract/{address}")
    fun getContract(@PathVariable("address") address: String): ResponseEntity<*> {
        return response(kasKIP17Service.getContract(address))
    }

    @PostMapping("/contract/token")
    fun token(to: String, uri: String): ResponseEntity<*> {
        return response(kasKIP17Service)
    }
}
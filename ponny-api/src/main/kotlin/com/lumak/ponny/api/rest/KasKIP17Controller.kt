package com.lumak.ponny.api.rest

import com.lumak.ponny.api.rest.dto.request.KasKIP17BurnRequest
import com.lumak.ponny.api.rest.dto.request.KasKIP17DeployRequest
import com.lumak.ponny.api.rest.dto.request.KasKIP17MintRequest
import com.lumak.ponny.api.rest.dto.request.KasKIP17TransferRequest
import com.lumak.ponny.api.rest.support.RestSupport
import com.lumak.ponny.api.service.KasKIP17Service
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["KAS API"])
@RestController
@RequestMapping("/kas/kip17")
class KasKIP17Controller(
    private val kasKIP17Service: KasKIP17Service
) : RestSupport() {

    @ApiOperation(value = "컨트랙트 배포")
    @PostMapping("/deploy")
    fun deployKIP17(@RequestBody request: KasKIP17DeployRequest): ResponseEntity<*> {
        return response(kasKIP17Service.deployKIP17(request.name, request.symbol, request.alias))
    }

    @ApiOperation("컨트랙트 정보 조회")
    @GetMapping("/contract/{symbol}")
    fun getContract(@PathVariable("symbol") symbol: String): ResponseEntity<*> {
        return response(kasKIP17Service.getContract(symbol))
    }

    @ApiOperation("토큰 발행")
    @PostMapping("/token")
    fun minToken(@RequestBody request: KasKIP17MintRequest): ResponseEntity<*> {
        return response(kasKIP17Service.mintToken(symbol = request.symbol, to = request.to, uri = request.uri))
    }

    @ApiOperation("토큰 정보 조회")
    @GetMapping("/token")
    fun getToken(
        @RequestParam("symbol") symbol: String,
        @RequestParam("tokenId") tokenId: String
    ): ResponseEntity<*> {
        return response(kasKIP17Service.getToken(symbol, tokenId))
    }

    @ApiOperation("토큰 전송")
    @PostMapping("/token/{symbol}/transfer")
    fun transfer(
        @PathVariable("symbol") symbol: String,
        @RequestBody request: KasKIP17TransferRequest
    ): ResponseEntity<*> {
        return response(
            kasKIP17Service.transfer(
                symbol = symbol,
                sender = request.sender,
                owner = request.owner,
                to = request.to,
                tokenId = request.tokenId
            )
        )
    }

    @ApiOperation("토큰 소각(삭제)")
    @DeleteMapping("/token/{symbol}")
    fun burnToken(
        @PathVariable("symbol") symbol: String,
        @RequestBody request: KasKIP17BurnRequest
    ): ResponseEntity<*> {
        return response(kasKIP17Service.burnToken(symbol = symbol, tokenId = request.tokenId, from = request.from))
    }

    @ApiOperation("특정 소유자의 토큰 목록 조회")
    @GetMapping("/token/{symbol}/owner")
    fun getOwnerToken(
        @PathVariable("symbol") symbol: String,
        @RequestParam("owner") owner: String
    ): ResponseEntity<*> {
        return response(kasKIP17Service.getOwnerToken(symbol, owner))
    }

    @ApiOperation("특정 토큰의 소유권 변경 기록 조회")
    @GetMapping("/token/{symbol}/history")
    fun getTokenHistory(
        @PathVariable("symbol") symbol: String,
        @RequestParam("tokenId") tokenId: String
    ): ResponseEntity<*> {
        return response(kasKIP17Service.getTokenHistory(symbol, tokenId))
    }
}
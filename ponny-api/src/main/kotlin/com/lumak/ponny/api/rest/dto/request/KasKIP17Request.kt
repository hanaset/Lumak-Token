package com.lumak.ponny.api.rest.dto.request

data class KasKIP17DeployRequest(
    val name: String,
    val symbol: String,
    val alias: String
)

data class KasKIP17MintRequest(
    val symbol: String,
    val to: String,
    val uri: String
)

data class KasKIP17TransferRequest(
    val tokenId: String,
    val sender: String,
    val owner: String,
    val to: String
)

data class KasKIP17BurnRequest(
    val tokenId: String,
    val from: String // 소유권이 잇는 유저의 address
)
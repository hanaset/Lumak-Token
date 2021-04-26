package com.lumak.ponny.api.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import xyz.groundx.caver_ext_kas.CaverExtKAS
import javax.annotation.PostConstruct

@Component
class KasApiClient(
    @Value("\${klaytn.api.access-key}") private val accessKey: String,
    @Value("\${klaytn.api.secret-key}") private val secretKey: String,
    @Value("\${klaytn.api.authorization}") private val authorization: String,
    @Value("\${klaytn.api.chain-id}") private val chainId: Int
) {

    val caver: CaverExtKAS = CaverExtKAS()

    @PostConstruct
    fun init() {
        // KAS API 초기화
        caver.initKASAPI(chainId, accessKey, secretKey)

        // Node API 초기화
        caver.initNodeAPI(chainId, accessKey, secretKey)

        // TokenHistory API 초기화
        caver.initTokenHistoryAPI(chainId, accessKey, secretKey)

        // Anchor API 초기화
        caver.initAnchorAPI(chainId, accessKey, secretKey)

        // KIP-17 API 초기화
        caver.initKIP17API(chainId, accessKey, secretKey)
    }
}
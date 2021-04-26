package com.lumak.ponny.api.service

import com.lumak.ponny.api.client.KasApiClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17ContractInfoResponse
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse

@Service
class KasKIP17Service(
    private val kasApiClient: KasApiClient
) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val kip17 = kasApiClient.caver.kas.kip17

    fun deployKIP17(name: String, symbol:String, alias: String): Kip17TransactionStatusResponse {
        return kip17.deploy(name, symbol, alias)
    }

    fun getContract(address: String): Kip17ContractInfoResponse {
        return kip17.getContract(address)
    }

    fun token(to: String, uri: String) {

    }
}
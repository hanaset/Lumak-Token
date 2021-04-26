package com.lumak.ponny.api.service

import com.lumak.ponny.api.client.KasApiClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Account
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.AccountsByPubkey

@Service
class KasWalletApiService(
    private val kasApiClient: KasApiClient
) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val wallet = kasApiClient.caver.kas.wallet

    fun createWallet(): Account {
        return wallet.createAccount()
    }

    fun getWallet(address: String): Account {
        return wallet.getAccount(address)
    }

    fun getWalletByPublicKey(publicKey: String): AccountsByPubkey {
        return wallet.getAccountListByPublicKey(publicKey)
    }
}
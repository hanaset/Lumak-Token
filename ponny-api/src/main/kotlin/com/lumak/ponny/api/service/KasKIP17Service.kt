package com.lumak.ponny.api.service

import com.lumak.ponny.api.client.KasApiClient
import com.lumak.ponny.api.exception.ErrorCode
import com.lumak.ponny.api.exception.PonnyException
import com.lumak.ponny.api.exception.PonnyNotFoundTokenException
import com.lumak.ponny.common.entity.TokenEntity
import com.lumak.ponny.common.repository.TokenRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.GetKip17TokenHistoryResponse
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.GetKip17TokenResponse
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.GetOwnerKip17TokensResponse
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17ContractInfoResponse
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.kip17.model.Kip17TransactionStatusResponse
import java.time.Instant
import kotlin.random.Random

@Service
class KasKIP17Service(
    private val kasApiClient: KasApiClient,
    private val tokenRepository: TokenRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val kip17 = kasApiClient.caver.kas.kip17

    // 컨트랙트 배포
    fun deployKIP17(name: String, symbol:String, alias: String): Kip17TransactionStatusResponse {
        try {
            val res = kip17.deploy(name, symbol, alias)

            val tokenEntity = TokenEntity(
                name = name,
                symbol = symbol,
                address = res.transactionHash,
                alias = alias,
                status = res.status
            )

            tokenRepository.save(tokenEntity)
            logger.info("KIP17 Token Deploy :: ${name}/${symbol} :: ${res.transactionHash}")
            return res
        } catch (ex: Exception) {
            throw PonnyException(ErrorCode.INTERNAL_SERVER_ERROR, "KasKIP17Service :: deployKIP17() Exception :: ${ex.message}")
        }
    }

    // 컨트랙트 정보 조회
    fun getContract(symbol: String): Kip17ContractInfoResponse {
        return kip17.getContract(getAlias(symbol))
    }

    // 컨트랙트 (토큰) 발행
    fun mintToken(symbol: String, to: String, uri: String): Kip17TransactionStatusResponse {
        return kip17.mint(getAlias(symbol), to, generateTokenId(symbol), uri)
    }

    // 토큰 정보 조회
    fun getToken(symbol: String, tokenId: String): GetKip17TokenResponse {
        return kip17.getToken(getAlias(symbol), tokenId)
    }

    // 토큰 전송
    fun transfer(symbol: String, tokenId: String, sender: String, owner: String, to: String): Kip17TransactionStatusResponse {
        val res = kip17.transfer(getAlias(symbol), sender, owner, to, tokenId)
        logger.info("Token Transfer :: $symbol :: $sender => $to")
        return res
    }

    // 토큰 태우기 (삭제)
    fun burnToken(symbol: String, tokenId: String, from: String): Kip17TransactionStatusResponse {
        val res = kip17.burn(getAlias(symbol), from, tokenId)
        logger.info("Token Burn :: $symbol :: $tokenId")
        return res
    }

    // 소유자의 토큰 조회
    fun getOwnerToken(symbol: String, owner: String): GetOwnerKip17TokensResponse {
        return kip17.getTokenListByOwner(getAlias(symbol), owner)
    }

    // 토큰의 소유자 변경 히스토리 조회
    fun getTokenHistory(symbol: String, tokenId: String): GetKip17TokenHistoryResponse {
        return kip17.getTransferHistory(getAlias(symbol), tokenId)
    }

    private fun generateTokenId(symbol: String): String {
        val random = Random.nextInt(10, 100)
        val timestamp = Instant.now().epochSecond
        val id = "0x" + "${random}${timestamp}".toLong().toString(16)

        logger.info("KasKIPService :: generateTokenId() :: $id")
        return id
    }

    private fun getAlias(symbol: String): String {
        val tokenEntity = tokenRepository.findBySymbol(symbol) ?: throw PonnyNotFoundTokenException()
        return tokenEntity.alias
    }
}
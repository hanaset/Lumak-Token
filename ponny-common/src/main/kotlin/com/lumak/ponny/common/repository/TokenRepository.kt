package com.lumak.ponny.common.repository

import com.lumak.ponny.common.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository: JpaRepository<TokenEntity, Long> {

    fun findByAddress(address: String): TokenEntity?

    fun findByAlias(alias: String): TokenEntity?

    fun findByName(name: String): TokenEntity?

    fun findBySymbol(symbol: String): TokenEntity?
}
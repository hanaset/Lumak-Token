package com.lumak.ponny.api.client

import com.klaytn.caver.ipfs.IPFS
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class IPFSClient(
    @Value("\${ipfs.host}") private val host: String,
    @Value("\${ipfs.port}") private val port: Int
) {

    lateinit var ipfs: IPFS

    @PostConstruct
    fun init() {
        ipfs = IPFS(host, port, false)
    }
}
package com.lumak.ponny.common.entity

import com.lumak.ponny.common.converter.LocalDateTimeConverter
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "token")
class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    val name: String,

    val symbol: String,

    val address: String,

    val alias: String,

    val status: String
) {
    @Column(name = "created_at")
    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter::class)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Convert(converter = LocalDateTimeConverter::class)
    val updatedAt: LocalDateTime = LocalDateTime.now()
}
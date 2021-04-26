package com.lumak.ponny.common.converter

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateTimeConverter : AttributeConverter<LocalDateTime, Timestamp> {
    override fun convertToDatabaseColumn(entityValue: LocalDateTime): Timestamp {
        return Timestamp.valueOf(entityValue)
    }

    override fun convertToEntityAttribute(databaseValue: Timestamp): LocalDateTime {
        return databaseValue.toLocalDateTime()
    }
}
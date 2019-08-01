package com.kentrino.db

import org.joda.time.DateTime
import java.math.BigDecimal

data class UfoSighting(
        val id: Long?,
        val date: DateTime,
        val city: String,
        val state: String,
        val country: String,
        val shape: String,
        val duration: BigDecimal,
        val comments: String,
        val latitude: BigDecimal,
        val longitude: BigDecimal
)

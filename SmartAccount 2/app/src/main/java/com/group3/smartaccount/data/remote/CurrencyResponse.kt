package com.group3.smartaccount.data.remote
data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

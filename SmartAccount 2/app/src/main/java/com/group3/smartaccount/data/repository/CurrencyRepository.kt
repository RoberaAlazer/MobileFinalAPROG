package com.group3.smartaccount.data.repository
import com.group3.smartaccount.data.remote.RetrofitClient

class CurrencyRepository {

    private val api = RetrofitClient.api

    suspend fun getCadToUsd(): Double? {
        return api.getRates().rates["USD"]
    }

    suspend fun getCadToEur(): Double? {
        return api.getRates().rates["EUR"]
    }

    suspend fun getCadToGbp(): Double? {
        return api.getRates().rates["GBP"]
    }
}

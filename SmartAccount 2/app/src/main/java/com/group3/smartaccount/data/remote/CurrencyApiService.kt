package com.group3.smartaccount.data.remote
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("v4/latest/CAD")
    suspend fun getRates(): CurrencyResponse
}
